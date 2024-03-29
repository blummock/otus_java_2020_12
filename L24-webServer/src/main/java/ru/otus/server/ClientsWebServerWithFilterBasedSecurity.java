package ru.otus.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.dao.crm.service.ClientDao;
import ru.otus.services.ClientAuthService;
import ru.otus.services.TemplateProcessor;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.LoginServlet;

import java.util.Arrays;

public class ClientsWebServerWithFilterBasedSecurity extends ClientsWebServerSimple {
    private final ClientAuthService authService;

    public ClientsWebServerWithFilterBasedSecurity(int port,
                                                   ClientAuthService authService,
                                                   ClientDao clientDao,
                                                   Gson gson,
                                                   TemplateProcessor templateProcessor) {
        super(port, clientDao, gson, templateProcessor);
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
