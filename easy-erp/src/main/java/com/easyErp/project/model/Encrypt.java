package com.easyErp.project.model;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bson.internal.Base64;


public class Encrypt {
	
	private static final String ALGORITHM = "AES";
	private static final byte[] SALT = "eAsYeRpCeRn0vIGV".getBytes();

    static String getEncrypted(String plainText) {
    	
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
        	AppManager.printError("Error al encriptar");
        }
        return null;
    }

    public static String getDecrypted(String encodedText){

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
        	AppManager.printError("Error al desencriptar");
        }
        return null;
        
    }

    static Key getSalt() {
        return new SecretKeySpec(SALT, ALGORITHM);
    }

}
