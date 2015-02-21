package hackpoly.swaggertext;

/**
 * Created by Roshan on 2/21/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * @author JavaDigest
 *
 */
public class Encryption extends Activity {



    /**
     * String to hold name of the encryption algorithm.
     */
    public static final String ALGORITHM = "RSA";

    /**
     * String to hold the name of the private key file.
     */

    public static String PRIVATE_KEY_FILE = "privateKey";

    /**
     * String to hold name of the public key file.
     */
    public static String PUBLIC_KEY_FILE = "publicKey";

    /**
     * Generate key which contains a pair of private and public key using 1024
     * bytes. Store the set of keys in Prvate.key and Public.key files.
     *
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws FileNotFoundException
     */
    public   void generateKey() {
        try {

           // ContextWrapper c = new ContextWrapper(this);
          //  String path = c.getFilesDir().toString();

            ContextWrapper c = new ContextWrapper(this);
            final String path = c.getFilesDir().toString() + "/";



            PRIVATE_KEY_FILE = path + PRIVATE_KEY_FILE;
            PUBLIC_KEY_FILE = path + PUBLIC_KEY_FILE;

            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024);
            final KeyPair key = keyGen.generateKeyPair();

            // Create files to store public and private key
            //File privateKeyFile = new File(PRIVATE_KEY_FILE);
           // File publicKeyFile = new File(PUBLIC_KEY_FILE);



           // Saving the Public key in a file
           OutputStreamWriter outPublic = new OutputStreamWriter(openFileOutput(PUBLIC_KEY_FILE, Context.MODE_PRIVATE));
            outPublic.write(keyGen.genKeyPair().getPublic().toString());
            outPublic.flush();


           // Saving the Private key in a file
            OutputStreamWriter outPrivate = new OutputStreamWriter(openFileOutput(PRIVATE_KEY_FILE, Context.MODE_PRIVATE));
           outPrivate.write(keyGen.genKeyPair().getPrivate().toString());
            outPrivate.flush();

          // outPrivate.close();
          //  outPublic.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * The method checks if the pair of public and private key has been generated.
     *
     * @return flag indicating if the pair of keys were generated.
     */
    public static boolean areKeysPresent() {

        File privateKey = new File(PRIVATE_KEY_FILE);
        File publicKey = new File(PUBLIC_KEY_FILE);

        if (privateKey.exists() && publicKey.exists()) {
            return true;
        }
        return false;
    }

    /**
     * Encrypt the plain text using public key.
     *
     * @param text
     *          : original plain text
     * @param key
     *          :The public key
     * @return Encrypted text
     * @throws java.lang.Exception
     */
    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    /**
     * Decrypt text using private key.
     *
     * @param text
     *          :encrypted text
     * @param key
     *          :The private key
     * @return plain text
     * @throws java.lang.Exception
     */
    public static String decrypt(byte[] text, PrivateKey key) {
        byte[] decryptedText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, key);
            decryptedText = cipher.doFinal(text);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(decryptedText);
    }


}