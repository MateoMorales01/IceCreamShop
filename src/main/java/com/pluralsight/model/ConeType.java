package com.pluralsight.model;

public class ConeType {
    public static final ConeType CUP = new ConeType("Cup", 0.00);
    public static final ConeType SUGAR_CONE = new ConeType("Sugar Cone", 0.50);
    public static final ConeType WAFFLE_CONE = new ConeType("Waffle Cone", 1.00);

    private final String display;
    private final double price;

    private ConeType(String display, double price) {
        this.display = display;
        this.price = price;
    }

    public String getDisplay() { return display; }
    public double getPrice() { return price; }

    public static ConeType[] getAllTypes() {
        return new ConeType[]{CUP, SUGAR_CONE, WAFFLE_CONE};
    }
}
}
