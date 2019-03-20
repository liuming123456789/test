package com.liuming.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;




//j2ee有冲突 
public class Mail {    
   public static void main(String[] args) throws AddressException,MessagingException {       
	  Properties properties = new Properties();
	  properties.put("mail.transport.protocol", "smtp");// 连接协议        
	 // properties.put("mail.smtp.host", "smtp.qq.com");// 主机名        
	  properties.put("mail.smtp.port", 465);// 端口号        
	  properties.put("mail.smtp.auth", "true");  //通过验证   
	  properties.put("mail.smtp.ssl.enable", "true");//设置是否使用ssl安全连接  ---一般都使用        
	  properties.put("mail.debug", "true");//设置是否显示debug信息  true 会在控制台显示相关信息        
	//得到回话对象        
		Session session = Session.getInstance(properties);        
		// 获取邮件对象        
		Message message = new MimeMessage(session);        
		//设置发件人邮箱地址       
		//message.setFrom(new InternetAddress("m15605216780_1@163.com"));
		message.setFrom(new InternetAddress("2861571035@qq.com"));
		 //设置收件人地址       
		message.setRecipients(RecipientType.TO,new InternetAddress[] { new InternetAddress("2628812502@qq.com") });       
		 //设置邮件标题        
		message.setSubject("这是第一封Java邮件");        
		//发送正文
		MimeMultipart mp = new MimeMultipart("mixed"); 
			MimeBodyPart mbp = new MimeBodyPart();  
			MimeBodyPart attch1 = new MimeBodyPart();
			     // 得到数据源  
			FileDataSource fds = new FileDataSource("D:\\image.jpg");  
			   // 得到附件本身并至入BodyPart  
			mbp.setDataHandler(new DataHandler(fds));  
			  // 得到文件名同样至入BodyPart  
			mbp.setFileName(fds.getName());  
			// 把这个mbp附件add进去 
			
			mp.addBodyPart(mbp);  
			mp.addBodyPart(attch1);  
			 message.setContent(mp);
			 MimeMultipart bodyMultipart = new MimeMultipart("related");  
			 attch1.setContent(bodyMultipart);
			 MimeBodyPart htmlBody = new MimeBodyPart();  
	        bodyMultipart.addBodyPart(htmlBody);  
	        //设置HTML正文  
	        htmlBody.setContent("<span style='color:red;font-size:16px'>求职信息</span><br><h4>职位：</h4><br>"  , "text/html;charset=UTF-8");  
	        message.saveChanges();
		//message.setText("内容为： 这是第一封java发送来的邮件。");       
		 //得到邮差对象        
		Transport transport = session.getTransport();        
		//连接自己的邮箱账户 
		//transport.connect("smtp.163.com","m15605216780_1@163.com" ,"742418lmlmx");
		transport.connect("smtp.qq.com","2861571035@qq.com" ,"gxprtbuehhwtddih");//密码为刚才得到的授权码        
		//发送邮件        
		transport.sendMessage(message, message.getAllRecipients());    
		}
 }