package security;

import java.util.Scanner;

public class Hill {
    private static String cipherDecryption(String msg, String key) {
        msg = msg.replaceAll("\\s" , "");
        msg = msg.toUpperCase();

        // if irregular length, then perform padding
        int lenChk = 0;
        if (msg.length() % 2 != 0){
            msg += "0";
            lenChk = 1;
        }

        // message to matrices
        int[][] msg2D = new int[2][msg.length()];
        int itr1 = 0;
        int itr2 = 0;
        for (int i = 0; i < msg.length(); i++){
            if (i%2 == 0){
                msg2D[0][itr1] = ((int)msg.charAt(i)) - 65;
                itr1++;
            } else {
                msg2D[1][itr2] = ((int)msg.charAt(i)) - 65;
                itr2++;
            } // if-else
        } // for

        key = key.replaceAll("\\s","");
        key = key.toUpperCase();

        // key to 2x2 matrix
        int[][] key2D = new int[2][2];
        int itr3 = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                key2D[i][j] = (int)key.charAt(itr3)-65;
                itr3++;
            }
        }

        int deter = key2D[0][0] * key2D[1][1] - key2D[0][1] * key2D[1][0];
        deter = moduloFunc(deter, 26);

        // multiplicative inverse of key matrix
        int mulInverse = -1;
        for (int i = 0; i < 26; i++) {
            int tempInv = deter * i;
            if (moduloFunc(tempInv, 26) == 1){
                mulInverse = i;
                break;
            } else {
                continue;
            } // if-else
        } // for

        int swapTemp = key2D[0][0];
        key2D[0][0] = key2D[1][1];
        key2D[1][1] = swapTemp;

        // changing signs
        key2D[0][1] *= -1;
        key2D[1][0] *= -1;

        key2D[0][1] = moduloFunc(key2D[0][1], 26);
        key2D[1][0] = moduloFunc(key2D[1][0], 26);

        // multiplying multiplicative inverse with adjugate matrix
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                key2D[i][j] *= mulInverse;
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                key2D[i][j] = moduloFunc(key2D[i][j], 26);
            }
        }

        String decrypText = "";
        int itrCount = msg.length() / 2;
        if (lenChk == 0){
            // if msg length % 2 = 0
            for (int i = 0; i < itrCount; i++) {
                int temp1 = msg2D[0][i] * key2D[0][0] + msg2D[1][i] * key2D[0][1];
                decrypText += (char)((temp1 % 26) + 65);
                int temp2 = msg2D[0][i] * key2D[1][0] + msg2D[1][i] * key2D[1][1];
                decrypText += (char)((temp2 % 26) + 65);
            }
        } else {
            // if msg length % 2 != 0 (irregular length msg)
            for (int i = 0; i < itrCount-1; i++) {
                int temp1 = msg2D[0][i] * key2D[0][0] + msg2D[1][i] * key2D[0][1];
                decrypText += (char)((temp1 % 26) + 65);
                int temp2 = msg2D[0][i] * key2D[1][0] + msg2D[1][i] * key2D[1][1];
                decrypText += (char)((temp2 % 26) + 65);
            }
        }
        return decrypText;
    }

    private static String cipherEncryption(String msg, String key) {

        msg = msg.replaceAll("\\s" , "");
        msg = msg.toUpperCase();

        // if irregular length, then perform padding
        int lenChk = 0;
        if (msg.length() % 2 != 0){
            msg += "0";
            lenChk = 1;
        }

        // message to matrices
        int[][] msg2D = new int[2][msg.length()];
        int itr1 = 0;
        int itr2 = 0;
        for (int i = 0; i < msg.length(); i++){
            if (i%2 == 0){
                msg2D[0][itr1] = ((int)msg.charAt(i)) - 65;
                itr1++;
            } else {
                msg2D[1][itr2] = ((int)msg.charAt(i)) - 65;
                itr2++;
            } // if-else
        } // for

        key = key.replaceAll("\\s","");
        key = key.toUpperCase();

        // key to 2x2 matrix
        int[][] key2D = new int[2][2];
        int itr3 = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                key2D[i][j] = (int)key.charAt(itr3)-65;
                itr3++;
            }
        }

        int deter = key2D[0][0] * key2D[1][1] - key2D[0][1] * key2D[1][0];
        deter = moduloFunc(deter, 26);

        int mulInverse = -1;
        for (int i = 0; i < 26; i++) {
            int tempInv = deter * i;
            if (moduloFunc(tempInv, 26) == 1){
                mulInverse = i;
                break;
            } else {
                continue;
            } // if-else
        } // for

        if (mulInverse == -1){
            System.out.println("invalid key");
            System.exit(1);
        }

        String encrypText = "";
        int itrCount = msg.length() / 2;
        if (lenChk == 0){
            // if msg length % 2 = 0
            for (int i = 0; i < itrCount; i++) {
                int temp1 = msg2D[0][i] * key2D[0][0] + msg2D[1][i] * key2D[0][1];
                encrypText += (char)((temp1 % 26) + 65);
                int temp2 = msg2D[0][i] * key2D[1][0] + msg2D[1][i] * key2D[1][1];
                encrypText += (char)((temp2 % 26) + 65);
            }
        } else {
            // if msg length % 2 != 0 (irregular length msg)
            for (int i = 0; i < itrCount-1; i++) {
                int temp1 = msg2D[0][i] * key2D[0][0] + msg2D[1][i] * key2D[0][1];
                encrypText += (char)((temp1 % 26) + 65);
                int temp2 = msg2D[0][i] * key2D[1][0] + msg2D[1][i] * key2D[1][1];
                encrypText += (char)((temp2 % 26) + 65);
            }
        }

        return encrypText;
    }

    // modulo function
    public static int moduloFunc(int a, int b){
        int result = a % b;
        if (result < 0){
            result += b;
        }
        return result;
    }


    public static void main(String[] args){
        Scanner in;
        in = new Scanner(System.in);
        System.out.print("Enter message: ");
        String msg = in.nextLine();
        System.out.print("Enter key: ");
        String key = in.nextLine();
        System.out.println("Encryption : " + cipherEncryption(msg, key));
        String cipherText = cipherEncryption(msg, key);
        System.out.println("Decryption : " + cipherDecryption(cipherText, key));
    }
}

