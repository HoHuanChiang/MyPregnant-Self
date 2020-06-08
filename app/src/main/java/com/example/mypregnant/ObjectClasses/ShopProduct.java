package com.example.mypregnant.ObjectClasses;

public class ShopProduct {
    int productID;
    String shop;
    String link;
    String productName;
    int pregnantWeek;
    String category;
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPregnantWeek() {
        return pregnantWeek;
    }

    public void setPregnantWeek(int pregnantWeek) {
        this.pregnantWeek = pregnantWeek;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
