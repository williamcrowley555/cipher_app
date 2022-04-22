package security;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    public static PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(GenerateKeysRSA.PRIVATE_KEY_FILE).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(GenerateKeysRSA.PUBLIC_KEY_FILE).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static void main(String[] args) throws Exception {
        try {
            new GenerateKeysRSA(1024).generateKeysToFile();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        PrivateKey privateKey = getPrivateKey();
        PublicKey publicKey = getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        String original = "toiquaemtuyetlam";
        byte[] byteEncrypted = cipher.doFinal(original.getBytes());
        String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);


        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
        String decrypted = new String(byteDecrypted);
        System.out.println("original  text: " + original);
        System.out.println("encrypted text: " + encrypted);
        System.out.println("decrypted text: " + decrypted);

    }
}
