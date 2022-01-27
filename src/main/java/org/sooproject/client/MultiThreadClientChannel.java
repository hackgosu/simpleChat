package org.sooproject.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadClientChannel implements Runnable {
	//������ ������ ���� ��ü
	public Socket serverConn = null;
	//�⺻ ������
	private String defaultIP = "127.0.0.1";
	//�⺻ ��Ʈ
	private int defaultPort = 3348;
	private boolean isErr = false;
	//��ü ������ �ʱ�ȭ
	public MultiThreadClientChannel(String defaultIP,int defaultPort) {
		
		try {
			this.serverConn = new Socket(defaultIP,defaultPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("�ش� ȣ��Ʈ�� ã�� �� �����ϴ�.");
			//� �������� ���� ó���Ͽ� Ȯ��
			e.printStackTrace();
			isErr = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("�������� ��Ʈ���� �ƴմϴ�.");
			//� �������� ���� ó���Ͽ� Ȯ��
			e.printStackTrace();
			isErr = true;
		}
		//���� �����Ǹ� �ʱ�ȭ
		this.defaultIP = defaultIP;
		//���� ��Ʈ�� �ʱ�ȭ
		this.defaultPort = defaultPort;
		
	}
	//�ݺ� �۾��� �� �κ��� �ش� �κп� �߰�
	public void run() {
		if(this.serverConn.isClosed())
			return;
		while(true) {
			if(isErr) return;
			try {
				DataInputStream dis = new DataInputStream(serverConn.getInputStream());
				if(!dis.readUTF().equals("")) {
					System.out.println(dis.readUTF());
				}
			} catch (IOException e) {
				isErr = true;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
}
