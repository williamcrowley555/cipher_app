package security;

public class Affine {
    public static String encryptMessage(String msg, int a, int b)
    {
        String cipher = "";
        for (int i = 0; i < msg.length(); i++) {
            // Avoid space to be encrypted
			/* applying encryption formula ( a x + b ) mod m
			{here x is msg[i] and m is 26} and added 'A' to
			bring it in range of ascii alphabet[ 65-90 | A-Z ] */
            if (msg.charAt(i) != ' ') {
                cipher = cipher
                        + (char) ((((a * (msg.charAt(i) - 'A')) + b) % 26) + 'A');
            } else {
                cipher += msg.charAt(i);
            }
        }
        return cipher;
    }

    public static String decryptCipher(String cipher, int a, int b)
    {
        String msg = "";
        int a_inv = 0;
        int flag = 0;

        for (int i = 0; i < 26; i++) {
            flag = (a * i) % 26;
            if (flag == 1) {
                a_inv = i;
            }
        }
        for (int i = 0; i < cipher.length(); i++) {
            if (cipher.charAt(i) != ' ') {
                msg = msg + (char) (((a_inv *
                        ((cipher.charAt(i) + 'A' - b)) % 26)) + 'A');
            } else {
                msg += cipher.charAt(i);
            }
        }

        return msg;
    }

    public static void main(String[] args)
    {
        String msg = "HENTOITHUBAY";
        // Calling encryption function
        String cipherText = encryptMessage(msg, 5, 6);
        System.out.println("Encrypted Message is : " + cipherText);

        // Calling Decryption function
        System.out.println("Decrypted Message is: " + decryptCipher(cipherText, 5, 6));

    }
}
