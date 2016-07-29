package com.langying.toolbox.utils;

import ch.qos.logback.classic.Logger;
import groovyx.net.http.HTTPBuilder;
import groovyx.net.http.Method;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;


/**
 * Created by chenxu on 2015/11/30.
 */
public class HttpRemoteRequest {



    private static final Logger logger = (Logger) LoggerFactory.getLogger(HttpRemoteRequest.class);

    /**
     * GET请求获取远端json数据
     * @param url
     * @param map
     * @param header
     * @return
     */
    static Map get(String url, Map map, Map header) throws URISyntaxException {
        logger.info("request URL:"+url+",param:"+map.toString());
        HTTPBuilder http = new HTTPBuilder(url);
        Map mapJson=new HashMap();
        header.put("User-Agent","Mozill/5.0");
    /*    http.request(Method.GET,JSON){
            uri.query=map;
            headers=header;
            response.success={resp,json ->
                mapJson=json
            }
            response.failure={resp,json->
                mapJson=json
            }
        }
        logger.info("return data:"+mapJson);*/
        return mapJson;
    }
  /*  public static void main(String[] args){
        HttpRemoteRequest.get("http://192.168.12.250:8081/scoring/39",[:],[token:"333333"])
    }*/

    public static Map<String,Object> post(String url,Map map) throws Exception {
        logger.info("request URL:"+url+",param:"+map.toString());
        HttpPost post = new HttpPost(url);
        List<NameValuePair> postdata = new ArrayList<NameValuePair>();
        Set<String> keySet = map.keySet();
        for (String key : keySet)
        {
            postdata.add(new BasicNameValuePair(key, map.get(key).toString()));
        }
        post.setEntity(new UrlEncodedFormEntity(postdata, "UTF-8"));
        DefaultHttpClient client = wrapClient(new DefaultHttpClient());
        post.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        HttpResponse resp = client.execute(post);
        try {
            // ** 获取状态码，生成session_token和session_secret。
            StatusLine status = resp.getStatusLine();
            int stCode = status.getStatusCode();
            // http 响应实体返回，转成字符串来处理
            String str = EntityUtils.toString(resp.getEntity(), "UTF-8");
            logger.info("return data:"+str);
            JSONObject jsonObject = JSONObject.fromObject(str.startsWith("[")?("{list:"+str+"}"):str);
            return JSONObject.fromObject(jsonObject);
        } finally {
            // 最后释放连接
            post.releaseConnection();
        }
    }
    public static DefaultHttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            };
            System.out.print("warp Client has been enter?======================================");
            TrustManager[] managers= new TrustManager[1];
            managers[0]=tm;
            ctx.init(null, managers , null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            // 设置要使用的端口，默认是443
            sr.register(new Scheme("https", 443, ssf));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
