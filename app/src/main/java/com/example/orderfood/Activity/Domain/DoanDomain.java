package com.example.orderfood.Activity.Domain;

public class DoanDomain {
    private String titletxt;
    private String picCart;
    private Double fee;

    public DoanDomain(String titletxt, String picCart, Double fee) {
        this.titletxt = titletxt;
        this.picCart = picCart;
        this.fee = fee;
    }

    public String getTitletxt() {
        return titletxt;
    }

    public void setTitletxt(String titletxt) {
        this.titletxt = titletxt;
    }

    public String getPicCart() {
        return picCart;
    }

    public void setPicCart(String picCart) {
        this.picCart = picCart;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
