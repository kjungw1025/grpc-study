package grpc.server.product.model.entity;

import grpc.server.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private double price;

    @Builder
    private Product (@NonNull String name,
                     @NonNull String description,
                     double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
