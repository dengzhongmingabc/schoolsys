

package com.honorfly.schoolsys.controller;


import com.alibaba.fastjson.JSONObject;
import com.honorfly.schoolsys.utils.Result;
import com.honorfly.schoolsys.utils.ResultGenerator;
import com.honorfly.schoolsys.utils.web.BaseController;
import com.honorfly.schoolsys.utils.wexin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Controller
@RequestMapping("/wechatAction/")
public class WechatAction extends BaseController {
    public final String appId = "wxc29a98afd069edb2";
    public final String appsecret = "6f75e0ae11a0f062c592a658d1c838b9";
    public final static String yfurl = "http://ieqb7k.natappfree.cc";
    private static Logger log = LoggerFactory.getLogger(WechatAction.class);


    @ResponseBody
    @RequestMapping(value = "/getCodeUrl", method = {RequestMethod.POST,RequestMethod.GET})
    public Result getCodeUrl() throws Exception {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE";
        url = url.replace("APPID", appId);
        url = url.replace("REDIRECT_URI", yfurl);
        url = url.replace("SCOPE", "snsapi_userinfo");
        return ResultGenerator.genSuccessResult(url);

    }

    /**
     * 获取网页授权凭证
     *
     * @param code
     * @return WeixinAouth2Token
     */
    @ResponseBody
    @RequestMapping(value = "/getSNSUserInfo", method = RequestMethod.POST)
    public Result getSNSUserInfo(String code) {
        WeixinOauth2Token wat = null;
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appsecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            wat = new WeixinOauth2Token();
            wat.setAccessToken(jsonObject.getString("access_token"));
            wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
            wat.setRefreshToken(jsonObject.getString("refresh_token"));
            wat.setOpenId(jsonObject.getString("openid"));
            wat.setScope(jsonObject.getString("scope"));
            // 拼接请求地址
            requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
            requestUrl = requestUrl.replace("ACCESS_TOKEN", wat.getAccessToken()).replace("OPENID", wat.getOpenId());
            // 通过网页授权获取用户信息
            jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
            if (null != jsonObject) {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getIntValue("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
//                snsUserInfo.setPrivilegeList(JSONArray.parseArray(jsonObject.getJSONArray("privilege").toString(), List.class));
            }
        }
        System.out.println(snsUserInfo.getOpenId());

        return ResultGenerator.genSuccessResult(snsUserInfo);
    }


    @ResponseBody
    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    public void test(HttpServletRequest request,
                     HttpServletResponse response,
                     @RequestParam(value = "signature", required = true) String signature,
                     @RequestParam(value = "timestamp", required = true) String timestamp,
                     @RequestParam(value = "nonce", required = true) String nonce,
                     @RequestParam(value = "echostr", required = true) String echostr) throws Exception {
        try {
            System.out.println(signature);
            System.out.println(timestamp);
            System.out.println(nonce);
            System.out.println(echostr);
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //发送模板信息
    /*
    * {
  "touser": "OPENID",
  "template_id": " TjtCwJWgJB9rX5dwDrcMQ80we8vlUrgof12A2K4qj4",
  "url": yfurl,
  "topcolor": "#FF0000",
  "data": {
    "title": {
      "value": "上课提醒",
      "color": "#173177"
    },
    "studentName": {
      "value": "邓忠明",
      "color": "#173177"
    },
    "course": {
      "value": "绘图",
      "color": "#173177"
    },
    "courseTime": {
      "value": "10:30",
      "color": "#173177"
    },
    "courseAddr": {
      "value": "飞誉画室",
      "color": "#173177"
    },
    "remark": {
      "value": "努力吧少年",
      "color": "#173177"
    }
  }
}*/
    /*public static void main(String[] args) {
        // 第三方用户唯一凭证
        String appId = "wxc29a98afd069edb2";
        // 第三方用户唯一凭证密钥
        String appSecret = "6f75e0ae11a0f062c592a658d1c838b9";
        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
        if (null != at) {
            // 调用接口创建菜单
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
            url = url.replace("ACCESS_TOKEN",at.getToken());
            String data = "{\n" +
                    "  \"touser\": \"o1PmT5ox0KRYezZtjByv1oqYeZko\",\n" +
                    "  \"template_id\": \"TjtCwJWgJB9rX5dwDrcMQ80we8vlUr-gof12A2K4qj4\",\n" +
                    "  \"url\": \""+yfurl+"\",\n" +
                    "  \"topcolor\": \"#FF0000\",\n" +
                    "  \"data\": {\n" +
                    "    \"title\": {\n" +
                    "      \"value\": \"上课提醒\",\n" +
                    "      \"color\": \"#173177\"\n" +
                    "    },\n" +
                    "    \"studentName\": {\n" +
                    "      \"value\": \"邓忠明\",\n" +
                    "      \"color\": \"#173177\"\n" +
                    "    },\n" +
                    "    \"course\": {\n" +
                    "      \"value\": \"绘图\",\n" +
                    "      \"color\": \"#173177\"\n" +
                    "    },\n" +
                    "    \"courseTime\": {\n" +
                    "      \"value\": \"10:30\",\n" +
                    "      \"color\": \"#173177\"\n" +
                    "    },\n" +
                    "    \"courseAddr\": {\n" +
                    "      \"value\": \"飞誉画室\",\n" +
                    "      \"color\": \"#173177\"\n" +
                    "    },\n" +
                    "    \"remark\": {\n" +
                    "      \"value\": \"努力吧少年\",\n" +
                    "      \"color\": \"#173177\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
            JSONObject result = WeixinUtil.httpRequest(url,"POST",data);

            // 判断菜单创建结果
            if (0 == result.getIntValue("errcode"))
                log.info("信息发送成功！");
            else
                log.info("信息发送失败，错误码：" + result);
        }
    }
*/
/*    //配置行业
    public static void main(String[] args) {
        // 第三方用户唯一凭证
        String appId = "wxc29a98afd069edb2";
        // 第三方用户唯一凭证密钥
        String appSecret = "6f75e0ae11a0f062c592a658d1c838b9";
        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
        if (null != at) {
            // 调用接口创建菜单
            String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
            url = url.replace("ACCESS_TOKEN",at.getToken());
            String data = "{\n" +
                    "    \"industry_id1\":\"16\",\n" +
                    "    \"industry_id2\":\"17\"\n" +
                    "}\n";
            JSONObject result = WeixinUtil.httpRequest(url,"POST",data);

            // 判断菜单创建结果
            if (0 == result.getIntValue("errcode"))
                log.info("行业设置成功！");
            else
                log.info("行业设置失败，错误码：" + result);
        }
    }*/


    //配置按钮
    public static void main(String[] args) {
        // 第三方用户唯一凭证
        String appId = "wxc29a98afd069edb2";
        // 第三方用户唯一凭证密钥
        String appSecret = "6f75e0ae11a0f062c592a658d1c838b9";
        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
        if (null != at) {
            // 调用接口创建菜单
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());
            // 判断菜单创建结果
            if (0 == result)
                log.info("菜单创建成功！");
            else
                log.info("菜单创建失败，错误码：" + result);
        }
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {

        CommonButton btn31 = new CommonButton();
        btn31.setName("邦定学员");
        btn31.setType("click");
        btn31.setKey("31");

        CommonButton btn32 = new CommonButton();
        btn32.setName("个人信息");
        btn32.setType("click");
        btn32.setKey("32");

        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("消息");
        mainBtn1.setType("click");
        mainBtn1.setKey("1");

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("服务首页");
        mainBtn2.setType("view");
        mainBtn2.setUrl(yfurl);
        mainBtn2.setKey("2");

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("我的");
        mainBtn3.setSub_button(new CommonButton[]{btn31, btn32});
        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[]{mainBtn1, mainBtn2, mainBtn3});
        return menu;
    }
}
