package com.pluralsight.model;

public class Toppings {
    public static final Toppings WHIPPED_CREAM = new Toppings("Whipped Cream");
    public static final Toppings CRUSHED_OREOS = new Toppings("Crushed Oreos");
    public static final Toppings GUMMY_BEARS = new Toppings("Gummy Bears");
    public static final Toppings BROWNIE_PIECES = new Toppings("Brownie Pieces");
    public static final Toppings MARSHMALLOWS = new Toppings("Marshmallows");
    public static final Toppings CHOCOLATE_CHIPS = new Toppings("Chocolate Chips");
    public static final Toppings PEANUT_BUTTER = new Toppings("Peanut Butter");
    public static final Toppings COOKIE_PIECES = new Toppings("Cookie Pieces");
    public static final Toppings NUTS = new Toppings("Nuts");
    public static final Toppings RAINBOW_SPRINKLES = new Toppings("Rainbow Sprinkles");
    public static final Toppings CHOCOLATE_SPRINKLES = new Toppings("Chocolate Sprinkles");
    public static final Toppings CHERRY = new Toppings("Cherry");
    public static final Toppings COCONUT_FLAKES = new Toppings("Coconut Flakes");
    public static final Toppings KitKats = new Toppings("KitKats");
    public static final Toppings SnickerBits = new Toppings("Snickers Bits");
    public static final Toppings ButterFingers = new Toppings("Butterfingers");
    public static final Toppings MM = new Toppings("M&M's");

    private String display;

    public Toppings(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public static Toppings[] getAllTypes() {
        return new Toppings[]{
                WHIPPED_CREAM, CRUSHED_OREOS, GUMMY_BEARS,
                BROWNIE_PIECES, MARSHMALLOWS, CHOCOLATE_CHIPS, PEANUT_BUTTER, COOKIE_PIECES, NUTS, RAINBOW_SPRINKLES,
                CHOCOLATE_SPRINKLES, CHERRY, COCONUT_FLAKES, SnickerBits, KitKats, MM, ButterFingers
        };
    }
}
