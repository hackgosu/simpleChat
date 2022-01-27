package org.sooproject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.sooproject.client.MultiThreadClientChannel;
import org.sooproject.server.MultiThreadServerChannel;

public class SimpleChat {
	//�������� ��Ʈ�� �ʱ�ȭ�Ѵ�.
	private static ServerSocket serverSocket = null;
	//Ŭ���̾�Ʈ ����Ʈ�� �ʱ�ȭ�Ѵ�.
	public static List<MultiThreadServerChannel> clientList = null;
	
	//�ʱ�ȭ �Լ�
	public static void initialized() {
		//�����͸� ���� �ѷ��� ���� ��ü ����Ʈ�� �����Ѵ�.
		clientList = new ArrayList<MultiThreadServerChannel>();
		
	}
	
	public static void main(String[] args) {
		initialized();
		if (args.length > 0) {
			if (args[0].equals("server")) {
				try {
					serverSocket = new ServerSocket(3348);

				} catch (IOException e) {

					e.printStackTrace();

				}
				while (true) {
					try {
						Socket clientSocket = serverSocket.accept();
						MultiThreadServerChannel msc = new MultiThreadServerChannel(clientSocket);
						clientList.add(msc);
						Thread serverChannelThread = new Thread(msc);
						serverChannelThread.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} else if (args[0].equals("client")) {
				MultiThreadClientChannel mtc = new MultiThreadClientChannel("127.0.0.1", 3348);
				Thread clientChannelThread = new Thread(mtc);
				clientChannelThread.start();
				while(true) {
					
					try {
						Scanner sc = new Scanner(System.in);
						String line = sc.next();
						
						DataOutputStream dos = new DataOutputStream(mtc.serverConn.getOutputStream());
						dos.writeUTF(line);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		}
	}
	 
}
