package com.jt.test.utils.PushControllerUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
public class GetOpenIdAct {
    public List<String> getWeixinUser(){
        String access_token=getToken();
        List<String> openIds=getUsers(access_token);
        //openIds为所有关注用户的openid
        System.out.println("===========获取关注该公众号的openid========");
        for(int i=0;i<openIds.size();i++){
            System.out.println(openIds.get(i));
        }
        return openIds;
    }
    /**
     * 获取微信公众号关注用户
     * 官方接口文档：https://developers.weixin.qq.com/doc/offiaccount/User_Management/Getting_a_User_List.html
     * @param access_token
     * @return
     */
    public List<String> getUsers(String access_token) {
        String usersGetUrl="https://api.weixin.qq.com/cgi-bin/user/get";
        usersGetUrl+="?access_token="+access_token;
        JSONObject data=getUrlResponse(usersGetUrl);
        List<String> openIds=new ArrayList<String>();
        Integer total=0,count=0;
        try {
            total=(Integer) data.get("total");
            count=(Integer) data.get("count");
            //总关注用户数超过默认一万
            if(count<total){
                openIds.addAll(getUsers(openIds,usersGetUrl, access_token, (String)data.get("next_openid")));
            }else{
                //有关注者 json才有data参数
                if(count>0){
                    JSONObject openIdData=(JSONObject) data.get("data");
                    JSONArray openIdArray= (JSONArray) openIdData.get("openid");
                    for(int i=0;i<openIdArray.length();i++){
                        openIds.add((String) openIdArray.get(i));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return openIds;
    }
    /**
     * 获取access_token
     *
     * @return
     */
    public String getToken() {
        String tokenGetUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";//微信提供获取access_token接口地址
        String appid = "wxf9fc4558c1f2812e";
        String secret = "bbb816109367824cf2f96bae0e791100";
        JSONObject tokenJson = new JSONObject();
        if (StringUtils.isNotBlank(appid) && StringUtils.isNotBlank(secret)) {
            tokenGetUrl += "&appid=" + appid + "&secret=" + secret;
            tokenJson = getUrlResponse(tokenGetUrl);
            System.out.println("~~~~~tokenJson:" + tokenJson.toString());
            try {
                return (String) tokenJson.get("access_token");
            } catch (JSONException e) {
                System.out.println("报错了");
                return null;
            }
        } else {
            System.out.println("appid和secret为空");
            return null;
        }
    }

    private  List<String> getUsers(List<String> openIds,String url,String access_token,String next_openid) {
        JSONObject data=getUrlResponse(url);
        try {
            Integer count=(Integer) data.get("count");
            String nextOpenId=(String) data.get("next_openid");
            if(count>0){
                JSONObject openIdData=(JSONObject) data.get("data");
                JSONArray openIdArray= (JSONArray) openIdData.get("openid");
                for(int i=0;i<openIdArray.length();i++){
                    openIds.add((String) openIdArray.get(i));
                }
            }
            if(StringUtils.isNotBlank(nextOpenId)){
                return getUsers(openIds,url, access_token, nextOpenId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return openIds;
    }

    private JSONObject getUrlResponse(String url) {
        CharsetHandler handler = new CharsetHandler("UTF-8");
        try {
            HttpGet httpget = new HttpGet(new URI(url));
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            //HttpClient
            CloseableHttpClient client = httpClientBuilder.build();
            client = (CloseableHttpClient) wrapClient(client);
            return new JSONObject(client.execute(httpget, handler));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLSv1");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;

        } catch (Exception ex) {
            return null;
        }
    }


    private class CharsetHandler implements ResponseHandler<String> {
        private String charset;

        public CharsetHandler(String charset) {
            this.charset = charset;
        }

        public String handleResponse(HttpResponse response)
                throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                if (!StringUtils.isBlank(charset)) {
                    return EntityUtils.toString(entity, charset);
                } else {
                    return EntityUtils.toString(entity);
                }
            } else {
                return null;
            }
        }

    }
}