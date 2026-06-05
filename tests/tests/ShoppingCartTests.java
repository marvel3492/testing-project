package tests;

import application.Items;
import application.ShoppingCart;
import org.junit.jupiter.api.*;
import testtools.CaptureStdOut;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTests {
    private final CaptureStdOut captureStdOut = new CaptureStdOut();

    @AfterAll
    static void afterAll() {
        Items.clear();
    }

    @BeforeEach
    void beforeEach() {
        captureStdOut.redirectToBuffer();
    }

    @AfterEach
    void afterEach() {
        captureStdOut.redirectToStdOut();
        ShoppingCart.clear();
        Items.clear();
    }

    @Test
    void instantiatedTest() {
        assertThrows(IllegalStateException.class, ShoppingCart::new);
    }

    @Test
    void invalidAddToCartTests() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(0, 1);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 0);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 1);
        Assertions.assertEquals(0.01, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 1);
        Assertions.assertEquals(0.01, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(new String[] {
                "Cart is empty", "Item with item ID 0 does not exist", "Cart is empty",
                "Quantity is less than 1", "Cart is empty", "a is added to the shopping cart",
                "The current count of items in the cart: 1", "ID: 1 | Item: a | Quantity: 1",
                "Shopping cart already has item with item ID 1", "ID: 1 | Item: a | Quantity: 1"
        });
    }

    @Test
    void addToCartOverflowTests() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        assertDoesNotThrow(()-> Items.addItem("b", 0.02));
        assertDoesNotThrow(()-> Items.addItem("c", 0.03));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, Integer.MAX_VALUE - 1);
        Assertions.assertEquals((Integer.MAX_VALUE - 1) * 0.01, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(2, 1);
        Assertions.assertEquals((Integer.MAX_VALUE - 1) * 0.01 + 0.02, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(3, 1);
        Assertions.assertEquals((Integer.MAX_VALUE - 1) * 0.01 + 0.02, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(new String[] {
                "Cart is empty", "a is added to the shopping cart",
                "The current count of items in the cart: 2147483646",
                "ID: 1 | Item: a | Quantity: 2147483646", "b is added to the shopping cart",
                "The current count of items in the cart: 2147483647",
                "ID: 1 | Item: a | Quantity: 2147483646", "ID: 2 | Item: b | Quantity: 1",
                "Item count will overflow with specified quantity", "ID: 1 | Item: a | Quantity: 2147483646",
                "ID: 2 | Item: b | Quantity: 1"
        });
    }

    @Test
    void printCurrentTotalTest1() {
        assertDoesNotThrow(()-> Items.addItem("a", 49.98));
        assertDoesNotThrow(()-> Items.addItem("b", 0.01));
        ShoppingCart.printCurrentTotal(true, true);
        ShoppingCart.addToCart(1, 1);
        ShoppingCart.printCurrentTotal(true, true);
        ShoppingCart.addToCart(2, 1);
        ShoppingCart.printCurrentTotal(true, true);
        captureStdOut.compare(new String[] {
                "Cart is empty", "a is added to the shopping cart",
                "The current count of items in the cart: 1",
                "The total for the items in the shopping cart: $62.98", "b is added to the shopping cart",
                "The current count of items in the cart: 2",
                "The total for the items in the shopping cart: $62.99"
        });
    }

    @Test
    void printCurrentTotalTest2() {
        assertDoesNotThrow(()-> Items.addItem("a", 50.0));
        assertDoesNotThrow(()-> Items.addItem("b", 0.01));
        ShoppingCart.addToCart(1, 1);
        ShoppingCart.printCurrentTotal(false, true);
        ShoppingCart.addToCart(2, 1);
        ShoppingCart.printCurrentTotal(false, true);
        captureStdOut.compare(new String[] {
                "a is added to the shopping cart", "The current count of items in the cart: 1",
                "The total for the items in the shopping cart: $50.00", "b is added to the shopping cart",
                "The current count of items in the cart: 2",
                "The total for the items in the shopping cart: $50.01"
        });
    }

    @Test
    void printCurrentTotalTest3() {
        assertDoesNotThrow(()-> Items.addItem("a", 100.0));
        ShoppingCart.addToCart(1, 1);
        ShoppingCart.printCurrentTotal(false, false);
        captureStdOut.compare(new String[] {
                "a is added to the shopping cart", "The current count of items in the cart: 1",
                "The total for the items in the shopping cart: $125.00"
        });
    }

    @Test
    void invalidEditQuantityTests() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(1, 1);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 1);
        Assertions.assertEquals(0.01, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(0, 1);
        Assertions.assertEquals(0.01, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(1, 0);
        Assertions.assertEquals(0.01, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(
                """
                Cart is empty
                Shopping cart does not have item with item ID 1
                Cart is empty
                a is added to the shopping cart
                The current count of items in the cart: 1
                ID: 1 | Item: a | Quantity: 1
                Shopping cart does not have item with item ID 0
                ID: 1 | Item: a | Quantity: 1
                Quantity is less than 1
                ID: 1 | Item: a | Quantity: 1
                """
        );
    }

    @Test
    void editQuantityTest() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        assertDoesNotThrow(()-> Items.addItem("b", 0.02));
        assertDoesNotThrow(()-> Items.addItem("c", 0.03));
        assertDoesNotThrow(()-> Items.addItem("d", 0.04));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 1);
        ShoppingCart.addToCart(2, 1);
        ShoppingCart.addToCart(3, 1);
        ShoppingCart.addToCart(4, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(0, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(1, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(1, 2);
        Assertions.assertEquals(0.11, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(1, 2);
        Assertions.assertEquals(0.11, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(1, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(1, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(2, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(2, 2);
        Assertions.assertEquals(0.12, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(2, 2);
        Assertions.assertEquals(0.12, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(2, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(2, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(3, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(3, 2);
        Assertions.assertEquals(0.13, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(3, 2);
        Assertions.assertEquals(0.13, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(3, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(3, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(4, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(4, 2);
        Assertions.assertEquals(0.14, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(4, 2);
        Assertions.assertEquals(0.14, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(4, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(4, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(5, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(
                """
                Cart is empty
                a is added to the shopping cart
                The current count of items in the cart: 1
                b is added to the shopping cart
                The current count of items in the cart: 2
                c is added to the shopping cart
                The current count of items in the cart: 3
                d is added to the shopping cart
                The current count of items in the cart: 4
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Shopping cart does not have item with item ID 0
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Changed quantity of a
                The current count of items in the cart: 5
                ID: 1 | Item: a | Quantity: 2
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 2
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Changed quantity of a
                The current count of items in the cart: 4
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Changed quantity of b
                The current count of items in the cart: 5
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 2
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 2
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Changed quantity of b
                The current count of items in the cart: 4
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Changed quantity of c
                The current count of items in the cart: 5
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 2
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 2
                ID: 4 | Item: d | Quantity: 1
                Changed quantity of c
                The current count of items in the cart: 4
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Changed quantity of d
                The current count of items in the cart: 5
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 2
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 2
                Changed quantity of d
                The current count of items in the cart: 4
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Shopping cart does not have item with item ID 5
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                """
        );
    }

    @Test
    void editQuantityOverflowTests() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        assertDoesNotThrow(()-> Items.addItem("b", 0.02));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, Integer.MAX_VALUE - 1);
        Assertions.assertEquals((Integer.MAX_VALUE - 1) * 0.01, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(2, 1);
        Assertions.assertEquals((Integer.MAX_VALUE - 1) * 0.01 + 0.02, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.editQuantity(2, 2);
        Assertions.assertEquals((Integer.MAX_VALUE - 1) * 0.01 + 0.02, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(new String[] {
                "Cart is empty", "a is added to the shopping cart",
                "The current count of items in the cart: 2147483646",
                "ID: 1 | Item: a | Quantity: 2147483646", "b is added to the shopping cart",
                "The current count of items in the cart: 2147483647",
                "ID: 1 | Item: a | Quantity: 2147483646", "ID: 2 | Item: b | Quantity: 1",
                "Item count will overflow with specified quantity", "ID: 1 | Item: a | Quantity: 2147483646",
                "ID: 2 | Item: b | Quantity: 1"
        });
    }

    @Test
    void removeItemTest0() {
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(0);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(1);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(
                """
                Cart is empty
                Shopping cart does not have item with item ID 0
                Cart is empty
                Shopping cart does not have item with item ID 1
                Cart is empty
                """
        );
    }

    @Test
    void removeItemTest1() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 1);
        Assertions.assertEquals(0.01, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(0);
        Assertions.assertEquals(0.01, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(1);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(2);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(
                """
                Cart is empty
                a is added to the shopping cart
                The current count of items in the cart: 1
                ID: 1 | Item: a | Quantity: 1
                Shopping cart does not have item with item ID 0
                ID: 1 | Item: a | Quantity: 1
                Removed item with item ID 1
                The current count of items in the cart: 0
                Cart is empty
                Shopping cart does not have item with item ID 2
                Cart is empty
                """
        );
    }

    @Test
    void removeItemTest2() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        assertDoesNotThrow(()-> Items.addItem("b", 0.02));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 1);
        ShoppingCart.addToCart(2, 1);
        Assertions.assertEquals(0.03, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(0);
        Assertions.assertEquals(0.03, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(1);
        Assertions.assertEquals(0.02, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(2);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(3);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(
                """
                Cart is empty
                a is added to the shopping cart
                The current count of items in the cart: 1
                b is added to the shopping cart
                The current count of items in the cart: 2
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                Shopping cart does not have item with item ID 0
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                Removed item with item ID 1
                The current count of items in the cart: 1
                ID: 2 | Item: b | Quantity: 1
                Removed item with item ID 2
                The current count of items in the cart: 0
                Cart is empty
                Shopping cart does not have item with item ID 3
                Cart is empty
                """
        );
    }

    @Test
    void removeItemTest3() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        assertDoesNotThrow(()-> Items.addItem("b", 0.02));
        assertDoesNotThrow(()-> Items.addItem("c", 0.03));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 1);
        ShoppingCart.addToCart(2, 1);
        ShoppingCart.addToCart(3, 1);
        Assertions.assertEquals(0.06, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(0);
        Assertions.assertEquals(0.06, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(1);
        Assertions.assertEquals(0.05, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(2);
        Assertions.assertEquals(0.03, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(3);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(4);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(
                """
                Cart is empty
                a is added to the shopping cart
                The current count of items in the cart: 1
                b is added to the shopping cart
                The current count of items in the cart: 2
                c is added to the shopping cart
                The current count of items in the cart: 3
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                Shopping cart does not have item with item ID 0
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                Removed item with item ID 1
                The current count of items in the cart: 2
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                Removed item with item ID 2
                The current count of items in the cart: 1
                ID: 3 | Item: c | Quantity: 1
                Removed item with item ID 3
                The current count of items in the cart: 0
                Cart is empty
                Shopping cart does not have item with item ID 4
                Cart is empty
                """
        );
    }

    @Test
    void removeItemTest4() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        assertDoesNotThrow(()-> Items.addItem("b", 0.02));
        assertDoesNotThrow(()-> Items.addItem("c", 0.03));
        assertDoesNotThrow(()-> Items.addItem("d", 0.04));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 1);
        ShoppingCart.addToCart(2, 1);
        ShoppingCart.addToCart(3, 1);
        ShoppingCart.addToCart(4, 1);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(0);
        Assertions.assertEquals(0.10, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(1);
        Assertions.assertEquals(0.09, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(2);
        Assertions.assertEquals(0.07, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(3);
        Assertions.assertEquals(0.04, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(4);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(5);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(
                """
                Cart is empty
                a is added to the shopping cart
                The current count of items in the cart: 1
                b is added to the shopping cart
                The current count of items in the cart: 2
                c is added to the shopping cart
                The current count of items in the cart: 3
                d is added to the shopping cart
                The current count of items in the cart: 4
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Shopping cart does not have item with item ID 0
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Removed item with item ID 1
                The current count of items in the cart: 3
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Removed item with item ID 2
                The current count of items in the cart: 2
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Removed item with item ID 3
                The current count of items in the cart: 1
                ID: 4 | Item: d | Quantity: 1
                Removed item with item ID 4
                The current count of items in the cart: 0
                Cart is empty
                Shopping cart does not have item with item ID 5
                Cart is empty
                """
        );
    }

    @Test
    void removeItemQuantityTest() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.01));
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.addToCart(1, 2);
        Assertions.assertEquals(0.02, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        ShoppingCart.removeItem(1);
        Assertions.assertEquals(0.0, ShoppingCart.getRawTotal());
        ShoppingCart.printContents();
        captureStdOut.compare(
                """
                Cart is empty
                a is added to the shopping cart
                The current count of items in the cart: 2
                ID: 1 | Item: a | Quantity: 2
                Removed item with item ID 1
                The current count of items in the cart: 0
                Cart is empty
                """
        );
    }

    @Test
    void checkoutTests() {
        assertDoesNotThrow(()-> Items.addItem("a", 0.98));
        assertDoesNotThrow(()-> Items.addItem("b", 0.01));
        assertDoesNotThrow(()-> Items.addItem("c", 99998.97));
        ShoppingCart.addToCart(1, 1);
        Assertions.assertFalse(ShoppingCart.checkout());
        ShoppingCart.addToCart(2, 1);
        Assertions.assertFalse(ShoppingCart.checkout());
        ShoppingCart.editQuantity(2, 2);
        Assertions.assertTrue(ShoppingCart.checkout());
        ShoppingCart.editQuantity(2, 3);
        Assertions.assertTrue(ShoppingCart.checkout());
        ShoppingCart.addToCart(3, 1);
        Assertions.assertTrue(ShoppingCart.checkout());
        ShoppingCart.editQuantity(2, 4);
        Assertions.assertTrue(ShoppingCart.checkout());
        ShoppingCart.editQuantity(2, 5);
        Assertions.assertFalse(ShoppingCart.checkout());
        ShoppingCart.editQuantity(2, 6);
        Assertions.assertFalse(ShoppingCart.checkout());
        captureStdOut.compare(
                """
                a is added to the shopping cart
                The current count of items in the cart: 1
                The smallest acceptable purchase amount is $1
                b is added to the shopping cart
                The current count of items in the cart: 2
                The smallest acceptable purchase amount is $1
                Changed quantity of b
                The current count of items in the cart: 3
                Transaction completed
                Changed quantity of b
                The current count of items in the cart: 4
                Transaction completed
                c is added to the shopping cart
                The current count of items in the cart: 5
                Transaction completed
                Changed quantity of b
                The current count of items in the cart: 6
                Transaction completed
                Changed quantity of b
                The current count of items in the cart: 7
                The maximum acceptable purchase amount is $99,999.99
                Changed quantity of b
                The current count of items in the cart: 8
                The maximum acceptable purchase amount is $99,999.99
                """
        );
    }
}