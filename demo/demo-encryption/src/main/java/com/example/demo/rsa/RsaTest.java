package com.example.demo.rsa;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuj231
 * @description
 * @date 2019/12/13 9:51
 */
public class RsaTest {
    /**
     * key算法
     */
    public static final String KEY_ALGORITHM = "RSA";
    
    //public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    
    /**
     * 公钥key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 私钥key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    
    public static void main(String[] args) {
        Map<String, Object> keyMap;
        try {
            keyMap = initKey();
            String publicKey = getPublicKey(keyMap);
            System.out.println(publicKey);
            String privateKey = getPrivateKey(keyMap);
            System.out.println(privateKey);
            
            String str = "qweasdzxc";

            String encrypt = encrypt(str, publicKey);
            System.out.println("encrypt:"+encrypt);
            String decrypt = decrypt(encrypt, privateKey);
            System.out.println("decrypt:"+decrypt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得公钥
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 获得私钥
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //byte[] privateKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 解码返回byte(base64解密)
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * 编码返回字符串（base64加密）
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * map对象中存放公私钥
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * RSA公钥加密 
     * @param str 加密字符串
     * @param publicKey 公钥 
     * @return 密文
     * @throws Exception 加密过程中的异常信息 
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     * @param str 加密字符串
     * @param privateKey 私钥 
     * @return 铭文
     * @throws Exception 解密过程中的异常信息 
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
    
}
