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
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet(name = "CreateServlet", value = "/CreateServlet")
public class CreateServlet extends HttpServlet {
    private String message;
    private String messageAddress;

    public void init() {
        message = "Данные клиента";
        messageAddress = "Данные адреса клиента";
    }


    @EJB
    private RepositoryLocal repositoryLocal;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        String createClient = request.getParameter("Create_client");

        PrintWriter out = response.getWriter();
        if(createClient!=null){
            out.println("<html><body>");
            out.println("<h1>" + message + "</h1>");
            out.println("<form method=\"post\" action=\"CreateServlet\"> ");
            //out.println("ID: <input type=\"text\" name=\"id\" pattern=\"^[ 0-9]+$\"/><br>");
            out.println("Наименование клиента: <input type=\"text\" name=\"Client_name\" " +
                    "title=\"Допускаются только русские буквы\"\n" +
                    "pattern=\"^[А-Яа-яЁё\\s\\d,.-]+$\"\n" +
                    "required/><br>");
            out.println("Тип клиента:\n" +
                    "  <select name=\"Type\">\n" +
                    "    <option>Юридическое лицо</option>\n" +
                    "    <option>Физическое лицо</option>\n" +
                    "  </select><br>");
            out.println("Дата добавления клиента:\n" +
                    "  <input type=\"date\" name=\"Added\"/>\n" +
                    "  <br>");
            out.println("<button>Добавить клиента</button>");
            out.println("</form>");
        } else {
            out.println("<h1>" + messageAddress + "</h1>");
            out.println("<form method=\"post\" action=\"CreateServlet\"> ");
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\">");
            out.println("IP адрес устройства: <input type=\"text\" name=\"Device_IP\" maxlength=\"25\"\n" +
                    "         title=\"Введите правильно IP адрес\"\n" +
                    "         pattern=\"\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\"\n" +
                    "         required/>\n" +
                    "  <br>");

            out.println("Mac адрес устройства:\n" +
                    "  <input type=\"text\" name=\"Mac\" maxlength=\"20\"\n" +
                    "         title=\"Введите правильно MAC адрес\"\n" +
                    "         pattern=\"^([0-9A-Fa-f]{2}[-]){5}([0-9A-Fa-f]{2})$\"\n" +
                    "         required/>\n" +
                    "  <br>");
            out.println("Модель устройства:\n" +
                    "  <input type=\"text\" name=\"Device_model\" maxlength=\"100\" required/>\n" +
                    "  <br>");
            out.println("Адрес места нахождения:\n" +
                    "  <input type=\"text\" name=\"Address\" maxlength=\"200\" required/>\n" +
                    "  <br>");
            out.println("<button>Добавить адрес клиента</button>");
            out.println("</form>");
            out.println("</body></html>");
       }
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String clientName = request.getParameter("Client_name");
        String clientType = request.getParameter("Type");
        String added = request.getParameter("Added");
        String ip = request.getParameter("Device_IP");
        String mac = request.getParameter("Mac");
        String model = request.getParameter("Device_model");
        String address = request.getParameter("Address");

        if(clientName!=null) {
            if(repositoryLocal.findByName(clientName).isEmpty()){
                ClientEntity client = new ClientEntity();
                client.setClientName(clientName);
                client.setClientType(clientType);
                client.setClientAdded(Date.valueOf(added));
                repositoryLocal.update(client);
            } else {
                getServletContext().getRequestDispatcher("/ViewListServlet").forward(request,response);
            }
        } else {
            if(repositoryLocal.findByMac(mac).isEmpty()){
                AddressEntity addressEntity = new AddressEntity();
                addressEntity.setIp(ip);
                addressEntity.setMac(mac);
                addressEntity.setModel(model);
                addressEntity.setAddress(address);
                addressEntity.setClientId(repositoryLocal.findById(ClientEntity.class, Integer.parseInt(id)));
                repositoryLocal.update(addressEntity);
            } else {
                getServletContext().getRequestDispatcher("/ViewListServlet").forward(request,response);
            }
        }

        getServletContext().getRequestDispatcher("/ViewListServlet").forward(request,response);
    }

    public void destroy() {
    }
}