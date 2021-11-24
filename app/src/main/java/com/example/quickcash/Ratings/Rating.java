package com.example.quickcash.Ratings;

public class Rating {

    private String ratingID;
    private String senderUserID;
    private String receiverUserId;
    private int ratingValue;

    public Rating(String ratingID, String senderUserID, String receiverUserId, int ratingValue) {
        this.ratingID = ratingID;
        this.senderUserID = senderUserID;
        this.receiverUserId = receiverUserId;
        this.ratingValue = ratingValue;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }

    public void setSenderUserID(String senderUserID) {
        this.senderUserID = senderUserID;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getRatingID() {
        return ratingID;
    }

    public String getSenderUserID() {
        return senderUserID;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public int getRatingValue() {
        return ratingValue;
    }
}
