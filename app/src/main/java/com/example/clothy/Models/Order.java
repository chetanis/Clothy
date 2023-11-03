package com.example.clothy.Models;

import java.util.ArrayList;

public class Order {
    ArrayList<Product> products;
    String fullname,phonenumber,willaya,address,user_id,order_id;
    int total_price,state;

    public Order() {
    }

    public Order(ArrayList<Product> products, String fullname, String phonenumber, String willaya, String address, String user_id, String order_id, int total_price, int state) {
        this.products = products;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.willaya = willaya;
        this.address = address;
        this.user_id = user_id;
        this.order_id = order_id;
        this.total_price = total_price;
        this.state = state;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getWillaya() {
        return willaya;
    }

    public void setWillaya(String willaya) {
        this.willaya = willaya;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
