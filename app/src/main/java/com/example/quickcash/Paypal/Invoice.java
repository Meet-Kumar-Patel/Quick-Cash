package com.example.quickcash.Paypal;

import java.util.UUID;

public class Invoice {

    private final String InvoiceID;
    private String jobID;
    private String totalPayment;

    protected Invoice(String jobID, String totalPayment) {
        this.InvoiceID = UUID.randomUUID().toString();
        this.jobID = jobID;
        this.totalPayment = totalPayment;
    }

    protected Invoice(){
        this.InvoiceID = UUID.randomUUID().toString();
    }

    public String getInvoiceID() {
        return InvoiceID;
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
