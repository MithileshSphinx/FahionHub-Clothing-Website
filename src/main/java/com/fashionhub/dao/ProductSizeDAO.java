package com.fashionhub.dao;

import java.util.List;
import com.fashionhub.model.ProductSize;

public interface ProductSizeDAO {


boolean addProductSize(ProductSize productSize);

ProductSize getSizeById(int sizeId);

List<ProductSize> getSizesByProductId(int productId);

boolean updateProductSize(ProductSize productSize);

boolean updateStock(int sizeId, int stockQuantity);

boolean deleteProductSize(int sizeId);


}
