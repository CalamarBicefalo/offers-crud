package org.bargains.offers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Data;
import lombok.val;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.money.UnknownCurrencyException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    public Resource<Offer> createOffer(@Valid @RequestBody OfferDTO offerDTO) {
        val offer = offersService.create(offerDTO.toOffer());
        return createOfferResource(offer);
    }

    @PatchMapping(value = "/{offerId}", params = "cancel=true")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void cancel(@PathVariable String offerId) {
        offersService.cancel(offerId);
    }

    @ExceptionHandler(UnknownCurrencyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<Map<String, Object>> processValidationError(UnknownCurrencyException ex) {
        return ImmutableList.of(ImmutableMap.of(
                "field", "price.currency",
                "error", "Unknown currency",
                "value", ex.getCurrencyCode()
        ));
    }

    private Resource<Offer> createOfferResource(Offer offer) {
        List<Link> links = new ArrayList<>();
        if (offer.isActive()) {
            links.add(linkTo(OffersController.class).slash(offer.getId() + "?cancel=true").withRel("cancel"));
            links.add(linkTo(OffersController.class).slash(offer.getId()).slash("redeem").withRel("redeem"));
        }
        return new Resource<>(offer, links);
    }

    @Data
    private static class OfferDTO {
        @NotEmpty
        private String description;
        @NotNull
        @Valid
        private Money price;
        @NotNull
        private Instant offerStarts;
        @NotNull
        private Instant offerEnds;

        Offer toOffer() {
            return Offer.builder()
                    .description(description)
                    .price(org.javamoney.moneta.Money.of(price.amount, price.currency))
                    .offerStarts(offerStarts)
                    .offerEnds(offerEnds)
                    .description(description)
                    .description(description)
                    .build();
        }

        @Data
        private static class Money {
            @NotEmpty
            private String currency;
            @NotNull
            private Double amount;
        }
    }
}
