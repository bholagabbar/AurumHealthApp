package com.iotaconcepts.aurum;

public class Product
{
    String name;
    int price;
    int image;
    boolean box;

    Product(int _price, String name)
    {
        this.name= name;
        price = _price;
    }
}