package util;

import model.CipherType;

import java.util.ArrayList;
import java.util.Arrays;

public class CipherUtils {
    public static final int CAESAR = 1;
    public static final int SUBSTITUTION = 2;
    public static final int AFFINE = 3;
    public static final int VIGENERE = 4;
    public static final int HILL = 5;

    public static ArrayList<CipherType> get() {
        ArrayList<CipherType> cipherTypes = new ArrayList<>(Arrays.asList(
                new CipherType(CAESAR, "Caesar"),
                new CipherType(SUBSTITUTION, "Substitution"),
                new CipherType(AFFINE, "Affine"),
                new CipherType(VIGENERE, "Vigenere"),
                new CipherType(HILL, "Hill")
        ));

        return cipherTypes;
    }

    public static String encrypt(String plainText, int cipherType) {
        String cipherText = null;

        switch (cipherType) {
            case CAESAR:

                break;

            case SUBSTITUTION:

                break;

            case AFFINE:

                break;

            case VIGENERE:

                break;

            case HILL:

                break;

            default:
                break;
        }

        return cipherText;
    }

    public static String decrypt(String cipherText, int cipherType) {
        String plainText = null;

        switch (cipherType) {
            case CAESAR:

                break;

            case SUBSTITUTION:

                break;

            case AFFINE:

                break;

            case VIGENERE:

                break;

            case HILL:

                break;

            default:
                break;
        }

        return plainText;
    }
}
