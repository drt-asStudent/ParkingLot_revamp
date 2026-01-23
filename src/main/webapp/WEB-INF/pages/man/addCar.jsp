<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add Car">
    <h1>Add Car</h1>

    <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/AddCar">

        <!-- 1. License Plate -->
        <div class="mb-3">
            <label for="license_plate" class="form-label">License Plate</label>
            <input type="text" class="form-control" id="license_plate" name="license_plate" required>
            <div class="invalid-feedback">
                License Plate is required.
            </div>
        </div>

        <!-- 2. Parking Spot -->
        <div class="mb-3">
            <label for="parking_spot" class="form-label">Parking Spot</label>
            <input type="text" class="form-control" id="parking_spot" name="parking_spot" required>
            <div class="invalid-feedback">
                Parking Spot is required.
            </div>
        </div>

        <!-- 3. Owner (Dropdown) -->
        <div class="mb-3">
            <label for="owner_id" class="form-label">Owner</label>
            <select class="form-select" id="owner_id" name="owner_id" required>
                <option value="">Choose...</option>

                    <%-- PASUL B: Aici am pus bucla care ia utilizatorii din Java și îi pune în listă --%>
                <c:forEach items="${users}" var="user">
                    <option value="${user.id}">${user.username}</option>
                </c:forEach>

            </select>
            <div class="invalid-feedback">
                Please select an owner.
            </div>
        </div>

        <!-- 4. Save Button -->
        <button type="submit" class="btn btn-primary">Save</button>

    </form>

    <script src="${pageContext.request.contextPath}/scripts/form-validation.js"></script>

</t:pageTemplate>