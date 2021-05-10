package crm.service;

import core.repository.DataTemplate;
import core.sessionmanager.TransactionManager;
import crm.model.Client;
import org.ehcache.Cache;

import java.util.Optional;

public class DbServiceClientCacheImpl extends DbServiceClientImpl {

    private final Cache<Long, Client> cache;

    @Override
    public Client saveClient(Client client) {
        return super.saveClient(client);
    }

    @Override
    public Optional<Client> getClient(long id) {
        var client = Optional.ofNullable(cache.get(id));
        if (client.isEmpty()) {
            client = super.getClient(id);
            client.ifPresent(c -> cache.put(c.getId(), c));
        }
        return client;
    }

    public DbServiceClientCacheImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate,
                                    Cache<Long, Client> cache) {
        super(transactionManager, clientDataTemplate);
        this.cache = cache;
    }


}
