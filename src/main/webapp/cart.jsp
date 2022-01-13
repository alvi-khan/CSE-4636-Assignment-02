<%@ page import="com.example.labassignment02.CartBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<h1>Cart</h1>
<form action="CartServlet" method="get">
    <%-- printing each item added to cart --%>
    <%
        CartBean cartBean = (CartBean) session.getAttribute("cart");
        for(int i = 0; i<cartBean.getTotalItemTypes(); i++) {
            int items = cartBean.getItemCount(i);
            if (items == 0) continue;
            %>
            <input type="submit" value="-" name="<%=i%>"/>
            <%=cartBean.getItemCount(i)%>
            <input type="submit" value="+" name="<%=i%>"/>Item <%=i%>
            <br>
            <%
        }
    %>
</form>
<form style="display: inline-block" action="LogoutServlet" method="post">
    <input type="submit" value="Logout" name="logout"/>
</form>
<form style="display: inline-block" action="homepage.jsp" method="post">
    <input type="submit" value="Homepage" name="homepage"/>
</form>
<form style="display: inline-block" action="CartServlet" method="get">
    <input type="submit" value="Checkout" name="checkout"/>
</form>
</body>
</html>
