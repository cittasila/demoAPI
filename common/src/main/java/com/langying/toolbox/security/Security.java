package com.langying.toolbox.security;

import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 */
@CompileStatic
@TypeChecked
public class Security {


    private static final char[] HEX_DIGITS = { '0','1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * SHA1加密
     * @param inStr
     * @return
     */
    public static String SHA1(String inStr) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(inStr.getBytes());
            outStr = byteToString(digest);
        }
        catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return outStr;
    }

    /**
     * MD5加密
     * @param inStr
     * @return
     */
    public static String MD5(String inStr) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(inStr.getBytes("UTF-8"));
            outStr = byteToString(digest);
        }
        catch (Exception nsae) {
            nsae.printStackTrace();
        }
        return outStr;
    }

    /**
     * 16位MD5，取中间的16位
     * @param inStr
     * @return
     */
    public static String MD5_16(String inStr) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(inStr.getBytes());
            outStr = byteToString(digest).substring(8, 24);
        }
        catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return outStr;
    }

    /**
     * AES加密
     * @param inStr
     * @param key
     * @return
     * @throws Exception
     */
    public static String AES_Encrypt(String inStr, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secureKey); // 设置密钥和加密形式
        return byteToString(cipher.doFinal(inStr.getBytes()));
    }

    /**
     * AES解密
     * @param inStr
     * @param key
     * @return
     * @throws Exception
     */
    public static String AES_Decrypt(String inStr, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), "AES");// 设置加密Key
        cipher.init(Cipher.DECRYPT_MODE, secureKey);// 设置密钥和解密形式
        return byteToString(cipher.doFinal(inStr.getBytes()));
    }


    /**
     * 转换字符串
     * @param digest
     * @return
     */
    public static String byteToString(byte[] digest) {
        int len = digest.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(digest[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[digest[j] & 0x0f]);
        }
        return buf.toString();
    }


    /**
     * Hex 编码
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeHexString(String content,String encoding) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(encoding)){
            encoding = "utf-8";
        }
        return Hex.encodeHexString(content.getBytes(encoding));
    }


}
