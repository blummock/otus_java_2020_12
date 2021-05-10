package demo;

import cache.DbCacheFactory;
import core.repository.DataTemplateHibernate;
import core.repository.HibernateUtils;
import core.sessionmanager.TransactionManagerHibernate;
import crm.model.AddressDataSet;
import crm.model.Client;
import crm.model.PhoneDataSet;
import crm.service.DbServiceClientCacheImpl;
import crm.service.DbServiceClientImpl;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class,
                AddressDataSet.class, PhoneDataSet.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        var dbServiceClient = new DbServiceClientCacheImpl(transactionManager, clientTemplate,
                DbCacheFactory.createSimple(Long.class, Client.class, 3));
        List<Long> clientIds = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            var client = new Client();
            var address = new AddressDataSet(null, "Pushkin Street " + i, client);
            var phones = Arrays.asList(new PhoneDataSet(null, "89100320032" + i, client),
                    new PhoneDataSet(null, "89210032004" + i, client));
            client.setName("Client #" + i);
            client.setAddress(address);
            client.setPhones(phones);
            clientIds.add(dbServiceClient.saveClient(client).getId());
        }
        clientIds.forEach(aLong -> {
                    var start = System.currentTimeMillis();
                    var client = dbServiceClient.getClient(aLong);
                    var operationTime = System.currentTimeMillis() - start;
                    log.info("SELECT OPERATION:{}ms  clientSelected:{}", operationTime, client);
                }
        );
        log.info("-------CACHE IS READY---------");
        Collections.reverse(clientIds);
        clientIds.forEach(aLong -> {
                    var start = System.currentTimeMillis();
                    var client = dbServiceClient.getClient(aLong);
                    var operationTime = System.currentTimeMillis() - start;
                    log.info("SELECT OPERATION:{}ms  clientSelected:{}", operationTime, client);
                }
        );
        DbCacheFactory.close();
    }
}
