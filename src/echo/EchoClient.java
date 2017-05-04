package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class EchoClient {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		//1.소켓생성
		Socket socket = null;
		//2.서버연결
		try {
			socket=new Socket();
			socket.connect(new InetSocketAddress("192.168.1.3",4000));
			//3.IOStream 받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			//true를 주는 이유는 버퍼를 비워주는flush()를 신경쓰지않고 다 보낼수 있음.
			PrintWriter pw =new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			
			while(true){
				System.out.println(">>");
				String message = scanner.nextLine();
				
				if("exit".equals(message))
					break;
				//메세지 보내기
				pw.println(message);
				//에코 메세지 읽어오기
				String echoMessage = br.readLine();
				if(echoMessage==null){
					System.out.println("[클라이언트] 연결이 끊어짐.");
					break;
				}
				//출력
				System.out.println("<<"+echoMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			scanner.close();
			try {
				if(socket != null && socket.isClosed()==true)
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		

	}

}
