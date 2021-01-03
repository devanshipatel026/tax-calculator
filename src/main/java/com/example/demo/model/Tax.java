package com.example.demo.model;

import java.util.List;

public class Tax {

    public String message;

    public int statusCode;

    public List<Bracket> bracket;

    public float totalTax;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setBracket(List<Bracket> bracket){
        this.bracket = bracket;
    }

    public List<Bracket> getBracket(){
        return this.bracket;
    }

    public void setTotalTax(float totalTax){this.totalTax = totalTax;}

    public float getTotalTax(){return this.totalTax;}
}
