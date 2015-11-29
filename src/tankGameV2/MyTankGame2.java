package tankGameV2;
import javax.imageio.ImageIO;
/*
 * ̹�˴�սv2.0
 * ���Է����ڵ�
 * ����ʱ������ըЧ��
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.io.*;
public class MyTankGame2 extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MyPanel panel = null;
	public static void main(String[] args){
		MyTankGame2 game = new MyTankGame2();
	}
	
	//Constructor
	public MyTankGame2(){
		panel = new MyPanel();
		this.addKeyListener(panel);
		
		Thread t1 = new Thread(panel);
		t1.start();
		this.add(panel);	
		
		this.setSize(700,600);
		this.setVisible(true);
		
	}
}


//MyPanel��
class MyPanel extends JPanel implements KeyListener,Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//����һ��MyTank�����
	MyTank myTank = null;
	
	//����Boom�����(vector)
	Vector<Boom> booms = new Vector<Boom>();
	
	//����EnemyTank�����(vector)

	Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
	
	//���屬ըͼƬ
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	
	final private int WIDTH = 600;
	final private int HEIGHT = 500;
	
	//Constructor
	public MyPanel(){
		//��ʼ���ҵ�̹��
		myTank = new MyTank(233,450,0);
		
		//��ʼ���з�̹��
		EnemyTank enemyTank = null;
		for(int i = 0 ; i < 5 ; i++){
			//Ĭ������
			enemyTank = new EnemyTank(50+i*50, 30+i*30, 1);
			enemyTanks.add(enemyTank);

			//���з�̹�����һ���ڵ�Ĭ������
			Shot s = new Shot(enemyTank.getX()+10,enemyTank.getY()+30,enemyTank.getDirect(),5);
			enemyTank.shot.add(s);
			//�����з�̹��
			Thread t2 = new Thread(enemyTank);
			t2.start();
			Thread t1 = new Thread(s);
			t1.start();
			
		}
		
		
		//��ʼ��ͼƬ(����ͼ��ɱ�ըЧ��)
		try {
			image1 = ImageIO.read(new File("bomb_1.gif"));
			image2 = ImageIO.read(new File("bomb_2.gif"));
			image3 = ImageIO.read(new File("bomb_3.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		
	}

	
	//��дpaint
	public void paint(Graphics g){
		
		super.paint(g);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//��������
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//�����Լ���̹��
		if(myTank.isLive())
			this.drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), myTank.getType());
		
		//������ըЧ��
		for(int i = 0 ; i < booms.size(); i++){
			
			
			Boom b = booms.get(i);
			//�������������ж�չʾ��ͼƬ
			if(b.getLife() > 10){
				g.drawImage(image1, b.getX(), b.getY(), 30, 30, this);
			}else if (b.getLife() > 4){
				g.drawImage(image2, b.getX(), b.getY(), 30, 30, this);
			}else{
				g.drawImage(image3, b.getX(), b.getY(), 30, 30, this);	
			}
			//������������
			b.lifeDown();
			//�Ƴ���ը�����
			if(!b.isLive()){
				booms.remove(b);
			}
		}
		
		
		//��������̹��(���ڵ�)
		for(int i = 0 ;i < enemyTanks.size();i++){
			EnemyTank et = enemyTanks.get(i);
			
			if(et.isLive()){
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), et.getType());
				for(int j=0 ; j<et.shot.size();j++){
					Shot shot1 = et.shot.get(j);
					if(shot1.isLive()){
						g.draw3DRect(shot1.getX(), shot1.getY(), 1, 1, false);
					}else {
						et.shot.remove(shot1);
					}
				}
			}
		}
		
		
		//�����ڵ�(����vector�����ҵ�)
		for(int i = 0 ; i < myTank.shot.size();i++){
			Shot shot1 = myTank.shot.get(i);
			if(shot1 != null && shot1.isLive()){
				g.setColor(Color.WHITE);
				g.draw3DRect(shot1.getX(), shot1.getY(), 1, 1, false);
			}
			
			//�Ƴ��ڵ�
			if(!shot1.isLive()){
				myTank.shot.remove(shot1);
			}
		}
	}
	
	//�ж��Ƿ���е���
	public void hitEnemy(){
		for(int i = 0 ; i < myTank.shot.size();i++){
			Shot myshot = myTank.shot.get(i);
			if(myshot.isLive()){
				for(int j = 0 ; j< enemyTanks.size();j++){
					EnemyTank et = enemyTanks.get(j);
					
					if(et.isLive()){
						this.hitTank(myshot, et);
					}
				}
			}
		}	
	}
	
	//�ж��Ƿ���м���
	public void hitMe(){
		for(int i = 0 ; i < enemyTanks.size(); i ++){
			EnemyTank et = enemyTanks.get(i);
			for(int k = 0 ; k < et.shot.size();k++){
				Shot eshot = et.shot.get(k);
				if(eshot.isLive()){
					this.hitTank(eshot, myTank);
				}
			}
		}
	}
	//judge whether shot hit the tank
	public void hitTank(Shot s, Tank t)
	{
		//judge the direct of tank
		switch(t.direct){
		case 0:
		case 1:
			if((s.getX()>t.getX()) && (s.getX()< t.getX()+20)
					&& (s.getY()>t.getY()) &&(s.getY()<t.getY()+30)){
				//hit it
				//�ڵ� ��
				//̹�� ��
				s.setLive(false);
				t.setLive(false);
				
				//��������Vector���Boom�����
				Boom b = new Boom(t.getX(),t.getY());
				booms.add(b);
			
			}
			break;
		case 2:
		case 3:
			if((s.getX()>t.getX()) && (s.getX()<t.getX()+30)
					&& (s.getY()>t.getY()) && (s.getY()<t.getY()+20)){
				//hit it
				//�ڵ� ��
				//̹�� ��
				s.setLive(false);
				t.setLive(false);
				//��Vector���Boom�����
				Boom b = new Boom(t.getX(),t.getY());
				booms.add(b);
			}
			break;
		}
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
			if(myTank.getY()+30<HEIGHT){
				//����
				myTank.setDirect(1);
				myTank.setY(myTank.getY()+myTank.getSpeed());
			}
		
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			if(myTank.getY() > 0){
				//����
				myTank.setDirect(0);	
				myTank.setY(myTank.getY()-myTank.getSpeed());
			}
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(myTank.getX()+30<WIDTH){
				//����
				myTank.setDirect(3);	
				myTank.setX(myTank.getX()+myTank.getSpeed());
			}
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(myTank.getX()>0){
				//����
				myTank.setDirect(2);	
				myTank.setX(myTank.getX()-myTank.getSpeed());
			}
		}
		
		//System.out.print(myTank.getDirect());
		
		//����J �����ڵ�
		if(e.getKeyCode() == KeyEvent.VK_J){
			if(myTank.shot.size() < 5)
				myTank.Shooting();
			
		}
		this.repaint();
	}
	


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//����
			hitMe();
			hitEnemy();
			
			this.repaint();
		}
	}
}





