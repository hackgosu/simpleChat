package org.sooproject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.sooproject.client.MultiThreadClientChannel;
import org.sooproject.server.MultiThreadServerChannel;

public class SimpleChat {
	// 서버소켓 포트를 초기화한다.
	private static ServerSocket serverSocket = null;
	// 클라이언트 리스트를 초기화한다.
	public static List<MultiThreadServerChannel> clientList = null;

	// 초기화 함수
	public static void initialized() {
		// 데이터를 각각 뿌려줄 서버 객체 리스트를 생성한다.
		clientList = new ArrayList<MultiThreadServerChannel>();

	}

	public static void main(String[] args) {
		initialized();
		if (args.length > 0) {
			if (args[0].equals("server")) {
				System.out.println("=서버 모드 시작=");
				try {
					serverSocket = new ServerSocket(3348);

				} catch (IOException e) {

					e.printStackTrace();

				}
				while (true) {
					try {
						Socket clientSocket = serverSocket.accept();
						System.out.println("=연결이 시작 되었습니다.=");

						MultiThreadServerChannel msc = new MultiThreadServerChannel(clientSocket);
						clientList.add(msc);
						System.out.printf("[현재 인원: %d명]\n", clientList.size());
						Thread serverChannelThread = new Thread(msc);
						serverChannelThread.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} else if (args[0].equals("client")) {
				System.out.println("=클라이언트 모드 시작=");
				MultiThreadClientChannel mtc = new MultiThreadClientChannel("127.0.0.1", 3348);
				Thread clientChannelThread = new Thread(mtc);
				clientChannelThread.start();
				Scanner sc = new Scanner(System.in);
				while (true) {

					try {
						
						String line = sc.nextLine();
						
						if (line != null) {
							if (!line.equals("")) {
								
								DataOutputStream dos = new DataOutputStream(mtc.serverConn.getOutputStream());
								
								dos.writeUTF(line);
								dos.flush();

							}

						}
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}
	}

}
