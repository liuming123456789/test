import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;




  
public class main {    
   public static void main(String[] args) throws SQLException{   
	    
	   try { 
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/stu?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","root"); 
		java.sql.Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from ut_stu_info");
		while(rs.next()){
			String job = rs.getString("stu_name");
			System.out.println(job);
		}
		rs.close();
		con.close();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	   
	}}        
	}
	  /* public class main   {
		   public static void main(String[] args) throws InterruptedException {
			   thread th = new thread();
			   thread th2 = new thread();
			   th.setName("q1");
			   th2.setName("q2");
			   th.start();
			   th2.start();
			 thread1 th = new thread1();
			 Thread daxiong = new Thread(th);
			 Thread panghu = new Thread(th);
			 Thread jingxiang = new Thread(th);
			 daxiong.setName("大雄");
			 panghu.setName("胖虎");
			 jingxiang.setName("静香");
			 daxiong.setDaemon(true);
			 panghu.setDaemon(true);
			jingxiang.setDaemon(true);
			 daxiong.start();  
			 panghu.start();
			 //panghu.join(); 
			 jingxiang.start();  
			 for(int i=0;i<4;i++){
				 Thread.sleep(1000);
                 System.out.println(Thread.currentThread().getName()+ "----------------" + i);
			 }
			   baozi ba = new baozi();
			   duolaAmeng ameng = new duolaAmeng(ba);
			   daxiong daxiong = new daxiong(ba);
			   Thread th1 = new Thread(ameng);
			   Thread th2 = new Thread(daxiong);
			   th1.start();
			   th2.start();
		}
		   
 }
	   class thread extends Thread{
		   private int sum = 5;
		   @Override 
		   public void run() {
			   while(sum>0){
				   System.out.println(this.getName()+"@@@@"+sum--);
			   }
		   }
		   
	   }
	   class thread1 implements Runnable {
		   private int sum = 30;
		    
		   public  void run() {
			   synchronized (new Object()) {
			   while(sum>0){
				   try { 
					Thread.sleep(0);
				System.out.println(Thread.currentThread().getName()+"帮我卖了第"+sum--+"个包子");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
			  }
		    }
	   }
	   class baozi{
		   private String baozi;
		   public boolean flag;
		   public String getBaozi(){
			   return baozi;
		   }
		   public void setBaozi(String baozi){
			   this.baozi = baozi;
		   }
	   }
	   class duolaAmeng implements Runnable{
		   private baozi baozi;
		   private int x = 0;
		   public duolaAmeng(baozi baozi){
			   this.baozi = baozi;  
		   }
		   public void run(){
			   synchronized (baozi){
				   while(true){
					   if(baozi.flag){
						   try {
							   baozi.wait();
						   }catch (Exception e) {
							   e.printStackTrace();
						    }
					   }
					   if(x%2==0){
						   baozi.setBaozi("有热包子吃"); 
					   }else{
						   baozi.setBaozi("有冷包子吃");
					   }
					   x++;
					   baozi.flag = true;
					   baozi.notify();
				   }
			   }
		   }
	   }
	   class daxiong implements Runnable{
		   private baozi baozi; 
		   public daxiong(baozi baozi){
			   this.baozi = baozi;  
		   }
		   public void run(){
			   synchronized (baozi){
				   while(true){
					   if(!baozi.flag){
						   try {
							   baozi.wait();
						   }catch (Exception e) {
							   e.printStackTrace();
						    }
					   }
					   System.out.println("吃----" +baozi.getBaozi());
					   baozi.flag = false;
					   baozi.notify();
				   }
			   }
		   }
	   }*/ 