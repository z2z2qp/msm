package com.will.utils.scure;

import com.will.utils.HexByteUtil;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * AES加解密
 *
 * @author DongJun
 */
public class AesSecure {

    /**
     * AES加密算法
     *
     * @param plaintext
     * @param secKey
     * @return
     */
    public static String encode(String plaintext, String secKey) {
        if (plaintext != null && secKey != null && !plaintext.equals("")
                && !secKey.equals("")) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128, new SecureRandom(secKey.getBytes()));
                SecretKey secretKey = kgen.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                byte[] byteContent = plaintext.getBytes("utf-8");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] result = cipher.doFinal(byteContent);
                return HexByteUtil.byteArrayToHexString(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param ciphertext
     * @param secKey
     * @return
     */
    public static String decode(String ciphertext, String secKey) {
        if (ciphertext != null && secKey != null && !ciphertext.equals("")
                && !secKey.equals("")) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128, new SecureRandom(secKey.getBytes()));
                SecretKey secretKey = kgen.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] result = cipher.doFinal(HexByteUtil
                        .hexStringToByteArray(ciphertext));
                return new String(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
