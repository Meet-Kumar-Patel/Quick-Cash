package com.example.quickcash.paypal;

import java.util.UUID;

/**
 * Invoice object which contains the billing information for paypal.
 */
public class Invoice {

    private final String invoiceID;
    private String jobID;
    private String totalPayment;

    /**
     * Invoice constructor.
     * @param jobID
     * @param totalPayment
     */
    protected Invoice(String jobID, String totalPayment) {
        this.invoiceID = UUID.randomUUID().toString();
        this.jobID = jobID;
        this.totalPayment = totalPayment;
    }

    protected Invoice(){
        this.invoiceID = UUID.randomUUID().toString();
    }

    public String getInvoiceID() {
        return invoiceID;
    }


    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }
}
