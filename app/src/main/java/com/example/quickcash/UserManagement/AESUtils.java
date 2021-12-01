package com.example.quickcash.UserManagement;

//[Reference: https://wajahatkarim.com/2018/08/encrypt-/-decrypt-strings-in-android/#aesutils-class]
// This entire java class was taken from the above mentioned url
// Date Accessed: 17 October,2021

import com.example.quickcash.common.Constants;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils implements IAESUtils{

    private static final byte[] keyValue =
            new byte[]{'c', 'o', 'd', 'i', 'n', 'g', 'a', 'f', 'f', 'a', 'i', 'r', 's', 'c', 'o', 'm'};
    private static final String HEX = "0123456789ABCDEF";

    @Override
    public String encrypt(String cleartext)
            throws Exception {
        byte[] rawKey = getRawKey();
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    @Override
    public String decrypt(String encrypted)
            throws Exception {
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(enc);
        return new String(result);
    }

    private static byte[] getRawKey() {
        SecretKey key = new SecretKeySpec(keyValue, Constants.AES);
        return key.getEncoded();
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKey secretKeySpec = new SecretKeySpec(raw, Constants.AES);
        Cipher cipher = Cipher.getInstance(Constants.AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] encrypted)
            throws Exception {
        SecretKey secretKeySpec = new SecretKeySpec(keyValue, Constants.AES);
        Cipher cipher = Cipher.getInstance(Constants.AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encrypted);
    }

    @Override
    public byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    @Override
    public String toHex(byte[] buffer) {
        if (buffer == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buffer.length);
        for (int i = 0; i < buffer.length; i++) {
            appendHex(result, buffer[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer stringBuffer, byte b) {
        stringBuffer.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}