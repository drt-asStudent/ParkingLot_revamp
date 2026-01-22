package com.parking.parkinglot.servlets.users;

import com.parking.parkinglot.ejb.UsersBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@DeclareRoles({"READ_USERS", "WRITE_USERS"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_USERS"}))
@WebServlet(name = "AddUser", value = "/AddUser")
public class AddUser extends HttpServlet {
    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("userGroup", new String[] {"READ_CARS", "WRITE_CARS", "READ_USERS", "WRITE_USERS"});
        request.getRequestDispatcher("/WEB-INF/pages/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String[] userGroup = request.getParameterValues("user_group");
        if (userGroup == null) {
            userGroup = new String[0];
        }

        try {
            usersBean.createUser(username, email, password, Arrays.asList(userGroup));
            response.sendRedirect(request.getContextPath() + "/Users");
        } catch (Exception e) {
            // This will help you see the error in the console if it fails
            e.printStackTrace();
            throw new ServletException("Error creating user", e);
        }
    }
}