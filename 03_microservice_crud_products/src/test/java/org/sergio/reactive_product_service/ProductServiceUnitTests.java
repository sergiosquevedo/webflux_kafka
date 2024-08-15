package org.sergio.reactive_product_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.sergio.reactive_product_service.model.Product;
import org.sergio.reactive_product_service.service.ProductService;
import org.sergio.reactive_product_service.service.ProductServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceUnitTests {

	private ProductService productService;

	/**
	 * Before each test's execution, we will provide a clean ProductService
	 * instance.
	 * Keep in mind that this implementation has an internal product's list (instead
	 * of use a repository)
	 * so, we need new instance for each test.
	 */
	@BeforeEach
	void createContext() {
		// TODO The hardcoded list kept in memory unless I've intializing before each
		// test. So, we are going to order the text execution
		productService = new ProductServiceImpl();
	}

	@Test
	@Order(1)
	void should_getProductCatalog() {
		StepVerifier.create(productService.getCatalog())
				.expectNextCount(8)
				.verifyComplete();
	}

	@Test
	@Order(2)
	void should_getProduct_ByCategory() {
		StepVerifier.create(productService.getProductByCategory("no-category"))
				.expectNextCount(0)
				.verifyComplete();

		StepVerifier.create(productService.getProductByCategory("Alimentación"))
				.expectNextCount(3)
				.verifyComplete();

		StepVerifier.create(productService.getProductByCategory("Hogar"))
				.expectNextCount(2)
				.verifyComplete();
	}

	@Test
	@Order(3)
	void should_getProduct_ByCode() {
		StepVerifier.create(productService.getProductByCode(23))
				.expectNextCount(0)
				.verifyComplete();

		StepVerifier.create(productService.getProductByCode(107))
				.assertNext(product -> {
					assertEquals(107, product.getCode());
					assertEquals("Detergente", product.getName());
					assertEquals("Limpieza", product.getCategory());
					assertEquals(8.7, product.getPrice());
					assertEquals(12, product.getStock());
				})
				.expectComplete()
				.verify();
	}

	@Test
	@Order(5)
	void should_createNewProduct() {
		var product = new Product(108, "Café", "Alimentación", 1.5, 90);

		StepVerifier.create(productService.saveProduct(product))
				.assertNext(createdProduct -> {
					assertEquals(product.getCode(), createdProduct.getCode());
					assertEquals(product.getName(), createdProduct.getName());
					assertEquals(product.getCategory(), createdProduct.getCategory());
					assertEquals(product.getPrice(), createdProduct.getPrice());
					assertEquals(product.getStock(), createdProduct.getStock());
				})
				.expectComplete()
				.verify();

		// Now, we are going to assert that the product's list has a new element
		StepVerifier.create(productService.getCatalog())
				.expectNextCount(9)
				.verifyComplete();
	}

	@Test
	@Order(4)
	void should_NotcreateNewProduct_ifProductCode_AlreadyExist() {
		var product = new Product(107, "Café", "Alimentación", 1.5, 90);

		StepVerifier.create(productService.saveProduct(product))
				.expectErrorMessage("Product already exist")
				.verify();

		// Now, we are going to assert that the product's list has a new element
		StepVerifier.create(productService.getCatalog())
				.expectNextCount(8)
				.verifyComplete();
	}

	@Test
	@Order(6)
	void should_DeleteProduct() {
		StepVerifier.create(productService.deleteProduct(104))
				.verifyComplete();
		StepVerifier.create(productService.deleteProduct(999))
				.verifyComplete();

		StepVerifier.create(productService.getCatalog())
				.expectNextCount(8)
				.verifyComplete();
	}

	@Test
	@Order(7)
	void shoud_UpdateProduct() {
		StepVerifier.create(productService.updateProductPrice(100, 20.2))
				.assertNext(updatedProduct -> {
					assertEquals(100, updatedProduct.getCode());
					assertEquals("Azucar", updatedProduct.getName());
					assertEquals("Alimentación", updatedProduct.getCategory());
					assertEquals(20.2, updatedProduct.getPrice());
					assertEquals(20, updatedProduct.getStock());
				})
				.expectComplete()
				.verify();
	}

	@Test
	@Order(7)
	void shoud_UpdateProduct_DoNothing_IfProductCode_NotExist() {
		StepVerifier.create(productService.updateProductPrice(989, 20.2))
				.expectNextCount(0)
				.verifyComplete();
	}
}
