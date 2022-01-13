package com.example.labassignment02;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends HttpServlet {

    /**
     * Processes all checkout requests and redirects user to homepage
     */
    public void checkout(CartBean cart, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.resetCart();   // empty cart
        String redirectURL = request.getContextPath() + "/homepage.jsp";

        response.getWriter().println(
                "<html>\n" +
                "  <body>\n" +
                "    Processing payment...\n" +
                "    <script type=\"application/javascript\">\n" +
                "      setTimeout(function() {\n" +
                "        window.location.href = \"" + redirectURL + "\";\n" +
                "      }, 3000); \n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>"
        );
    }

    /**
     * Processes additions/removals in cart and redirects to same page
     */
    public void updateCart(CartBean cart, HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (int itemNumber = 0; itemNumber < cart.getTotalItemTypes(); itemNumber++) {
            int itemCount = cart.getItemCount(itemNumber);
            String change = request.getParameter(String.valueOf(itemNumber));
            if (change == null) continue;   // this is not the item that was changed
            else if (change.equals("+")) cart.incrementItemCount(itemNumber);
            else if (change.equals("-") && itemCount != 0)   cart.decrementItemCount(itemNumber);
            break;
        }
        response.sendRedirect(request.getHeader("referer"));
    }

    /**
     * starts method for cart updates and checkouts
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartBean cartBean = (CartBean) request.getSession().getAttribute("cart");
        if (request.getParameter("checkout") != null)   checkout(cartBean, request, response);  // checkout button clicked
        else    updateCart(cartBean, request, response);    // + or - buttons clicked
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
