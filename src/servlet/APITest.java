package servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

public class APITest {
	static String TEST_URL = "https://b2c.icbc.com.cn/servlet/ICBCINBSEBusinessServlet";
	static String TEST_KEY = "待定";
	static String TEST_SEC = "待定";

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		getReqParam();
		String result = getResult(TEST_URL, getReqParam());
		// 以employee为例解析，map类似
		JSONObject jb = JSONObject.fromObject(result);
		String a = jb.getString("result");
		String b = jb.getString("errmsg");
		System.out.println(a + b);
		System.out.print(result);
	}

	private static String getReqParam() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		TreeMap<String, String> req = new TreeMap<String, String>();
		String aa = "TEST018";
		req.put("a", TEST_KEY);
		req.put("f", "json");
		req.put("l", "zh_CN");
		req.put("m", "zhongan.repair.query");
		req.put("v", "1.0");
		req.put("i", "" + System.currentTimeMillis() / 1000);
		req.put("params", "{\"assignNo\":\"TEST018\"}");
		req.put("param", "{\"assignNo\"" + ":" + aa + "\"}");
		//System.out.println(req);
		// req.put("param", "{assignNo:" + aa + "}");
		//System.out.println(req);
		req.put("s", sign(req, null, TEST_SEC));

		StringBuilder param = new StringBuilder();
		for (Iterator<Map.Entry<String, String>> it = req.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> e = it.next();
			param.append("&").append(e.getKey()).append("=").append(URLEncoder.encode(e.getValue(), "UTF-8"));
		}

		System.out.println(param);

		StringBuffer par = new StringBuffer();
		for (Map.Entry<String, String> e : req.entrySet()) {
			par.append("&").append(e.getKey()).append("=").append(URLEncoder.encode(e.getValue(), "UTF-8"));
		}
		System.out.println(par);
		return param.toString().substring(1);

	}

	private static String sign(Map<String, String> paramValues, List<String> ignoreParamNames, String secret) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		List<String> paramNames = new ArrayList<String>(paramValues.size());
		paramNames.addAll(paramValues.keySet());
		if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
			for (String ignoreParamName : ignoreParamNames) {
				paramNames.remove(ignoreParamName);
			}
		}
		Collections.sort(paramNames);

		sb.append(secret);
		for (String paramName : paramNames) {
			sb.append(paramName).append(paramValues.get(paramName));
		}
		sb.append(secret);

		MessageDigest md = MessageDigest.getInstance("SHA-1");
		return byte2hex(md.digest(sb.toString().getBytes("UTF-8")));
	}

	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

	private static String getResult(String urlStr, String content) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			connection.setUseCaches(false);
			connection.connect();

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.write(content.getBytes("UTF-8"));
			out.flush();
			out.close();
			int responseCode = connection.getResponseCode();
			// 表示请求成功
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.print("OK" + responseCode);
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				reader.close();

				return buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return null;
	}

}