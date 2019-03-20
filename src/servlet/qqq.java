package servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class qqq {
	public static void main(String[] args){
		  int i =0 ;
		  outer:
		  for (;true;){
			  inner:
				 for(;i<10;i++){
					 System.out.print("i= " +i);
					 if(i==2){
						 System.out.println("continue");
						 continue;
					 }
					 if(i == 3){
						 System.out.println("break");
						 i++;
						 break;
					 }
					 if(i==7){
						 System.out.println("continue outer");
						 i++;
						 continue outer;
					 }
					 if(i == 8){
						 System.out.println("break outer");
						 break outer; 
					 }
					 for(int k = 0;k<5;k++){
						 if(k == 3){
							 System.out.println( " continue inner");
							 continue inner;
						 }
					 }
				 } 
		  }
			  
		/*  ServletContext application=session.getServletContext();
          Map<String, String> loginMap = (Map<String, String>)application.getAttribute("loginMap");
          if(loginMap==null){
              loginMap = new HashMap<String, String>();
          }
          for(String key:loginMap.keySet()) {
              if (vo.getAdm_name().equals(key)) {
                  if(session.getId().equals(loginMap.get(key))) {
                      System.out.println(username+"在同一地点多次登录！");
                  }else{
                      System.out.println(username+"异地登录被拒绝！");
                      json.put("errmsg", "该用户已经异地登录！");
                      json.put("gotoUrl", "/adm/admLogout.jhtml");
                      response.getWriter().write(json.toString());
      				return;
                  }
              }
          }
          loginMap.put(vo.getAdm_name(),session.getId());
          application.setAttribute("loginMap", loginMap);
          session.setAttribute("username",vo.getAdm_name());*/
	}
}
