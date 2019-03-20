package com.liuming.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.liuming.util.ConstantUtil;

/**
 * 用于检测用户是否登陆的过滤器，如果未登录，则重定向到指的登录页面
 * <p>
 * 配置参数
 * <p>
 * checkSessionKey 需检查的在 Session 中保存的关键字<br/>
 * redirectURL 如果用户未登录，则重定向到指定的页面，URL不包括 ContextPath<br/>
 * notCheckURLList 不做检查的URL列表，以分号分开，并且 URL 中不包括 ContextPath<br/>
 */
public class SessionFilter implements Filter {

	private static final Logger logger = Logger.getLogger("logger");
	protected FilterConfig filterConfig = null;
	private static String redirectURL = null;
	private static Set<String> notCheckURLList = null;

	/**
	 * 初始化方法
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		notCheckURLList = new HashSet<String>();
		this.filterConfig = filterConfig;
		redirectURL = filterConfig.getInitParameter("redirectURL");
		String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
		logger.info("filterConfig Init  \n redirectURL=" + redirectURL + "|notCheckURLListStr=" + notCheckURLListStr);
		String url = null;
		if (notCheckURLListStr != null) {
			StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");
			notCheckURLList.clear();
			if (redirectURL != null) {
				notCheckURLList.add(redirectURL);
			}
			while (st.hasMoreTokens()) {
				url = st.nextToken();
				notCheckURLList.add(url);
				logger.info("\nurl=" + url);
			}
		}
	}

	/**
	 * 释放资源
	 */
	public void destroy() {
		notCheckURLList.clear();
	}

	/**
	 * 完成具体的过滤工作
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		servletRequest.setCharacterEncoding("UTF8");
		servletResponse.setCharacterEncoding("UTF8");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		/**
		 * 过滤请求参数中包含js代码
		 */
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String n = (String) e.nextElement();
			String[] s = request.getParameterValues(n);
			for (int i = 0; i < s.length; i++) {
				if (s[i] != null) {
					String temp = s[i].toLowerCase().replaceAll("\\s", "");
					if (s[i].indexOf("'") >= 0 || temp.indexOf("<script>") > -1 || temp.indexOf("javascript:") > -1 || temp.indexOf("</script>") > -1 /*
																																					 * ||
																																					 * temp
																																					 * .
																																					 * matches
																																					 * (
																																					 * ".*[a-zA-Z0-9]+\\(.*\\).*"
																																					 * )
																																					 */) {
						// response.sendRedirect(request.getContextPath() +
						// "/comm.do?method=err&errorcode="+DBConstant.UNRULER_OPRATE);
						response.sendRedirect(request.getContextPath() + redirectURL);
						logger.error("request paramter include lllegal character " + n + ":" + s[i]);
						return;
					}
				}
			}
		}
		/**
		 * 登录验证
		 */
		HttpSession session = request.getSession();
		if (!checkRequestURIIntNotFilterList(request)) {

			// 用户用户登录的session vo
			Object obj = session.getAttribute(ConstantUtil.C_SESS_ADMINVO);
			if (null == obj) {
				//response.sendRedirect(request.getContextPath() + redirectURL);
				java.io.PrintWriter out = response.getWriter();  
			    out.println("<html>");  
			    out.println("<script>");  
			    out.println("window.open ('/adm/admLogout.jhtml','_top')");  
			    out.println("</script>");  
			    out.println("</html>");  

				return;
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 不作SESSION判断的.do,.jsp列表
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 */
	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		// System.out.println("uri=" + uri);
		boolean bExist = notCheckURLList.contains(uri);
		// System.out.println("uri=" + uri + ", if exists = " + bExist);
		return bExist;
	}

}
