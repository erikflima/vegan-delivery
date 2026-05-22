package com.eriklima.vegandelivery.courier.management.domain.model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Stream;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Setter(AccessLevel.PRIVATE)
public class Courier {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Setter(AccessLevel.PUBLIC)
    private String name;

    @Setter(AccessLevel.PUBLIC)
    private String phone;

    private Integer fulfilledDeliveriesQuantity;

    private Integer pendingDeliveriesQuantity;

    private OffsetDateTime lastFulfilledDeliveryAt;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "courier" )
    private List<AssignedDelivery> pendingDeliveries = new ArrayList<>();


    public List<AssignedDelivery> getPendingDeliveries() {

        return Collections.unmodifiableList(this.pendingDeliveries);
    }


    public static Courier brandNew( String name, String phone ) {

        Courier courier = new Courier();
        courier.setId(UUID.randomUUID());
        courier.setName(name);
        courier.setPhone(phone);
        courier.setPendingDeliveriesQuantity(0);
        courier.setFulfilledDeliveriesQuantity(0);

        return courier;
    }


    public void assign(UUID deliveryId) {

        this.pendingDeliveries.add( AssignedDelivery.pending(deliveryId, this) );
        this.pendingDeliveriesQuantity++;
    }


    public void fulfill( UUID deliveryId ) {

        // Cria um stream da lista de entregas pendentes
        Stream<AssignedDelivery> pendingDeliveriesStream = this.pendingDeliveries.stream();

        // Filtra apenas a entrega com o ID informado
        Stream<AssignedDelivery> filteredDeliveries = pendingDeliveriesStream.filter( delivery -> delivery.getId().equals(deliveryId) );

        // Busca a primeira entrega encontrada
        Optional<AssignedDelivery> optionalDelivery = filteredDeliveries.findFirst();

        // Obtém a entrega encontrada ou lança exceção se não existir
        AssignedDelivery delivery = optionalDelivery.orElseThrow();

        // Remove a entrega da lista de pendentes
        this.pendingDeliveries.remove(delivery);

        // Decrementa a quantidade de entregas pendentes
        this.pendingDeliveriesQuantity--;

        // Incrementa a quantidade de entregas concluídas
        this.fulfilledDeliveriesQuantity++;

        // Atualiza a data/hora da última entrega concluída
        this.lastFulfilledDeliveryAt = OffsetDateTime.now();
    }
    
}