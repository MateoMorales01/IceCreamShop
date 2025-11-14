package com.pluralsight.model;

public class Syrups {
    public static final Syrups CHOCOLATE = new Syrups("Chocolate Sauce");
    public static final Syrups CARAMEL = new Syrups("Caramel Sauce");
    public static final Syrups STRAWBERRY = new Syrups("Strawberry Sauce");
    public static final Syrups HOT_FUDGE = new Syrups("Hot Fudge");
    public static final Syrups BUTTERSCOTCH = new Syrups("Butterscotch");

    private String display;

    public Syrups(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public static Syrups[] getAllTypes() {
        return new Syrups[]{CHOCOLATE, CARAMEL, STRAWBERRY, HOT_FUDGE, BUTTERSCOTCH};
    }
}
