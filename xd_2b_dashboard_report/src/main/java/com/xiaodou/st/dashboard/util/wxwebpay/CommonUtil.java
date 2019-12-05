package com.xiaodou.st.dashboard.util.wxwebpay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 第二步 : 通用工具类 CommonUtil （用于请求接口） 发送https请求
 * 
 * @author 李德洪
 * @date 2016年12月7日
 */
public class CommonUtil {

  /**
   * 
   * @param requestUrl 请求地址
   * @param requestMethod 请求方式（GET、POST）
   * @param outputStr 提交的数据
   * @return 返回微信服务器响应的信息
   */
  public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
    try {
      // 创建SSLContext对象，并使用我们指定的信任管理器初始化
      TrustManager[] tm = {new MyX509TrustManager()};
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new SecureRandom());
      // 从上述SSLContext对象中得到SSLSocketFactory对象
      SSLSocketFactory ssf = sslContext.getSocketFactory();
      URL url = new URL(requestUrl);
      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
      conn.setSSLSocketFactory(ssf);
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      // 设置请求方式（GET/POST）
      conn.setRequestMethod(requestMethod);
      conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
      // 当outputStr不为null时向输出流写数据
      if (null != outputStr) {
        OutputStream outputStream = conn.getOutputStream();
        // 注意编码格式
        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }
      // 从输入流读取返回内容
      InputStream inputStream = conn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String str = null;
      StringBuffer buffer = new StringBuffer();
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      // 释放资源
      bufferedReader.close();
      inputStreamReader.close();
      inputStream.close();
      inputStream = null;
      conn.disconnect();
      return buffer.toString();
    } catch (ConnectException ce) {
      LoggerUtil.error("连接超时：{}", ce);
    } catch (Exception e) {
      LoggerUtil.error("https请求异常：{}", e);
    }
    return null;
  }

  public static String urlEncodeUTF8(String source) {
    String result = source;
    try {
      result = java.net.URLEncoder.encode(source, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return result;
  }
  
  /**
   * 
   * @description 获取用户真实IP
   * @author 李德洪
   * @Date 2017年5月18日
   * @param request
   * @return
   */
  public static String getIpAddress(HttpServletRequest request) {  
    String ip = request.getHeader("x-forwarded-for");  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("Proxy-Client-IP");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("WL-Proxy-Client-IP");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("HTTP_CLIENT_IP");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
    }  
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        ip = request.getRemoteAddr();  
    }  
    return ip;  
}  
  
  
  
}
