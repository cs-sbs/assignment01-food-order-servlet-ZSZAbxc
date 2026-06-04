package cs.sbs.web.servlet;

import cs.sbs.web.model.DataStore;
import cs.sbs.web.model.MenuItem;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MenuListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");

        String nameFilter = req.getParameter("name");

        List<MenuItem> menu = DataStore.getMenu();

        if (nameFilter != null && !nameFilter.isBlank()) {
            String lowerFilter = nameFilter.toLowerCase();
            menu = menu.stream()
                       .filter(item -> item.getName().toLowerCase().contains(lowerFilter))
                       .collect(Collectors.toList());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Menu List:\n");

        if (menu.isEmpty()) {
            sb.append("\nNo menu items found.");
        } else {
            sb.append("\n");
            int index = 1;
            for (MenuItem item : menu) {
                sb.append(index).append(". ").append(item.getName())
                  .append(" - $").append(item.getPrice()).append("\n");
                index++;
            }
        }

        resp.getWriter().print(sb.toString());
    }
}
