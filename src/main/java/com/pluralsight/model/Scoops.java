package com.pluralsight.model;

public class Scoops {
    public static final Scoops One = new Scoops(1.50, "Small");
    public static final Scoops Two = new Scoops(2.50, "Medium");
    public static final Scoops Three = new Scoops(3.50, "Large");

    private final double basePrice;
    private final String display;

    private Scoops(double basePrice, String display) {
        this.basePrice = basePrice;
        this.display = display;
    }

    public double getBasePrice() { return basePrice; }
    public String getDisplay() { return display; }

    public static Scoops[] getAllSizes() {
        return new Scoops[]{One,Two, Three};
    }

    public int getNumberOfScoops() {
        return 0;
    }
}
