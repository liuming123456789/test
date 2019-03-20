package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;

import com.liuming.util.RsaUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class HttpServlet1 extends HttpServlet {
	private static Logger log = Logger.getLogger("logger");

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer sb = new StringBuffer("servlet交互");
		log.info(sb.toString() + "开始"); 
		// 设置请求编码方式
		request.setCharacterEncoding("utf-8");
		// 设置返回编码方式
		response.setContentType("text/html;charset=utf-8");
		// 传递的参数，中间使用&符号分割。
		String filepath="D:/tmp/";  
		
		//RsaUtil.genKeyPair(filepath);  
		
		
		System.out.println("--------------公钥加密私钥解密过程-------------------");   
		String admin = "admin";
		//String pwd = "1234";
		StringBuffer sd = new StringBuffer();
		sd.append(admin);
		//sd.append(pwd);
		String plainText=sd.toString();  
		//公钥加密过程  
		byte[] cipherData = null;
		
		try {
			cipherData = RsaUtil.encrypt(RsaUtil.loadPublicKeyByStr(RsaUtil.loadPublicKeyByFile(filepath)),plainText.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cipher=Base64.encode(cipherData);  
		System.out.println("原文："+plainText);  
		System.out.println("加密："+cipher);  
		// System.out.println("解密："+restr);  
		System.out.println(); 
		// 向servletB发送请求
		//String http = "http://192.168.1.103:8024/dfwl/user/httpservlet?sig=AE7AF658B4F74388FC386FB26D2604F2";  
		String http = "http://localhost:8024/dfwl/HttpServlet2?"+"cipher="+cipher.replaceAll("\\+","%2b")+"&admin_id=admin"; 
		URL url = new URL(http);
		// 生成HttpURLConnection连接
		HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
		// 设置有输出流，默认为false，就是不能传递参数。
		httpurlconnection.setDoOutput(true);
		// 设置发送请求的方式。注意：一定要大写
		httpurlconnection.setRequestMethod("POST");
		// 设置连接超时的时间。不过不设置，在网络异常的情况下，可能会造成程序僵死而无法继续向下执行，所以一般设置一个超时时间。单位为毫秒
		httpurlconnection.setConnectTimeout(1000);
		// 设置输出流。
		OutputStreamWriter writer = new OutputStreamWriter(httpurlconnection.getOutputStream(), "utf-8");
        //私钥解密过程  
       /* byte[] res=RsaUtil.decrypt(RsaUtil.loadPrivateKeyByStr(RsaUtil.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));  
        String restr=new String(res);  */
		//writer.write("cipher="+cipher.replaceAll("\\+","%2b"));
		// 用于刷新缓冲流。因为默认她会写入到内存的缓冲流中，到一定的数据量时，才会写入，使用这个命令可以让他立即写入，不然下面就到关闭流了
		writer.flush();
		// 用于关闭输出流，关闭之后就不可以输出数据了，所以要使用flush刷新缓冲流
		writer.close();
		// 获得返回的请求吗。
		int responseCode = httpurlconnection.getResponseCode();
		// 表示请求成功
		if (responseCode == HttpURLConnection.HTTP_OK) {
			log.info("OK" + responseCode);
			// 获得服务端的输出流。得到返回的数据
			//SAXReader saxReader = new SAXReader(); 
			java.io.InputStream urlstream = httpurlconnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlstream, "utf-8"));
			String line;
			String tline = "";
			while ((line = reader.readLine()) != null) {
				tline += line;
			}
			System.out.println(tline);

			/*
			 * try { Document document = DocumentHelper.parseText(tline);
			 * Element root = document.getRootElement();
			 * System.out.println(root.elementTextTrim("id")); Iterator iter =
			 * root.elementIterator("id"); System.out.println("id:" + iter);
			 * while (iter.hasNext()) { Element recordEle = (Element)
			 * iter.next(); String title = recordEle.elementTextTrim("id"); //
			 * 拿到head节点下的子节点title值 System.out.println("list:" + title); } }
			 * catch (DocumentException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

			/*
			 * try { org.dom4j.Document document = saxReader.read(reader);
			 * org.dom4j.Element user = document.getRootElement();
			 * System.out.println("数据库"); Iterator it = user.elementIterator();
			 * while (it.hasNext()) { System.out.println("=====开始遍历user=====");
			 * org.dom4j.Element list = (org.dom4j.Element) it.next();
			 * List<Attribute> list1 = list .attributes();
			 * System.out.println(list1); } } catch (DocumentException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */
		/*	String path = this.getServletContext().getRealPath("//page/index.jsp");
			System.out.println(path);*/
		} else {
			System.out.println("ERR" + responseCode);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
