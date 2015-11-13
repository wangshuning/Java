package org.limingnihao.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptAESUtil {

    private static final String AESTYPE = "AES/ECB/PKCS5Padding";

    public static String AES_Encrypt(String keyStr, String plainText) {
        byte[] encrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(encrypt));
    }

    public static String AES_Decrypt(String keyStr, String encryptData) {
        byte[] decrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decrypt).trim();
    }

    private static Key generateKey(String key) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static void main(String[] args) {

        String keyStr = "UITN25LMUQC436IM";

        String plainText = "this is a string will be AES_Encrypt";

        String encText = AES_Encrypt(keyStr, plainText);
        String decString = AES_Decrypt("1234567890abcdef", "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");

        System.out.println(encText);
        System.out.println(decString);
    }
}
