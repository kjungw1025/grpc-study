package grpc.server.product.service;

import grpc.server.global.model.dto.ResponseIdDto;
import grpc.server.product.model.dto.request.RequestProductDto;
import grpc.server.product.model.entity.Product;
import grpc.server.product.repository.ProductRepository;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import productInfo.ProductInfoGrpc;
import productInfo.ProductInfoOuterClass;

import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase {

    private final ProductService productService;

    private final ProductRepository productRepository;

    @Override
    public void addProduct(ProductInfoOuterClass.Product request,
                           StreamObserver<ProductInfoOuterClass.ProductID> responseObserver) {
        RequestProductDto requestProductDto = RequestProductDto.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        ResponseIdDto responseIdDto = productService.createProduct(requestProductDto);

        ProductInfoOuterClass.ProductID id = ProductInfoOuterClass.ProductID.newBuilder()
                        .setId(responseIdDto.getId()).build();

        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(ProductInfoOuterClass.ProductID request,
                           StreamObserver<ProductInfoOuterClass.Product> responseObserver) {
        Long id = request.getId();

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            responseObserver.onNext(
                    ProductInfoOuterClass.Product.newBuilder()
                            .setId(product.get().getId())
                            .setName(product.get().getName())
                            .setDescription(product.get().getDescription())
                            .setPrice(product.get().getPrice())
                            .build()
            );
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
        }
    }
}
