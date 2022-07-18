package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

import com.skylightapp.Classes.AppConstants;
import com.skylightapp.R;
import com.twilio.rest.api.v2010.account.Message;

import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;

public class TellerSendEmailAct extends AppCompatActivity {
    String base64EncodedCredentials;
    Map<String, String> data;
    Session session;
    MimeMessage mimeMessage;
    String host = "https://skylightciacs.com:2096/";
    Integer port;
    final String user="bdm@skylightciacs.com";
    final String password= AppConstants.emailPassword;
    Bundle emailBundle;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 340;
    String phoneNumber;
    String smsMessage, cusEmail;
    String from = "Skylight Coop.";
    String to;
    Message message;
    private String subject,body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_t_send_email);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties=new Properties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        mimeMessage=new MimeMessage(session);

        emailBundle = getIntent().getExtras();
        if(emailBundle !=null){
            phoneNumber= emailBundle.getString(PROFILE_PHONE);
            smsMessage= emailBundle.getString("EmailMessage");
            from= emailBundle.getString("from");
            to= emailBundle.getString("to");
            cusEmail= emailBundle.getString("emailAddress");
            subject= emailBundle.getString("subject");

        }
        sendEmail(smsMessage,cusEmail,subject,mimeMessage);
    }
    protected void sendEmail(String smsMessage, String cusEmail, String subject, MimeMessage mimeMessage){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    mimeMessage.setFrom(new InternetAddress("Skylight Coop."));

                    //DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
                    mimeMessage.setSubject(subject);
                    mimeMessage.setText(smsMessage);
                    mimeMessage.setSender(new InternetAddress("bdm@skylightciacs.com"));
                    //mimeMessage.setDataHandler(handler);


                    if (cusEmail.indexOf(',') > 0)
                        mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(cusEmail));
                    else
                        mimeMessage.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(cusEmail));

                    Transport.send(mimeMessage);
                    System.out.println("message sent successfully....");

                }
                catch (MessagingException mex) {mex.printStackTrace();
                }

            }

        }).start();
    }

}