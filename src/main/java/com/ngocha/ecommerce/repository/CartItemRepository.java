package com.ngocha.ecommerce.repository;

import com.ngocha.ecommerce.entity.CartItem;
import com.ngocha.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci.product FROM CartItem ci WHERE ci.product.id = ?1")
    Product findProductById(Long productId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?2 AND ci.product.id = ?1")
    CartItem findCartItemByProductIdAndCartId(Long productId, Long cartId);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.product.id = ?1 AND ci.cart.id = ?2")
    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);
}
