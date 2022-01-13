<%@ page import="com.example.labassignment02.CartBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Homepage</title>
</head>
<body>
<h1>Homepage</h1>
<form action="CartServlet" method="get">
    <%-- printing each item in inventory --%>
    <%
        CartBean cartBean = (CartBean) session.getAttribute("cart");
        %>
        Items in cart: <%=cartBean.getTotalItemCount()%>
        <br>
        <%
        for(int i = 0; i<cartBean.getTotalItemTypes(); i++) {
            %>
            <button type="submit" value="+" name="<%=i%>">Add to Cart</button> Item <%=i%>
            <br>
            <%
        }
    %>
</form>
<form style="display: inline-block" action="LogoutServlet" method="post">
    <input type="submit" value="Logout" name="logout"/>
</form>
<form style="display: inline-block" action="cart.jsp" method="post">
    <input type="submit" value="View Cart" name="viewCart"/>
</form>
</body>
</html>
