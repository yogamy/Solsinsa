package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{

	Socket socket;
	BufferedReader br;
	PrintWriter out;
	Scanner sc;
	
	public static String msg = null;
	
	final String localhost = "127.0.0.1";
	
	@Override
	public void run() {
		System.out.println("Connecting...");

		try{
			// 서버의 accept()가 호출 후 쓰레드가 만들어지고, List에 추가됨
			socket = new Socket(localhost, 7048); // 7048포트로 서버와 연결
			SocketThread thread = new SocketThread(); // 메시지 수신을 위한 쓰레드 객체 생성
			thread.start(); // 생성한 쓰레드 객체 실행
			
			out = new PrintWriter(socket.getOutputStream(), true);
//			sc = new Scanner(System.in);
			
			String userId = "";
			System.out.println("Connected!!");
//			System.out.println("ID를 입력");
//			out.print(userId);
 
			while (true) {
//				String inputLine = sc.nextLine(); // 사용자에게서 서버로 보낼 값을 입력받는 변수
				if(msg != null)
					out.println(msg); // 입력받은 키보드 값을 서버로 전송
				msg = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class SocketThread extends Thread { // 메시지 수신

		@Override
		public void run() {
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String res = null; // 수신된 메시지를 저장
				while((res = br.readLine()) != null) { // br에 읽을 메시지가 있는 한 무한루프
					System.out.println(res);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		Client client = new Client(); // 클라이언트 실행
		client.start();
		msg = "test,pw:1001";
	}

}
