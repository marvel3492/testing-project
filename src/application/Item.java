package application;

public record Item(String name, double price) {
    public Item {
        Items.validateName(name);
        Items.validatePrice(price);
    }
}