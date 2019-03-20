package com.liuming.action;

import com.liuming.service.ICommService;
import com.liuming.util.ConstantUtil;
import com.liuming.util.Md5Util;
import com.liuming.vo.UtAdmInfoVo;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/adm")
public class AdminAction {
	private static Logger log = Logger.getLogger("logger");
	@Autowired
	private ICommService CommService;

	public void setCommService(ICommService commservice) {
		this.CommService = commservice;
	}

	/**
	 * 管理员登录
	 * 
	 * @param request
	 * @param response
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param vcodeIn
	 *            验证码
	 */
	@RequestMapping(value = "/admLogin.jhtml")
	@ResponseBody
	public void admLogin(HttpServletRequest request, HttpServletResponse response, String username, String password, String vcodeIn) {

		// StringBuffer sbdc = new
		// StringBuffer("登录流程开始").append(username).append(vcodeIn).append("------------------");
		StringBuffer sbdc = new StringBuffer("登录流程开始|username=").append(username).append("|vcodeIn=").append(vcodeIn).append("|");
		log.info(sbdc.toString() + "流程开始");
		JSONObject json = new JSONObject();
		try {

			// 设置JSON格式
			/*response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
			response.setContentType("application/json");*/

			HttpSession session = request.getSession();

			// 参数为空校验
			if (null == username || null == password || "".equals(username.trim()) || "".equals(password) || null == vcodeIn || "".equals(vcodeIn.trim())) {
				log.error(sbdc.toString() + "参数异常");
				json.put("result", "202");
				response.getWriter().write(json.toString());
				return;
			}
			log.info(sbdc.toString() + "参数为空校验成功");

			// 获取Session中保存的验证码
			String vcode = (String) session.getAttribute(ConstantUtil.C_SESS_VCODE);

			// 验证码是否为空
			if (null == vcode || "".equals(vcode.trim())) {
				log.error(sbdc.toString() + "参数异常");
				json.put("result", "202");
				response.getWriter().write(json.toString());
				return; 
			}

			// 验证码校验
			if (!vcode.trim().equalsIgnoreCase(vcodeIn.trim())) {
				log.error(sbdc.toString() + "验证码错误");
				json.put("result", "207");
				response.getWriter().write(json.toString());
				return;
			}
			log.info(sbdc.toString() + "验证码校验成功");

			// 与vo表中数据进行比较
			UtAdmInfoVo vo = CommService.getAdmVo(username);
			// 判断用户是否存在
			if (null == vo) {
				log.error(sbdc.toString() + "用户名不存在");
				json.put("result", "213");
				response.getWriter().write(json.toString());
				return;
			}
			log.info(sbdc.toString() + "存在该用户");

			// 验证状态
			if (!"0".equals(vo.getAdm_state())) {
				log.error(sbdc.toString() + "用户状态不正常");
				json.put("result", "205");
				response.getWriter().write(json.toString());
				return;
			}
			log.info(sbdc.toString() + "用户正常使用");

			// 验证密码
			String inPwdSign = Md5Util.getMd5(password, ConstantUtil.C_SYS_MD5KEY, ConstantUtil.C_SYS_CHARSET).toUpperCase();
			if (!inPwdSign.equals(vo.getAdm_pwd())) {
				log.error(sbdc.toString() + "密码错误");
				json.put("result", "213");
				response.getWriter().write(json.toString());
				return;
			}
			log.info(sbdc.toString() + "密码正确");

			// 删除session表中验证码
			session.removeAttribute(ConstantUtil.C_SESS_VCODE);
			// 设置Session中用户实体
			session.setAttribute(ConstantUtil.C_SESS_ADMINVO, vo);

			json.put("result", "0");
			response.getWriter().write(json.toString());
			log.info(sbdc.toString() + "登录成功");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(sbdc.toString() + "抛异常>>" + e.toString());
			json.put("result", "999");
			try {
				response.getWriter().write(json.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}

	}

	/**
	 * 跳转到主页面
	 * 
	 * @param request
	 * @param response
	 * @return 主页面
	 */
	@RequestMapping(value = "admLgnSucc.jhtml")
	public String getAdmLgnSucc(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer("跳转主页面");
		log.info(sb.toString() + "流程开始");
		return "/page/main";

	}

	/**
	 * 管理员退出
	 * 
	 * @param request
	 * @param response
	 * @return 登录页面
	 */
	@RequestMapping(value = "admLgnout.jhtml")
	public String getAdmLgnout(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer("管理员退出");
		log.info(sb.toString() + "返回登录页面");
		try {
			// 清空session中的缓存信息
			request.getSession().invalidate();
			log.info(sb.toString() + "清空session表");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(sb.toString() + "抛异常" + e.toString());
		}
		return "/page/index";
	}

	/**
	 * 管理员修改密码
	 * 
	 * @param request
	 * @param response
	 * @param password
	 * @param pwdNew
	 * @param pwdNew2
	 * @throws IOException
	 */

	@RequestMapping(value = "admPwdChange.jhtml")
	public void getAdmPwdChange(HttpServletRequest request, HttpServletResponse response, String password, String pwdNew, String pwdNew2) throws IOException {
		StringBuffer sb = new StringBuffer("修改密码|password=").append(password).append("|").append("pwdNew=").append(pwdNew).append("|").append("pwdNew2=")
				.append(pwdNew2).append("|");
		log.info(sb.toString() + "流程开始");
		
		JSONObject json = new JSONObject();

		try {
			// 设置json格式
			response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
			response.setContentType("application/json");
			// 取得session中的vo表
			HttpSession session = request.getSession();
			UtAdmInfoVo vo = (UtAdmInfoVo) session.getAttribute(ConstantUtil.C_SESS_ADMINVO);

			// 参数为空校验
			if (null == password || null == pwdNew || null == pwdNew2 || "".equals(password.trim()) || "".equals(pwdNew.trim()) || "".equals(pwdNew2.trim())) {
				log.error(sb.toString() + "参数异常");
				json.put("result", "202");
				response.getWriter().write(json.toString());
				return;
			}
			log.info(sb.toString() + "参数为空校验成功");

			// 检验密码长度
			if (pwdNew.trim().length() < ConstantUtil.C_SYS_LIMITPWDLENGTH || pwdNew.trim().length() > ConstantUtil.C_SYS_LAGERPWDLENGTH) {
				log.error(sb.toString() + "密码长度设置不正确");
				json.put("result", "204");
				response.getWriter().write(json.toString());
				return;
			}
			log.info(sb.toString() + "密码长度合适");

			// 检查新密码和二次密码是否一致
			if (!pwdNew.trim().equals(pwdNew2.trim())) {
				log.error(sb.toString() + "两次输入密码不一样");
				json.put("result", "206");
				response.getWriter().write(json.toString());
				return;
			}
			log.info(sb.toString() + "两次密码输入一致");

			// 检查原用户密码是否正确
			String inPwdSign = Md5Util.getMd5(password, ConstantUtil.C_SYS_MD5KEY, ConstantUtil.C_SYS_CHARSET).toUpperCase();
			// String test = vo.getAdm_pwd();
			// log.info(inPwdSign+test);
			if (!inPwdSign.equalsIgnoreCase(vo.getAdm_pwd())) {
				log.error(sb.toString() + "原用户密码错误");
				json.put("result", "209");
				response.getWriter().write(json.toString());
				return;
			}
			log.info(sb.toString() + "用户原密码校验成功");

			// 判断是否修改密码成功
			boolean lm = CommService.getAdmPwdChange(vo.getAdm_acc(), pwdNew);
			if (lm) {
				log.info(sb.toString() + "密码修改成功");
				json.put("result", "0");
				response.getWriter().write(json.toString());
				return;
			}

			json.put("result", "911");
			response.getWriter().write(json.toString());
			log.error(sb.toString() + "密码修改失败");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(sb.toString() + e.toString());
			json.put("result", "209");
			response.getWriter().write(json.toString());
			return;
		}

	}
}
