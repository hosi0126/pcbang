package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientMain extends JFrame implements ActionListener{
	JPanel p_center,p_south;
	JTextArea area;
	JScrollPane scroll;
	JTextField t_input;
	JButton bt;
	String[] path={"mario.png",
			"base.png",
			"baskeat.png",
			"block1.png",
			"enemy.png",
			"enemy2.png",
			"enemy3.png",
			"enemy4.png"};
	Socket socket;
	int port=7878;
	String ip="localhost";
	ClientThread ct;
	
	public ClientMain() {
		p_center=new JPanel();
		p_south=new JPanel();
		area=new JTextArea();
		scroll=new JScrollPane(area);
		t_input=new JTextField(40);
		bt=new JButton("Connect");

		createProduct();
		p_center.add(scroll);
		p_south.add(t_input);
		p_south.add(bt);
		
		add(p_center);
		add(p_south,BorderLayout.SOUTH);
		
		scroll.setPreferredSize(new Dimension(580, 200));
		
		bt.addActionListener(this);
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					String msg=t_input.getText();
					ct.send("requestType=chat&msg="+msg+"&id=batman");
					t_input.setText("");
				}
			}
		});
		
		
		setSize(650,700);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void createProduct(){
		try {
			for(int i=0;i<path.length;i++){
				URL url=new URL("http://211.238.142.121:9090/data/"+path[i]);
				Product product=new Product(url,this);
				p_center.add(product);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void connect(){
		try {
			socket=new Socket(ip, port);
			
			ct=new ClientThread(socket,area);
			ct.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e) {
		connect();
	}
	
	public static void main(String[] args) {
		new ClientMain();
	}
}
