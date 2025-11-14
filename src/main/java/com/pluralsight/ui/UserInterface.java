package com.pluralsight.ui;

import com.pluralsight.data.ReceiptManager;
import com.pluralsight.model.*;

import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.concurrent.atomic.AtomicInteger;

public class UserInterface {
    private Scanner scanner;
    private Order currentOrder;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║   Welcome to Ice Cream Shop!   ║");
        System.out.println("║    Your Ice Cream Paradise     ║");
        System.out.println("╚════════════════════════════════╝");

        boolean running = true;
        while (running) {
            running = showHomeScreen();
        }

        System.out.println("Take Care!");
        scanner.close();
    }

    private boolean showHomeScreen() {
        System.out.println("=== Home Screen ===");
        System.out.println("1) New Order");
        System.out.println("0) Exit");
        System.out.println("Select an option: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            startNewOrder();
            return true;
        } else if (choice.equals("0")) {
            return false;
        } else {
            System.out.println("Invalid option. Please try again.");
            return true;
        }
    }

    private void startNewOrder() {
        currentOrder = new Order();
        System.out.println("🍨 --- New Order Started --- 🍨");
        showOrderScreen();
    }

    private void showOrderScreen() {
        boolean ordering = true;

        while (ordering) {
            System.out.println("=== Order Screen ===");
            System.out.println("1) Add Ice Cream");
            System.out.println("2) Add Shake");
            System.out.println("3) Checkout");
            System.out.println("0) Cancel Order");
            System.out.println("Select an option: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                addIceCream();
            } else if (choice.equals("2")) {
                addShake();
            } else if (choice.equals("3")) {
                if (validateOrder()) {
                    checkout();
                    ordering = false;
                }
            } else if (choice.equals("0")) {
                System.out.println("Order cancelled.");
                currentOrder = null;
                ordering = false;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private boolean validateOrder() {
        if (currentOrder.isEmpty()) {
            System.out.println("Cannot checkout with an empty order!");
            return false;
        }
        return true;
    }

    private void addIceCream() {
        System.out.println("🍦 --- Create Your Ice Cream --- 🍦");

        System.out.println("Select number of scoops (max 3):");
        Scoops[] sizes = Scoops.getAllSizes();
        IntStream.range(0, sizes.length).forEach(i ->
                System.out.printf("%d) %s - $%.2f (%d flavor%s max)",
                        i + 1, sizes[i].getDisplay(), sizes[i].getBasePrice(),
                        sizes[i].getNumberOfScoops(),
                        sizes[i].getNumberOfScoops() > 1 ? "s" : "")
        );
        System.out.println("Choice: ");
        int sizeChoice = getIntInput(1, sizes.length);
        Scoops size = sizes[sizeChoice - 1];

        System.out.println("Select container:");
        ConeType[] containers = ConeType.getAllTypes();
        IntStream.range(0, containers.length).forEach(i -> {
            String priceStr = containers[i].getPrice() > 0 ?
                    String.format(" (+$%.2f)", containers[i].getPrice()) : "";
            System.out.printf("%d) %s%s", i + 1, containers[i].getDisplay(), priceStr);
        });
        System.out.println("Choice: ");
        int containerChoice = getIntInput(1, containers.length);
        ConeType container = containers[containerChoice - 1];

        IceCream iceCream = new IceCream(size, container);

        addFlavors(iceCream);
        addPremiumToppings(iceCream);
        addRegularToppings(iceCream);
        addSauces(iceCream);

        currentOrder.addItem(iceCream);
        System.out.println("Ice cream added to order!");
    }

    private void addFlavors(IceCream iceCream) {
        System.out.printf("Select flavors (you can choose up to %d):", iceCream.getMaxFlavors());
        Flavors[] flavors = Flavors.getAllTypes();

        while (iceCream.getCurrentFlavorCount() < iceCream.getMaxFlavors()) {
            System.out.printf("Available flavors:");
            IntStream.range(0, flavors.length).forEach(i ->
                    System.out.printf("%d) %s", i + 1, flavors[i].getDisplay())
            );
            System.out.printf("0) Done selecting flavors");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, flavors.length);
            if (choice == 0) {
                break;
            } else {
                Flavors flavors1 = flavors[choice - 1];
                if (iceCream.addFlavor(flavors1)) {
                    System.out.printf("%s added (%d/%d)",
                            flavors1.getDisplay(),
                            iceCream.getCurrentFlavorCount(),
                            iceCream.getMaxFlavors());
                } else {
                    System.out.printf("Cannot add more flavors!");
                    break;
                }
            }
        }

        if (iceCream.getCurrentFlavorCount() == 0) {
            System.out.printf("No flavor selected. Adding Vanilla by default.");
            iceCream.addFlavor(Flavors.VANILLA);
        }
    }

    private void addPremiumToppings(IceCream iceCream) {
        System.out.printf("Add premium toppings (enter 0 when done):");
        Toppings[] toppings = Toppings.getAllTypes();

        boolean addingToppings = true;
        while (addingToppings) {
            IntStream.range(0, toppings.length).forEach(i ->
                    System.out.printf("%d) %s", i + 1, toppings[i].getDisplay())
            );
            System.out.printf("0) Done adding premium toppings");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, toppings.length);
            if (choice == 0) {
                addingToppings = false;
            } else {
                Toppings toppingType = toppings[choice - 1];
                System.out.printf("Extra portion? (yes/no): ");
                String extraChoice;
                extraChoice = scanner.nextLine().trim().toLowerCase();
                boolean extra = extraChoice.equals("yes");
                iceCream.addPremiumTopping(toppingType, extra);
                System.out.printf(" %s added%s",
                        toppingType.getDisplay(),
                        extra ? " (Extra)" : "");
            }
        }
    }

    private void addRegularToppings(IceCream iceCream) {
        System.out.printf("Add free toppings (enter 0 when done):");
        Toppings[] toppings = Toppings.getAllTypes();

        boolean addingToppings = true;
        while (addingToppings) {
            IntStream.range(0, toppings.length).forEach(i ->
                    System.out.printf("%d) %s", i + 1, toppings[i].getDisplay())
            );
            System.out.printf("0) Done adding toppings%n");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, toppings.length);
            if (choice == 0) {
                addingToppings = false;
            } else {
                Toppings toppingType = toppings[choice - 1];
                iceCream.addToppings(toppingType);
                System.out.printf(" %s added", toppingType.getDisplay());
            }
        }
    }

    private void addSauces(IceCream iceCream) {
        System.out.printf("Add sauces (enter 0 when done):");
        Syrups[] sauces = Syrups.getAllTypes();

        boolean addingSauces = true;
        while (addingSauces) {
            IntStream.range(0, sauces.length).forEach(i ->
                    System.out.printf("%d) %s", i + 1, sauces[i].getDisplay())
            );
            System.out.printf("0) Done adding sauces");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, sauces.length);
            if (choice == 0) {
                addingSauces = false;
            } else {
                Syrups sauceType = sauces[choice - 1];
                iceCream.addSauce(sauceType);
                System.out.printf(" %s added", sauceType.getDisplay());
            }
        }
    }

    private void addShake() {
        System.out.printf("🥤 --- Create Your Shake --- 🥤");

        System.out.printf("Select shake flavor:");
        ShakeFlavor[] flavors = ShakeFlavor.getAllFlavors();
        IntStream.range(0, flavors.length).forEach(i ->
                System.out.printf("%d) %s", i + 1, flavors[i].getDisplay())
        );
        System.out.printf("Choice: ");
        int Flavors = getIntInput(1, flavors.length);
        int flavorChoice;
        flavorChoice = 0;
        ShakeFlavor flavor = flavors[flavorChoice - 1];

        System.out.printf("Select shake size:");
        Shake[] sizes = Shake.getAllSizes();
        IntStream.range(0, sizes.length).forEach(i ->
                System.out.printf("%d) %s - $%.2f", i + 1, sizes[i].getDisplay(), sizes[i].getPrice())
        );
        System.out.printf("Choice: ");
        int sizeChoice = getIntInput(1, sizes.length);
        ShakeSize size = sizes[sizeChoice - 1];

        Shake shake = new Shake(1.50, Flavors);
        addShakeToppings(shake);

        currentOrder.addItem(shake);
        System.out.printf("Shake added to order!");
    }

    private void addShakeToppings(Shake shake) {
        System.out.printf("Add shake toppings (enter 0 when done):");
        ShakeTopping[] toppings = ShakeTopping.getAllToppings();

        boolean addingToppings = true;
        while (addingToppings) {
            IntStream.range(0, toppings.length).forEach(i ->
                    System.out.printf("%d) %s (+$%.2f)",
                            i + 1, toppings[i].getDisplay(), toppings[i].getPrice())
            );
            System.out.printf("0) Done adding toppings");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, toppings.length);
            if (choice == 0) {
                addingToppings = false;
            } else {
                ShakeToppings topping = topping[choice - 1];
                shake.addTopping(topping);
                System.out.printf(" %s added", topping.getDisplay());
            }
        }
    }

    private void checkout() {
        System.out.printf("╔════════════════════════════════╗");
        System.out.printf("║        ORDER SUMMARY           ║");
        System.out.printf("╚════════════════════════════════╝");

        AtomicInteger itemNumber = new AtomicInteger(1);
        currentOrder.getItems().forEach(item -> {
            System.out.printf("Item %d:", itemNumber.getAndIncrement());
            if (item instanceof IceCream) {
                System.out.printf("%s", item.getClass());
                System.out.printf("Price: $%.2f", item.getPrice());
            } else {
                System.out.printf("%s", item.getClass());
            }
        });

        System.out.printf("%n════════════════════════════════%n");
        System.out.printf("TOTAL: $%.2f", currentOrder.getTotalPrice());
        System.out.printf("════════════════════════════════%n");

        System.out.printf("Confirm order? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            ReceiptManager.saveReceipt(currentOrder);
            System.out.printf("Order complete! Enjoy your ice cream! 🍦");
            currentOrder = null;
        } else {
            System.out.printf("Order cancelled.");
            currentOrder = null;
        }
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("Please enter a number between %d and %d: ", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.printf("Invalid input. Please enter a number: ");
            }
        }
    }
}