package tankGameV1;
/*
 * 画出坦克啦啦啦
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


//MyPanel类
class MyPanel extends JPanel implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//定义一个MyTank类对象
	MyTank myTank = null;
	//定义一个EmeryTank类对象
	EmeryTank emeryTank = null;
	
	
	//Constructor
	public MyPanel(){
		myTank = new MyTank(20,30,0);
		emeryTank = new EmeryTank(100,150,1);
	}
	
	
	//重写paint
	public void paint(Graphics g){
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		this.drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), myTank.getType());
		this.drawTank(emeryTank.getX(), emeryTank.getY(), g, emeryTank.getDirect(), emeryTank.getType());
		
	}
	
	//Draw the tank
	public void drawTank(int x, int y,Graphics g, int direct,int type){
		switch(type){
		//判断是敌是友
		case 0:
			g.setColor(Color.cyan);
			break;
			
		case 1:
			g.setColor(Color.BLUE);
			break;
		}
		
		//判断方向
		switch(direct){
		//向上
		case 0:

			//draw a tank using your brain hole
			//1.画出左边的矩形
			g.fill3DRect(x, y, 5, 30,false);
			//2.画出右边的矩形
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.画出中间矩形
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.画出圆形
			g.fillOval(x+5, y+10, 10, 10);
			//5.画出线
			g.drawLine(x+10, y+15, x+10, y);
			
			break;
			//向下
		case 1:
			
			//draw a tank using your brain hole
			//1.画出左边的矩形
			g.fill3DRect(x+15, y, 5, 30,false);
			//2.画出右边的矩形
			g.fill3DRect(x, y, 5, 30,false);
			//3.画出中间矩形
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.画出圆形
			g.fillOval(x+5, y+10, 10, 10);
			//5.画出线
			g.drawLine(x+10, y+15, x+10, y+30);

			break;
			
			
			//向左
		case 2:
			//draw a tank using your brain hole
			//1.画出上边的矩形
			g.fill3DRect(x, y, 30, 5,false);
			//2.画出下边的矩形
			g.fill3DRect(x, y+15, 30, 5,false);
			//3.画出中间矩形
			g.fill3DRect(x+5, y+5, 20, 10,false);
			//4.画出圆形
			g.fillOval(x+10, y+5, 10, 10);
			//5.画出线
			g.drawLine(x, y+10, x+15, y+10);
			
			break;
			
			//向右
		case 3:
			//draw a tank using your brain hole
			//1.画出左边的矩形
			g.fill3DRect(x, y, 30, 5,false);
			//2.画出右边的矩形
			g.fill3DRect(x, y+15, 30, 5,false);
			//3.画出中间矩形
			g.fill3DRect(x+5, y+5, 20, 10,false);
			//4.画出圆形
			g.fillOval(x+10, y+5, 10, 10);
			//5.画出线
			g.drawLine(x+15, y+10, x+30, y+10);
			
			break;
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			
			//向下
			myTank.setDirect(1);
			myTank.setY(myTank.getY()+1);
			
			//重绘函数
			this.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			
			//向上
			myTank.setDirect(0);	
			myTank.setY(myTank.getY()-1);
		
			//重绘函数
			this.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			
			//向右
			myTank.setDirect(3);	
			myTank.setX(myTank.getX()+1);
			//重绘函数
			this.repaint();
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			
			//向左
			myTank.setDirect(2);	
			myTank.setX(myTank.getX()-1);
			//重绘函数
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


//tank类
class Tank{
	
	//横坐标,纵坐标
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
	
	//朝向
	int direct = 0;
	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	
	//类型(1我方 0对方)
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

//myTank类
class MyTank extends Tank{
	
	public MyTank (int x, int y,int direct){
		super(x,y,direct,1);
	}
}

//EmeryTank类
class EmeryTank extends Tank{
	public EmeryTank (int x, int y, int direct){
		super(x,y,direct,0);
	}
}


