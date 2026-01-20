package com.parking.parkinglot.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.CarsBean; // AM ADĂUGAT ACEST IMPORT
import com.parking.parkinglot.ejb.UsersBean;

@WebServlet(name = "AddCar", value = "/AddCar")
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}))
public class AddCar extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Inject
    CarsBean carsBean; // 1. Injectăm CarsBean

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request, response);
    }

    // 2. Metoda doPost care salvează mașina
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Preluăm parametrii din formular (numele trebuie să fie exact ca în JSP: name="...")
        String licensePlate = request.getParameter("license_plate");
        String parkingSpot = request.getParameter("parking_spot");

        // Convertim ID-ul userului din String în Long
        Long userId = Long.parseLong(request.getParameter("owner_id"));

        // Apelăm metoda creată în CarsBean
        carsBean.createCar(licensePlate, parkingSpot, userId);

        // Redirecționăm către pagina principală (Cars)
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}