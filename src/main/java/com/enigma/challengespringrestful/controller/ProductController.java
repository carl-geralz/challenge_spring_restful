package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.APIURL;
import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.ProductDAO;
import com.enigma.challengespringrestful.dto.request.ProductDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIURL.PRODUCT)
public class ProductController {
    private final ProductDAO productDAO;

    @PostMapping
    public ResponseEntity<CommonResponse<Product>> createProduct(@RequestBody ProductDTORequest productDTORequest) {
        Product newProduct = productDAO.create(productDTORequest);

        CommonResponse<Product> commonResponse = CommonResponse.<Product>builder().statusCode(HttpStatus.CREATED.value()).message(ConstantMessage.CREATED).data(newProduct).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> findProductsById(@PathVariable String id) {
        Product product = productDAO.findById(id);

        CommonResponse<Product> commonResponse = CommonResponse.<Product>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(product).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<Product>>> findAllProducts(@RequestParam(name = "name", required = false) String name) {
        List<Product> productList = productDAO.findAll(name);

        CommonResponse<List<Product>> commonResponse = CommonResponse.<List<Product>>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(productList).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Product>> updateProduct(@RequestBody ProductDTORequest productDTORequest) {
        Product updateProduct = productDAO.update(productDTORequest);

        CommonResponse<Product> commonResponse = CommonResponse.<Product>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.UPDATED).data(updateProduct).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteProduct(@PathVariable String id) {
        productDAO.deleteById(id);
        CommonResponse<?> commonResponse = CommonResponse.builder().statusCode(HttpStatus.NO_CONTENT.value()).message(ConstantMessage.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
    }

}
