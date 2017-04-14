package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;

//서버 메세지를 실시간청취하기 위해서는 와일문으로 리드라인을 실행해야한다 따라서 메인쓰레드도 이 작업을 시도하면 메인쓰레드가 와일문에서 빠져나오지 못하므로 모든 유아이가 멈춰있게된다
public class ClientThread extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	JTextArea area;
	boolean flag=true;
	
	public ClientThread(Socket socket,JTextArea area) {
		this.socket=socket;
		this.area=area;
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			area.append(msg+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(flag){
			listen();
		}
	}
}
