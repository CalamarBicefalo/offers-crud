package org.bargains.offers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offers")
public class OffersController {

    private final OffersService offersService;

    public OffersController(OffersService offersService) {
        this.offersService = offersService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOffer(@RequestBody Offer offer) {
        offersService.create(offer);
    }
}
