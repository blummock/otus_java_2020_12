package ru.otus.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.FrontendService;

@RestController
public class ClientRestController {

    private final FrontendService frontendService;

    public ClientRestController(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @PostMapping("/api/client")
    public void saveClient(@RequestBody Client client) {
        frontendService.putClientAsync(client, c -> frontendService.getClientsAsync());
    }

    @GetMapping("api/client/list")
    public void getClients() {
        frontendService.getClientsAsync();
    }
}
