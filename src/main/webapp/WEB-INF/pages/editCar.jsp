<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Edit Car">
    <h1>Edit Car</h1>

    <!-- FORMULARUL TRIMITE DATELE LA /EditCar -->
    <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/EditCar">

        <!-- 1. License Plate -->
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="license_plate">License Plate</label>
                <!-- Observă atributul value="${car.licensePlate}" care completează căsuța -->
                <input type="text" class="form-control" id="license_plate" name="license_plate"
                       placeholder="" value="${car.licensePlate}" required>
                <div class="invalid-feedback">
                    License Plate is required.
                </div>
            </div>
        </div>

        <!-- 2. Parking Spot -->
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="parking_spot">Parking Spot</label>
                <!-- Observă atributul value="${car.parkingSpot}" -->
                <input type="text" class="form-control" id="parking_spot" name="parking_spot"
                       placeholder="" value="${car.parkingSpot}" required>
                <div class="invalid-feedback">
                    Parking Spot is required.
                </div>
            </div>
        </div>

        <!-- 3. Owner Dropdown -->
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="owner_id">Owner</label>
                <select class="custom-select d-block w-100" id="owner_id" name="owner_id" required>
                    <option value="">Choose...</option>
                    <c:forEach var="user" items="${users}">
                        <!-- Logica de selecție: Dacă numele proprietarului mașinii este egal cu userul din listă, îl selectăm -->
                        <option value="${user.id}" ${car.ownerName eq user.username ? 'selected' : ''}>${user.username}</option>
                    </c:forEach>
                </select>
                <div class="invalid-feedback">
                    Please select an owner.
                </div>
            </div>
        </div>

        <hr class="mb-4">

        <!-- INPUT ASCUNS PENTRU ID (CRITIC PENTRU UPDATE) -->
        <input type="hidden" name="car_id" value="${car.id}" />

        <button class="btn btn-primary btn-lg btn-block" type="submit">Save</button>
    </form>

    <!-- Script pentru validarea formularului (Bootstrap standard) -->
    <script>
        (function () {
            'use strict'
            window.addEventListener('load', function () {
                var forms = document.getElementsByClassName('needs-validation')
                Array.prototype.filter.call(forms, function (form) {
                    form.addEventListener('submit', function (event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault()
                            event.stopPropagation()
                        }
                        form.classList.add('was-validated')
                    }, false)
                })
            }, false)
        }())
    </script>
</t:pageTemplate>