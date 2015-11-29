package tankGameV1;
/*
 * ����̹��������
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MyTankGame1 extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MyPanel panel = null;
	public static void main(String[] args){
		MyTankGame1 game = new MyTankGame1();
	}
	
	//Constructor
	public MyTankGame1(){
		panel = new MyPanel();
		this.addKeyListener(panel);
		
		this.add(panel);	
		this.setSize(600,500);
		this.setVisible(true);
		
	}
}


//MyPanel��
class MyPanel extends JPanel implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//����һ��MyTank�����
	MyTank myTank = null;
	//����һ��EmeryTank�����
	EmeryTank emeryTank = null;
	
	
	//Constructor
	public MyPanel(){
		myTank = new MyTank(20,30,0);
		emeryTank = new EmeryTank(100,150,1);
	}
	
	
	//��дpaint
	public void paint(Graphics g){
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		this.drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), myTank.getType());
		this.drawTank(emeryTank.getX(), emeryTank.getY(), g, emeryTank.getDirect(), emeryTank.getType());
		
	}
	
	//Draw the tank
	public void drawTank(int x, int y,Graphics g, int direct,int type){
		switch(type){
		//�ж��ǵ�����
		case 0:
			g.setColor(Color.cyan);
			break;
			
		case 1:
			g.setColor(Color.BLUE);
			break;
		}
		
		//�жϷ���
		switch(direct){
		//����
		case 0:

			//draw a tank using your brain hole
			//1.������ߵľ���
			g.fill3DRect(x, y, 5, 30,false);
			//2.�����ұߵľ���
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.�����м����
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.����Բ��
			g.fillOval(x+5, y+10, 10, 10);
			//5.������
			g.drawLine(x+10, y+15, x+10, y);
			
			break;
			//����
		case 1:
			
			//draw a tank using your brain hole
			//1.������ߵľ���
			g.fill3DRect(x+15, y, 5, 30,false);
			//2.�����ұߵľ���
			g.fill3DRect(x, y, 5, 30,false);
			//3.�����м����
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.����Բ��
			g.fillOval(x+5, y+10, 10, 10);
			//5.������
			g.drawLine(x+10, y+15, x+10, y+30);

			break;
			
			
			//����
		case 2:
			//draw a tank using your brain hole
			//1.�����ϱߵľ���
			g.fill3DRect(x, y, 30, 5,false);
			//2.�����±ߵľ���
			g.fill3DRect(x, y+15, 30, 5,false);
			//3.�����м����
			g.fill3DRect(x+5, y+5, 20, 10,false);
			//4.����Բ��
			g.fillOval(x+10, y+5, 10, 10);
			//5.������
			g.drawLine(x, y+10, x+15, y+10);
			
			break;
			
			//����
		case 3:
			//draw a tank using your brain hole
			//1.������ߵľ���
			g.fill3DRect(x, y, 30, 5,false);
			//2.�����ұߵľ���
			g.fill3DRect(x, y+15, 30, 5,false);
			//3.�����м����
			g.fill3DRect(x+5, y+5, 20, 10,false);
			//4.����Բ��
			g.fillOval(x+10, y+5, 10, 10);
			//5.������
			g.drawLine(x+15, y+10, x+30, y+10);
			
			break;
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			
			//����
			myTank.setDirect(1);
			myTank.setY(myTank.getY()+1);
			
			//�ػ溯��
			this.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			
			//����
			myTank.setDirect(0);	
			myTank.setY(myTank.getY()-1);
		
			//�ػ溯��
			this.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			
			//����
			myTank.setDirect(3);	
			myTank.setX(myTank.getX()+1);
			//�ػ溯��
			this.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			
			//����
			myTank.setDirect(2);	
			myTank.setX(myTank.getX()-1);
			//�ػ溯��
			this.repaint();
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}


//tank��
class Tank{
	
	//������,������
	int x = 0;
	int y = 0;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	//����
	int direct = 0;
	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	
	//����(1�ҷ� 0�Է�)
	int type ;
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Tank(int x, int y, int direct,int type){
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.type = type;
	}
}

//myTank��
class MyTank extends Tank{
	
	public MyTank (int x, int y,int direct){
		super(x,y,direct,1);
	}
}

//EmeryTank��
class EmeryTank extends Tank{
	public EmeryTank (int x, int y, int direct){
		super(x,y,direct,0);
	}
}


