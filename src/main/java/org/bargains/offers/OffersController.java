package org.bargains.offers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/offers")
public class OffersController {

    private final OffersService offersService;

    public OffersController(OffersService offersService) {
        this.offersService = offersService;
    }

    @GetMapping
    public Collection<Resource<Offer>> getOffers() {
        return offersService.findAll().stream()
                .map(this::createOfferResource)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Offer> createOffer(@RequestBody Offer offer) {
        offer = offersService.create(offer);
        return createOfferResource(offer);
    }

    @PatchMapping(value = "/{offerId}", params = "cancel=true")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void cancel(@PathVariable String offerId) {
        offersService.cancel(offerId);
    }

    private Resource<Offer> createOfferResource(@RequestBody Offer offer) {
        List<Link> links = new ArrayList<>();
        if (offer.isActive()) {
            links.add(linkTo(OffersController.class).slash(offer.getId() + "?cancel=true").withRel("cancel"));
            links.add(linkTo(OffersController.class).slash(offer.getId()).slash("redeem").withRel("redeem"));
        }
        return new Resource<>(offer, links);
    }
}
