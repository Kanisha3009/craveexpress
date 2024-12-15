package com.example.craveexpress;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodDrink implements Parcelable {
    private String name;
    private String price;
    private int imageResId;
    private int quantity;

    public FoodDrink(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.quantity = 0; // Default quantity
    }

    // Getters and Setters
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

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Parcelable implementation
    protected FoodDrink(Parcel in) {
        name = in.readString();
        price = in.readString();
        imageResId = in.readInt();
        quantity = in.readInt();
    }

    public static final Creator<FoodDrink> CREATOR = new Creator<FoodDrink>() {
        @Override
        public FoodDrink createFromParcel(Parcel in) {
            return new FoodDrink(in);
        }

        @Override
        public FoodDrink[] newArray(int size) {
            return new FoodDrink[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeInt(imageResId);
        dest.writeInt(quantity);
    }
}
