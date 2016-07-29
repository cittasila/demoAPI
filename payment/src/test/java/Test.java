import com.langying.payment.common.Configure;
import com.langying.payment.common.HttpsRequest;
import com.langying.payment.models.UnifiedOrderData;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * Created by chenxu on 2016/4/7.
 */
public class Test {

    public static void main(String[] args) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
     //   UnifiedOrderData unifiedOrderData=new UnifiedOrderData("2016061222","金币充值",1,"127.0.0.1","20091225091010","");
      //  HttpsRequest request=new HttpsRequest();
      //  System.out.println(request.sendXmlPost(Configure.UnifiedOrder,unifiedOrderData));
        UnifiedOrderData unifiedOrderData=new UnifiedOrderData("12016040810004927","有氧英语充值",1,"127.0.0.1","20160408100049","616011","");
        HttpsRequest request=new HttpsRequest();
        String xml=request.sendXmlPost(Configure.UnifiedOrder,unifiedOrderData);
    }
}
