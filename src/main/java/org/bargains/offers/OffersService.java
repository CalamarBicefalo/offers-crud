package org.bargains.offers;

public interface OffersService {
    Offer create(Offer offer);

    Iterable<Offer> findAll();

    void cancel(String id);
}
