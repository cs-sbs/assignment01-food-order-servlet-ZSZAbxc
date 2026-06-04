package cs.sbs.web.servlet;

import cs.sbs.web.model.DataStore;
import cs.sbs.web.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class OrderCreateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");

        String customer = req.getParameter("customer");
        String food = req.getParameter("food");
        String quantityStr = req.getParameter("quantity");

        // Validate required parameters
        if (customer == null || customer.isBlank()) {
            resp.getWriter().print("Error: missing customer name");
            return;
        }
        if (food == null || food.isBlank()) {
            resp.getWriter().print("Error: missing food name");
            return;
        }
        if (quantityStr == null || quantityStr.isBlank()) {
            resp.getWriter().print("Error: missing quantity");
            return;
        }

        // Validate quantity is a valid number
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                resp.getWriter().print("Error: quantity must be a valid number");
                return;
            }
        } catch (NumberFormatException e) {
            resp.getWriter().print("Error: quantity must be a valid number");
            return;
        }

        int id = DataStore.nextId();
        Order order = new Order(id, customer, food, quantity);
        DataStore.addOrder(order);

        resp.getWriter().print("Order Created: " + id);
    }
}
