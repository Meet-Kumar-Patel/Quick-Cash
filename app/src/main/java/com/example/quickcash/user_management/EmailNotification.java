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
/**
 * This class is responsible for sending email notification to a recipient email address from a sender email address.
 */
public class EmailNotification extends AppCompatActivity {

    /**
     * This is the first method to run while creating of the class.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_notification);

    }

    // Reference: https://www.youtube.com/watch?v=roruU4hVwXA
    // Date accessed: December 5,2021
    // The Reference was used to understand and implement the send email notification method.

    /**
     * This method is responsible for sending the email notification to the recipient email address from the sender email address
     * @param senderEmailAddress    the sender email address
     * @param recipientEmailAddress the recipient email address
     * @param senderPassword        the sender password
     * @param senderMessage         the email message.
     */
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
        // instantiating session for email transfer
        Session session = Session.getInstance(props, new Authenticator() {
            /**
             * This method is responsible for authenticating the password
             * @return password authentication object with provided username and password.
             */
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmailAddress));
            message.setSubject("QuickCash Notification");
            // setting the email message
            message.setText(senderMessage);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}