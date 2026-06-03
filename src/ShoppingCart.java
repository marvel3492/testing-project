import java.util.TreeMap;

public class ShoppingCart {
    private static TreeMap<Integer, Integer> cart = new TreeMap<>();
    private static int count = 0;

    private ShoppingCart() { }

    public static boolean willOverflow(int quantity) { // To prevent overflow
        return count + quantity < 0;
    }

    public static void addToCart(int itemId, int quantity) {
        if (cart.containsKey(itemId)) {
            System.out.println("Shopping cart already has item with item ID " + itemId);
        } else if (quantity < 1) {
            System.out.println("Quantity is less than 1");
        } else if (Items.getItem(itemId) == null) {
            System.out.println("Item with item ID " + itemId + " does not exist");
        } else if (willOverflow(quantity)) {
            System.out.println("Item count will overflow with specified quantity");
        } else {
            cart.put(itemId, quantity);
            count += quantity;
            System.out.println(Items.getItem(itemId).name() + " is added to the shopping cart");
            System.out.println("The current count of items in the cart: " + getCount());
        }
    }

    public static double getRawTotal() {
        double total = 0.0;
        for (Integer itemId : cart.keySet()) {
            total += Items.getItem(itemId).price() * cart.get(itemId);
        }

        return Math.round(total * 100.0) / 100.0;
    }

    public static void printCurrentTotal(boolean salesTax, boolean standardShipping) {
        if (count == 0) {
            System.out.println("Cart is empty");
        } else {
            double rawTotal = getRawTotal();
            double total = rawTotal;
            if (salesTax) {
                total *= 1.06;
            }
            if (standardShipping && rawTotal < 50.0) {
                total += 10.0;
            } else if (!standardShipping) {
                total += 25.0;
            }

            total = Math.round(total * 100.0) / 100.0;
            String strTotal = (total * 10.0) % 1.0 == 0.0 ? total + "0" : total + "";
            System.out.println("The total for the items in the shopping cart: $" + strTotal);
        }
    }

    public static void printContents() {
        if (count == 0) {
            System.out.println("Cart is empty");
        } else {
            for (Integer itemId : cart.keySet()) {
                System.out.println("ID: " + itemId + " | Item: " + Items.getItem(itemId).name() +
                        " | Quantity: " + cart.get(itemId));
            }
        }
    }

    public static void editQuantity(int itemId, int quantity) {
        if (!cart.containsKey(itemId)) {
            System.out.println("Shopping cart does not have item with item ID " + itemId);
        } else if (quantity < 1) {
            System.out.println("Quantity is less than 1");
        } else if (willOverflow(quantity - cart.get(itemId))) {
            System.out.println("Item count will overflow with specified quantity");
        } else if (quantity == cart.get(itemId)) {
            System.out.println("Quantity did not change");
        } else {
            int dif = quantity - cart.get(itemId);
            cart.put(itemId, quantity);
            count += dif;
            System.out.println("Changed quantity of " + Items.getItem(itemId).name());
            System.out.println("The current count of items in the cart: " + getCount());
        }
    }

    public static void removeItem(int itemId) {
        if (!cart.containsKey(itemId)) {
            System.out.println("Shopping cart does not have item with item ID " + itemId);
        } else {
            int quantity = cart.get(itemId);
            cart.remove(itemId);
            count -= quantity;
            System.out.println("Removed item with item ID " + itemId);
            System.out.println("The current count of items in the cart: " + getCount());
        }
    }

    public static boolean checkout() {
        if (getRawTotal() < 1.0) {
            System.out.println("The smallest acceptable purchase amount is $1");
            return false;
        } else if (getRawTotal() > 99999.99) {
            System.out.println("The maximum acceptable purchase amount is $99,999.99");
            return false;
        } else {
            System.out.println("Transaction completed");
            return true;
        }
    }

    public static int getCount() {
        return count;
    }

    public static void clear() {
        cart = new TreeMap<>();
        count = 0;
    }
}