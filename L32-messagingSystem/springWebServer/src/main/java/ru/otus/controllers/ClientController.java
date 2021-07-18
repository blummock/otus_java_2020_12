package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        return "clients";
    }
}
