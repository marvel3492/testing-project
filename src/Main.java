import java.util.Scanner;

public class Main {
    public static void main() {
        try (Scanner scanner = new Scanner(System.in)) {
            // Add items here
            Items.addItem("a", 0.01);
            Items.addItem("b", 1.0);
            Items.addItem("c", 49.98);
            Items.addItem("d", 99949.0);
            assert Items.getMaxId() > 0;

            System.out.println("Hello, " + Choices.getName(scanner));
            System.out.println();

            boolean salesTax = Choices.getState(scanner) == 1;
            System.out.println();

            boolean standardShipping = Choices.getShipping(scanner) == 1;
            System.out.println();

            while (true) {
                int option = Choices.getOption(scanner);
                if (option == 1) {
                    ShoppingCart.addToCart(Choices.getItemId(scanner), Choices.getQuantity(scanner));
                } else if (option == 2) {
                    ShoppingCart.printCurrentTotal(salesTax, standardShipping);
                } else if (option == 3) {
                    ShoppingCart.printContents();
                } else if (option == 4) {
                    ShoppingCart.editQuantity(Choices.getItemId(scanner), Choices.getQuantity(scanner));
                } else if (option == 5) {
                    ShoppingCart.removeItem(Choices.getItemId(scanner));
                } else if (ShoppingCart.checkout()) {
                    break;
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}