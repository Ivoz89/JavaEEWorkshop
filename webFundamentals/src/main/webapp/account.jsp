<%-- 
    Document   : account
    Created on : Aug 4, 2015, 9:45:44 AM
    Author     : izielinski
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Account</title>
        <script type="text/javascript" src="js/jquery-2.1.4.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#trBut').click(function () {
                    var amount = $('#amFld').val();
                    var name = $('#hidlog').val()
                    $.ajax({
                        type: 'POST',
                        data: {login: name, amount: amount},
                        url: 'trans',
                        success: function (result) {
                            console.log(result);
                            var trHTML = '<p>Date: ' + result[0].date + ' Amount: ' + result[0].amount + '</p>';
                            $('#transactionTable').append(trHTML);
                            $('#balance').html(result[1]);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <p>Welcome ${userData.name} ${userData.surname}</p>
        <p>Account no. ${accountData.accountNumber}</p>
        <p >Balance: <span id="balance">${accountData.balance}</span></p>
        <p>Transactions:</p>
        <div id="transactionTable">
            <c:forEach items="${transactions}" var="t">
                <p>Date: <fmt:formatDate type="both" 
                                value="${t.date}" /> Amount: ${t.amount}</p>
                </c:forEach>
        </div>
        <p>
            Amount: <input id="amFld" type="number" name="amount"/>
            <input id="hidlog" type="hidden" name="login" value="${userData.name}">
        </p>
        <p>
            <input id="trBut" type="button" name="Execute" value="EXECUTE"/>
        </p>
    </body>
</html>
