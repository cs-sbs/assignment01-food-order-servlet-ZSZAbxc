package cs.sbs.web.servlet;

import cs.sbs.web.model.DataStore;
import cs.sbs.web.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class OrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");

        // Extract order ID from path: /order/{id}
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print("Error: missing order ID");
            return;
        }

        // Remove leading "/"
        String idStr = pathInfo.startsWith("/") ? pathInfo.substring(1) : pathInfo;

        int orderId;
        try {
            orderId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print("Error: invalid order ID");
            return;
        }

        Order order = DataStore.findOrderById(orderId);
        if (order == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().print("Error: Order not found");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Order Detail\n");
        sb.append("\n");
        sb.append("Order ID: ").append(order.getId()).append("\n");
        sb.append("Customer: ").append(order.getCustomer()).append("\n");
        sb.append("Food: ").append(order.getFood()).append("\n");
        sb.append("Quantity: ").append(order.getQuantity()).append("\n");

        resp.getWriter().print(sb.toString());
    }
}
