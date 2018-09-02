package com.taotao.sso.httpclient;

import org.apache.commons.codec.StringEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import sun.net.www.http.HttpClient;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {
    //        第一步：把HttpClient使用的jar包添加到工程中。 在taotao-common中有
//        第二步：创建一个HttpClient的测试类
//        第三步：创建测试方法。
//        第四步：创建一个HttpClient对象
//        第五步：创建一个HttpGet对象，需要制定一个请求的url
//        第六步：执行请求。
//        第七步：接收返回结果。HttpEntity对象。
//        第八步：取响应的内容。
//        第九步：关闭HttpGet、HttpClient。
    @Test
    public void testHttp() throws Exception{
        CloseableHttpClient httpClient=HttpClients.createDefault();
        HttpGet httpGet=new HttpGet("http://www.itheima.com");
        CloseableHttpResponse response=httpClient.execute(httpGet);
        HttpEntity entity=response.getEntity();
        String html=EntityUtils.toString(entity);
        System.out.println(html);
        response.close();
    }

//    第一步：创建一个httpClient对象
//    第二步：创建一个HttpPost对象。需要指定一个url
//    第三步：创建一个list模拟表单，list中每个元素是一个NameValuePair对象
//    第四步：需要把表单包装到Entity对象中。StringEntity
//    第五步：执行请求。
//    第六步：接收返回结果
//    第七步：关闭流。
    @Test
    public void testHttpPost()throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8082/posttest.html");
        List<NameValuePair> formList = new ArrayList<>();
        formList.add(new BasicNameValuePair("name", "张三"));
        formList.add(new BasicNameValuePair("pass", "1234"));
        StringEntity entity = new UrlEncodedFormEntity(formList,"utf-8");
        //会出现乱码
        //StringEntity entity = new UrlEncodedFormEntity(formList);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        System.out.println(result);
        response.close();
    }
}
