package hackpoly.swaggertext;


import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.PublicKey;


public class MainActivity extends ActionBarActivity {

    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContextWrapper c = new ContextWrapper(this);
        final String path = c.getFilesDir().toString() + "/";

        try
        {
            Encryption e = new Encryption();
            e.generateKey();
        }
catch(Exception exx)
{

}
            buttonSend = (Button) findViewById(R.id.buttonSend);
            textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
            textSMS = (EditText) findViewById(R.id.editTextSMS);

            buttonSend.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    String phoneNo = textPhoneNo.getText().toString();
                    String sms = textSMS.getText().toString();


                    try {
                        SmsManager smsManager = SmsManager.getDefault();

                        //final String originalText = sms;
                       ObjectInputStream inputStream = null;
                       // inputStream.defaultReadObject();
                        // Encrypt the string using the public key
//
//                       BufferedReader br = new BufferedReader(new FileReader(path + Encryption.PUBLIC_KEY_FILE));
//                       String line;
//                        StringBuilder sb = new StringBuilder();
//                        while ((line = br.readLine()) != null) {
//                            sb.append(line);
//
//                        }
//                        String everything = sb.toString();
//                        br.close();

                        //inputStream= new FileInputStream(new File(path+Encryption.PUBLIC_KEY_FILE))) ;
                        FileInputStream f = new FileInputStream(new File(path+Encryption.PUBLIC_KEY_FILE));
                       // f.close();


                        inputStream = new ObjectInputStream(f);

                       // f.close();
                        //inputStream.close();
                        //Object a = ;
                        //inputStream.();
                        final PublicKey publicKey = (PublicKey) inputStream.readObject();
                        final byte[] cipherText = Encryption.encrypt(sms, publicKey);

                        // Decrypt the cipher text using the private key.
//                        inputStream = new ObjectInputStream(new FileInputStream(path+Encryption.PRIVATE_KEY_FILE));
//                        final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
//                        final String plainText = Encryption.decrypt(cipherText, privateKey);

                        // Printing the Original, Encrypted and Decrypted Text
                        System.out.println("Original: " + sms);
                        System.out.println("Encrypted: " + cipherText.toString());
                       // System.out.println("Decrypted: " + plainText);


                        smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                        Toast.makeText(getApplicationContext(), "SMS Sent!",
                                Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS failed, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }
            });


        }
    }



