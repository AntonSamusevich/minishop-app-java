package com.example.minishop;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int id; //идентификатор продукта
    private String name; //название продукта
    private double price; //цена продукта
    private boolean isSelected; //поле для хранения информации о выборе продукта

    //конструктор класса Product
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isSelected = false; // По умолчанию продукт не выбран
    }

    //конструктор Parcelable, используемый для восстановления объекта из Parcel
    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        isSelected = in.readByte() != 0; // Чтение значения isSelected из Parcel
    }

    //статический CREATOR, используемый для создания экземпляра класса из Parcel
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    //методы доступа к полям класса
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //метод для записи данных объекта в Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeByte((byte) (isSelected ? 1 : 0)); // Запись значения isSelected в Parcel
    }
}
