package com.example.android_project.data;

import com.example.android_project.models.CartItem;
import com.example.android_project.models.Food;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<CartItem> cartItems = new ArrayList<>();

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void addToCart(Food food, int quantity) {
        // nếu đã có món thì + số lượng
        for (CartItem item : cartItems) {
            if (item.getFood().getId().equals(food.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(food, quantity));
    }
    public static void clearCart() {
        cartItems.clear();
    }
    public static void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    public static double getTotal() {
        double sum = 0;
        for (CartItem item : cartItems) {
            sum += item.getTotalPrice();
        }
        return sum;
    }
}
