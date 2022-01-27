package org.sooproject.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Random;

import org.sooproject.SimpleChat;

public class MultiThreadServerChannel implements Runnable {

	public Socket socket = null;
	private String nickName = "";
	private boolean isClosed = true;

	public MultiThreadServerChannel(Socket socket) {
		this.socket = socket;
		Random rndObject = new Random();
		this.nickName = String.format("�մ�%x",rndObject.nextInt(10000) +  1000);
	}

	public void allListWrite(String in) {

		for (MultiThreadServerChannel mtsc : SimpleChat.clientList) {
			try {
				DataOutputStream dos = new DataOutputStream(mtsc.socket.getOutputStream());
				dos.writeUTF(in);
				dos.flush();
				System.out.println(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		while (true) {

			isClosed = socket.isClosed();
			if (isClosed) {
				SimpleChat.clientList.remove(this);
				break;
			}

			try {
				DataInputStream dis = new DataInputStream(socket.getInputStream());

				String line = dis.readUTF();
			
				allListWrite(String.format("%s: %s", nickName, line));
			} catch (IOException e) {

				SimpleChat.clientList.remove(this);
				System.out.println("=������ ������ϴ�.=");
				System.out.printf("[���� ������ %d�� �Դϴ�]\n", SimpleChat.clientList.size());
				break;
			}

		}
	}

}
