package com.liuming.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.liuming.service.ICommService;
import com.liuming.util.ConstantUtil;
import com.liuming.util.StringUtil;
import com.liuming.vo.UtStuInfoVo;

@Controller
@RequestMapping(value = "/upLoad")
public class UpAction {
	private static Logger log = Logger.getLogger("logger");
	@Autowired
	private ICommService CommService;

	public void setCommonService(ICommService commservice) {
		this.CommService = commservice;
	}

	/**
	 * EXCEL 2003 和2010兼容上传
	 * 
	 * @param request
	 * @param response
	 * @param file
	 *            文件
	 * @param stustate
	 */
	@RequestMapping(value = "/excelUpLoad.jhtml")
	public void upExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") CommonsMultipartFile file, String stustate) {
		StringBuffer sb = new StringBuffer("导入excel");
		log.info(sb.toString() + "流程开始");
		// 文件名
		String fileName = file.getOriginalFilename();
		Workbook workbook = null;
		response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
		response.setContentType("application/json");
		JSONObject json = new JSONObject();
		try {
			// 以后缀作为判断将输入流做兼容放入HSSFWorkbook和XSSFWorkbook中
			if (fileName.endsWith(ConstantUtil.OFFICE_EXCEL_2003_POSTFIX)) {
				workbook = new HSSFWorkbook(file.getInputStream());
				log.info("2003版");
				json.put("result", "2003");
			} else if (fileName.endsWith(ConstantUtil.OFFICE_EXCEL_2010_POSTFIX)) {
				workbook = new XSSFWorkbook(file.getInputStream());
				log.info("2010版");
				json.put("result", "2010");
			} else {
				log.info("该文件不是Excel文件");
				json.put("result", "000");
				response.getWriter().write(json.toString());
				return;
			}
			// 找到Excel表
			Sheet sheet = workbook.getSheet("Sheet1");
			// log.info(sheet+"-------------------------------");
			// 得到行数
			int rows = sheet.getLastRowNum();
			// 遍历行取得单元格里的值
			for (int j = 0; j < rows + 1; j++) {
				Row row = sheet.getRow(j);
				String stuacc = StringUtil.getCellValue(row.getCell(0));
				String stuname = StringUtil.getCellValue(row.getCell(1));
				String stupwd = StringUtil.getCellValue(row.getCell(2));
				String stuidcard = StringUtil.getCellValue(row.getCell(3));
				String stuaddress = StringUtil.getCellValue(row.getCell(4));
				String stusex = StringUtil.getCellValue(row.getCell(5));
				String stubirth = StringUtil.getCellValue(row.getCell(6));
				String prov_id = StringUtil.getCellValue(row.getCell(7));
				String city_id = StringUtil.getCellValue(row.getCell(8));

				/*
				 * String[] str = stuacc.split(","); String[] str2 =
				 * stuname.split(","); String[] str3 = stupwd.split(",");
				 * String[] str4 = stubirth.split(","); String[] str5 =
				 * stuaddress.split(","); String[] str6 = stuidcard.split(",");
				 * String[] str7 = stusex.split(","); String[] str8 =
				 * prov_id.split(","); String[] str9 = city_id.split(","); for
				 * (int i = 0; i < str.length; i++) { if (null == str[i] || null
				 * == str2[i] || null == str3[i] || null == str5[i] || null ==
				 * str6[i] || null == str4[i] || null == str8[i] || null ==
				 * str9[i] || null == str7[i] || "".equals(str[i].trim()) ||
				 * "".equals(str2[i].trim()) || "".equals(str3[i].trim()) ||
				 * "".equals(str5[i].trim()) || "".equals(str6[i].trim()) ||
				 * "".equals(str8[i].trim()) || "".equals(str9[i].trim()) ||
				 * "".equals(str7[i].trim()) || "".equals(str4[i].trim())) {
				 * log.error(sb.toString() + "参数异常"); json.put("result", "202");
				 * response.getWriter().write(json.toString()); return; }
				 * 
				 * log.info(sb.toString() + "参数为空校验成功");
				 * 
				 * // 检验数据库中是否含有添加的记录 UtStuInfoVo vo =
				 * CommService.getStuInfoVo(str[i], stustate, str6[i]); if (null
				 * != vo) { log.error(sb.toString() + "以含有该学生");
				 * json.put("result", "205");
				 * response.getWriter().write(json.toString()); return; }
				 * 
				 * log.info(sb.toString() + "没有该学生可以添加"); // 检验密码长度 if
				 * (str3[i].trim().length() < ConstantUtil.C_SYS_LIMITPWDLENGTH
				 * || str3[i].trim().length() >
				 * ConstantUtil.C_SYS_LAGERPWDLENGTH) { log.error(sb.toString()
				 * + "密码长度设置不正确"); json.put("result", "204");
				 * response.getWriter().write(json.toString()); return; }
				 * 
				 * log.info(sb.toString() + "密码长度合适"); }
				 */

				if (null == stuacc || null == stuname || null == stupwd || null == stuaddress || null == stubirth || null == stusex || null == prov_id
						|| null == city_id || null == stuidcard || "".equals(stuacc.trim()) || "".equals(stuname.trim()) || "".equals(stupwd.trim())
						|| "".equals(stuaddress.trim()) || "".equals(stubirth.trim()) || "".equals(stusex.trim()) || "".equals(prov_id.trim())
						|| "".equals(city_id.trim()) || "".equals(stuidcard.trim())) {
					log.error(sb.toString() + "参数异常");
					json.put("result", "202");
					response.getWriter().write(json.toString());
					continue;
				}

				log.info(sb.toString() + "参数为空校验成功");

				// 检验数据库中是否含有添加的记录
				UtStuInfoVo vo = CommService.getStuInfoVo(stuacc, stustate, stuidcard);
				if (null != vo) {
					log.error(sb.toString() + "以含有该学生");
					json.put("result", "205");
					response.getWriter().write(json.toString());
					continue;
				}

				log.info(sb.toString() + "没有该学生可以添加");
				// 检验密码长度
				if (stupwd.trim().length() < ConstantUtil.C_SYS_LIMITPWDLENGTH || stupwd.trim().length() > ConstantUtil.C_SYS_LAGERPWDLENGTH) {
					log.error(sb.toString() + "密码长度设置不正确");
					json.put("result", "204");
					response.getWriter().write(json.toString());
					return;
				}

				log.info(sb.toString() + "密码长度合适");

				boolean asd = CommService.addStuInfo(stuacc, stuname, stupwd, stuaddress, stuidcard, stusex, prov_id, city_id, stubirth);
				if (asd) {
					log.info(sb.toString() + "添加成功");
					json.put("result", "0");
					response.getWriter().write(json.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(sb.toString() + e.toString());
		}
		return;
	}

	/**
	 * 上传text文件
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @param stustate
	 * @param filePath 
	 */
	@RequestMapping(value = "/textUpLoad.jhtml")
	public void upText(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") CommonsMultipartFile file, String stustate ) {
		StringBuffer sb = new StringBuffer("导入text");
		log.info(sb.toString() + "流程开始");
		String fileName = file.getOriginalFilename();
		response.setCharacterEncoding(ConstantUtil.C_SYS_CHARSET);
		response.setContentType("application/json");
		JSONObject json = new JSONObject();
		try {

			if (fileName.endsWith(ConstantUtil.TEXT_POSTFIX)) {
				// 拿到输入流
				InputStream inputStream = file.getInputStream();
				// available拿到字节总数
/*				byte[] by = new byte[is.available()];
				// 循环读取文件内容
				is.read(by);
				String s = new String(by);
				String[] rows = s.split("[\\p{Space}]+");
				 for (int z = 0; z < rows.length; z++) {*/
				String encoding="gbk";
				InputStreamReader read = new InputStreamReader(inputStream,encoding);
				BufferedReader br=new BufferedReader(read);
				String line="";
		        String[] columnNameLine=null;
		        while ((line=br.readLine())!=null && line.length() != 0) { 
		        	columnNameLine=line.split(","); 
					// log.info(sheet+"-------------------------------"); for (int j = 0; j < columnNameLine.length; j++) {
						String stuacc = columnNameLine[0];
						String stuname = columnNameLine[1];
						String stupwd = columnNameLine[2];
						String stuidcard = columnNameLine[3];
						String stuaddress = columnNameLine[4];
						String stusex = columnNameLine[5];
						String stubirth = columnNameLine[6];
						String prov_id = columnNameLine[7];
						String city_id = columnNameLine[8].trim();

						if (null == stuacc || null == stuname || null == stupwd || null == stuaddress || null == stubirth || null == stusex || null == prov_id
								|| null == city_id || null == stuidcard || "".equals(stuacc.trim()) || "".equals(stuname.trim()) || "".equals(stupwd.trim())
								|| "".equals(stuaddress.trim()) || "".equals(stubirth.trim()) || "".equals(stusex.trim()) || "".equals(prov_id.trim())
								|| "".equals(city_id.trim()) || "".equals(stuidcard.trim())) {
							log.error(sb.toString() + "参数异常");
							json.put("result", "202");
							response.getWriter().write(json.toString());
							continue ;
						}

						log.info(sb.toString() + "参数为空校验成功");

						// 检验数据库中是否含有添加的记录
						UtStuInfoVo vo = CommService.getStuInfoVo(stuacc, stustate, stuidcard);
						if (null != vo) {
							log.error(sb.toString() + "以含有该学生");
							json.put("result", "205");
							response.getWriter().write(json.toString());
							continue ;
						}

						log.info(sb.toString() + "没有该学生可以添加");
						// 检验密码长度
						if (stupwd.trim().length() < ConstantUtil.C_SYS_LIMITPWDLENGTH || stupwd.trim().length() > ConstantUtil.C_SYS_LAGERPWDLENGTH) {
							log.error(sb.toString() + "密码长度设置不正确");
							json.put("result", "204");
							response.getWriter().write(json.toString());
							continue ;
						}

						log.info(sb.toString() + "密码长度合适");

						boolean asd = CommService.addStuInfo(stuacc, stuname, stupwd, stuaddress, stuidcard, stusex, prov_id, city_id, stubirth);
						if (asd) {
							log.info(sb.toString() + "添加成功");
							json.put("result", "0");
							response.getWriter().write(json.toString());
						} 
				}
				/*String filePath = System.getProperty("user.dir") + "\\download.txt";
				File file1 = new File(filePath);
		    	if(!file1.getParentFile().exists()){
		    		file1.createNewFile();
		    		//file1.mkdirs();
		    	}
				FileOutputStream fos = new FileOutputStream(file); 
				byte buffer[] = new byte[1024];
				 int length = 0;
				 while((length = inputStream.read(buffer))>0){
					 fos.write(buffer, 0, length);              
				 }
			inputStream.close();
			 fos.close();*/
			} else {
				log.info("该文件不是text文件");
				json.put("result", "010");
				response.getWriter().write(json.toString());
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(sb.toString() + e.toString());
		}
		return;
	}

}
