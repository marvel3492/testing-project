package application;

import java.util.Scanner;

public class Choices {
    // https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalStateException.html
    public Choices() throws IllegalStateException {
        throw new IllegalStateException("Choices cannot be instantiated");
    }

    public static void validateScanner(Scanner scanner) throws IllegalArgumentException {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
    }

    public static String getName(Scanner scanner) throws IllegalArgumentException {
        validateScanner(scanner);
        String name = null;
        while (name == null || name.isBlank()) {
            System.out.println("Enter name: ");
            name = scanner.nextLine();
            if (name.isBlank()) {
                System.out.println("Name cannot be blank");
            }
        }
        return name;
    }

    public static int getState(Scanner scanner) throws IllegalArgumentException {
        validateScanner(scanner);
        int state = 0;
        while (state != 1 && state != 2) {
            System.out.println("Enter state: ");
            System.out.println("1. CA/IL/NY");
            System.out.println("2. Other");
            System.out.println("Option: ");
            state = scanner.nextInt();
            if (state != 1 && state != 2) {
                System.out.println("Invalid option");
            }
        }
        return state;
    }

    public static int getShipping(Scanner scanner) throws IllegalArgumentException {
        validateScanner(scanner);
        int shipping = 0;
        while (shipping != 1 && shipping != 2) {
            System.out.println("Enter shipping ");
            System.out.println("1. Standard");
            System.out.println("2. Next Day");
            System.out.println("Option: ");
            shipping = scanner.nextInt();
            if (shipping != 1 && shipping != 2) {
                System.out.println("Invalid option");
            }
        }
        return shipping;
    }

    public static int getOption(Scanner scanner) throws IllegalArgumentException {
        validateScanner(scanner);
        int option = 0;
        while (option < 1 || option > 6) {
            System.out.println("1. Add item to the shopping cart");
            System.out.println("2. Get current total");
            System.out.println("3. See contents of shopping cart");
            System.out.println("4. Edit quantity of items in shopping cart");
            System.out.println("5. Remove items from shopping cart");
            System.out.println("6. Checkout");
            System.out.println("Option: ");
            option = scanner.nextInt();
            if (option < 1 || option > 6) {
                System.out.println("Invalid option");
            }
        }
        return option;
    }

    public static int getItemId(Scanner scanner) throws IllegalArgumentException {
        validateScanner(scanner);
        if (Items.getMaxId() == 0) {
            System.out.println("No items available");
            return 0;
        } else {
            int itemId = 0;
            while (itemId < 1 || itemId > Items.getMaxId()) {
                System.out.println("Item ID (1 - " + Items.getMaxId() + "): ");
                itemId = scanner.nextInt();
                if (itemId < 1 || itemId > Items.getMaxId()) {
                    System.out.println("Invalid item ID");
                }
            }
            return itemId;
        }
    }

    public static int getQuantity(Scanner scanner) throws IllegalArgumentException {
        validateScanner(scanner);
        int quantity = 0;
        while (quantity < 1) {
            System.out.println("Quantity: ");
            quantity = scanner.nextInt();
            if (quantity < 1) {
                System.out.println("Invalid quantity");
            }
        }
        return quantity;
    }
}