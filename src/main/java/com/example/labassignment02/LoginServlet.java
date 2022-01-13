package com.example.labassignment02;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    /**
     * Saves the provided username and password to the userbase file
     */
    private void saveToUserbase(String username, String password) {
        try {
            File userbase = new File(getServletContext().getResource("/users.xml").getFile());
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(userbase);

            Element user = document.createElement("user");
            user.setAttribute("username", username);
            user.setAttribute("password", password);
            document.getDocumentElement().appendChild(user);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(userbase);
            Source input = new DOMSource(document);
            transformer.transform(input, output);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves userbase file
     */
    private NodeList retrieveUserbase() {
        try {
            File userbase = new File(getServletContext().getResource("/users.xml").getFile());
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(userbase);
            return document.getElementsByTagName("user");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if the user exists in the userbase file
     */
    private boolean userExists(String username) {
        NodeList userbase = retrieveUserbase();
        if (userbase == null)   return false;

        for (int i = 0; i<userbase.getLength(); i++) {
            Node node = userbase.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element user = (Element) node;
                if (username.equals(user.getAttribute("username")))
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks if the provided username and password match those in the userbase file
     */
    private boolean verifyCredentials(String username, String password) {
        NodeList userbase = retrieveUserbase();
        if (userbase == null)   return false;

        for (int i = 0; i<userbase.getLength(); i++) {
            Node node = userbase.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element user = (Element) node;
                if (username.equals(user.getAttribute("username")) && password.equals(user.getAttribute("password")))
                    return true;
            }
        }
        return false;
    }

    /**
     * Creates a new user account
     */
    private void createUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String password = request.getParameter("password");

        if (userExists(user)) {
            System.out.println("user exists");
            request.setAttribute("errorMessage", "User already exists.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        else {
            saveToUserbase(user, password);
            grantSession(request, response);
        }
    }

    /**
     * Logs user into existing account
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String user = request.getParameter("username");
        String password = request.getParameter("password");

        if (!userExists(user) || !verifyCredentials(user, password)) {
            request.setAttribute("errorMessage", "Invalid Credentials");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        else    grantSession(request, response);
    }

    /**
     * Starts new session with required attributes
     */
    private void grantSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("username");
        String cartFile = getServletContext().getRealPath("/") + user + "_cart";
        request.getSession().setAttribute("user", request.getParameter("username"));
        request.getSession().setAttribute("cart", CartBean.getCart(cartFile));
        response.sendRedirect(request.getContextPath() + "/homepage.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * Processes all login requests
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginRequest = request.getParameter("login");
        if (loginRequest == null)  createUser(request, response);
        else    login(request, response);
    }
}
