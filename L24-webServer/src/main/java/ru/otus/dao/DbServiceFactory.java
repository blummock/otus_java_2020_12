package ru.otus.dao;


import org.hibernate.cfg.Configuration;
import ru.otus.dao.core.repository.DataTemplateHibernate;
import ru.otus.dao.core.repository.HibernateUtils;
import ru.otus.dao.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.dao.crm.model.Client;
import ru.otus.dao.crm.service.ClientDao;
import ru.otus.dao.crm.service.ClientDaoImpl;


public class DbServiceFactory {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static ClientDao createClientDao() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        return new ClientDaoImpl(transactionManager, clientTemplate);
    }
}