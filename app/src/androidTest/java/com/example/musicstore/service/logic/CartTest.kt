package com.example.musicstore.service.logic

import com.example.musicstore.data.ProductData
import com.example.musicstore.service.Cart
import com.example.musicstore.service.group.BasicLogicTest
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.categories.Category

@Category(BasicLogicTest::class)
class CartTest {

    @Test
    fun addProductTwoTypes() {
        Cart.clear()
        Cart.addProduct(ProductData("a", "a", "a", 1, 1, 1, "1"), null)
        Cart.addProduct(ProductData("b", "b", "b", 2, 2, 2, "2"), null)
        Assert.assertEquals(2, Cart.getProducts().size)
    }

    @Test
    fun addProductOneType() {
        Cart.clear()
        val testProductData = ProductData("a", "a", "a", 1, 1, 1, "1")
        Cart.addProduct(testProductData, null)
        Cart.addProduct(testProductData, null)
        Assert.assertEquals(2, Cart.getCount()[0])
    }

    @Test
    fun deleteProductFromEmptyCart() {
        Cart.clear()
        Cart.deleteProduct(ProductData("c", "c", "c", 3, 3, 3, "3"), null)
        Assert.assertEquals(0, Cart.getProducts().size)
    }

    @Test
    fun deleteProductCart() {
        Cart.clear()
        val testProductData = ProductData("a", "a", "a", 1, 1, 1, "1")
        Cart.addProduct(testProductData, null)
        Cart.deleteProduct(testProductData, null)
        Cart.deleteProduct(testProductData, null)
        Assert.assertEquals(0, Cart.getProducts().size)
    }
}