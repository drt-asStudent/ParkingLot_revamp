<%--
  Created by IntelliJ IDEA.
  User: dan_t
  Date: Sat, 13-Dec-25
  Time: 7:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Login">
<c:if test="${message != null}">
    <div class="alert alert-warning" role="alert">
        ${message}
    </div>
</c:if>

<form class="form-signin" method="post" action="j_security_check" >
    <h2 class="h3 mb-3 font-weight-normal">Please sign in</h2>
    <label for="username" class="sr-only">Username</label>
    <input type="text" id="username" name="j_username" class="form-control" placeholder="Username" required autofocus />
    <label for="password" class="sr-only">Password</label>
    <input type="password" id="password" name="j_password" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
</form>
</t:pageTemplate>