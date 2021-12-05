package com.example.quickcash.user_management;

//[Reference: https://wajahatkarim.com/2018/08/encrypt-/-decrypt-strings-in-android/#aesutils-class]
// This entire java class was taken from the above mentioned url
// Date Accessed: 17 October,2021
import com.example.quickcash.common.Constants;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is responsible for implementing the encryption and decryption of a given string.
 */
public class AESUtils implements IAESUtils {

    private static final byte[] keyValue =
            new byte[]{'c', 'o', 'd', 'i', 'n', 'g', 'a', 'f', 'f', 'a', 'i', 'r', 's', 'c', 'o', 'm'};
    private static final String HEX = "0123456789ABCDEF";

    private static byte[] getRawKey() {
        SecretKey key = new SecretKeySpec(keyValue, Constants.AES);
        return key.getEncoded();
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        SecretKey secretKeySpec = new SecretKeySpec(raw, Constants.AES);
        Cipher cipher = Cipher.getInstance(Constants.AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] encrypted)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        SecretKey secretKeySpec = new SecretKeySpec(keyValue, Constants.AES);
        Cipher cipher = Cipher.getInstance(Constants.AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encrypted);
    }

    private static void appendHex(StringBuilder stringBuilder, byte b) {
        stringBuilder.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    /**
     * This method is responsible for encrypting plain text
     * @param cleartext the text which needs to be encrypted
     * @return encrypted text
     */
    @Override
    public String encrypt(String cleartext)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        byte[] rawKey = getRawKey();
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    /**
     * This method is responsible for decrypting plain text
     * @param encrypted the encrypted text that needs to be decrypted
     * @return decrypted text
     */
    @Override
    public String decrypt(String encrypted)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(enc);
        return new String(result);
    }

    /**
     * This method is responsible for converting the hex string into byte code
     * @param hexString the hexadecimal string which needs to be converted into bytes.
     * @return the converted hex string into byte.
     */
    @Override
    public byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    /**
     * This method is responsible for converting the byte array into hexadecimal
     * @param buffer the byte array which needs to be converted.
     * @return returns a string which is a conversion of the byte array into hexadecimal string.
     */
    @Override
    public String toHex(byte[] buffer) {
        // to check if the array is null or not.
        if (buffer == null)
            return "";
        StringBuilder result = new StringBuilder(2 * buffer.length);
        // run the loop and create the hexadecimal string.
        for (int i = 0; i < buffer.length; i++) {
            appendHex(result, buffer[i]);
        }
        return result.toString();
    }
}