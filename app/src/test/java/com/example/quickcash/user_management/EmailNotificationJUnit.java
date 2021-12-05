package com.example.quickcash.user_management;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.common.Constants;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class EmailNotificationJUnit {

    String senderEmailAddress;
    String recipientEmailAddress;
    String senderPassword;
    String senderMessage;
    @Test
    public void EmailNotificationSending(){
        EmailNotification emailNotification = Mockito.mock(EmailNotification.class);
        this.senderEmailAddress = Constants.EMAIL_ADDRESS;
        this.recipientEmailAddress = Constants.JOHN_EMAIL_ADDRESS;
        this.senderPassword = Constants.STRONG_PASSWORD;
        this.senderMessage = Constants.HI +Constants.EMAIL_ADDRESS+ Constants.THE_FOLLOWING_EMPLOYER +"Smith" + Constants.CHECK_OUT_DETAILS;
        Mockito.when(emailNotification.sendEmailNotification(senderEmailAddress,recipientEmailAddress,senderPassword,senderMessage)).thenReturn();
    }
}
