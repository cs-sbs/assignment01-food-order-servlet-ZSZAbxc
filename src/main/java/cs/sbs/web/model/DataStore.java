package cs.sbs.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * In-memory data store shared across servlets.
 * Thread-safe for concurrent requests during testing.
 */
public class DataStore {

    private static final List<MenuItem> MENU = new ArrayList<>();
    private static final List<Order> ORDERS = new CopyOnWriteArrayList<>();
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(1001);

    static {
        MENU.add(new MenuItem("Fried Rice", 8));
        MENU.add(new MenuItem("Fried Noodles", 9));
        MENU.add(new MenuItem("Burger", 10));
    }

    public static List<MenuItem> getMenu() {
        return MENU;
    }

    public static List<Order> getOrders() {
        return ORDERS;
    }

    public static int nextId() {
        return ID_COUNTER.getAndIncrement();
    }

    public static Order findOrderById(int id) {
        for (Order order : ORDERS) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public static void addOrder(Order order) {
        ORDERS.add(order);
    }
}
