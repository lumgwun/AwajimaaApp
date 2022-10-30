package com.skylightapp.Classes;

import android.content.Context;
import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

@SuppressWarnings("ALL")
public class SendMail extends AsyncTask {
    private Context context;
    private Session session;
    private String email,receiverEmail;
    private String password,sender;
    private String subject,body;
    private String message;
    private ProgressDialog progressDialog;
    public SendMail(Context context, String email, String subject, String message){
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        try {
            MimeMessage mm = new MimeMessage(session);
            //DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            mm.setFrom(new InternetAddress("Awajima Coop."));
            //mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mm.setSubject(subject);
            mm.setText(message);
            mm.setSender(new InternetAddress("admin@skylightciacs.com"));
            //mm.setDataHandler(handler);

            if (receiverEmail.indexOf(',') > 0)
                mm.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
            else
                mm.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));

            Transport.send(mm);

        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }
    /*@Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
    }*/

}
