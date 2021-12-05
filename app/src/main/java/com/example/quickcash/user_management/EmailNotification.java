package com.example.quickcash.user_management;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quickcash.R;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;

public class EmailNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_notification);

    }
    // source for the sendEmailNotification https://www.youtube.com/watch?v=roruU4hVwXA
    public void sendEmailNotification(String senderEmailAddress, String recipientEmailAddress,
                                  String senderPassword, String senderMessage) {
    final String username = senderEmailAddress;
    final String password = senderPassword;
    // defining properties for the email transfer
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });
    try {
        Message message = new MimeMessage(session);
        //setting the sender email adress
        message.setFrom(new InternetAddress(username));
        //setting the recipieter email address
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipientEmailAddress));
        message.setSubject("QuickCash Notification");
        // setting the email message
        message.setText(senderMessage);
        Transport.send(message);
    } catch (MessagingException e) {
        throw new RuntimeException(e);
    }
}
}