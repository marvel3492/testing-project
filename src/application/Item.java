package application;

// PIT Tests will not test records; ignore warning
public class Item {
    private final String name;
    private final double price;

    public Item(String name, double price) {
        Items.validateName(name);
        Items.validatePrice(price);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}