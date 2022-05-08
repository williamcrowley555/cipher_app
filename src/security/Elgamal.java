package security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

public class Elgamal {
    static Random sc = new SecureRandom();
    static BigInteger p = BigInteger.probablePrime(64, sc);
    static BigInteger EC;
    static BigInteger b = new BigInteger("3");
    public static String encrypt(String plainText, BigInteger secretKey) throws UnsupportedEncodingException {
        // public key calculation
        BigInteger c = b.modPow(secretKey, p);

        // Encryption
        BigInteger X = new BigInteger(plainText.getBytes("UTF-8"));

        BigInteger r = new BigInteger(64, sc);

        EC = X.multiply(c.modPow(r, p)).mod(p);

        BigInteger brmodp = b.modPow(r, p);

        return brmodp.toString();
    }

    public static String decrypt(String cipherText, BigInteger secretKey) throws UnsupportedEncodingException {
        BigInteger brmodp = new BigInteger(cipherText);

        BigInteger crmodp = brmodp.modPow(secretKey, p);
        BigInteger d = crmodp.modInverse(p);
        BigInteger ad = d.multiply(EC).mod(p);
        return new String(ad.toByteArray());
    }

    public static void main(String[] args) throws IOException {
        String msg = "HelloWorld";
        System.out.println("Message: " + msg);
        BigInteger p, c, secretKey;
        Random sc = new SecureRandom();
        secretKey = new BigInteger("12345678901234567890");
        // public key calculation
        System.out.println("secretKey = " + secretKey);

        String cipherText = encrypt(msg, secretKey);
        System.out.println("Cypher text: " + cipherText);
        // Decryption
        System.out.println("Decrypt: " + decrypt(cipherText, secretKey));
    }
}
