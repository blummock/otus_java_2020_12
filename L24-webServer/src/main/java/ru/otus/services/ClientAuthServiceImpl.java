package ru.otus.services;

import ru.otus.dao.UserDao;

public class ClientAuthServiceImpl implements ClientAuthService {

    private final UserDao clientDao;

    public ClientAuthServiceImpl(UserDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return clientDao.findByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
