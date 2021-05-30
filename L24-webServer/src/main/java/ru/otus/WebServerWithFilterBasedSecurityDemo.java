package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.dao.DbServiceFactory;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.InitDb;
import ru.otus.dao.UserDao;
import ru.otus.dao.crm.service.ClientDao;
import ru.otus.server.ClientsWebServer;
import ru.otus.server.ClientsWebServerWithFilterBasedSecurity;
import ru.otus.services.ClientAuthService;
import ru.otus.services.ClientAuthServiceImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/clients
*/
public class WebServerWithFilterBasedSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        UserDao userDao = new InMemoryUserDao();
        ClientDao clientDao = DbServiceFactory.createClientDao();
        InitDb.init(clientDao);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        ClientAuthService authService = new ClientAuthServiceImpl(userDao);
        ClientsWebServer clientsWebServer = new ClientsWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, clientDao, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
