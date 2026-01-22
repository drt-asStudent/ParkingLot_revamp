<%--
  Created by IntelliJ IDEA.
  User: dan_t
  Date: Wed, 21-Jan-26
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add Car Photo">
    <h1>Add Car Photo</h1>
    <form class="needs-validation" novalidate method="post" enctype="multipart/form-data"
          action="${pageContext.request.contextPath}/AddCarPhoto">

        <div class="row">
            <div class="col-md-6 mb-3">
                License plate: ${car.licensePlate}
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="file">Photo</label>
                <input type="file" name="file" id="file" required>
                    <div class="invalid-feedback">
                        Please select a photo.
                    </div>
            </div>
        </div>

        <input type="hidden" name="car_id" value="${car.id}"/>
        <hr class="mb-4">
        <button class="btn btn-primary btn-lg btn-block" type="submit">Save</button>
    </form>

</t:pageTemplate>