package com.xiaodou.server.pay.payplatform.dc.wxpay;

import java.util.SortedMap;
import java.util.TreeMap;

import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.server.pay.constant.WeixinPayConstants;
import com.xiaodou.server.pay.payplatform.dc.wxpay.utils.GetWxOrderno;
import com.xiaodou.server.pay.payplatform.dc.wxpay.utils.RequestHandler;
import com.xiaodou.server.pay.payplatform.dc.wxpay.utils.Sha1Util;
import com.xiaodou.server.pay.payplatform.dc.wxpay.utils.TenpayUtil;


/**
 * @author zhouhuan
 * 
 */
public class WxPay {

  /**
   * 获取请求预支付id报文
   * 
   * @return
   */
  public String getPackage(WxPayDto tpWxPayDto) {
    String appid = WeixinPayConstants.getAppId(tpWxPayDto.getBusinessType());
    String partner = WeixinPayConstants.getPartner(tpWxPayDto.getBusinessType());
    String appsecret = WeixinPayConstants.getAppSecret(tpWxPayDto.getBusinessType());
    // 这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
    String partnerkey = WeixinPayConstants.getPartnerKey(tpWxPayDto.getBusinessType());
    // openId 是微信用户针对公众号的标识，授权的部分这里不解释
    String openId = "";
    // 微信支付成功后通知地址 必须要求80端口并且地址不能带参数
    String notifyurl = WeixinPayConstants.getNotifyUrl();

    /* String openId = tpWxPayDto.getOpenId(); */
    // 1 参数
    // 订单号
    String orderId = tpWxPayDto.getOrderId();
    // 附加数据 原样返回
    String attach = "";
    // 总金额以分为单位，不带小数点
    String totalFee = getMoney(tpWxPayDto.getTotalFee());

    // 订单生成的机器 IP
    String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
    // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
    String notify_url = notifyurl;
    String trade_type = "APP";

    // ---必须参数
    // 商户号
    String mch_id = partner;
    // 随机字符串
    String nonce_str = getNonceStr();

    // 商品描述根据情况修改
    String body = tpWxPayDto.getBody();

    // 商户订单号
    String out_trade_no = orderId;

    SortedMap<String, String> packageParams = new TreeMap<String, String>();
    packageParams.put("appid", appid);
    packageParams.put("mch_id", mch_id);
    packageParams.put("nonce_str", nonce_str);
    packageParams.put("body", body);
    packageParams.put("attach", attach);
    packageParams.put("out_trade_no", out_trade_no);

    // 这里写的金额为1 分到时修改
    packageParams.put("total_fee", totalFee);
    packageParams.put("spbill_create_ip", spbill_create_ip);
    packageParams.put("notify_url", notify_url);

    packageParams.put("trade_type", trade_type);
    packageParams.put("openid", openId);

    RequestHandler reqHandler = new RequestHandler(null, null);
    reqHandler.init(appid, appsecret, partnerkey);

    String sign = reqHandler.createSign(packageParams);
    String xml =
        "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>"
            + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>"
            + "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + out_trade_no
            + "</out_trade_no>" + "<attach>" + attach + "</attach>" + "<total_fee>" + totalFee
            + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
            + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
            + "</trade_type>" + "</xml>";
    String prepay_id = "";
    String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";


    prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);

    /* System.out.println("获取到的预支付ID：" + prepay_id); */


    // 获取prepay_id后，拼接最后请求支付所需要的package

    SortedMap<String, String> finalpackage = new TreeMap<String, String>();
    String timestamp = Sha1Util.getTimeStamp();
    String packages = "prepay_id=" + prepay_id;
    /*
     * finalpackage.put("appId", appid); finalpackage.put("timeStamp", timestamp);
     * finalpackage.put("nonceStr", nonce_str); finalpackage.put("package", packages);
     * finalpackage.put("partnerid",prepay_id);
     */
    /* finalpackage.put("signType", "MD5"); */

    finalpackage.put("appid", appid);
    finalpackage.put("key", sign);
    finalpackage.put("noncestr", nonce_str);
    finalpackage.put("package", "Sign=WXPay");
    finalpackage.put("partnerid", partner);
    finalpackage.put("prepayid", prepay_id);
    finalpackage.put("timestamp", timestamp);
    // 要签名

    String finalsign = reqHandler.createSign(finalpackage);
    String finaPackage =
        "\"appId\":\"" + appid + "\",\"timeStamp\":\"" + timestamp + "\",\"nonceStr\":\""
            + nonce_str + "\",\"package1\":\"" + packages + "\",\"signType\" : \"MD5"
            + "\",\"paySign\":\"" + finalsign + "\"";

    System.out.println("V3 jsApi package:" + finaPackage);
    return finaPackage;
  }

  /**
   * 获取随机字符串
   * 
   * @return
   */
  public static String getNonceStr() {
    // 随机数
    String currTime = TenpayUtil.getCurrTime();
    // 8位日期
    String strTime = currTime.substring(8, currTime.length());
    // 四位随机数
    String strRandom = TenpayUtil.buildRandom(4) + "";
    // 10位序列号,可以自行调整。
    return strTime + strRandom;
  }

  /**
   * 元转换成分
   * 
   * @param money
   * @return
   */
  public static String getMoney(String amount) {
    if (amount == null) {
      return "";
    }
    // 金额转化为分为单位
    String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥ 或者$的金额
    int index = currency.indexOf(".");
    int length = currency.length();
    Long amLong = 0l;
    if (index == -1) {
      amLong = Long.valueOf(currency + "00");
    } else if (length - index >= 3) {
      amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
    } else if (length - index == 2) {
      amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
    } else {
      amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
    }
    return amLong.toString();
  }
  public static void main(String[] args) {
    System.out.println(RandomUtil.randomString(32));
  }

}
