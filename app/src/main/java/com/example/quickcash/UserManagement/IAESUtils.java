package com.example.quickcash.UserManagement;

public interface IAESUtils {
    String encrypt(String cleartext) throws Exception;

    String decrypt(String encrypted) throws Exception;

    byte[] toByte(String hexString);

    String toHex(byte[] buffer);
}
