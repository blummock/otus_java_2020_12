package demo;

import core.repository.DataTemplateHibernate;
import core.repository.HibernateUtils;
import core.sessionmanager.TransactionManagerHibernate;
import crm.model.AddressDataSet;
import crm.model.Client;
import crm.model.PhoneDataSet;
import crm.service.DbServiceClientImpl;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;


public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class,
                AddressDataSet.class, PhoneDataSet.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        var client = new Client();
        var address = new AddressDataSet(null, "Pushkin Street", client);
        var phones = Arrays.asList(new PhoneDataSet(null, "89100320032", client),
                new PhoneDataSet(null, "89210032004", client));
        client.setName("First Client");
        client.setAddress(address);
        client.setPhones(phones);
        dbServiceClient.saveClient(client);
        var second = dbServiceClient.saveClient(new Client(null, "Second Client",
                new AddressDataSet(null, "Wall Street", null),
                Collections.singletonList(new PhoneDataSet(null, "8800123456", null))));
        var clientSecondSelected = dbServiceClient.getClient(second.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + second.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);

        log.info("All clients");
        dbServiceClient.findAll().forEach(c -> log.info("client:{}", c));
    }
}
