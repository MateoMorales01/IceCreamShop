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
        System.out.printf("╔════════════════════════════════╗%n");
        System.out.printf("║   Welcome to Sweet Scoops! 🍦  ║%n");
        System.out.printf("║    Your Ice Cream Paradise     ║%n");
        System.out.printf("╚════════════════════════════════╝%n");

        boolean running = true;
        while (running) {
            running = showHomeScreen();
        }

        System.out.printf("%nThank you for visiting Sweet Scoops!%n");
        System.out.printf("Have a sweet day! 🍦%n");
        scanner.close();
    }

    private boolean showHomeScreen() {
        System.out.printf("%n=== Home Screen ===%n");
        System.out.printf("1) New Order%n");
        System.out.printf("0) Exit%n");
        System.out.printf("Select an option: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            startNewOrder();
            return true;
        } else if (choice.equals("0")) {
            return false;
        } else {
            System.out.printf("Invalid option. Please try again.%n");
            return true;
        }
    }

    private void startNewOrder() {
        currentOrder = new Order();
        System.out.printf("%n🍨 --- New Order Started --- 🍨%n");
        showOrderScreen();
    }

    private void showOrderScreen() {
        boolean ordering = true;

        while (ordering) {
            System.out.printf("%n=== Order Screen ===%n");
            System.out.printf("1) Add Ice Cream%n");
            System.out.printf("2) Add Shake%n");
            System.out.printf("3) Checkout%n");
            System.out.printf("0) Cancel Order%n");
            System.out.printf("Select an option: ");

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
                System.out.printf("Order cancelled.%n");
                currentOrder = null;
                ordering = false;
            } else {
                System.out.printf("Invalid option. Please try again.%n");
            }
        }
    }

    private boolean validateOrder() {
        if (currentOrder.isEmpty()) {
            System.out.printf("Cannot checkout with an empty order!%n");
            return false;
        }
        return true;
    }

    private void addIceCream() {
        System.out.printf("%n🍦 --- Create Your Ice Cream --- 🍦%n");

        System.out.printf("%nSelect number of scoops (max 3):%n");
        Scoops[] sizes = Scoops.getAllSizes();
        IntStream.range(0, sizes.length).forEach(i ->
                System.out.printf("%d) %s - $%.2f (%d flavor%s max)%n",
                        i + 1, sizes[i].getDisplay(), sizes[i].getBasePrice(),
                        sizes[i].getNumberOfScoops(),
                        sizes[i].getNumberOfScoops() > 1 ? "s" : "")
        );
        System.out.printf("Choice: ");
        int sizeChoice = getIntInput(1, sizes.length);
        Scoops size = sizes[sizeChoice - 1];

        System.out.printf("%nSelect container:%n");
        ConeType[] containers = ConeType.getAllTypes();
        IntStream.range(0, containers.length).forEach(i -> {
            String priceStr = containers[i].getPrice() > 0 ?
                    String.format(" (+$%.2f)", containers[i].getPrice()) : "";
            System.out.printf("%d) %s%s%n", i + 1, containers[i].getDisplay(), priceStr);
        });
        System.out.printf("Choice: ");
        int containerChoice = getIntInput(1, containers.length);
        ConeType container = containers[containerChoice - 1];

        IceCream iceCream = new IceCream(size, container);

        addFlavors(iceCream);
        addPremiumToppings(iceCream);
        addMixIns(iceCream);
        addRegularToppings(iceCream);
        addSauces(iceCream);

        currentOrder.addItem(iceCream);
        System.out.printf("%n✓ Ice cream added to order!%n");
    }

    private void addFlavors(IceCream iceCream) {
        System.out.printf("%nSelect flavors (you can choose up to %d):%n", iceCream.getMaxFlavors());
        Flavors[] flavors = Flavors.getAllTypes();

        while (iceCream.getCurrentFlavorCount() < iceCream.getMaxFlavors()) {
            System.out.printf("%nAvailable flavors:%n");
            IntStream.range(0, flavors.length).forEach(i ->
                    System.out.printf("%d) %s%n", i + 1, flavors[i].getDisplay())
            );
            System.out.printf("0) Done selecting flavors%n");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, flavors.length);
            if (choice == 0) {
                break;
            } else {
                Flavors flavors1 = flavors[choice - 1];
                if (iceCream.addFlavor(flavors1)) {
                    System.out.printf("✓ %s added (%d/%d)%n",
                            flavors1.getDisplay(),
                            iceCream.getCurrentFlavorCount(),
                            iceCream.getMaxFlavors());
                } else {
                    System.out.printf("Cannot add more flavors!%n");
                    break;
                }
            }
        }

        if (iceCream.getCurrentFlavorCount() == 0) {
            System.out.printf("No flavor selected. Adding Vanilla by default.%n");
            iceCream.addFlavor(Flavors.VANILLA);
        }
    }

    private void addPremiumToppings(IceCream iceCream) {
        System.out.printf("%nAdd premium toppings (enter 0 when done):%n");
        Toppings[] toppings = Toppings.getAllTypes();

        boolean addingToppings = true;
        while (addingToppings) {
            IntStream.range(0, toppings.length).forEach(i ->
                    System.out.printf("%d) %s%n", i + 1, toppings[i].getDisplay())
            );
            System.out.printf("0) Done adding premium toppings%n");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, toppings.length);
            if (choice == 0) {
                addingToppings = false;
            } else {
                Toppings toppingType = toppings[choice - 1];
                System.out.printf("Extra portion? (yes/no): ");
                String extraChoice;
                extraChoice = scanner.nextLine().trim().toLowerCase();
                boolean extra = extraChoice.equals("yes") || extraChoice.equals("y");
                iceCream.addPremiumTopping(toppingType, extra);
                System.out.printf(" %s added%s%n",
                        toppingType.getDisplay(),
                        extra ? " (Extra)" : "");
            }
        }
    }

    private void addRegularToppings(IceCream iceCream) {
        System.out.printf("%nAdd free toppings (enter 0 when done):%n");
        RegularToppingType[] toppings = RegularToppingType.getAllTypes();

        boolean addingToppings = true;
        while (addingToppings) {
            IntStream.range(0, toppings.length).forEach(i ->
                    System.out.printf("%d) %s%n", i + 1, toppings[i].getDisplay())
            );
            System.out.printf("0) Done adding toppings%n");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, toppings.length);
            if (choice == 0) {
                addingToppings = false;
            } else {
                RegularToppingType toppingType = toppings[choice - 1];
                iceCream.addRegularTopping(toppingType);
                System.out.printf("✓ %s added%n", toppingType.getDisplay());
            }
        }
    }

    private void addSauces(IceCream iceCream) {
        System.out.printf("%nAdd sauces (enter 0 when done):%n");
        Syrups[] sauces = Syrups.getAllTypes();

        boolean addingSauces = true;
        while (addingSauces) {
            IntStream.range(0, sauces.length).forEach(i ->
                    System.out.printf("%d) %s%n", i + 1, sauces[i].getDisplay())
            );
            System.out.printf("0) Done adding sauces%n");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, sauces.length);
            if (choice == 0) {
                addingSauces = false;
            } else {
                Syrups sauceType = sauces[choice - 1];
                iceCream.addSauce(sauceType);
                System.out.printf("✓ %s added%n", sauceType.getDisplay());
            }
        }
    }

    private void addShake() {
        System.out.printf("%n🥤 --- Create Your Shake --- 🥤%n");

        System.out.printf("%nSelect shake flavor:%n");
        ShakeFlavor[] flavors = ShakeFlavor.getAllFlavors();
        IntStream.range(0, flavors.length).forEach(i ->
                System.out.printf("%d) %s%n", i + 1, flavors[i].getDisplay())
        );
        System.out.printf("Choice: ");
        int Flavors = getIntInput(1, flavors.length);
        int flavorChoice;
        flavorChoice = 0;
        ShakeFlavor flavor = flavors[flavorChoice - 1];

        System.out.printf("%nSelect shake size:%n");
        Shake[] sizes = Shake.getAllSizes();
        IntStream.range(0, sizes.length).forEach(i ->
                System.out.printf("%d) %s - $%.2f%n", i + 1, sizes[i].getDisplay(), sizes[i].getPrice())
        );
        System.out.printf("Choice: ");
        int sizeChoice = getIntInput(1, sizes.length);
        ShakeSize size = sizes[sizeChoice - 1];

        Shake shake = new Shake(size, flavor);
        addShakeToppings(shake);

        currentOrder.addItem(shake);
        System.out.printf("%n✓ Shake added to order!%n");
    }

    private void addShakeToppings(Shake shake) {
        System.out.printf("%nAdd shake toppings (enter 0 when done):%n");
        ShakeTopping[] toppings = ShakeTopping.getAllToppings();

        boolean addingToppings = true;
        while (addingToppings) {
            IntStream.range(0, toppings.length).forEach(i ->
                    System.out.printf("%d) %s (+$%.2f)%n",
                            i + 1, toppings[i].getDisplay(), toppings[i].getPrice())
            );
            System.out.printf("0) Done adding toppings%n");
            System.out.printf("Choice: ");

            int choice = getIntInput(0, toppings.length);
            if (choice == 0) {
                addingToppings = false;
            } else {
                ShakeTopping topping = toppings[choice - 1];
                shake.addTopping(topping);
                System.out.printf("✓ %s added%n", topping.getDisplay());
            }
        }
    }

    private void checkout() {
        System.out.printf("%n╔════════════════════════════════╗%n");
        System.out.printf("║        ORDER SUMMARY           ║%n");
        System.out.printf("╚════════════════════════════════╝%n");

        AtomicInteger itemNumber = new AtomicInteger(1);
        currentOrder.getItems().forEach(item -> {
            System.out.printf("%nItem %d:%n", itemNumber.getAndIncrement());
            if (item instanceof IceCream) {
                System.out.printf("%s", item.getDescription());
                System.out.printf("Price: $%.2f%n", item.getPrice());
            } else {
                System.out.printf("%s%n", item.getDescription());
            }
        });

        System.out.printf("%n════════════════════════════════%n");
        System.out.printf("TOTAL: $%.2f%n", currentOrder.getTotalPrice());
        System.out.printf("════════════════════════════════%n");

        System.out.printf("%nConfirm order? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            ReceiptManager.saveReceipt(currentOrder);
            System.out.printf("%n✓ Order complete! Enjoy your ice cream! 🍦%n");
            currentOrder = null;
        } else {
            System.out.printf("Order cancelled.%n");
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