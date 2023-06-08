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
import java.util.List;

@WebServlet(name = "UpdateServlet", value = "/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    @EJB
    private RepositoryLocal repositoryLocal;
    private String message;
    private String message2;

    public void init() {
        message = "Изменение данных клиента";
        message2 = "Изменение данных адреса клиента";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");



        String id = request.getParameter("id");
        String idAddress = request.getParameter("id_address");

        ClientEntity clientEntity = repositoryLocal.findById(ClientEntity.class, Integer.parseInt(id));
        List<AddressEntity> addressEntities = clientEntity.getAddressEntities();

        String clientName = clientEntity.getClientName();
        String clientType = clientEntity.getClientType();
        String added = clientEntity.getClientAdded().toString();


        PrintWriter out = response.getWriter();

        if(idAddress==null) {
            out.println("<html><body>");
            out.println("<h1>" + message + "</h1>");
            out.println("<form method=\"post\" action=\"UpdateServlet\"> ");
            out.println("<h3> ID: " + id + "</h3>");
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\">");
            out.println("Наименование клиента: <input type=\"text\" name=\"Client_name\" value=\"" + clientName + "\"" +
                    "title=\"Допускаются только русские буквы\"\n" +
                    "pattern=\"^[А-Яа-яЁё\\s\\d,.-]+$\"\n" +
                    "required/><br>");
            out.println("Тип клиента:\n" +
                    "  <select name=\"Type\">\n" +
                    "    <option>Юридическое лицо</option>\n" +
                    "    <option>Физическое лицо</option>\n" +
                    "  </select><br>");
            out.println("Дата добавления клиента:\n" +
                    "  <input type=\"date\" name=\"Added\" value=\"" + added + "\"" +
                    "  <br>");
            out.println("<button>Внести изменения</button>");
            out.println("</form>");
            out.println("</body></html>");
        } else {
            for (AddressEntity addressEntity : addressEntities) {
                String ip = addressEntity.getIp();
                String mac = addressEntity.getMac();
                String model = addressEntity.getModel();
                String address = addressEntity.getAddress();

                out.println("<html><body>");
                out.println("<h1>" + message2 + "</h1>");
                out.println("<form method=\"post\" action=\"UpdateServlet\"> ");
                out.println("<input type=\"hidden\" name=\"id_address\" value=\"" + idAddress + "\">");
                out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\">");
                out.println("IP адрес устройства: <input type=\"text\" name=\"Device_IP\" value=\"" + ip + "\" maxlength=\"25\"\n" +
                        "         title=\"Введите правильно IP адрес\"\n" +
                        "         pattern=\"\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\"\n" +
                        "         required/>\n" +
                        "  <br>");
                out.println("Mac адрес устройства:\n" +
                        "  <input type=\"text\" name=\"Mac\" value=\"" + mac + "\" maxlength=\"20\"\n" +
                        "         title=\"Введите правильно MAC адрес\"\n" +
                        "         pattern=\"^([0-9A-Fa-f]{2}[-]){5}([0-9A-Fa-f]{2})$\"\n" +
                        "         required/>\n" +
                        "  <br>");
                out.println("Модель устройства:\n" +
                        "  <input type=\"text\" name=\"Device_model\" value=\"" + model + "\" maxlength=\"100\" required/>\n" +
                        "  <br>");
                out.println("Адрес места нахождения:\n" +
                        "  <input type=\"text\" name=\"Address\" value=\"" + address + "\" maxlength=\"200\" required/>\n" +
                        "  <br>");
                out.println("<button>Внести изменения</button>");
                out.println("</form>");
                out.println("</body></html>");
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

        String idAddress = request.getParameter("id_address");

        if(idAddress==null) {
            ClientEntity client = new ClientEntity();
            client.setId(Integer.parseInt(id));
            client.setClientName(clientName);
            client.setClientType(clientType);
            client.setClientAdded(Date.valueOf(added));
            repositoryLocal.update(client);
        } else {

            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setId(Integer.parseInt(idAddress));
            addressEntity.setIp(ip);
            addressEntity.setMac(mac);
            addressEntity.setModel(model);
            addressEntity.setAddress(address);
            addressEntity.setClientId(repositoryLocal.findById(ClientEntity.class, Integer.parseInt(id)));

            repositoryLocal.update(addressEntity);
        }

        getServletContext().getRequestDispatcher("/ViewListServlet").forward(request,response);


    }

    public void destroy() {
    }
}