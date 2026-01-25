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
                <div class="col">
                    <c:forEach var="group" items="${user.userGroups}" varStatus="status">
                        <c:if test="${!status.first}">, </c:if>${group}
                    </c:forEach>
                </div>
            </div>

        </c:forEach>
    </div>
    <hr>
    <div>
    <c:if test="${not empty invoices}">
        <h2>Invoices</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Groups</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="invoiceUser" items="${invoices}" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${invoiceUser.username}</td>
                        <td>${invoiceUser.email}</td>
                        <td>
                            <c:forEach var="group" items="${invoiceUser.userGroups}" varStatus="status">
                                <c:if test="${!status.first}">, </c:if>${group}
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    </div>
</t:pageTemplate>
</form>
