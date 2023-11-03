package com.example.clothy.Models;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class Product implements Serializable {
    String productImage,productName,ProductDesc,productCategory,ID;
    int productPrice;


    public Product() {
    }

    public Product(String productImage, String productName, String productDesc, String productCategory, String ID, int productPrice) {
        this.productImage = productImage;
        this.productName = productName;
        ProductDesc = productDesc;
        this.productCategory = productCategory;
        this.ID = ID;
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}
