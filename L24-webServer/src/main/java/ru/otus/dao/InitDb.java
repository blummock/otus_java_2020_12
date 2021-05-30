package ru.otus.dao;

import ru.otus.dao.crm.model.Client;
import ru.otus.dao.crm.service.ClientDao;

public class InitDb {

    public static void init(ClientDao clientDao) {
        if(clientDao.findAll().isEmpty()) {
            clientDao.saveClient(new Client("PackMan", "lucky", "Game Area"));
            clientDao.saveClient(new Client("Siri", "Jobs", "Your Phone"));
        }
     }

}
