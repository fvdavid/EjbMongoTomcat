<%-- 
    Document   : persons
    Created on : Nov 2, 2015, 11:39:43 PM
    Author     : saddamtbg
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Persons Manage Page</title>
        <style type="text/css">
            table,th,td {
                border: 1px solid black;
            }
        </style>
    </head>
    <body>
        <%-- Person Add/Edit logic --%>
        <c:if test="${requestScope.error ne null}">
            <strong style="color: red;">
                <c:out value="${requestScope.error}"></c:out>
            </strong>
        </c:if>
            
        <c:if test="${requestScope.success ne null}">
            <strong style="color: green;">
                <c:out value="${requestScope.success}"></c:out>
            </strong>
        </c:if>
            
        <c:url value="/addCustomer" var="addURL"></c:url>
        <c:url value="/editCustomer" var="editURL"></c:url>

        <%-- Edit Request --%>
        <c:if test="${requestScope.customer ne null}">
            <form action='<c:out value="${editURL}"></c:out>' method="post">

                    <p>
                        ID:
                        <input type="text" value="${requestScope.customer.id}" readonly="readonly" name="id">
                    </p>
                
                    <p>
                        Name:
                        <input type="text" value="${requestScope.customer.name}" name="name">
                    </p>
                
                    <p>
                        Address:
                        <input type="text" value="${requestScope.customer.address}" name="address">
                    </p>
                    
                    <p>
                        Kota:
                        <input type="text" value="${requestScope.customer.kota}" name="kota">
                    </p>

                <p>
                    <input type="submit" value="Edit Customer">
                </p>

            </form>
        </c:if>

        <%-- Add Request --%>
        <c:if test="${requestScope.customer eq null}">
            <form action='<c:out value="${addURL}"></c:out>' method="post">
                    <p>
                        Name: <input type="text" name="name"><br>
                    </p>
                    <p>
                        Address: <input type="text" name="address"><br> 
                    </p>
                    <p>
                        Kota <input type="text" name="kota"><br> 
                    </p>

                    <p>
                        <input type="submit" value="Add Customer">
                    </p>

                </form>
        </c:if>

        <%-- Persons List Logic --%>
        <c:if test="${not empty requestScope.customers}">
            <table>
                <tbody>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Kota</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    
                    <c:forEach items="${requestScope.customers}" var="customer">
                        
                        <c:url value="/editCustomer" var="editURL">
                            <c:param name="id" value="${customer.id}"></c:param>
                        </c:url>
                        
                        <c:url value="/deleteCustomer" var="deleteURL">
                            <c:param name="id" value="${customer.id}"></c:param>
                        </c:url>
                        
                        <tr>
                            <td><c:out value="${customer.id}"></c:out></td>
                            <td><c:out value="${customer.name}"></c:out></td>
                            <td><c:out value="${customer.address}"></c:out></td>
                            <td><c:out value="${customer.kota}"></c:out></td>
                            
                            <td>
                                <a href='<c:out value="${editURL}" escapeXml="true"></c:out>'>Edit</a>
                            </td>
                            <td>
                                <a href='<c:out value="${deleteURL}" escapeXml="true"></c:out>'>Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>