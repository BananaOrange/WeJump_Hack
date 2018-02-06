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
	private Font text = new Font("����", Font.PLAIN, 30);
	
	Image png = null;
	MouseMonitor monitor = null;
	ButtonMonitor buttonMonitor = null;
	
	public void createScreen(){
		this.setLayout(null);
		this.setLocation(500,80);
		this.setSize(Screen_Width/2,Screen_Height/2);
		this.setTitle("΢����һ������");
		
		Panel panel = new Panel();
		panel.setLocation(150,50);
		panel.setSize(200,50);
		panel.setLayout(null);
		this.add(panel);
		
		buttonMonitor = new ButtonMonitor();
		
		Button reset = new Button("��ѡ���");
		reset.setSize(100, 50);
		reset.setLocation(0, 0);
		reset.addActionListener(buttonMonitor);
		panel.add(reset);
		
		Button next = new Button("��һ��(���½�ͼ)");
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
			g.drawString("��Ծ��...", 120, 500);
		}
		if(canGetPressTime == false && MouseMonitor.clickFlag == 0) {
			g.setColor(Color.RED);
			g.setFont(text);
			g.drawString("���ע���", 120, 500);
		}
		if(canGetPressTime == false && MouseMonitor.clickFlag == 1) {
			g.setColor(Color.GREEN);
			g.setFont(text);
			g.drawString("���ע�յ�", 120, 500);
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
			g.drawString("��Ծ���룺  0.0", 80, 200);
			g.drawString("����ʱ�䣺  0.0"+" ms",80 , 160);
		}
		if(Point.points.size() == 2) {
			g.setFont(text);
			g.setColor(Color.BLACK);
			g.drawString("��Ծ���룺"+Command.getDistance(Point.points.get(0),Point.points.get(1)), 80, 200);
			duration = (int) (Command.jumpCoefficient*Command.getDistance(Point.points.get(0),Point.points.get(1)));
			g.drawString("����ʱ�䣺 "+duration+" ms",80 , 160);
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
	

	// ����һ��������Ļ��������˫����������˸
	Image offScreenImage = null;

	// ��Ϊrepaint���������ȵ���update��update�ٵ���paint��������дupdate����
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(Screen_Width / 2, Screen_Height / 2);
		}
		// �õ�������Ļ�ġ����ʡ�
		Graphics gOffScreen = offScreenImage.getGraphics();
		// ˢ�±���
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.white);
		gOffScreen.fillRect(0, 0, Screen_Width / 2, Screen_Height / 2);
		gOffScreen.setColor(c);
		// ��������Ļ������
		paint(gOffScreen);
		// ��������Ļ�ϵĻ�������ʵ��Ļ��,g����ʵ��Ļ�ġ����ʡ�
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private class ButtonMonitor implements ActionListener {
		

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("��ѡ���")) {	
				MouseMonitor.clickFlag = 0;
				Point.clearPoints();
			}
			
			if(e.getActionCommand().equals("��һ��(���½�ͼ)")) {
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
