package com.example.clientdataserver.servlet;

import com.example.clientdataserver.entities.AddressEntity;
import com.example.clientdataserver.entities.ClientEntity;
import com.example.clientdataserver.model.Address;
import com.example.clientdataserver.model.Client;
import com.example.clientdataserver.repository.RepositoryLocal;
import com.example.clientdataserver.services.ClientService;
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
    private ClientService clientService;
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
            /*
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\">" +
                    "<input type=\"hidden\" name=\"Client_name\" value=\"" + clientName + "\">" +
                    "<input type=\"hidden\" name=\"Type\" value=\"" +  clientType + "\">" +
                    "<input type=\"hidden\" name=\"Added\" value=\"" + added + "\">");
            out.println("<h3> ID: " + id + "</h3>");
            out.println("<h3> Наименование клиента: " + clientName + "</h3>");
            out.println("<h3> Тип клиента:" + clientType + "</h3>");
            out.println("<h3> Дата добавления клиента:" + added + "</h3>");
*/

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
            ClientEntity client = new ClientEntity();
            client.setClientName(clientName);
            client.setClientType(clientType);
            client.setClientAdded(Date.valueOf(added));
            repositoryLocal.update(client);
        } else {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setIp(ip);
            addressEntity.setMac(mac);
            addressEntity.setModel(model);
            addressEntity.setAddress(address);
            addressEntity.setClientId(repositoryLocal.findById(ClientEntity.class, Integer.parseInt(id)));
            repositoryLocal.update(addressEntity);
        }

        /*
        if(clientName!=null) {
            Client client = new Client(Integer.parseInt(id),clientName,clientType, added);
            clientService.create(client);
        } else {
            Client client = clientService.findClient(Integer.parseInt(id));
            client.setAddress(new Address(ip,mac,model,address));
            clientService.create(client);
        }

         */
        /*
        Client client = new Client(Integer.parseInt(id),clientName,clientType, added);
        client.setAddress(new Address(ip,mac,model,address));
        clientService.create(client);



        PrintWriter out = response.getWriter();

        out.println("<form method=\"get\" action=\"ViewListServlet\"> ");
        out.println("<button>Добавить адрес клиента</button>");
        out.println("</form>");

        out.println("<form method=\"get\" action=\"ViewListServlet\"> ");
        out.println("<button>Просмотр базы данных</button>");
        out.println("</form>");

         */

        getServletContext().getRequestDispatcher("/ViewListServlet").forward(request,response);
    }

    public void destroy() {
    }
}