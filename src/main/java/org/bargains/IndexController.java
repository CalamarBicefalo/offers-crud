package org.bargains;

import org.bargains.offers.OffersController;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class IndexController {

    @GetMapping({"", "/"})
    public Collection<Link> index() {
        return Collections.singletonList(linkTo(OffersController.class).withRel("offers"));
    }
}
