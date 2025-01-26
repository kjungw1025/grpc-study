package main

import (
	"context"
	"flag"
	"grpc/client/config"
	"log"
	"time"

	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	productInfo "grpc/client/proto"
)

var configFlag = flag.String("config", "./config.toml", "config path")

func main() {
	// Config 초기화
	cfg := config.NewConfig(*configFlag)

	// GRPC 연결 생성
	conn, err := grpc.NewClient(cfg.GRPC.URL, grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		log.Fatalf("did not connect: %v", err)
	}
	defer conn.Close()
	c := productInfo.NewProductInfoClient(conn)

	// 상품 정보 추가
	name := "Apple MacBook M3 Pro"
	description := "Meet Apple MacBook M3 Pro. Featuring the powerful M3 Pro chip for enhanced performance and efficiency."
	price := float64(1699.99)
	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
	defer cancel()

	r, err := c.AddProduct(ctx, &productInfo.Product{Name: name, Description: description, Price: price})
	if err != nil {
		log.Fatalf("Could not add product: %v", err)
	}
	log.Printf("Product ID: %s added successfully", r.Id)

	// 상품 정보 조회
	product, err := c.GetProduct(ctx, &productInfo.ProductID{Id: r.Id})
	if err != nil {
		log.Fatalf("Could not get product: %v", err)
	}
	log.Printf("Product: %v", product.String())
}
