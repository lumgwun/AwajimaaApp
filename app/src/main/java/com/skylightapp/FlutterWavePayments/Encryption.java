package com.skylightapp.FlutterWavePayments;

import android.annotation.SuppressLint;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    OurConfig keys= new OurConfig();


    public static String toHexStr(byte[] bytes){

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < bytes.length; i++ ){
            builder.append(String.format("%02x", bytes[i]));
        }

        return builder.toString();
    }

    public static String getKey(String seedKey) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] hashedString = md.digest(seedKey.getBytes("utf-8"));
            byte[] subHashString = toHexStr(Arrays.copyOfRange(hashedString, hashedString.length - 12, hashedString.length)).getBytes("utf-8");
            String subSeedKey = seedKey.replace("FLWSECK-", "");
            subSeedKey = subSeedKey.substring(0, 12);
            byte[] combineArray = new byte[24];
            System.arraycopy(subSeedKey.getBytes(), 0, combineArray, 0, 12);
            System.arraycopy(subHashString, subHashString.length - 12, combineArray, 12, 12);
            return new String(combineArray);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getGlobal().log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @SuppressLint("NewApi")
    public static String encryptData(String message, String _encryptionKey)  {
        try {
            final byte[] digestOfPassword = _encryptionKey.getBytes("utf-8");
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            final SecretKey key = new SecretKeySpec( keyBytes , "DESede");
            final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            final byte[] plainTextBytes = message.getBytes("utf-8");
            final byte[] cipherText = cipher.doFinal(plainTextBytes);
            return Base64.getEncoder().encodeToString(cipherText);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {


            return "";
        }

    }

    public String encryptParameters(JSONObject api) {

        try{
            api.put("PBFPubKey",OurConfig.SKYLIGHT_PUBLIC_KEY);
        }catch(Exception ex){}



        String message= api.toString();

        String encrypt_secret_key=getKey(OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);
        String encrypted_message= encryptData(message,encrypt_secret_key);


        return encrypted_message;

    }


    public String encryptParametersPreAuth(JSONObject api){

        try{
            api.put("PBFPubKey","FLWPUBK-8cd258c49f38e05292e5472b2b15906e-X");
        }catch(Exception ex){}

        String message= api.toString();



        String encrypt_secret_key=getKey("FLWSECK-c51891678d48c39eff3701ff686bdb69-X");
        String encrypted_message= encryptData(message,encrypt_secret_key);


        return encrypted_message;

    }
}
