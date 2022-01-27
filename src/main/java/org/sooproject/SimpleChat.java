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
	// �������� ��Ʈ�� �ʱ�ȭ�Ѵ�.
	private static ServerSocket serverSocket = null;
	// Ŭ���̾�Ʈ ����Ʈ�� �ʱ�ȭ�Ѵ�.
	public static List<MultiThreadServerChannel> clientList = null;

	// �ʱ�ȭ �Լ�
	public static void initialized() {
		// �����͸� ���� �ѷ��� ���� ��ü ����Ʈ�� �����Ѵ�.
		clientList = new ArrayList<MultiThreadServerChannel>();

	}

	public static void main(String[] args) {
		initialized();
		if (args.length > 0) {
			if (args[0].equals("server")) {
				System.out.println("=���� ��� ����=");
				try {
					serverSocket = new ServerSocket(3348);

				} catch (IOException e) {

					e.printStackTrace();

				}
				while (true) {
					try {
						Socket clientSocket = serverSocket.accept();
						System.out.println("=������ ���� �Ǿ����ϴ�.=");

						MultiThreadServerChannel msc = new MultiThreadServerChannel(clientSocket);
						clientList.add(msc);
						System.out.printf("[���� �ο�: %d��]\n", clientList.size());
						Thread serverChannelThread = new Thread(msc);
						serverChannelThread.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} else if (args[0].equals("client")) {
				System.out.println("=Ŭ���̾�Ʈ ��� ����=");
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
