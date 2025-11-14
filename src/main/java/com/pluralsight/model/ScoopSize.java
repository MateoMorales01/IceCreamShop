package com.pluralsight.model;

public class ScoopSize {
    public static final ScoopSize SMALL = new ScoopSize(4.00, "Small");
    public static final ScoopSize MEDIUM = new ScoopSize(6.00, "Medium");
    public static final ScoopSize LARGE = new ScoopSize(8.00, "Large");

    private final double basePrice;
    private final String display;

    private ScoopSize(double basePrice, String display) {
        this.basePrice = basePrice;
        this.display = display;
    }

    public double getBasePrice() { return basePrice; }
    public String getDisplay() { return display; }

    public static ScoopSize[] getAllSizes() {
        return new ScoopSize[]{SMALL, MEDIUM, LARGE};
    }
}
}
