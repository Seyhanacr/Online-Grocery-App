package com.seyhn.acar.marketprojesi.models;

//ana sayfa recommended(öneri) bölümü
public class RecommendedModel {
    String name;
    String description ;
    String img_url;
    String rating;
    int price;
     public RecommendedModel(){}

     public RecommendedModel(String name, String description,String img_url, String rating, int price){
         this.name=name;
         this.description=description;
         this.img_url=img_url;
         this.rating=rating;
         this.price=price;
     }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

