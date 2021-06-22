package ru.otus.controllers;

import org.springframework.web.bind.annotation.*;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.util.Random;

@RestController
public class ClientRestController {

    private final DBServiceClient serviceClient;

    public ClientRestController(DBServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @PostMapping("/api/client")
    public Client saveClient(@RequestBody Client client) {
        return serviceClient.save(client);
    }

}
