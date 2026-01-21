<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>

    <!-- Formularul începe aici -->
    <form method="POST" action="${pageContext.request.contextPath}/Users">
    <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
        <!-- Buton ADD USER -->
        <a href="${pageContext.request.contextPath}/AddUser"
           class="btn btn-primary btn-lg mb-3">
            Add User
        </a>
        <button type="submit" class="btn btn-secondary btn-lg mb-3">
            Invoice
        </button>
    </c:if>

    <div class="container text-center">
            <%-- Iterăm prin lista de useri --%>
        <c:forEach var="user" items="${users}">

            <div class="row">
                <div class="col">
                    <input type="checkbox" name="user_ids" value="${user.id}">
                </div>
                <div class="col">
                        ${user.username}
                </div>
                <div class="col">
                        ${user.email}
                </div>
            </div>

        </c:forEach>
    </div>
</t:pageTemplate>
</form>
<c:if test="${not empty invoices}">
    <h2>Invoices</h2>
    <c:forEach var="username" items="${invoices}" varStatus="status">
        ${status.index+1}.${username}
        <br>
    </c:forEach>
</c:if>