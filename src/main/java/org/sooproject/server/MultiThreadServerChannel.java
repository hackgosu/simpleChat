package org.sooproject.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDate;

import org.sooproject.SimpleChat;
import org.sooproject.client.MultiThreadClientChannel;

public class MultiThreadServerChannel implements Runnable {
	
	public Socket socket = null;
	private String nickName = "";
	private boolean isConnected = true;
	
	public MultiThreadServerChannel(Socket socket) {
		this.socket = socket;
		this.nickName = String.format( "¼Õ´Ô%x" , LocalDate.now().now().toEpochDay());
	}
	public void allListWrite(String in) {
		
		for(MultiThreadServerChannel mtc:SimpleChat.clientList)
		{
			try {
				DataOutputStream dos = new DataOutputStream(mtc.socket.getOutputStream());
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
			
			isConnected = socket.isConnected();
			if (!isConnected) {
				SimpleChat.clientList.remove(this);
				return;
			}
			
			try {
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String result = dis.readUTF();
				if (!result.equals("")) {
					System.out.println(result);
					allListWrite(String.format("%s: %s", nickName, result));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
