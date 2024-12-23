package com.ngocha.ecommerce.configuration;

public class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "30";
    public static final String SORT_CATEGORIES_BY ="categoryId";
    public static final String SORT_PRODUCT_BY= "productId";
    public static final String  SORT_USER_BY= "userId";
    public static final String SORT_ORDERS_BY= "totalAmount";
    public static final String SORT_DIR = "asc";
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 48 * 60 * 60 * 1000;
    public static final String[] PUBLIC_URLS = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/*", "/api-docs/**", "/api/register/**", "/api/login", "/api/refresh", "/api/test",};
    public static final String[] USER_URLS = { "/api/public/**"};
    public static final String[] ADMIN_URLS = { "/api/admin/**"};
}
