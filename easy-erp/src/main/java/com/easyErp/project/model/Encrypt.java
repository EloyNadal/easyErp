package com.easyErp.project.model;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bson.internal.Base64;

import com.easyErp.project.log.EasyErpException;


public class Encrypt {
	
	private static final String ALGORITHM = "AES";
	private static final byte[] SALT = "eAsYeRpCeRn0vIGV".getBytes();

    static String getEncrypted(String plainText) throws EasyErpException {
    	
        if (plainText == null) {
            return null;
        }

        Key salt = getSalt();
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, salt);
            byte[] encodedValue = cipher.doFinal(plainText.getBytes());
            return Base64.encode(encodedValue);
        } catch (Exception e) {
           throw new EasyErpException("Error al encriptar");
        }
    }

    public static String getDecrypted(String encodedText) throws EasyErpException {

        if (encodedText == null) {
            return null;
        }

        Key salt = getSalt();
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, salt);
            byte[] decodedValue = Base64.decode(encodedText);
            byte[] decValue = cipher.doFinal(decodedValue);
            return new String(decValue);
        } catch (Exception e) {
        	throw new EasyErpException("Error al desencriptar");
        }
        
    }

    static Key getSalt() {
        return new SecretKeySpec(SALT, ALGORITHM);
    }

}
