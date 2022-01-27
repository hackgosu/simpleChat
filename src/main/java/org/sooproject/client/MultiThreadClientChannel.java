package org.sooproject.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadClientChannel implements Runnable {
	//서버를 연결할 소켓 객체
	public Socket serverConn = null;
	//기본 아이피
	private String defaultIP = "127.0.0.1";
	//기본 포트
	private int defaultPort = 3348;
	private boolean isErr = false;
	//객체 생성시 초기화
	public MultiThreadClientChannel(String defaultIP,int defaultPort) {
		
		try {
			this.serverConn = new Socket(defaultIP,defaultPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("해당 호스트를 찾을 수 없습니다.");
			//어떤 에러인지 예외 처리하여 확인
			e.printStackTrace();
			isErr = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("정상적인 스트림이 아닙니다.");
			//어떤 에러인지 예외 처리하여 확인
			e.printStackTrace();
			isErr = true;
		}
		//연결 아이피를 초기화
		this.defaultIP = defaultIP;
		//연결 포트를 초기화
		this.defaultPort = defaultPort;
		
	}
	//반복 작업을 할 부분을 해당 부분에 추가
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
