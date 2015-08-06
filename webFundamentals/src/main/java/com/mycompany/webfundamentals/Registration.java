/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webfundamentals;

import com.mycompany.dataaccess.dao.Dao;
import java.io.IOException;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author izielinski
 */
@WebServlet(name = "registration", urlPatterns = {"/registration"})
@Named
public class Registration extends HttpServlet {

    private Dao dao;
    
    @Inject
    public Registration(Dao dao) {
        this.dao = dao;
    }

    public Registration() {
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        Objects.requireNonNull(name);
        Objects.requireNonNull(surname);
        dao.createUserAccount(name, surname);
        req.setAttribute("login", name);
        RequestDispatcher dispatcher = getServletContext().getNamedDispatcher("Home");
        dispatcher.forward(req, resp);
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}
