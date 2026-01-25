<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>

    <!-- Formularul începe aici -->
    <form method="POST" action="${pageContext.request.contextPath}/Cars">

        <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
            <!-- Buton ADD CAR -->
            <a href="${pageContext.request.contextPath}/AddCar"
               class="btn btn-primary btn-lg mb-3">
                Add Car
            </a>

            <!-- Buton DELETE CARS -->
            <button class="btn btn-danger mb-3" type="submit">Delete Cars</button>
        </c:if>

        <div class="container text-center">
            <c:forEach var="car" items="${cars}">
                <div class="row mb-2 align-items-center">

                    <!-- Coloana Checkbox -->
                    <div class="col">
                        <input type="checkbox" name="car_ids" value="${car.id}" />
                    </div>

                    <!-- Datele mașinii -->
                    <div class="col">
                            ${car.licensePlate}
                    </div>
                    <div class="col">
                            ${car.parkingSpot}
                    </div>
                    <div class="col">
                            ${car.ownerName}
                    </div>


                    <div class="col">
                        <img src="${pageContext.request.contextPath}/CarPhotos?id=${car.id}" width="48"/>
                    </div>

                <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                    <div class="col">
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/AddCarPhoto?id=${car.id}" role="button">Add photo</a>
                    </div>
                    <!-- Buton Edit -->
                    <div class="col">
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditCar?id=${car.id}">Edit Car</a>
                    </div>
                </c:if>

                </div>
            </c:forEach>
        </div>

    </form>

    <h5 class="mt-3">Free parking spots: ${numberOfFreeParkingSpots}</h5>
</t:pageTemplate>
