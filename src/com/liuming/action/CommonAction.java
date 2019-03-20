package com.liuming.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hispeed.util.CaptchaHispeed;
import com.liuming.service.ICommService;
import com.liuming.util.ConstantUtil;
import com.liuming.util.VerifyCodeUtils;
import com.liuming.vo.UtCityListVo;
import com.liuming.vo.UtProvListVo;
import com.liuming.vo.UtStuInfoVo;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 公共Crontroll
 * 
 * @author LI
 * 
 */

@Controller
@RequestMapping(value = "/comm")
public class CommonAction {

	// 日志
	private static Logger log = Logger.getLogger("logger");

	@Resource(name = "multipartResolver")
	private CommonsMultipartResolver cmmonMultipartResolver;

	@Autowired
	private ICommService CommService;

	public void setCommService(ICommService commservice) {
		this.CommService = commservice;
	}

	/**
	 * 生成页面验证码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/GetVcode.jhtml")
	public void GetVcode(HttpServletRequest request, HttpServletResponse response) {
		log.info("[登陆页验证码]流程开始");
		try {

			/**
			 * 设置页面不缓存
			 */
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");

			/**
			 * 验证码图象生成
			 */
			String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

			// 将生成的图片验证码对应的字符保存的Session
			HttpSession session = request.getSession(true);
			session.removeAttribute(ConstantUtil.C_SESS_VCODE);
	        session.setAttribute(ConstantUtil.C_SESS_VCODE, verifyCode.toLowerCase()); 
	        int w = 100, h = 30; 
	        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode); 
		} catch (Exception e) {
			log.error("[登陆页验证码]抛异常>>" + e.toString());
		}

	}

	/**
	 * 跳转省份列表字典
	 * 
	 * @param request
	 * @param response
	 * @return 页面provListShow
	 */
	@RequestMapping(value = "/provListShow.jhtm")
	public String provListShow(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer("省分列表查询");
		log.info(sb.toString() + "流程开始");

		try {

			List<UtProvListVo> provList = CommService.getProvList();
			/**
			 * 响应参数给页面
			 */
		/*	jsp和action都是服务端程序，本质上都是servlet对象。
			这个问题中讨论的“request与response”，是servlet容器中的java对象，和http协议的名称相似，是方便理解的。不等于http协议的“request与response”。
			request.setAttribute(); 就是servlet容器中servlet对象直接传递数据的一个方法，是servlet容器标准定义的*/
			request.setAttribute("provList", provList);

		} catch (Exception e) {

			e.printStackTrace();
			log.error("抛异常" + e.toString());
		}
		return "/page/provListShow";

	}

	@RequestMapping(value = "/getProvList.jhtml")
	public void getProvList(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
			response.setContentType("application/json");
			List<UtProvListVo> provList = CommService.getProvList();
			JSONObject json = new JSONObject();
			json.put("province", provList);
			//用户最终看到的都是由JSP编译过的html静态页面了，发送这个静态页面，才是response的事儿
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取得城市列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/cityListShow.jhtml")
	public void getCityList(HttpServletRequest request, HttpServletResponse response, String prov_id) {
		StringBuffer sb = new StringBuffer("城市列表查询");
		log.info(sb.toString() + "流程开始");

		try {
			// 定义响应的字符和数据格式
			response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
			response.setContentType("application/json");
			// 定义数据返回对象
			JSONObject json = new JSONObject();

			List<UtCityListVo> cityList = CommService.getCityList(prov_id);

			/**
			 * 响应参数到页面
			 */
			// 登录成功
			json.put("cityList", cityList);
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(sb.toString() + "抛异常>>" + e.toString());
		}

	}

	/**
	 * 跳转到学生增加页面
	 * 
	 * @param request
	 * @param response
	 * @return 学生省份城市等到增加页面
	 */
	@RequestMapping(value = "/goAddStuInfo.jhtml")
	public String goStuInfoAdd(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer("跳转到学生信息增加页面");
		log.info(sb.toString() + "流程开始");
		try {
			// 页面放入引用
			List<UtProvListVo> provList = CommService.getProvList();
			request.setAttribute("provList", provList);
			// map取出对象
			
			/*  Map<String, Object> map = new HashMap<String, Object>();
			  map.put("prov_id", provList.get(0).getProv_id());
			  map.put("province_name",provList.get(0).getProvince_name());
			  JSONObject jsonObject = JSONObject.fromObject(map);
			log.info(jsonObject+"------------------------------------");
			request.setAttribute("jsonObject", jsonObject);*/
		} catch (Exception e) {
			e.printStackTrace();
			log.error("抛异常" + e.toString());
		}
		return "/page/teee";
	}

	/**
	 * 添加学生信息
	 * 
	 * @param request
	 * @param response
	 * @param stuacc
	 *            学生账户
	 * @param stuname
	 *            学生姓名
	 * @param stupwd
	 *            学生登录密码
	 * @param stuaddress
	 *            学生联系地址
	 * @param stuidcard
	 *            学生身份证号
	 * @param stubirth
	 *            学生出生日期
	 * @param prov_id
	 *            学生籍贯
	 * @param city_id
	 *            学生籍贯
	 * @param stusex
	 *            学生性别
	 * 
	 *            return 0为成功其他情况以json.result返回
	 */
	@RequestMapping(value = "stuInfoAdd.jhtml")
	public void addStuInfo(HttpServletRequest request, HttpServletResponse response, String stuacc, String stuname, String stupwd, String stuaddress,
			String stuidcard, String stubirth, String prov_id, String city_id, String stusex, String stustate) {
		// 日志
		StringBuffer sb = new StringBuffer("添加学生信息|stuacc=").append(stuacc).append("|stuname=").append(stuname).append("|stubirth=").append(stubirth).append(
				"|stuaddress=").append(stuaddress).append("|prov_id=").append(prov_id).append("|city_id=").append(city_id).append("|stusex=").append(stusex);
		log.info(sb.toString() + "流程开始");
		JSONObject json = new JSONObject();

		try {
			// 设置json格式
			response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
			response.setContentType("application/json");
			// 参数为空检查

			String[] str = stuacc.split(",");
			String[] str2 = stuname.split(",");
			String[] str3 = stupwd.split(",");
			String[] str4 = stubirth.split(",");
			String[] str5 = stuaddress.split(",");
			String[] str6 = stuidcard.split(",");
			String[] str7 = stusex.split(",");
			String[] str8 = prov_id.split(",");
			String[] str9 = city_id.split(",");
			for (int i = 0; i < str.length; i++) {
				if (null == str[i] || null == str2[i] || null == str3[i] || null == str5[i] || null == str6[i] || null == str4[i] || null == str8[i]
						|| null == str9[i] || null == str7[i] || "".equals(str[i].trim()) || "".equals(str2[i].trim()) || "".equals(str3[i].trim())
						|| "".equals(str5[i].trim()) || "".equals(str6[i].trim()) || "".equals(str8[i].trim()) || "".equals(str9[i].trim())
						|| "".equals(str7[i].trim()) || "".equals(str4[i].trim())) {
					log.error(sb.toString() + "参数异常");
					json.put("result", "202");
					response.getWriter().write(json.toString());
				}
				log.info(sb.toString() + "参数为空校验成功");

				// 检验数据库中是否含有添加的记录
				UtStuInfoVo vo = CommService.getStuInfoVo(str[i], stustate, str6[i]);
				if (null != vo) {
					log.error(sb.toString() + "以含有该学生");
					json.put("result", "205");
					response.getWriter().write(json.toString());
					return;
				}

				log.info(sb.toString() + "没有该学生可以添加");
				// 检验密码长度
				if (str3[i].trim().length() < ConstantUtil.C_SYS_LIMITPWDLENGTH || str3[i].trim().length() > ConstantUtil.C_SYS_LAGERPWDLENGTH) {
					log.error(sb.toString() + "密码长度设置不正确");
					json.put("result", "204");
					response.getWriter().write(json.toString());
					return;
				}

				log.info(sb.toString() + "密码长度合适");
			}
			// 设置学生密码长度
			// String stupwdMd5 = Md5Util.getMd5(stupwd,
			// ConstantUtil.C_SYS_MD5KEY,
			// ConstantUtil.C_SYS_CHARSET).toUpperCase();

			// 向service层添加学生信息
			boolean asd = CommService.addStuInfo(stuacc, stuname, stupwd, stuaddress, stuidcard, stusex, prov_id, city_id, stubirth);
			if (asd) {
				log.info(sb.toString() + "添加成功");
				json.put("result", "0");
				response.getWriter().write(json.toString());
				return;
			}

			json.put("result", "911");
			response.getWriter().write(json.toString());
			log.error(sb.toString() + "信息添加失败");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(sb.toString() + e.toString());
			json.put("result", "209");
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
	 * 表单提交通过stucard pageNo进行分页查询
	 * 
	 * @param request
	 * @param response
	 * @param stucard
	 *            学生身份证号
	 * @param pageNo
	 *            页数
	 * @return 跳转到查询页面
	 */
	@RequestMapping(value = "stuInfoQuery.jhtml")
	public String stuInfoQuery(HttpServletRequest request, HttpServletResponse response, String stucard, String pageNo) {
		StringBuffer sb = new StringBuffer("学生信息分页查询|stucard=").append(stucard).append("|pageNo=").append(pageNo);
		log.info(sb.toString() + "查询开始");
		try {

			pageNo = (null == pageNo || "".equals(pageNo.trim())) ? "1" : pageNo.trim();
			// 查询list表
			List<UtStuInfoVo> stuList = CommService.getStuInfoList(stucard, Integer.parseInt(pageNo));
			// 查询有多少条含有stucard的记录
			Integer stuInfoCount = CommService.getStuInfoCount(stucard);
			stuInfoCount = (null == stuInfoCount ? 0 : stuInfoCount);
			// 总共几页
			int pageCount = (stuInfoCount % ConstantUtil.C_SYS_PAGESIZE > 0 ? (stuInfoCount / ConstantUtil.C_SYS_PAGESIZE + 1) : stuInfoCount
					/ ConstantUtil.C_SYS_PAGESIZE);

			log.info("-------------------------------------------------------" + stuInfoCount + stuList);
			// 返回给页面的信息
			request.setAttribute("stuList", stuList);
			request.setAttribute("stucard", stucard);
			request.setAttribute("stuInfoCount", stuInfoCount);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("href1", "/comm/stuDow.jhtml?stu_card=" + stucard);
			request.setAttribute("href2", "/comm/stuDowCsv.jhtml?stu_card=" + stucard);
			request.setAttribute("href3", "/comm/stuDowTxt.jhtml?stu_card=" + stucard);
			request.setAttribute("pageCount", pageCount);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(sb.toString() + e.toString());
		}
		return "/page/stuInfoQuery";
	}

	// 批量删除学生信息
	/**
	 * 通过学生id对学生进行批量删除
	 * 
	 * @param request
	 * @param response
	 * @param stu_id
	 *            学生id
	 * @return 成功返回0则操作成功不成功怎反异常
	 */
	@RequestMapping(value = "batchDelStu.jhtml")
	public void batchDelStu(HttpServletRequest request, HttpServletResponse response, String stu_id) {
		StringBuffer sb = new StringBuffer("批量删除|stu_id=").append(stu_id);
		log.info(sb.toString() + "流程开始");
		// 设置json格式
		response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
		response.setContentType("application/json");
		JSONObject json = new JSONObject();
		try {
			// 在service层进行删除
			CommService.batchDelStu(stu_id);

			log.info(sb.toString() + "批量删除成功");
			json.put("result", "0");
			response.getWriter().write(json.toString());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(sb.toString() + e.toString());
			json.put("result", "209");
			try {
				response.getWriter().write(json.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/*
	 * // 单个删除学生信息
	 *//**
	 * 通过学生id对学生进行单个删除
	 * 
	 * @param request
	 * @param response
	 * @param stu_id
	 *            学生id
	 * @return 成功返回0则操作成功不成功怎反异常
	 */
	/*
	 * @RequestMapping(value = "delStu.jhtml") public void
	 * delStu(HttpServletRequest request, HttpServletResponse response, String
	 * stu_id) { StringBuffer sb = new
	 * StringBuffer("单个删除|stu_id=").append(stu_id); log.info(sb.toString() +
	 * "流程开始"); // 设置json格式
	 * response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
	 * response.setContentType("application/json"); JSONObject json = new
	 * JSONObject(); try { // 在service层进行删除 CommService.delStu(stu_id);
	 * 
	 * log.info(sb.toString() + "批量删除成功"); json.put("result", "0");
	 * response.getWriter().write(json.toString()); return; } catch (Exception
	 * e) { e.printStackTrace(); log.error(sb.toString() + e.toString());
	 * json.put("result", "209"); try {
	 * response.getWriter().write(json.toString()); } catch (IOException e1) {
	 * // TODO Auto-generated catch block e1.printStackTrace(); } } }
	 */
	// 批量修改学生信息
	/**
	 * 通过学生id对学生进行批量修改
	 * 
	 * @param request
	 * @param response
	 * @param stu_id
	 *            学生id
	 * @return 成功返回0则操作成功不成功怎反异常
	 */
	@RequestMapping(value = "batchChangeStuPwd.jhtml")
	public void batchChangeStuPwd(HttpServletRequest request, HttpServletResponse response, String stu_id) {
		StringBuffer sb = new StringBuffer("批量修改|stu_id=").append(stu_id);
		log.info(sb.toString() + "流程开始");
		// 设置json格式
		response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
		response.setContentType("application/json");
		JSONObject json = new JSONObject();
		try {
			// 在service层进行修改
			CommService.batchChangeStuPwd(stu_id);

			log.info(sb.toString() + "批量修改成功");
			json.put("result", "0");
			response.getWriter().write(json.toString());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(sb.toString() + e.toString());
			json.put("result", "209");
			try {
				response.getWriter().write(json.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	/**
	 * 批量下载
	 * @param request
	 * @param response
	 * @param stu_id
	 * @throws IOException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "stuDow.jhtml")
	public @ResponseBody void downStu(HttpServletRequest request, HttpServletResponse response, String stu_id,String stu_card) throws IOException {
		StringBuffer sb = new StringBuffer("批量下载|stucard=").append(stu_card);
		log.info(sb.toString() + "流程开始"); 
/* //	    String path = System.getProperty( "user.dir" ) ;
			File file = new File("D:\\23.xls");
 	    File file = new File(path);
		file.createNewFile();
		file.delete(); 
		if(file.exists()){
			try {
				ServletOutputStream out=response.getOutputStream();
				response.setContentType("application/x-msdownload");  
				response.setHeader("Content-Disposition", "attachment; filename="+ new String(file.getName().getBytes("GBK"),"ISO-8859-1"));
				int fileLength = (int) file.length();  
				response.setContentLength(fileLength);  
				if (fileLength != 0) {  
					InputStream ins;  
					try {  
						ins = new FileInputStream(file);  
						byte[] buf = new byte[4096];  
						ServletOutputStream sos;  
						try {  
							sos = response.getOutputStream();  
							int readLength;  
							while (((readLength = ins.read(buf)) != -1)) {  
								sos.write(buf, 0, readLength);  
							}  
							ins.close();  
							sos.flush();  
							sos.close();  
						} catch (IOException e) {  
							e.printStackTrace();  
						}  
					} catch (FileNotFoundException e) {  
						e.printStackTrace();  
					}  
					
//			CommService.downStu(stu_id,out); 
//			log.info(sb.toString() + "批量下载成功"); 
//			return;
				} catch (Exception e) {
			e.printStackTrace();
			log.error(sb.toString() + e.toString());
			json.put("result", "209");
		}
			}catch (FileNotFoundException e) {  
				e.printStackTrace();  
			}  
		}*/
		 response.setContentType("application/binary;charset=UTF-8");
		 try{
			String[] titles = { "用户编号", "用户姓名", "用户密码", "身份证号","地址","性别","出生日期","省份","城市" };
			File file = CommService.downStu(titles,stu_card,request); 
//			FileInputStream fis = new FileInputStream(file);
//			ServletOutputStream out = response.getOutputStream();
//			String fileName=new String(("stuInfo "+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(),"gbk");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			int fileLength = (int) file.length();  
			response.setContentLength(fileLength);  
			if (fileLength != 0) {  
				InputStream ins;  
				try {  
					ins = new FileInputStream(file);  
					byte[] buf = new byte[4096];  
					ServletOutputStream sos;  
					try {  
						sos = response.getOutputStream();
						int readLength;  
						while (((readLength = ins.read(buf)) != -1)) {  
							sos.write(buf, 0, readLength);  
						}  
						ins.close();  
						sos.flush();  
						sos.close();  
					} catch (IOException e) {  
						e.printStackTrace();  
					}  
				} catch (FileNotFoundException e) {  
					e.printStackTrace();  
				}  
			}
		 }catch (Exception e) {
			 e.printStackTrace();
		}
	}
	/**
	 * 导出text 文件
	 * @param request
	 * @param response
	 * @param stu_id
	 * @param stu_card
	 * @throws IOException
	 */
	@RequestMapping(value = "stuDowTxt.jhtml")
	public @ResponseBody void downStuTxt(HttpServletRequest request, HttpServletResponse response, String stu_id,String stu_card) throws IOException {
		StringBuffer sb = new StringBuffer("批量下载|stucard=").append(stu_card);
		log.info(sb.toString() + "流程开始");  
		 response.setContentType("application/binary;charset=UTF-8");
		 try{
			String[] titles = { "用户编号", "用户姓名", "用户密码", "身份证号","地址","性别","出生日期","省份","城市" };
			File file = CommService.downStuTxt(titles,stu_card,request);  
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			int fileLength = (int) file.length();  
			response.setContentLength(fileLength);  
			if (fileLength != 0) {  
				InputStream ins;  
				try {  
					ins = new FileInputStream(file);  
					byte[] buf = new byte[4096];  
					ServletOutputStream sos;  
					try {     
						sos = response.getOutputStream();  
						int readLength;  
						while (((readLength = ins.read(buf)) != -1)) {  
							sos.write(buf, 0, readLength);  
						}  
						ins.close();  
						sos.flush();  
						sos.close();  
					} catch (IOException e) {  
						e.printStackTrace();  
					}  
				} catch (FileNotFoundException e) {  
					e.printStackTrace();  
				}  
			}
		 }catch (Exception e) {
			 e.printStackTrace();
		}
	}
	/**
	 * 导出csv文件
	 * @param request
	 * @param response
	 * @param stu_id
	 * @param stu_card
	 * @throws IOException
	 */
	@RequestMapping(value = "stuDowCsv.jhtml")
	public @ResponseBody void downStuCsv(HttpServletRequest request, HttpServletResponse response, String stu_id,String stu_card) throws IOException {
		StringBuffer sb = new StringBuffer("批量下载|stucard=").append(stu_card);
		log.info(sb.toString() + "流程开始");  
		 response.setContentType("application/binary;charset=UTF-8");
		 try{
			String[] titles = { "用户编号", "用户姓名", "用户密码", "身份证号","地址","性别","出生日期","省份","城市" };
			File file = CommService.downStuCsv(titles,stu_card,request);  
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			int fileLength = (int) file.length();  
			response.setContentLength(fileLength);  
			if (fileLength != 0) {  
				InputStream ins;  
				try {  
					ins = new FileInputStream(file);  
					byte[] buf = new byte[4096];  
					ServletOutputStream sos;  
					try {  
						sos = response.getOutputStream();  
						int readLength;  
						while (((readLength = ins.read(buf)) != -1)) {  
							sos.write(buf, 0, readLength);  
						}  
						ins.close();  
						sos.flush();  
						sos.close();  
					} catch (IOException e) {  
						e.printStackTrace();  
					}  
				} catch (FileNotFoundException e) {  
					e.printStackTrace();  
				}  
			}
		 }catch (Exception e) {
			 e.printStackTrace();
		}
	}
}
