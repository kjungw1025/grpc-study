package grpc.server.product.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestProductDto {

    private final String name;

    private final String description;

    private final double price;
}
