package com.bk.bestfind.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mrt on 24/06/2017.
 */

public class Shop implements Parcelable {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private double longitude;
    private double latitude;
    private String shop_src_id;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getShop_src_id() {
        return shop_src_id;
    }

    public void setShop_src_id(String shop_src_id) {
        this.shop_src_id = shop_src_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeString(this.shop_src_id);
    }

    public Shop() {
    }

    protected Shop(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.shop_src_id = in.readString();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
}
