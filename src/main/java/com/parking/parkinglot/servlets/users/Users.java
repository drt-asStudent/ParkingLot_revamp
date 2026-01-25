package com.parking.parkinglot.servlets.users;

import com.parking.parkinglot.ejb.InvoiceBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.UsersBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@DeclareRoles({"READ_USERS", "WRITE_USERS"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"READ_USERS", "WRITE_USERS"}))
@WebServlet(name = "Users", value = "/Users")
public class Users extends HttpServlet {

    // 1. Injectăm EJB-ul pentru a putea accesa baza de date
    @Inject
    UsersBean usersBean;

    @Inject
    InvoiceBean invoiceBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        // 2. Apelăm metoda din EJB care returnează lista de DTO-uri
        List<UserDto> users = usersBean.findAllUsers();

        // 3. Trimitem lista către JSP
        request.setAttribute("users", users);

        // 4. Setăm pagina activă pentru ca meniul să o evidențieze (vezi logica din menu.jsp)
        request.setAttribute("activePage", "Users");

        //4,5
        if(!invoiceBean.getUserIds().isEmpty()){
            List<UserDto> invoiceUsers = usersBean.findUsersByUserIds(invoiceBean.getUserIds());
            request.setAttribute("invoices", invoiceUsers);
        }

        // 5. Facem forward către pagina de afișare
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String[] userIdsAsString = request.getParameterValues("user_ids");
        if (userIdsAsString!=null){
            List<Long> userIds = new ArrayList<>();
            for(String userIdAsString : userIdsAsString){
                userIds.add(Long.parseLong(userIdAsString));
            }
            invoiceBean.getUserIds().addAll(userIds);
        }
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}
