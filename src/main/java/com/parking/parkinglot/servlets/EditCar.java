package com.parking.parkinglot.servlets;

import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.CarsBean;
import com.parking.parkinglot.ejb.UsersBean;

import java.io.IOException;
import java.util.List;
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}))
@WebServlet(name = "EditCar", value = "/EditCar")
public class EditCar extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Luăm lista de utilizatori pentru a popula dropdown-ul "Owner"
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);

        // 2. Luăm ID-ul mașinii din URL (când dai click pe Edit, linkul este EditCar?id=...)
        Long carId = Long.parseLong(request.getParameter("id"));

        // 3. Căutăm datele mașinii folosind metoda findById din CarsBean
        CarDto car = carsBean.findById(carId);

        // 4. Punem mașina pe request ca să o putem afișa în JSP
        request.setAttribute("car", car);

        // 5. Deschidem pagina editCar.jsp
        request.getRequestDispatcher("/WEB-INF/pages/editCar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Preluăm datele din formular
        String licensePlate = request.getParameter("license_plate");
        String parkingSpot = request.getParameter("parking_spot");
        Long userId = Long.parseLong(request.getParameter("owner_id"));

        // Aici folosim ID-ul ascuns (hidden input) din formular
        Long carId = Long.parseLong(request.getParameter("car_id"));

        // 2. Apelăm EJB-ul pentru a face update în baza de date
        carsBean.updateCar(carId, licensePlate, parkingSpot, userId);

        // 3. Redirecționăm la pagina principală
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}