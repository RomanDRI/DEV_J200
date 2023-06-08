package com.example.clientdataserver.servlet;

import com.example.clientdataserver.entities.AddressEntity;
import com.example.clientdataserver.entities.ClientEntity;
import com.example.clientdataserver.repository.RepositoryLocal;
import jakarta.ejb.EJB;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "ViewListServlet", value = "/ViewListServlet")
public class ViewListServlet extends HttpServlet {


    @EJB
    private RepositoryLocal repositoryLocal;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendHtml(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendHtml(request, response);
    }

    private void sendHtml(HttpServletRequest request, HttpServletResponse response) throws IOException{

        String type = request.getParameter("type");
        String nameOrAddress = request.getParameter("name_or_address");


        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body align=\"center\">");
        out.println("<h1>" + "Все клиенты" + "</h1>");
        out.println("<form action=\"ViewListServlet\" method=\"get\" align=\"center\">\n" +
                "    Тип клиента: <select name=\"type\">\n" +
                        "    <option>Юридическое лицо</option>\n" +
                        "    <option>Физическое лицо</option>\n" +
                        "  </select>" +
                "    <input type=\"text\" name=\"name_or_address\">\n" +
                "    <input type=\"submit\" value=\"Поиск\">\n" +
                "</form>");
        out.println("<form action=\"ViewListServlet\" method=\"get\" align=\"center\">\n" +
                "    <input type=\"submit\" value=\"Сбросить\">\n" +
                "</form><br><br>");
        out.println("<table align=\"center\" border=\"1\" cellpadding=\"5\">");
        out.println("<tr>");
        out.println("<td></td>");
        out.println("<td>ID</td>");
        out.println("<td>Name</td>");
        out.println("<td>Type</td>");
        out.println("<td>Added</td>");
        out.println("<td></td>");
        out.println("<td>IP</td>");
        out.println("<td>Mac</td>");
        out.println("<td>Model</td>");
        out.println("<td>Address</td>");
        out.println("<td></td>");
        out.println("</tr>");

        Map<ClientEntity, List<AddressEntity>> clientMap = repositoryLocal.findByFilter2(type, nameOrAddress);
        for (ClientEntity client : clientMap.keySet()) {
            List<AddressEntity> addresses = client.getAddressEntities();
            if(addresses.size()>0) {
                for (AddressEntity address : clientMap.get(client)) {
                    out.println("<tr>");
                    out.println("<td> <form method=\"get\" action=\"UpdateServlet\"> " +
                            "<button>Изменить клиента</button> " +
                            "<input type=\"hidden\" name=\"id\" value=\"" + client.getId() + "\">" +
                            "</form> </td>");
                    out.println("<td>" + client.getId() + "</td>");
                    out.println("<td>" + client.getClientName() + "</td>");
                    out.println("<td>" + client.getClientType() + "</td>");
                    out.println("<td>" + client.getClientAdded() + "</td>");
                    out.println("<td> <form method=\"get\" action=\"CreateServlet\"> " +
                            "<button>Добавить адрес клиента</button>" +
                            "<input type=\"hidden\" name=\"id_address\" value=\"" + address.getId() + "\">" +
                            "<input type=\"hidden\" name=\"id\" value=\"" + client.getId() + "\">" +
                            "</form> " +
                            "<form method=\"get\" action=\"UpdateServlet\"> " +
                            "<button>Изменить адрес клиента</button> " +
                            "<input type=\"hidden\" name=\"id_address\" value=\"" + address.getId() + "\">" +
                            "<input type=\"hidden\" name=\"id\" value=\"" + client.getId() + "\">" +
                            "</form> </td>");
                    out.println("<td>" + address.getIp() + "</td>");
                    out.println("<td>" + address.getMac() + "</td>");
                    out.println("<td>" + address.getModel() + "</td>");
                    out.println("<td>" + address.getAddress() + "</td>");
                    out.println("<td> <form method=\"post\" action=\"DeleteServlet\"> " +
                            "<button>Удалить адрес клиента</button> " +
                            "<input type=\"hidden\" name=\"id_address\" value=\"" + address.getId() + "\">" +
                            "</form> </td>");
                    out.println("</tr>");
                }
            } else {
                out.println("<tr>");
                out.println("<td> <form method=\"get\" action=\"UpdateServlet\"> " +
                        "<button>Изменить клиента</button> " +
                        "<input type=\"hidden\" name=\"id\" value=\"" + client.getId() + "\">" +
                        "</form> </td>");
                out.println("<td>" + client.getId() + "</td>");
                out.println("<td>" + client.getClientName() + "</td>");
                out.println("<td>" + client.getClientType() + "</td>");
                out.println("<td>" + client.getClientAdded() + "</td>");
                out.println("<td> <form method=\"get\" action=\"CreateServlet\"> " +
                        "<button>Добавить адрес клиента</button>" +
                        "<input type=\"hidden\" name=\"id\" value=\"" + client.getId() + "\">" +
                        "</form> </td>");
                out.println("<td></td>");
                out.println("<td></td>");
                out.println("<td></td>");
                out.println("<td></td>");
                out.println("<td> <form method=\"post\" action=\"DeleteServlet\"> " +
                        "<button>Удалить клиента</button> " +
                        "<input type=\"hidden\" name=\"id\" value=\"" + client.getId() + "\">" +
                        "</form> </td>");
                out.println("</tr>");

            }
        }
        out.println("</table> <br><br>");

        out.println("<form method=\"get\" action=\"CreateServlet\" align=\"Center\">\n " +
                "<input type=\"hidden\" name=\"Create_client\" value=\"A\">"+
                "<button>Добавление нового клиента</button> </form>");


        out.println("</body></html>");
    }
}

