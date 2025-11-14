package com.pluralsight.data;
import com.pluralsight.model.Order;
import com.pluralsight.model.Scoops;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiptManager {
    private static String RECEIPTS_FOLDER;

    static {
        RECEIPTS_FOLDER = "receipts";
    }

    public ReceiptManager() {
        File folder = new File(RECEIPTS_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static void saveReceipt(Order order) {
        File folder = new File(RECEIPTS_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String filename = String.format("%s/%s.txt", RECEIPTS_FOLDER, now.format(formatter));

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("╔════════════════════════════════╗\n");
            writer.write("║    Sweet Scoops Ice Cream      ║\n");
            writer.write("║         Order Receipt          ║\n");
            writer.write("╚════════════════════════════════╝\n");
            writer.write(String.format("Date: %s\n\n",
                    now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

            writer.write("Order Items:\n");
            writer.write("------------\n");

            AtomicInteger itemNumber = new AtomicInteger(1);
            order.getItems().forEach(item -> {
                try {
                    writer.write(String.format("%d. ", itemNumber.getAndIncrement()));
                    if (item instanceof Scoops) {
                        writer.write(item.getDescription());
                        writer.write(String.format("   Price: $%.2f\n", item.getPrice()));
                    } else {
                        writer.write(String.format("%s\n", item.getDescription()));
                    }
                    writer.write("\n");
                } catch (IOException e) {
                    System.err.printf("Error writing item: %s\n", e.getMessage());
                }
            });

            writer.write("════════════════════════════════\n");
            writer.write(String.format("Total: $%.2f\n", order.getTotalPrice()));
            writer.write("════════════════════════════════\n");
            writer.write("\nThank you for visiting Sweet Scoops!\n");
            writer.write("Have a sweet day! 🍦\n");

            System.out.printf("Receipt saved to: %s\n", filename);
        } catch (IOException e) {
            System.err.printf("Error saving receipt: %s\n", e.getMessage());
        }
    }
}
