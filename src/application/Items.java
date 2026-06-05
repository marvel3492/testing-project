package application;

import java.util.TreeMap;

public class Items {
    public static final int MAX_ITEMS = 10;
    private static TreeMap<Integer, Item> items = new TreeMap<>();
    private static int maxId = 0;

    // https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalStateException.html
    public Items() throws IllegalStateException {
        throw new IllegalStateException("Items cannot be instantiated");
    }

    public static void validateName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        } else if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    public static void validatePrice(double price) throws IllegalArgumentException {
        if (price < 0.0) {
            throw new IllegalArgumentException("Price cannot be negative");
        } else if (price > 99999.99) { // To prevent overflow
            throw new IllegalArgumentException("Price cannot be greater than $99,999.99");
        }
    }

    public static void addItem(String name, double price) throws IllegalArgumentException, IllegalStateException {
        validateName(name);
        validatePrice(price);

        // Check for duplicates
        for (Item item : items.values()) { // Simple for efficiency, but not optimal
            if (item.name().trim().equalsIgnoreCase(name.trim())) {
                throw new IllegalArgumentException("Item already exists");
            }
        }

        // https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalStateException.html
        if (MAX_ITEMS == maxId) { // To prevent overflow
            throw new IllegalStateException("Maximum items reached");
        } else {
            items.put(++maxId, new Item(name.trim(), price));
        }
    }

    public static Item getItem(int itemId) {
        return items.get(itemId);
    }

    public static int getMaxId() {
        return maxId;
    }

    public static void clear() {
        items = new TreeMap<>();
        maxId = 0;
    }
}