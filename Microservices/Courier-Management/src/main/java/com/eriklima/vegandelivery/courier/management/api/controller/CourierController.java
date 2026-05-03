package com.eriklima.vegandelivery.courier.management.api.controller;
import com.eriklima.vegandelivery.courier.management.api.model.CourierInput;
import com.eriklima.vegandelivery.courier.management.domain.model.Courier;
import com.eriklima.vegandelivery.courier.management.domain.repository.CourierRepository;
import com.eriklima.vegandelivery.courier.management.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
public class CourierController {


    private final CourierRegistrationService courierRegistrationService;
    private final CourierRepository courierRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier create(@Valid @RequestBody CourierInput input) {

        return courierRegistrationService.create(input);
    }


    @PutMapping("/{courierId}")
    public Courier update( @PathVariable UUID courierId,
                           @Valid @RequestBody CourierInput input) {

        return courierRegistrationService.update( courierId, input );
    }


    @GetMapping
    public PagedModel<Courier> findAllx(@PageableDefault Pageable pageable) {

        Page<Courier> couriersPage = courierRepository.findAll( pageable );

        PagedModel<Courier> pagedModel = new PagedModel<>( couriersPage );

        return pagedModel;
    }


    @GetMapping("/{courierId}")
    public Courier findById( @PathVariable UUID courierId ) {

        Optional<Courier> courierOptional = courierRepository.findById( courierId );

        if ( courierOptional.isEmpty() ) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND );
        }

        Courier courier = courierOptional.get();

        return courier;
    }


}