package org.bargains.offers;

public interface OffersService {
    void create(Offer offer);
    Iterable<Offer> findAll();
}
