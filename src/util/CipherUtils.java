package util;

import model.CipherType;
import security.Caesar;
import security.Substitution;

import javax.swing.*;
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

    public static CipherType get(String cipherName) {
        ArrayList<CipherType> cipherTypes = get();

        for (CipherType type : cipherTypes) {
            if (type.getName().equals(cipherName))
                return type;
        }

        return null;
    }

    public static String encrypt(String plainText, int cipherType, String key) {
        String cipherText = null;

        switch (cipherType) {
            case CAESAR:
                int shift = 0;

                try {
                    shift = Integer.valueOf(key);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Khóa phải là số nguyên",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }

                cipherText = Caesar.encrypt(plainText, shift);
                break;

            case SUBSTITUTION:
                cipherText = Substitution.encode(plainText, key);
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

    public static String decrypt(String cipherText, int cipherType, String key) {
        String plainText = null;

        switch (cipherType) {
            case CAESAR:
                int shift = 0;

                try {
                    shift = Integer.valueOf(key);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Khóa phải là số nguyên",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }

                plainText = Caesar.decrypt(cipherText, shift);
                break;

            case SUBSTITUTION:
                plainText = Substitution.decode(cipherText, key);
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
