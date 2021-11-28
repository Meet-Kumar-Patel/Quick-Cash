package com.example.quickcash.Ratings;

import java.util.UUID;

public class Rating {

    private final String ratingID;
    private String senderUserEmail;
    private String receiverUserEmail;
    private int ratingValue;

    public Rating(String senderUserEmail, String receiverUserEmail, int ratingValue) {
        this.ratingID = UUID.randomUUID().toString();
        this.senderUserEmail = senderUserEmail;
        this.receiverUserEmail = receiverUserEmail;
        this.ratingValue = ratingValue;
    }

    public Rating() {this.ratingID = UUID.randomUUID().toString();}

    public void setSenderUserEmail(String senderUserEmail) {
        this.senderUserEmail = senderUserEmail;
    }

    public void setReceiverUserEmail(String receiverUserEmail) {
        this.receiverUserEmail = receiverUserEmail;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getRatingID() {
        return ratingID;
    }

    public String getSenderUserEmail() {
        return senderUserEmail;
    }

    public String getReceiverUserEmail() {
        return receiverUserEmail;
    }

    public int getRatingValue() {
        return ratingValue;
    }
}
