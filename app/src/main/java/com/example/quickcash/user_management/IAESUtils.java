package com.example.quickcash.user_management;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * This is an interface responsible for declaring the methods for AESUtils class.
 */
public interface IAESUtils {

    /**
     * This method is responsible for encryption
     *
     * @param cleartext the text which needs to be encrypted.
     * @return the encrypted text.
     * @throws NoSuchAlgorithmException  This exception is thrown when a particular cryptographic algorithm is requested but is not available in the environment.
     * @throws NoSuchPaddingException    This exception is thrown when a particular padding mechanism is requested but is not available in the environment.
     * @throws InvalidKeyException       This is the exception for invalid Keys (invalid encoding, wrong length, uninitialized, etc).
     * @throws BadPaddingException       This exception is thrown when a particular padding mechanism is expected for the input data but the data is not padded properly.
     * @throws IllegalBlockSizeException This exception is thrown when the length of data provided to a block cipher is incorrect, i.e., does not match the block size of the cipher.
     */
    String encrypt(String cleartext) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException;

    /**
     * This method is responsible for decryption of the message
     *
     * @param encrypted the encrypted message.
     * @return the decrypted message
     * @throws NoSuchAlgorithmException  This exception is thrown when a particular cryptographic algorithm is requested but is not available in the environment.
     * @throws NoSuchPaddingException    This exception is thrown when a particular padding mechanism is requested but is not available in the environment.
     * @throws InvalidKeyException       This is the exception for invalid Keys (invalid encoding, wrong length, uninitialized, etc).
     * @throws BadPaddingException       This exception is thrown when a particular padding mechanism is expected for the input data but the data is not padded properly.
     * @throws IllegalBlockSizeException This exception is thrown when the length of data provided to a block cipher is incorrect, i.e., does not match the block size of the cipher.
     */
    String decrypt(String encrypted) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException;

    /**
     * This method is responsible for converting the hex string into byte array
     *
     * @param hexString the user given hex string
     * @return the byte array for the hex string.
     */
    byte[] toByte(String hexString);

    /**
     * This method is responsible for converting the byte array into hex string.
     *
     * @param buffer the user given byte array.
     * @return the hex string after conversion of the byte array.
     */
    String toHex(byte[] buffer);
}
