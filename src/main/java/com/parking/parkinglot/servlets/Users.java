package com.parking.parkinglot.servlets;

import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.UsersBean;

import java.io.IOException;
import java.util.List;
//@DeclareRoles({"READ_USERS", "WRITE_USERS"})
//@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"READ_USERS"}))
@WebServlet(name = "Users", value = "/Users")
public class Users extends HttpServlet {

    // 1. Injectăm EJB-ul pentru a putea accesa baza de date
    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        // 2. Apelăm metoda din EJB care returnează lista de DTO-uri
        List<UserDto> users = usersBean.findAllUsers();

        // 3. Trimitem lista către JSP
        request.setAttribute("users", users);

        // 4. Setăm pagina activă pentru ca meniul să o evidențieze (vezi logica din menu.jsp)
        request.setAttribute("activePage", "Users");

        // 5. Facem forward către pagina de afișare
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // Nu este necesar cod aici pentru simpla afișare a userilor
    }
}