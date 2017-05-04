package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;
	public EchoServerReceiveThread(Socket socket){
		// 기본생성자는 소켓이 널일수 있으므로 쓰지말고 이렇게 만들어 주자.
		this.socket=socket;
	}
	@Override
	public void run() {
		
		
		try{
			
			//4.연결성공
			InetSocketAddress remoteSocketAddress=(InetSocketAddress)socket.getRemoteSocketAddress();
			int remotePort=remoteSocketAddress.getPort();
			String remoteAddress=remoteSocketAddress.getAddress().getHostAddress();
			System.out.println("[서버] 연결됨"+remoteAddress+":"+remotePort);
			
			//5.IOStream 방아오기
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
		}catch(SocketException e){
			System.out.println("[서버] 비정상적 종료");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				if(socket!= null&&socket.isClosed()==false)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	

}
