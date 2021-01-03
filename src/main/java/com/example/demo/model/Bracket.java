package com.example.demo.model;

import java.util.List;

public class Bracket {
    private float taxAmount;
    private String title;
    private float percentage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public float getTaxAmount() {
        return this.taxAmount;
    }

}
