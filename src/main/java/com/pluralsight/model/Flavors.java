package com.pluralsight.model;

public class Flavors {
    public static final Flavors VANILLA = new Flavors("Vanilla");
    public static final Flavors CHOCOLATE = new Flavors("Chocolate");
    public static final Flavors STRAWBERRY = new Flavors("Strawberry");
    public static final Flavors MINT_CHIP = new Flavors("Mint Chip");
    public static final Flavors COOKIE_DOUGH = new Flavors("Cookie Dough");
    public static final Flavors COOKIES_CREAM = new Flavors("Cookies & Cream");
    public static final Flavors ROCKY_ROAD = new Flavors("Rocky Road");
    public static final Flavors BUTTER_PECAN = new Flavors("Butter Pecan");

    private final String display;

    private Flavors(String display) {
        this.display = display;
    }

    public String getDisplay() { return display; }

    public static Flavors[] getAllTypes() {
        return new Flavors[]{
                VANILLA, CHOCOLATE, STRAWBERRY, MINT_CHIP,
                COOKIE_DOUGH, COOKIES_CREAM, ROCKY_ROAD, BUTTER_PECAN
        };
    }
}
