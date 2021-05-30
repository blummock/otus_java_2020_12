package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.dao.crm.model.Client;
import ru.otus.dao.crm.service.ClientDao;

import java.io.IOException;
import java.io.InputStreamReader;

import static jakarta.servlet.http.HttpServletResponse.*;


public class ClientsApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final ClientDao clientDao;
    private final Gson gson;

    public ClientsApiServlet(ClientDao clientDao, Gson gson) {
        this.clientDao = clientDao;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Client client = gson.fromJson(new InputStreamReader(req.getInputStream()), Client.class);
            String params = getEmptyParams(client);
            if (!params.isEmpty()) {
                resp.sendError(SC_BAD_REQUEST, "Required " + params);
            } else {
                clientDao.saveClient(client);
                resp.setStatus(SC_CREATED);
            }
        } catch (Exception e) {
            resp.sendError(SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private String getEmptyParams(Client client) {
        String emptyParams = "";
        emptyParams += client.getName().isEmpty() ? "Name " : "";
        emptyParams += client.getLogin().isEmpty() ? "Login " : "";
        emptyParams += client.getAddress().isEmpty() ? "Address" : "";
        return emptyParams;
    }
}
