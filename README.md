This Java code defines a **command-line application** for a virtual ice cream shop called **Sweet Scoops** 🍦.

---

## 💻 `UserInterface.java` Summary

The `UserInterface` class is the **main component for user interaction** and order management.

* **Initialization:** It uses a `Scanner` to read user input from the console and maintains a reference to the current `Order` object.
* **Main Loop (`start`):** It displays a welcome message, then enters a loop to show the **Home Screen**, which lets the user choose between **New Order** or **Exit**.
* **Order Management:**
    * `startNewOrder` initializes a new `Order` object.
    * `showOrderScreen` allows the user to **Add Ice Cream**, **Add Shake**, **Checkout**, or **Cancel Order**.
* **Item Configuration:**
    * `addIceCream`: Prompts the user to select the **size** (number of scoops), **container** (e.g., cone or cup), **flavors** (with a max limit), **premium toppings**, **mix-ins**, **regular (free) toppings**, and **sauces**.
    * `addShake`: Prompts the user to select the **flavor** and **size**, and then add **toppings**.
* **Checkout:**
    * `validateOrder` checks if the order is empty before checkout.
    * `checkout` displays an **Order Summary** with item details and the **TOTAL price**, then asks the user to confirm. If confirmed, it calls an external `ReceiptManager.saveReceipt` method.
* **Utility:** `getIntInput` is a helper method to ensure the user provides valid integer input within a specified range.

The class relies on several external classes (like `Order`, `IceCream`, `Shake`, and various `*Type` classes like `FlavorType`, `IceCreamSize`, `ShakeSize`, etc.) to handle the business logic and data structure of the items, though these classes are not included in the provided code.

---

## 🚀 `IceCreamApp.java` Summary

The `IceCreamApp` class serves as the **entry point** for the application. Its `main` method simply creates an instance of the `UserInterface` and calls its `start()` method to begin the application's execution.
