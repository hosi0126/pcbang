package Client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Product extends JPanel implements ActionListener{
	Canvas can;
	JButton bt;
	BufferedImage image;
	URL url;
	int width=120;
	int height=150;
	ClientMain main;
	
	public Product(URL url,ClientMain main) {
		//image load
		this.main=main;
		this.url=url;
		try {
			image=ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		can=new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(image, 0,0, width,height,this);
			}
		};
		bt=new JButton("구매하기");
		bt.addActionListener(this);
		
		setLayout(new BorderLayout());
		
		add(can);
		add(bt,BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(width+4, height+45));
	}
	
	public void buy(){
		{
			"requestType":"chat",
			"msg":"",
			"id":"batman",
		}
		String msg="requestType=buy&product_id=1&id=batman";
		main.ct.send(msg);
	}

	public void actionPerformed(ActionEvent e) {
		buy();
	}
}
