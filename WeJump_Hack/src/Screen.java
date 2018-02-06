import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Screen extends Frame {
	public static final int Screen_Width = 720;
	public static final int Screen_Height = 1280;
	public static boolean canGetPressTime = false;
	public static int duration = 0;
	public static final int OVALSIZE = 15;
	private Font text = new Font("宋体", Font.PLAIN, 30);
	
	Image png = null;
	MouseMonitor monitor = null;
	ButtonMonitor buttonMonitor = null;
	
	public void createScreen(){
		this.setLayout(null);
		this.setLocation(500,80);
		this.setSize(Screen_Width/2,Screen_Height/2);
		this.setTitle("微信跳一跳辅助");
		
		Panel panel = new Panel();
		panel.setLocation(150,50);
		panel.setSize(200,50);
		panel.setLayout(null);
		this.add(panel);
		
		buttonMonitor = new ButtonMonitor();
		
		Button reset = new Button("重选起点");
		reset.setSize(100, 50);
		reset.setLocation(0, 0);
		reset.addActionListener(buttonMonitor);
		panel.add(reset);
		
		Button next = new Button("下一跳(重新截图)");
		next.setSize(100, 50);
		next.setLocation(100, 0);
		next.addActionListener(buttonMonitor);
		panel.add(next);
		
		
		this.setResizable(false);
		monitor = new MouseMonitor();
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addMouseListener(monitor);
		new Thread(new PaintThread()).start();
	}
	
	public void guiGetPng() {
		png = this.getToolkit().getImage(Command.localImg+"\\"+"jump.png").getScaledInstance(Screen_Width/2,Screen_Height/2, Image.SCALE_FAST);
		Command.step = 3;
	}
	
	public void paint(Graphics g) {
		g.drawImage(png, 0, 0, Screen_Width/2,Screen_Height/2,this);
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.drawString("By Banana", 270, 620);
		if(canGetPressTime == true) {
			g.setColor(Color.BLACK);
			g.setFont(text);
			g.drawString("跳跃中...", 120, 500);
		}
		if(canGetPressTime == false && MouseMonitor.clickFlag == 0) {
			g.setColor(Color.RED);
			g.setFont(text);
			g.drawString("请标注起点", 120, 500);
		}
		if(canGetPressTime == false && MouseMonitor.clickFlag == 1) {
			g.setColor(Color.GREEN);
			g.setFont(text);
			g.drawString("请标注终点", 120, 500);
		}
		if(Point.points.size() != 0){
			g.setColor(Color.BLACK);
			if(Point.points.size() == 1) {
				g.fillOval(Point.points.get(0).locationX, Point.points.get(0).locationY, OVALSIZE, OVALSIZE);
			}
			if(Point.points.size() == 2) {
				g.fillOval(Point.points.get(1).locationX, Point.points.get(1).locationY, OVALSIZE, OVALSIZE);
			}
		}
		if(Point.points.size() < 2) {
			g.setFont(text);
			g.setColor(Color.BLACK);
			g.drawString("跳跃距离：  0.0", 80, 200);
			g.drawString("长按时间：  0.0"+" ms",80 , 160);
		}
		if(Point.points.size() == 2) {
			g.setFont(text);
			g.setColor(Color.BLACK);
			g.drawString("跳跃距离："+Command.getDistance(Point.points.get(0),Point.points.get(1)), 80, 200);
			duration = (int) (Command.jumpCoefficient*Command.getDistance(Point.points.get(0),Point.points.get(1)));
			g.drawString("长按时间： "+duration+" ms",80 , 160);
		}
		g.setColor(c);
	}
	
	
	
	private class PaintThread implements Runnable{
		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	

	// 定义一个虚拟屏幕，这里是双缓冲消除闪烁
	Image offScreenImage = null;

	// 因为repaint方法会首先调用update，update再调用paint，所以重写update方法
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(Screen_Width / 2, Screen_Height / 2);
		}
		// 拿到虚拟屏幕的“画笔”
		Graphics gOffScreen = offScreenImage.getGraphics();
		// 刷新背景
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.white);
		gOffScreen.fillRect(0, 0, Screen_Width / 2, Screen_Height / 2);
		gOffScreen.setColor(c);
		// 在虚拟屏幕上作画
		paint(gOffScreen);
		// 将虚拟屏幕上的画传到真实屏幕上,g是真实屏幕的“画笔”
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private class ButtonMonitor implements ActionListener {
		

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("重选起点")) {	
				MouseMonitor.clickFlag = 0;
				Point.clearPoints();
			}
			
			if(e.getActionCommand().equals("下一跳(重新截图)")) {
				Screen.duration = 0;
				Screen.canGetPressTime = false;
				
				Command.step = 0;
				
				MouseMonitor.clickFlag = 0;
				
				Point.clearPoints();
				
				if(Command.step == 0) {
					Launch.stepOneTwo();
				}
				if(Command.step == 2) {
					guiGetPng();
				}
				
			}
			
		}

	}

}
