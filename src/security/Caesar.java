package security;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Caesar {
    public static String encrypt(String text, int shift) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                char ch = (char) (((int) text.charAt(i) +
                        shift - 65) % 26 + 65);
                result.append(ch);
            } else {
                char ch = (char) (((int) text.charAt(i) +
                        shift - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result.toString();
    }

    // Decrypts cipher using shift
    public static String decrypt(String cipher, int shift) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < cipher.length(); i++) {
            if (Character.isUpperCase(cipher.charAt(i))) {
                char ch = (char) (((int) cipher.charAt(i) +
                        (26 - shift) - 65) % 26 + 65);
                result.append(ch);
            } else {
                char ch = (char) (((int) cipher.charAt(i) +
                        (26 - shift) - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String readFile(){
        String text = "";
        try {
            File myObj = new File("C:\\Users\\Khoa Nguyen\\OneDrive\\Desktop\\Ceasar.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                text += data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return text;
    }

    public static void main(String[] args) {
        String originalText = "Sanfoundry";
        int shiftCount = 3;
//        String originalText = readFile();
        System.out.println("Original Text  : " + originalText);
        System.out.println("Shift Count : " + shiftCount);

        String cipher = encrypt(originalText, shiftCount);
        System.out.println("Encrypted : " + cipher);

        String decryptedPlainText = decrypt(cipher, shiftCount);
        System.out.println("Decrypted : " + decryptedPlainText);
    }
}
