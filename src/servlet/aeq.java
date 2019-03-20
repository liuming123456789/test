package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class aeq {
	/**
	 * 判断银行卡是否正确
	 * 
	 * @param logger
	 * @param logKey
	 * @param bankCardNo
	 *            ：银行卡号
	 * @param bankId
	 *            ：银行ID
	 * @return
	 */
	public String checkBankCardNo(Logger logger, String logKey, String bankCardNo, String bankId) {
		StringBuffer sbDesc = new StringBuffer("根据银行卡号验证银行卡号是否正确|key=").append(logKey).append("|");
		logger.info 

(sbDesc.toString() + "流程开始");
		logger.info 

(sbDesc.toString() + "入参|bankCardNo=" + bankCardNo + ">bankId=" + bankId);

		String msg = "";
		try {
			// 创建HttpClient实例
			String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo= ";
			url += bankCardNo;
			url += "&cardBinCheck=true";
			StringBuilder sb = new StringBuilder();
			try {
				URL urlObject = new URL(url);
				URLConnection uc = urlObject.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				String inputLine = null;
				while ((inputLine = in.readLine()) != null) {
					sb.append(inputLine);
				}
				in.close();

				/*JSONObject jsonObject = JSONObject.fromObject(sb.toString());
				TbSleBankVo sleBank = sleBankDao.getSleBankById(logger, logKey, bankId);
				if (jsonObject.get("bank") != null && sleBank.getBank_abb() != null && !sleBank.getBank_abb().equals(jsonObject.get("bank"))) {
					logger.error(sbDesc + "开户银行和银行卡号不匹配！");
					msg = ErrorCodeUtil.CT_HEADOFFICEBANKCARDNONOTMATCH;
				} else if (jsonObject.get("bank") == null) {
					logger.error(sbDesc + "无法识别该银行卡对应的银行信息！");
					msg = ErrorCodeUtil.CT_BANKCARDNOISERROR;
				}*/

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
}
