package com.example.quickcash.UserManagement;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface IAESUtils {
    String encrypt(String cleartext) throws NoSuchAlgorithmException,
    NoSuchPaddingException, InvalidKeyException, BadPaddingException,
    IllegalBlockSizeException;

    String decrypt(String encrypted) throws NoSuchAlgorithmException,
    NoSuchPaddingException, InvalidKeyException, BadPaddingException,
    IllegalBlockSizeException;

    byte[] toByte(String hexString);

    String toHex(byte[] buffer);
}
