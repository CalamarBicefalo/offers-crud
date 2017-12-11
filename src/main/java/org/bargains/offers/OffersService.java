package org.bargains.offers;

import java.util.List;

public interface OffersService {
    Offer create(Offer offer);

    List<Offer> findAll();

    void cancel(String id);
}
