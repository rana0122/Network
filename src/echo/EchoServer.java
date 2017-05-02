package echo;


import java.io.*;
import java.net.*;



public class EchoServer {

	public static void main(String[] args) {
		//1.서버소켓
		try {
			ServerSocket serverSocket = new ServerSocket();
			//2.바인딩(소켓주소ip+tcp)
			serverSocket.bind(new InetSocketAddress("192.168.1.3",4000));
			//3.accept(연결요청 기다림)
			Socket socket = serverSocket.accept();//blocking
			System.out.println("[서버] 연결됨");
			try{
				//4.연결성공
				//3.IOStream 받아오기
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
				//true를 주는 이유는 버퍼를 비워주는flush()를 신경쓰지않고 다 보낼수 있음.
				PrintWriter pw =new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);

			
				while(true){
					String message = br.readLine();//blocking
					if(message==null){
						System.out.println("[서버] 연결 끊어짐(클라이언트가 정상적 종료)");
						break;
					}
					System.out.println("[서버]메시지 받음:"+message);
					pw.println(message);
					//pw.print(message+"\n");
				}
			}catch(IOException e){
				e.printStackTrace();
				}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
