package org.bargains.offers;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class InMemoryOfferService implements OffersService {
    private final ConcurrentMap<UUID, Offer> datastore = new ConcurrentHashMap<>(10);

    @Override
    public void create(Offer offer) {
        datastore.putIfAbsent(UUID.randomUUID(), offer);
    }

    @Override
    public Iterable<Offer> findAll() {
        return datastore.values();
    }

}
