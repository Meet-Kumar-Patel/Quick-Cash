package com.example.quickcash.Paypal;

import java.util.UUID;

public class Invoice {

    private final String InvoiceID;
    private String jobID;
    private double totalPayment;

    protected Invoice(String jobID, double totalPayment) {
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

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }
}
