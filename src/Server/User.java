package Server;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class User extends JPanel implements Runnable{
	JLabel la;
	Thread thread;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	boolean flag=true;
	
	public User(Socket socket) {
		this.socket=socket;
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		thread=new Thread(this);
		thread.start();
		
		la=new JLabel("PC");
		add(la);
		
		setPreferredSize(new Dimension(150, 150));
		setBackground(Color.ORANGE);
	}
	
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			
			String[] data=msg.split("&");
			String[] requestType=data[0].split("=");
			if(requestType[1].equals("chat")){
				String[] str=data[1].split("=");
				send(str[1]);	
			}else if(requestType[1].equals("buy")){
				System.out.println("그만 시켜");
			}
			
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
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			listen();
		}
	}
}
