package com.example.quickcash.ratings;

import java.util.UUID;
/**
 * Rating Object
 */
public class Rating {

    private final String ratingID;
    private String senderUserEmail;
    private String receiverUserEmail;
    private float ratingValue;
    private String jobPostingID;

    /**
     * Rating
     *
     * @param senderUserEmail
     * @param receiverUserEmail
     * @param ratingValue
     * @param jobPostingID
     * @return
     */
    public Rating(String senderUserEmail, String receiverUserEmail, float ratingValue, String jobPostingID) {
        this.ratingID = UUID.randomUUID().toString();
        this.senderUserEmail = senderUserEmail;
        this.receiverUserEmail = receiverUserEmail;
        this.ratingValue = ratingValue;
        this.jobPostingID = jobPostingID;
    }

    /**
     * Rating
     * setting random ratingID
     */
    public Rating() {
        this.ratingID = UUID.randomUUID().toString();
    }

    public String getRatingID() {
        return ratingID;
    }

    public String getSenderUserEmail() {
        return senderUserEmail;
    }

    public void setSenderUserEmail(String senderUserEmail) {
        this.senderUserEmail = senderUserEmail;
    }

    public String getReceiverUserEmail() {
        return receiverUserEmail;
    }

    public void setReceiverUserEmail(String receiverUserEmail) {
        this.receiverUserEmail = receiverUserEmail;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getJobPostingID() {
        return jobPostingID;
    }

    public void setJobPostingID(String jobPostingID) {
        this.jobPostingID = jobPostingID;
    }

}
