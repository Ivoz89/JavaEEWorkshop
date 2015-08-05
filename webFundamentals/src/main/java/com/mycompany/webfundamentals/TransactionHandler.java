/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webfundamentals;

import com.google.gson.Gson;
import com.mycompany.dataaccess.Dao;
import com.mycompany.model.Account;
import com.mycompany.model.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author izielinski
 */
@WebServlet(name = "trans", urlPatterns = {"/trans"})
@Named
public class TransactionHandler extends HttpServlet {

    private final Dao dao;
    private StringBuilder sb = new StringBuilder();

    @Inject
    public TransactionHandler(Dao dao) {
        this.dao = dao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new IOException("CZEGO TU SZUKASZ???");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        int amount = Integer.parseInt(req.getParameter("amount"));
        Account account = dao.getAccountByLogin(login);
        if (account != null) {
            Transaction trans = new Transaction(new Date(), BigDecimal.valueOf(amount),1);
            account.addTransaction(new Transaction(new Date(), BigDecimal.valueOf(amount),1));
            dao.makeTransaction(login, trans);
           
            Gson gson = new Gson();
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            ArrayList<Object> list = new ArrayList<Object>();
            list.add(trans);
            list.add(account.getAccountData().getBalance());
            
            out.print(gson.toJson(list));
            out.flush();
        } else {
            throw new ServletException("No Such username!");
        }
    }

}
