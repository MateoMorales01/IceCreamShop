package com.pluralsight.model;

public class Shake {
    public static Shake SMALL = new Shake(4.00, "Small");
    public static Shake MEDIUM = new Shake(5.50, "Medium");
    public static Shake LARGE = new Shake(7.00, "Large");

    private double price;
    private String display;

    public Shake(double price, String display) {
        this.price = price;
        this.display = display;
    }

    public double getPrice() {
        return price;
    }

    public String getDisplay() {
        return display;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public static Shake[] getAllSizes() {
        return new Shake[]{SMALL, MEDIUM, LARGE};
    }
}
