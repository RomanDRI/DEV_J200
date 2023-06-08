package com.example.clientdataserver.servlet;

import com.example.clientdataserver.entities.AddressEntity;
import com.example.clientdataserver.entities.ClientEntity;
import com.example.clientdataserver.repository.RepositoryLocal;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;



@WebServlet(name = "DeleteServlet", value = "/DeleteServlet")
public class DeleteServlet extends HttpServlet {


    @EJB
    private RepositoryLocal repositoryLocal;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/ViewListServlet").forward(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String idAddress = request.getParameter("id_address");

            if (id == null && idAddress!=null) {
                    repositoryLocal.remove(repositoryLocal.findById(AddressEntity.class, Integer.parseInt(idAddress)));
            }
            if (idAddress == null && id!=null) {
                    repositoryLocal.remove(repositoryLocal.findById(ClientEntity.class, Integer.parseInt(id)));
                }
        getServletContext().getRequestDispatcher("/ViewListServlet").forward(request, response);
    }

    public void destroy() {
    }
}