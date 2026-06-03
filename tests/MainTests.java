import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainTests {
    private final CaptureStdOut captureStdOut = new CaptureStdOut();
    private final InputStream originalIn = System.in;

    @BeforeEach
    void beforeEach() {
        captureStdOut.redirectToBuffer();
    }

    @AfterEach
    void afterEach() {
        captureStdOut.redirectToStdOut();
        System.setIn(originalIn);
        ShoppingCart.clear();
        Items.clear();
    }

    static void runMain(String input) {
        if (input == null) {
            System.setIn(null);
        } else {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
        }
        Main.main();
    }

    @Test
    void nullScannerTest() {
        runMain(null);
        captureStdOut.compare(new String[] {"java.lang.NullPointerException: null"});
    }

    @Test
    void getNameTest1() {
        runMain("\r\n \na\n1\n1\n1\n2\n1\n6\n");
        captureStdOut.containsInOrder(new String[] {
                "Name cannot be blank", "Name cannot be blank", "Hello, a",
                "b is added to the shopping cart", "The current count of items in the cart: 1",
                "Transaction completed"
        });
    }

    @Test
    void getNameTest2() {
        runMain(
                """
                aa
                1 1
                1 2 1
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, aa", "b is added to the shopping cart", "The current count of items in the cart: 1",
                "Transaction completed"
        });
    }

    @Test
    void getStateTest1() {
        runMain("a\na\n");
        captureStdOut.containsInOrder(new String[] {"Hello, a", "java.util.InputMismatchException: null"});
    }

    @Test
    void getStateTest2() {
        runMain("a\n0\n1\n1\n1\n2\n1\n6\n");
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Invalid option", "b is added to the shopping cart",
                "The current count of items in the cart: 1", "Transaction completed"
        });
    }

    @Test
    void getStateTest3() {
        runMain("a\n3\n2\n1\n1\n2\n1\n6\n");
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Invalid option", "b is added to the shopping cart",
                "The current count of items in the cart: 1", "Transaction completed"
        });
    }

    @Test
    void getShippingTest1() {
        runMain("a\n1\na\n");
        captureStdOut.containsInOrder(new String[] {"Hello, a", "java.util.InputMismatchException: null"});
    }

    @Test
    void getShippingTest2() {
        runMain("a\n1\n0\n1\n1\n2\n1\n6\n");
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Invalid option", "b is added to the shopping cart",
                "The current count of items in the cart: 1", "Transaction completed"
        });
    }

    @Test
    void getShippingTest3() {
        runMain("a\n1\n3\n2\n1\n2\n1\n6\n");
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Invalid option", "b is added to the shopping cart",
                "The current count of items in the cart: 1", "Transaction completed"
        });
    }

    @Test
    void invalidOptionTests() {
        runMain(
                """
                a
                1 1
                0 7 a
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Invalid option", "Invalid option", "java.util.InputMismatchException: null"
        });
    }

    @Test
    void invalidItemIdTest1() {
        runMain(
                """
                a
                1 1
                1 a
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "java.util.InputMismatchException: null"
        });
    }

    @Test
    void invalidItemIdTest2() {
        runMain(
                """
                a
                1 1
                4 a
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "java.util.InputMismatchException: null"
        });
    }

    @Test
    void invalidItemIdTest3() {
        runMain(
                """
                a
                1 1
                5 a
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "java.util.InputMismatchException: null"
        });
    }

    @Test
    void invalidQuantityTest1() {
        runMain(
                """
                a
                1 1
                1 1 a
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "java.util.InputMismatchException: null"
        });
    }

    @Test
    void invalidQuantityTest2() {
        runMain(
                """
                a
                1 1
                4 1 a
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "java.util.InputMismatchException: null"
        });
    }

    @Test
    void invalidAddToCartTests() {
        runMain(
                """
                a
                1 1
                3
                1 0 2 0 1
                3
                1 2 1
                3
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "Invalid item ID", "Invalid quantity",
                "b is added to the shopping cart", "The current count of items in the cart: 1",
                "ID: 2 | Item: b | Quantity: 1", "Shopping cart already has item with item ID 2",
                "ID: 2 | Item: b | Quantity: 1", "Transaction completed"
        });
    }

    @Test
    void addToCartTest4() {
        runMain(
                """
                a
                1 1
                3
                1 0 1 1
                3
                1 2 1
                3
                1 3 1
                3
                1 5 4 1
                3
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "Invalid item ID", "a is added to the shopping cart",
                "The current count of items in the cart: 1", "ID: 1 | Item: a | Quantity: 1",
                "b is added to the shopping cart", "The current count of items in the cart: 2",
                "ID: 1 | Item: a | Quantity: 1", "ID: 2 | Item: b | Quantity: 1",
                "c is added to the shopping cart", "The current count of items in the cart: 3",
                "ID: 1 | Item: a | Quantity: 1", "ID: 2 | Item: b | Quantity: 1",
                "ID: 3 | Item: c | Quantity: 1", "Invalid item ID", "d is added to the shopping cart",
                "The current count of items in the cart: 4", "ID: 1 | Item: a | Quantity: 1",
                "ID: 2 | Item: b | Quantity: 1", "ID: 3 | Item: c | Quantity: 1",
                "ID: 4 | Item: d | Quantity: 1", "Transaction completed"
        });
    }

    @Test
    void addToCartQuantityTest() {
        runMain(
                """
                a
                1 1
                3
                1 2 2
                3
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "b is added to the shopping cart",
                "The current count of items in the cart: 2", "ID: 2 | Item: b | Quantity: 2",
                "Transaction completed"
        });
    }

    @Test
    void addToCartOverflowTests() {
        runMain(
                """
                a
                1 1
                3
                1 1 2147483646
                3
                1 2 1
                3
                1 3 1
                3
                4 1 1
                3
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "a is added to the shopping cart",
                "The current count of items in the cart: 2147483646",
                "ID: 1 | Item: a | Quantity: 2147483646", "b is added to the shopping cart",
                "The current count of items in the cart: 2147483647",
                "ID: 1 | Item: a | Quantity: 2147483646", "ID: 2 | Item: b | Quantity: 1",
                "Item count will overflow with specified quantity", "ID: 1 | Item: a | Quantity: 2147483646",
                "ID: 2 | Item: b | Quantity: 1", "Changed quantity of a",
                "The current count of items in the cart: 2", "ID: 1 | Item: a | Quantity: 1",
                "ID: 2 | Item: b | Quantity: 1", "Transaction completed"
        });
    }

    @Test
    void printCurrentTotalTest1() {
        runMain(
                """
                a
                1 1
                2
                1 3 1
                2
                1 1 1
                2
                4 1 2
                2
                4 1 3
                2
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "c is added to the shopping cart",
                "The current count of items in the cart: 1",
                "The total for the items in the shopping cart: $62.98", "a is added to the shopping cart",
                "The current count of items in the cart: 2",
                "The total for the items in the shopping cart: $62.99", "Changed quantity of a",
                "The current count of items in the cart: 3",
                "The total for the items in the shopping cart: $53.00", "Changed quantity of a",
                "The current count of items in the cart: 4",
                "The total for the items in the shopping cart: $53.01", "Transaction completed"
        });
    }

    @Test
    void printCurrentTotalTest2() {
        runMain(
                """
                a
                1 2
                2
                1 3 1
                2
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "c is added to the shopping cart",
                "The current count of items in the cart: 1",
                "The total for the items in the shopping cart: $77.98", "Transaction completed"
        });
    }

    @Test
    void printCurrentTotalTest3() {
        runMain(
                """
                a
                2 1
                2
                1 3 1
                2
                1 1 1
                2
                4 1 2
                2
                4 1 3
                2
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "c is added to the shopping cart",
                "The current count of items in the cart: 1",
                "The total for the items in the shopping cart: $59.98", "a is added to the shopping cart",
                "The current count of items in the cart: 2",
                "The total for the items in the shopping cart: $59.99", "Changed quantity of a",
                "The current count of items in the cart: 3",
                "The total for the items in the shopping cart: $50.00", "Changed quantity of a",
                "The current count of items in the cart: 4",
                "The total for the items in the shopping cart: $50.01", "Transaction completed"
        });
    }

    @Test
    void printCurrentTotalTest4() {
        runMain(
                """
                a
                2 2
                2
                1 3 1
                2
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "c is added to the shopping cart",
                "The current count of items in the cart: 1",
                "The total for the items in the shopping cart: $74.98", "Transaction completed"
        });
    }

    @Test
    void invalidEditQuantityTests() {
        runMain(
                """
                a
                1 1
                3
                4 2 1
                3
                1 2 1
                3
                4 0 2 0 1
                3
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "Shopping cart does not have item with item ID 2",
                "Cart is empty", "b is added to the shopping cart",
                "The current count of items in the cart: 1", "ID: 2 | Item: b | Quantity: 1",
                "Invalid item ID", "Invalid quantity", "Quantity did not change",
                "ID: 2 | Item: b | Quantity: 1", "Transaction completed"
        });
    }

    @Test
    void editQuantityTest4() {
        runMain(
                """
                a
                1 1
                3
                1 1 1
                1 2 1
                1 3 1
                1 4 1
                3
                4 0 1 1
                3
                4 1 2
                3
                4 1 2
                3
                4 1 1
                3
                4 1 1
                3
                4 2 1
                3
                4 2 2
                3
                4 2 2
                3
                4 2 1
                3
                4 2 1
                3
                4 3 1
                3
                4 3 2
                3
                4 3 2
                3
                4 3 1
                3
                4 3 1
                3
                4 4 1
                3
                4 4 2
                3
                4 4 2
                3
                4 4 1
                3
                4 5 4 1
                3
                6
                """
        );
        captureStdOut.containsInOrder(
                """
                Hello, a
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
                Invalid item ID
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
                Invalid item ID
                Quantity did not change
                ID: 1 | Item: a | Quantity: 1
                ID: 2 | Item: b | Quantity: 1
                ID: 3 | Item: c | Quantity: 1
                ID: 4 | Item: d | Quantity: 1
                Transaction completed
                """
        );
    }

    @Test
    void editQuantityOverflowTests() {
        runMain(
                """
                a
                1 1
                3
                1 1 2147483646
                3
                1 2 1
                3
                4 2 2
                3
                4 1 1
                3
                6
                """
        );
        captureStdOut.containsInOrder(new String[] {
                "Hello, a", "Cart is empty", "a is added to the shopping cart",
                "The current count of items in the cart: 2147483646",
                "ID: 1 | Item: a | Quantity: 2147483646", "b is added to the shopping cart",
                "The current count of items in the cart: 2147483647",
                "ID: 1 | Item: a | Quantity: 2147483646", "ID: 2 | Item: b | Quantity: 1",
                "Item count will overflow with specified quantity", "ID: 1 | Item: a | Quantity: 2147483646",
                "ID: 2 | Item: b | Quantity: 1", "Changed quantity of a",
                "The current count of items in the cart: 2", "ID: 1 | Item: a | Quantity: 1",
                "ID: 2 | Item: b | Quantity: 1", "Transaction completed"
        });
    }

    @Test
    void removeItemTest4() {
        runMain(
                """
                a
                1 1
                3
                1 1 1
                1 2 1
                1 3 1
                1 4 1
                3
                5 0 1
                3
                5 2
                3
                5 3
                3
                5 5 4
                3
                1 2 1
                3
                6
                """
        );
        captureStdOut.containsInOrder(
                """
                Hello, a
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
                Invalid item ID
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
                Invalid item ID
                Removed item with item ID 4
                The current count of items in the cart: 0
                Cart is empty
                b is added to the shopping cart
                The current count of items in the cart: 1
                ID: 2 | Item: b | Quantity: 1
                Transaction completed
                """
        );
    }

    @Test
    void removeItemQuantityTest() {
        runMain(
                """
                a
                1 1
                3
                1 1 2
                3
                5 1
                3
                1 2 1
                3
                6
                """
        );
        captureStdOut.containsInOrder(
                """
                Hello, a
                Cart is empty
                a is added to the shopping cart
                The current count of items in the cart: 2
                ID: 1 | Item: a | Quantity: 2
                Removed item with item ID 1
                The current count of items in the cart: 0
                Cart is empty
                b is added to the shopping cart
                The current count of items in the cart: 1
                ID: 2 | Item: b | Quantity: 1
                Transaction completed
                """
        );
    }

    @Test
    void checkoutTest1() {
        runMain(
                """
                a
                1 1
                1 4 1
                1 3 1
                1 2 1
                6
                """
        );
        captureStdOut.containsInOrder(
                """
                Hello, a
                d is added to the shopping cart
                The current count of items in the cart: 1
                c is added to the shopping cart
                The current count of items in the cart: 2
                b is added to the shopping cart
                The current count of items in the cart: 3
                Transaction completed
                """
        );
    }

    @Test
    void checkoutTest2() {
        runMain(
                """
                a
                1 1
                1 4 1
                1 3 1
                1 2 1
                1 1 1
                6
                """
        );
        captureStdOut.containsInOrder(
                """
                Hello, a
                d is added to the shopping cart
                The current count of items in the cart: 1
                c is added to the shopping cart
                The current count of items in the cart: 2
                b is added to the shopping cart
                The current count of items in the cart: 3
                a is added to the shopping cart
                The current count of items in the cart: 4
                Transaction completed
                """
        );
    }

    @Test
    void checkoutTest3() {
        runMain(
                """
                a
                1 1
                1 4 1
                1 3 1
                1 2 1
                1 1 2
                6
                4 1 1
                6
                """
        );
        captureStdOut.containsInOrder(
                """
                Hello, a
                d is added to the shopping cart
                The current count of items in the cart: 1
                c is added to the shopping cart
                The current count of items in the cart: 2
                b is added to the shopping cart
                The current count of items in the cart: 3
                a is added to the shopping cart
                The current count of items in the cart: 5
                The maximum acceptable purchase amount is $99,999.99
                Changed quantity of a
                The current count of items in the cart: 4
                Transaction completed
                """
        );
    }

    @Test
    void checkoutTest4() {
        runMain(
                """
                a
                1 1
                1 4 1
                1 3 1
                1 2 1
                1 1 3
                6
                4 1 1
                6
                """
        );
        captureStdOut.containsInOrder(
                """
                Hello, a
                d is added to the shopping cart
                The current count of items in the cart: 1
                c is added to the shopping cart
                The current count of items in the cart: 2
                b is added to the shopping cart
                The current count of items in the cart: 3
                a is added to the shopping cart
                The current count of items in the cart: 6
                The maximum acceptable purchase amount is $99,999.99
                Changed quantity of a
                The current count of items in the cart: 4
                Transaction completed
                """
        );
    }
}