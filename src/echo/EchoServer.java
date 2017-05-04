package echo;


import java.io.*;
import java.net.*;



public class EchoServer {

	private static final int PORT = 4000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket=null;
			//1.서버소켓
		try {
			serverSocket = new ServerSocket();
			//2.바인딩(소켓주소ip+tcp)
			InetAddress inetAddress= InetAddress.getLocalHost();
			String localhost=inetAddress.getHostAddress(); 
			serverSocket.bind(new InetSocketAddress( localhost,PORT));
			System.out.println("[서버] 바인딩"+localhost+":"+PORT+" 연결됨");
			
			//3.accept(연결요청 기다림)
			while(true){
				Socket socket = serverSocket.accept();//blocking
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
				System.out.println("[서버] 연결됨");
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(serverSocket!= null&&serverSocket.isClosed()==false)
					serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
