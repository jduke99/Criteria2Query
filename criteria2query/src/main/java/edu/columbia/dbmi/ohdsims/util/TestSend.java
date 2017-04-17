package edu.columbia.dbmi.ohdsims.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class TestSend {

    public static String URL = "http://api.ohdsi.org/WebAPI/cdmresults/1PCT/conceptRecordCount";

    public static void main(String[] args) {

//        JSONObject jsobj1 = new JSONObject();
//        JSONObject jsobj2 = new JSONObject();
//        jsobj2.put("deviceID", "112");
//        jsobj2.put("channel", "channel");
//        jsobj2.put("state", "0");
//        jsobj1.put("item", jsobj2);
//        jsobj1.put("requestCommand", "control");
//		JSONArray jsonArray = new JSONArray();
//        jsonArray.add(0, 40386357);
//        jsonArray.add(1, 45446447);
//        jsonArray.add(2, 45426562);
//        System.out.println("jsonArray1：" + jsonArray);

//        try {
//			post(jsonArray);
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	String inresult = CmdUtil.callEliIE("");
//101882
    	JSONObject jcs=new JSONObject();
    	JSONArray jaa=new JSONArray();
    	
    	jaa.add(ATLASUtil.querybyconceptid(101882));
    	jcs.accumulate("ConceptSets",jaa);
    	System.out.println(jcs);
    }

    public static String post(JSONArray jsonArray) throws URISyntaxException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        
        post.setHeader("Content-Type", "application/json");
       //post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
        
        try {

            StringEntity s = new StringEntity(jsonArray.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            System.out.println(result);
            
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                
                    System.out.println("请求服务器成功，做相应处理");
                
            } else {
                
                System.out.println("请求服务端失败");
                
            }
            

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    }

}