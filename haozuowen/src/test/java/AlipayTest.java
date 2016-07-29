import com.langying.payment.common.alipay.AlipaySubmit;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.junit.*;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liwei on 2016/6/11.
 */
public class AlipayTest {

    /**
     *
     */
    @org.junit.Test
    public void testUrl(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timestampStr = sdf.format(date);


        Map<String,String> sParaTemp = new HashMap<>();
        sParaTemp.put("app_id","2016041301294805");
        sParaTemp.put("method","alipay.trade.query");
        // sParaTemp.put("format","JSON");
        sParaTemp.put("charset","utf-8");
        sParaTemp.put("sign_type","RSA");
        sParaTemp.put("timestamp",timestampStr);
        sParaTemp.put("version","1.0");
        Map<String, String> sPara = AlipaySubmit.buildRequestPara(sParaTemp);
        // RSA 算法


        // https://openapi.alipay.com/gateway.do
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost("openapi.alipay.com")
                .setPath("/gateway.do");

        for(Map.Entry<String,String> entry:sPara.entrySet()){
            uriBuilder.setParameter(entry.getKey(),entry.getValue());
        }

        try {
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            String uri = httpGet.getURI().toString();
            System.out.println(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
