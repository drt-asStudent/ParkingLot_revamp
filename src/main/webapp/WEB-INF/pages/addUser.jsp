<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%-- PASUL A: Aici am adăugat linia pentru JSTL ca să putem folosi forEach --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add User">
  <h1>Add User</h1>

  <form autocomplete="off" class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/AddUser">

    <!-- 1.Username-->
    <div class="mb-3">
      <label for="username" class="form-label">Nume utilizator</label>
      <input type="text" class="form-control" id="username" name="username" required autocomplete="off">
      <div class="invalid-feedback">
        Username is required.
      </div>
    </div>

  <!-- 2. Email -->
  <div class="mb-3">
    <label for="email" class="form-label">e-Mail</label>
    <input type="email" class="form-control" id="email" name="email" required autocomplete="off">
    <div class="invalid-feedback">
      Email is required.
    </div>
  </div>

    <!-- 3. Password -->
    <div class="mb-3">
      <label for="password" class="form-label">Parola</label>
      <input type="password" class="form-control" id="password" name="password" required autocomplete="new-password">
      <div class="invalid-feedback">
        Password is required.
      </div>
    </div>

        <div class="mb-3">
          <label for="user_group" class="form-label">Groups</label>
          <select class="custom-select d-block w-100" id="user_group" name="user_group" multiple>

            <c:forEach items="${userGroup}" var="userGroup" varStatus="status">
              <option value="${userGroup}">${userGroup}</option>
            </c:forEach>

          </select>
          <div class="invalid-feedback">
            Please select a user group.
          </div>
        </div>

    <!-- 4. Save Button -->
    <button type="submit" class="btn btn-primary">Save</button>

  </form>

  <script src="${pageContext.request.contextPath}/scripts/form-validation.js"></script>



</t:pageTemplate>