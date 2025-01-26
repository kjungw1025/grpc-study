package grpc.server.product.service;

import grpc.server.global.model.dto.ResponseIdDto;
import grpc.server.product.model.dto.request.RequestProductDto;
import grpc.server.product.model.entity.Product;
import grpc.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ResponseIdDto createProduct(RequestProductDto dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
        productRepository.save(product);

        return new ResponseIdDto(product.getId());
    }

}
