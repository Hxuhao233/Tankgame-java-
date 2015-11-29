package tankGameV2;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.Vector;

/*
 * 各类的声明
 */
public class Member {

}

//爆炸效果类
class Boom{
	//横纵坐标
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
	
	
	//存活周期(用来判断展示哪张图片)
	int life = 15;

	//是否存活
	boolean isLive = true;
	

		
	public Boom(int x, int y){
		
		
		this.x = x;
		this.y = y;
	}
	//减少生存周期
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



//炮弹类
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

//tank类
class Tank{
	
	//横坐标,纵坐标
	int x = 0;
	int y = 0;
	//朝向
	int direct;
	//是否存活
	boolean isLive = true;
	//速度
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

	
	//类型(1我方 0对方)
	int type ;
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	//炮弹
	Vector<Shot> shot = new Vector<Shot>();
	Shot s = null;
	
	

	public Tank(int x, int y, int direct,int type){
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.type = type;
		this.speed = 1;
	}
	

	//发射炮弹
	public void Shooting(){
		if(this.isLive()){
		//System.out.println(this.direct);
		//根据坦克方向判断
		switch(direct){
		//向上
		case 0:
			s = new Shot(x+10,y,0,3);
			shot.add(s);
			break;
		
		//向下
		case 1:
			s = new Shot(x+10,y+20,1,3);
			shot.add(s);
			break;
		
		//向左
		case 2:
			s = new Shot(x,y+10,2,3);	
			shot.add(s);
			break;
		
		//向右
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

//myTank类
class MyTank extends Tank{

	public MyTank (int x, int y,int direct){
		super(x,y,direct,1);
	}

}


//EnemyTank类
class EnemyTank extends Tank implements Runnable{
	public EnemyTank (int x, int y, int direct){
		super(x,y,direct,0);
	}
	
	int times = 0;

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//实现敌方坦克的随机移动
		while(true){
			//惯性运动
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
					
					//System.out.println("次数:i"+i);
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
					//System.out.println("次数:j"+j);
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
					//System.out.println("次数:k"+k);
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
					//System.out.println("次数:l"+l);
					//System.out.println(this.getDirect());
					//System.out.println(this.getX()+" , "+this.getY());
					//System.out.println(this.getY());
				}
				
				break;
			}
			times ++;
			//判断是否需要加入炮弹
			if(times % 2==0){

				this.Shooting();
			}	
			//随机改变方向
			this.setDirect((int)(Math.random()*4));
			
			//判断是否死亡
			if(this.isLive()==false){
				break;
			}
			
			
		}
	}
}