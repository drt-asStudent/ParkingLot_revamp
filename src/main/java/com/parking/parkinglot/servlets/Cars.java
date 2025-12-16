package com.parking.parkinglot.servlets;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.ejb.CarsBean;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList; // Import necesar

@WebServlet(name = "Cars", value = "/Cars")
public class Cars extends HttpServlet {
    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CarDto> cars = carsBean.findAllCars();
        request.setAttribute("cars", cars);
        
        int numberOfFreeParkingSpots = 12 - cars.size();
        request.setAttribute("numberOfFreeParkingSpots", numberOfFreeParkingSpots);

        request.getRequestDispatcher("/WEB-INF/pages/cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Preluăm valorile selectate din checkbox-uri
        String[] carIdsAsString = request.getParameterValues("car_ids");

        // 2. Dacă există selecții, le convertim și apelăm Bean-ul
        if (carIdsAsString != null) {
            List<Long> carIds = new ArrayList<>();
            for (String carIdAsString : carIdsAsString) {
                carIds.add(Long.parseLong(carIdAsString));
            }
            carsBean.deleteCarsByIds(carIds);
        }

        // 3. Redirecționăm către pagina GET pentru a vedea lista actualizată
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}