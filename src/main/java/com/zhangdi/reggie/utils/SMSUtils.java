package com.zhangdi.reggie.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.HttpClientConfig;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;




/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
		//String signName, String templateCode,String phoneNumbers,
<<<<<<< HEAD
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "***", "***");
=======
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tPGa8EswCdP5ayVfq84", "m0F4IahxSpPCY53u37SjCDoacTLk4K");
>>>>>>> origin/master
		HttpClientConfig clientConfig = HttpClientConfig.getDefault();
		clientConfig.setHttpProxy("http://127.0.0.1:10809");
		clientConfig.setHttpsProxy("http://127.0.0.1:10809");
		profile.setHttpClientConfig(clientConfig);


		IAcsClient client = new DefaultAcsClient(profile);

		SendSmsRequest request = new SendSmsRequest();
		request.setMethod(MethodType.POST);
		request.setVersion("2017-05-25");
		request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers(phoneNumbers);//"phoneNumbers",
		request.setSignName(signName);//"signName",
		request.setTemplateCode(templateCode);//"templateCode",
		request.setTemplateParam("{\"code\":\""+param+"\"}");



		try {
			SendSmsResponse response = client.getAcsResponse(request);
			System.out.println("Message sent successfully");//短信发送成功
		}catch (ClientException e) {
			e.printStackTrace();
		}
	}

}


