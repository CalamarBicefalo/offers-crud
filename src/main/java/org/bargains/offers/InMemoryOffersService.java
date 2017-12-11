package org.bargains.offers;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class InMemoryOffersService implements OffersService {
    private final ConcurrentMap<UUID, Offer> datastore = new ConcurrentHashMap<>(10);

    @Override
    public Offer create(Offer offer) {
        UUID id = UUID.randomUUID();
        datastore.putIfAbsent(id, offer);
        offer.setId(id.toString());
        return offer;
    }

    @Override
    public List<Offer> findAll() {
        return new ArrayList<>(datastore.values());
    }

    @Override
    public void cancel(String id) {
        datastore.computeIfPresent(UUID.fromString(id), (key, offer) -> {
            offer.cancel();
            return offer;
        });
    }

}
