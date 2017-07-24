package com.bk.bestfind.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mrt on 24/06/2017.
 */

public class Product implements Parcelable {
    private int id;
    private String name;
    private String price;
    private String category;
    private String description;
    private String image_link;
    private String product_link;
    private String product_src_id;
    private Shop shop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getProduct_link() {
        return product_link;
    }

    public void setProduct_link(String product_link) {
        this.product_link = product_link;
    }

    public String getProduct_src_id() {
        return product_src_id;
    }

    public void setProduct_src_id(String product_src_id) {
        this.product_src_id = product_src_id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.category);
        dest.writeString(this.description);
        dest.writeString(this.image_link);
        dest.writeString(this.product_link);
        dest.writeString(this.product_src_id);
        dest.writeParcelable(this.shop, flags);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.price = in.readString();
        this.category = in.readString();
        this.description = in.readString();
        this.image_link = in.readString();
        this.product_link = in.readString();
        this.product_src_id = in.readString();
        this.shop = in.readParcelable(Shop.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
