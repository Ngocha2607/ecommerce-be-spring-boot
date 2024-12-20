package com.ngocha.ecommerce.service.impl;

import com.ngocha.ecommerce.entity.Cart;
import com.ngocha.ecommerce.entity.CartItem;
import com.ngocha.ecommerce.entity.Product;
import com.ngocha.ecommerce.exception.APIException;
import com.ngocha.ecommerce.exception.ResourceNotFoundException;
import com.ngocha.ecommerce.payload.CartDto;
import com.ngocha.ecommerce.payload.ProductDto;
import com.ngocha.ecommerce.repository.CartItemRepository;
import com.ngocha.ecommerce.repository.CartRepository;
import com.ngocha.ecommerce.repository.ProductRepository;
import com.ngocha.ecommerce.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartDto addProductToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException("Cart", "cartId", cartId)
        );

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "productId", productId)
        );

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);

        if (cartItem != null) {
            throw new APIException("Product " + product.getProductName() + " already exists in the cart");
        }

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName() +
                    " less then or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity() - quantity);

        cart.setTotalPrice(cart.getTotalPrice() + product.getSpecialPrice() * quantity);

        CartDto cartDto = modelMapper.map(cart, CartDto.class);

        List<ProductDto> products = cart.getCartItems().stream().map(p ->
                modelMapper.map(p.getProduct(), ProductDto.class)
        ).collect(Collectors.toList());

        cartDto.setProducts(products);

        return cartDto;
    }

    @Override
    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        if (carts.isEmpty()) {
            throw new APIException("No cart exists");
        }

        List<CartDto> cartDtos = carts.stream().map(cart -> {
            CartDto cartDto = modelMapper.map(cart, CartDto.class);
            List<ProductDto> productDtos = cart.getCartItems().stream().map(p ->
                    modelMapper.map(p.getProduct(), ProductDto.class)
            ).collect(Collectors.toList());

            cartDto.setProducts(productDtos);
            return cartDto;
        }).collect(Collectors.toList());

        return cartDtos;
    }

    @Override
    public CartDto getCart(String userEmail, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(userEmail, cartId);

        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }

        CartDto cartDto = modelMapper.map(cart, CartDto.class);

        List<ProductDto> productDtos = cart.getCartItems().stream().map(p ->
                modelMapper.map(p.getProduct(), ProductDto.class)
        ).collect(Collectors.toList());

        cartDto.setProducts(productDtos);

        return cartDto;
    }

    @Override
    public CartDto updateProductQuantityInCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException("Cart", "cartId", cartId)
        );

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product", "productId", productId)
        );

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }
        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName() +
                    " less then or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart");
        }

        float cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        product.setQuantity(product.getQuantity() + cartItem.getQuantity() - quantity);

        cartItem.setProductPrice(product.getSpecialPrice());
        cartItem.setQuantity(quantity);
        cartItem.setDiscount(product.getDiscount());

        cart.setTotalPrice(cartPrice + cartItem.getProductPrice() * quantity);

        cartItem = cartItemRepository.save(cartItem);

        CartDto cartDto = modelMapper.map(cart, CartDto.class);

        List<ProductDto> products = cart.getCartItems().stream().map(p ->
                modelMapper.map(p.getProduct(), ProductDto.class)
        ).collect(Collectors.toList());

        cartDto.setProducts(products);
        return cartDto;
    }

    @Override
    public void updateProductInCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException("Cart", "cartId", cartId)
        );

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product", "productId", productId)
        );

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart");
        }

        float cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getSpecialPrice());

        cart.setTotalPrice(cartPrice + cartItem.getProductPrice() * cartItem.getQuantity());
        cartItem = cartItemRepository.save(cartItem);
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException("Cart", "cartId", cartId)
        );

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("CartItem", "cartId", cartId);
        }

        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProductPrice() * cartItem.getQuantity());

        Product product = cartItem.getProduct();

        product.setQuantity(product.getQuantity() + cartItem.getQuantity());

        cartItemRepository.deleteCartItemByProductIdAndCartId(productId, cartId);
        return "Product " + product.getProductName() + " removed from the cart";
    }
}
