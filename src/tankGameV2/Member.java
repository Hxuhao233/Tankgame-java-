package tankGameV2;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.Vector;

/*
 * ���������
 */
public class Member {

}

//��ըЧ����
class Boom{
	//��������
	int x,y;
	
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
	
	
	//�������(�����ж�չʾ����ͼƬ)
	int life = 15;

	//�Ƿ���
	boolean isLive = true;
	

		
	public Boom(int x, int y){
		
		
		this.x = x;
		this.y = y;
	}
	//������������
	public void lifeDown(){
		if(life > 0){
			life --;
		}else{
			isLive = false;
		}
	}
	
	
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
}



//�ڵ���
class Shot implements Runnable{
	int x;
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

	int y;
	
	int direct = 0;
	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public Shot(int x, int y, int direct, int speed){
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.speed = speed;
	}
	
	boolean isLive = true;

	
	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	int speed ;
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(isLive){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(this.direct){
			case 0:
				y-=speed;
				break;
			case 1:
				y+=speed;
				break;
			case 2:
				x-=speed;
				break;
			case 3:
				x+=speed;
				break;
			}
			if(x<0||x>600||y<0||y>500){
				isLive = false;
				break;
			}
			
		}
	}
}

//tank��
class Tank{
	
	//������,������
	int x = 0;
	int y = 0;
	//����
	int direct;
	//�Ƿ���
	boolean isLive = true;
	//�ٶ�
	int speed ;
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

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
	
	
	//�ڵ�
	Vector<Shot> shot = new Vector<Shot>();
	Shot s = null;
	
	

	public Tank(int x, int y, int direct,int type){
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.type = type;
		this.speed = 1;
	}
	

	//�����ڵ�
	public void Shooting(){
		if(this.isLive()){
		//System.out.println(this.direct);
		//����̹�˷����ж�
		switch(direct){
		//����
		case 0:
			s = new Shot(x+10,y,0,3);
			shot.add(s);
			break;
		
		//����
		case 1:
			s = new Shot(x+10,y+20,1,3);
			shot.add(s);
			break;
		
		//����
		case 2:
			s = new Shot(x,y+10,2,3);	
			shot.add(s);
			break;
		
		//����
		case 3:
			s = new Shot(x+20,y+10,3,3);
			shot.add(s);
			break;
		}
		//System.out.println(shot.direct);
		Thread t = new Thread(s);
		t.start();
		
		}
	}
}

//myTank��
class MyTank extends Tank{

	public MyTank (int x, int y,int direct){
		super(x,y,direct,1);
	}

}


//EnemyTank��
class EnemyTank extends Tank implements Runnable{
	public EnemyTank (int x, int y, int direct){
		super(x,y,direct,0);
	}
	
	int times = 0;

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//ʵ�ֵз�̹�˵�����ƶ�
		while(true){
			//�����˶�
			switch(this.getDirect()){
			case 0:
				for(int i = 0 ; i < 15 ; i++){
					if(y>0)
						setY(getY() - 1);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println(this.getDirect());
					//System.out.println(this.getX()+" , "+this.getY());
					
					//System.out.println("����:i"+i);
				}
				
				break;
			case 1:
				for(int j = 0 ; j < 15;j++){
					if(y+30<500)
						setY(getY() + 1);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					//System.out.println("����:j"+j);
					//System.out.println(this.getDirect());
					//System.out.println(this.getX()+" , "+this.getY());
					//System.out.println(this.getY());
				
					
				}

				break;
			case 2:
				for(int k = 0 ; k < 15;k++){
					if(x>0)
					setX(getX()-1);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("����:k"+k);
					//System.out.println(this.getDirect());
					//System.out.println(this.getX()+" , "+this.getY());

					//System.out.println(this.getY());
				}
				
				break;
			case 3:
				for(int l = 0 ; l < 15;l++){
					if(x+30<600)
						setX(getX()+1);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("����:l"+l);
					//System.out.println(this.getDirect());
					//System.out.println(this.getX()+" , "+this.getY());
					//System.out.println(this.getY());
				}
				
				break;
			}
			times ++;
			//�ж��Ƿ���Ҫ�����ڵ�
			if(times % 2==0){

				this.Shooting();
			}	
			//����ı䷽��
			this.setDirect((int)(Math.random()*4));
			
			//�ж��Ƿ�����
			if(this.isLive()==false){
				break;
			}
			
			
		}
	}
}