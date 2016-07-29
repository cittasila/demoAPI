package com.langying.payment.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxu on 2016/4/7.
 */
public class QRCodeUtil {
    /***
     * 生成二维码图片
     * @param content 二维码文本内容
     * @param outputStream 输出流
     * @throws Exception
     */
    public static void qrcode(String content, OutputStream outputStream) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        int width = 100; // 图像宽度
        int height = 100; // 图像高度
        hints.put(EncodeHintType.MARGIN,0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);
         MatrixToImageWriter.writeToStream(bitMatrix,"JPEG",outputStream);
        /*String path="d://";
        File file1 = new File(path,"餐巾纸.jpg");
        MatrixToImageConfig config=new MatrixToImageConfig();

        MatrixToImageWriter.writeToFile(bitMatrix,"JPEG",file1,config);*/
    }
    public static void main(String[] args) throws Exception {
        qrcode("http://www.baidu.com",null);
    }
}
