package com.liuming.service.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuming.dao.IAdmDao;
import com.liuming.dao.ICityDao;
import com.liuming.dao.IProvDao;
import com.liuming.dao.IStuDao;
import com.liuming.service.ICommService;
import com.liuming.util.ConstantUtil;
import com.liuming.util.Md5Util;
import com.liuming.vo.UtAdmInfoVo;
import com.liuming.vo.UtCityListVo;
import com.liuming.vo.UtProvListVo;
import com.liuming.vo.UtStuInfoVo;

/**
 * 用户service
 * 
 * @author liumi
 * 
 */

@Service(value = "CommService")
@Transactional
public class CommService implements ICommService {

	// 得到用户dao
	@Autowired
	private IAdmDao admDao;

	public void setAdmDao(IAdmDao adminDao) {
		this.admDao = adminDao;
	}

	// 得到省份Dao
	@Autowired
	private IProvDao provDao;

	public void setProvDao(IProvDao provinceDao) {
		this.provDao = provinceDao;
	}

	// 得到城市Dao
	@Autowired
	private ICityDao cityDao;

	public void setCityDao(ICityDao cityDao) {
		this.cityDao = cityDao;
	}

	// 得到学生dao
	@Autowired
	private IStuDao stuDao;

	public void setStuDao(IStuDao stuDao) {
		this.stuDao = stuDao;
	}

	/**
	 * 通过用户账号得到用户实体
	 * 
	 * @param adm_acc
	 *            用户账号
	 * @return 用户实体
	 * 
	 * 
	 */
	public UtAdmInfoVo getAdmVo(String adm_acc) {
		return admDao.getAdmVo(adm_acc);
	}

	/**
	 * 通过用户账号得到vo表然后用新密码进行修改密码
	 * 
	 * @param adm_acc
	 *            用户账号
	 * @prama pwdNew 新密码
	 * @return 如果成功则返回true
	 */
	public boolean getAdmPwdChange(String adm_acc, String pwdNew) {
		UtAdmInfoVo vo = admDao.getAdmVo(adm_acc);
		String inPwdSign = Md5Util.getMd5(pwdNew, ConstantUtil.C_SYS_MD5KEY, ConstantUtil.C_SYS_CHARSET).toUpperCase();
		vo.setAdm_pwd(inPwdSign);
		return true;
	}

	/**
	 * 得到省份字典
	 */

	public List<UtProvListVo> getProvList() {

		return provDao.getProvList();
	}

	/**
	 * 得到城市字典
	 */
	public List<UtCityListVo> getCityList(String prov_id) {

		return cityDao.getCityList(prov_id);
	}

	/**
	 * 通过以下信息向学生信息表中添加信息
	 * 
	 * @param stuacc
	 *            学生账号
	 * @param stuname
	 *            学生姓名
	 * @param stupwdMd5
	 *            加密密码
	 * @param stuaddress
	 *            学生地址
	 * @param stuidcard
	 *            学生身份证号
	 * @param stubirth
	 *            学生出生日期
	 * @param stuid
	 *            学生id
	 * @param stustate
	 *            学生状态
	 * @param stusex
	 *            学生性别
	 * @param prov_id
	 *            省份id
	 * @param city_id
	 *            城市id
	 */
	public boolean addStuInfo(String stuacc, String stuname, String stupwd, String stuaddress, String stuidcard, String stusex, String prov_id, String city_id,
			String stubirth) {

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
			UtStuInfoVo vo = new UtStuInfoVo();
			vo.setStu_acc(str[i]);
			vo.setStu_name(str2[i]);
			vo.setStu_address(str5[i]);
			vo.setStu_card(str6[i]);
			vo.setStu_pwd(Md5Util.getMd5(str3[i], ConstantUtil.C_SYS_MD5KEY, ConstantUtil.C_SYS_CHARSET).toUpperCase());
			vo.setStu_birth(str4[i]);
			vo.setStu_sex(str7[i]);
			vo.setStu_state("0");
			vo.setProv_id(str8[i]);
			vo.setCity_id(str9[i]);
			stuDao.addStuInfoVo(vo);
		}
		return true;
	}

	/**
	 * 向vo表查询含有以下参数的列表
	 * 
	 * @param stuacc
	 * @param stustate
	 * @param stuidcard
	 * @return 如果存在该信息怎返回true
	 */
	public UtStuInfoVo getStuInfoVo(String stuacc, String stustate, String stuidcard) {
		// TODO Auto-generated method stub
		return stuDao.getStuInfoVo(stuacc, stustate, stuidcard);

	}

	/**
	 * 得到学生分页表
	 * 
	 * @param stucard
	 *            学生身份证号
	 * @param pageNo
	 *            学生表页面页数
	 * @return stuDao的list
	 */
	public List<UtStuInfoVo> getStuInfoList(String stucard, int pageNo) {
		// TODO Auto-generated method stub
		return stuDao.getStuInfoList(stucard, pageNo);
	}

	/**
	 * 得到页数
	 */
	public Integer getStuInfoCount(String stucard) {

		return stuDao.getStuInfoCount(stucard);
	}

	/**
	 * 删除学生信息
	 */
	public void batchDelStu(String stu_id) {
		String[] str = stu_id.split(",");
		if (null == str || str.length == 0) {
			return;
		}
		UtStuInfoVo vo = null;
		for (int i = 0; i < str.length; i++) {
			if (null == str[i]) {
				continue;
			}
			vo = stuDao.getStuInfoVoAndLock(str[i]);
			if (null == vo) {
				continue;
			}
			stuDao.batchDelStu(vo);
		}
	}

	/**
	 * 重置密码
	 */
	public void batchChangeStuPwd(String stu_id) {
		String[] str = stu_id.split(",");
		if (null == str || str.length == 0) {
			return;
		}
		UtStuInfoVo vo = null;
		for (int i = 0; i < str.length; i++) {
			if (null == str[i]) {
				continue;
			}
			vo = stuDao.getStuInfoVoAndLock(str[i]);
			if (null == vo) {
				continue;

			}
			vo.setStu_pwd(Md5Util.getMd5("000000", ConstantUtil.C_SYS_MD5KEY, ConstantUtil.C_SYS_CHARSET).toUpperCase());
			// stuDao.addStuInfoVo(vo);
		}
	}

	/**
 *  
 */
	public File downStu(String[] titles, String stu_card,HttpServletRequest request) {
		//创建文件夹
		String path = System.getProperty("user.dir") + "\\download.xls";
		File file = new File(path);// Excel文件生成后存储的位置。
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			// 建一个03的工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 工作簿中添加表
			HSSFSheet hssfSheet = workbook.createSheet("sheet1");
			HSSFRow hssfRow1 = hssfSheet.createRow(0);
			HSSFCell hssfCell = null;
			for (int i = 0; i < titles.length; i++) {
				hssfCell = hssfRow1.createCell(i);
				hssfCell.setCellValue(titles[i]);
			}
             List<UtStuInfoVo> list = stuDao.getStuInfoVoByCard(stu_card);
                for(int i = 0 ; i<list.size();i++){
				HSSFRow hssfRow = hssfSheet.createRow(i + 1);
				hssfRow.createCell(0).setCellValue(list.get(i).getStu_id());
				hssfRow.createCell(1).setCellValue(list.get(i).getStu_name());
				hssfRow.createCell(2).setCellValue(list.get(i).getStu_pwd());
				hssfRow.createCell(3).setCellValue(list.get(i).getStu_card());
				hssfRow.createCell(4).setCellValue(list.get(i).getStu_address());
				hssfRow.createCell(5).setCellValue(list.get(i).getStu_sex());
				hssfRow.createCell(6).setCellValue(list.get(i).getStu_birth());
				hssfRow.createCell(7).setCellValue(list.get(i).getProv_id());
				hssfRow.createCell(8).setCellValue(list.get(i).getCity_id());
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				workbook.write(os);
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] content = os.toByteArray();
			OutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				fos.write(content);
				os.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return file;

	}

	public File downStuTxt(String[] titles, String stu_card, HttpServletRequest request) {
		String path = System.getProperty("user.dir") + "\\download.txt";
		File file = new File(path);// Excel文件生成后存储的位置。
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		try {  
			StringBuffer write = new StringBuffer();
			String enter = "\r\n";  
			String title =   "用户编号"+"用户姓名"+ "用户密码"+ "身份证号"+"地址"+"性别"+"出生日期"+"省份"+"城市"  ;
			write.append(title); 
			write.append(enter); 
			List<UtStuInfoVo> list = stuDao.getStuInfoVoByCard(stu_card);
			for (int i = 0; i < list.size(); i++) { 
			      write.append(list.get(i).getStu_id()+","); 
			      write.append(list.get(i).getStu_name()+","); 
			      write.append(list.get(i).getStu_pwd()+","); 
			      write.append(list.get(i).getStu_card()+","); 
			      write.append(list.get(i).getStu_address()+","); 
			      write.append(list.get(i).getStu_sex()+","); 
			      write.append(list.get(i).getStu_birth()+","); 
			      write.append(list.get(i).getProv_id()+","); 
			      write.append(list.get(i).getCity_id()); 
			      write.append(enter);   
			     } 
			/*ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				workbook.write(os);
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] content = os.toByteArray();*/
			OutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				fos.write(write.toString().getBytes("UTF-8"));
				//os.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public File downStuCsv(String[] titles, String stu_card, HttpServletRequest request) {
		String path = System.getProperty("user.dir") + "\\download.csv";
		File file = new File(path);// Excel文件生成后存储的位置。
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		try {  
			StringBuffer write = new StringBuffer();
			String enter = "\r\n";  
		List<UtStuInfoVo> list = stuDao.getStuInfoVoByCard(stu_card);
		for (int i = 0; i < list.size(); i++) { 
		      write.append(list.get(i).getStu_id()+","); 
		      write.append(list.get(i).getStu_name()+","); 
		      write.append(list.get(i).getStu_pwd()+","); 
		      write.append(list.get(i).getStu_card()+","); 
		      write.append(list.get(i).getStu_address()+","); 
		      write.append(list.get(i).getStu_sex()+","); 
		      write.append(list.get(i).getStu_birth()+","); 
		      write.append(list.get(i).getProv_id()+","); 
		      write.append(list.get(i).getCity_id()+","); 
		      write.append(enter);   
		     } 
		/*ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();*/
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(write.toString().getBytes("UTF-8"));
			//os.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return file; 
	}

	/*
	 * public void delStu(String stu_id) { UtStuInfoVo vo =
	 * stuDao.getStuInfoVoAndLock(stu_id); stuDao.batchDelStu(vo);
	 * 
	 * }
	 */

}
