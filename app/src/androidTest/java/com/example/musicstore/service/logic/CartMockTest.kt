package com.example.musicstore.service.logic

import com.example.musicstore.data.ProductData
import com.example.musicstore.service.Cart
import com.example.musicstore.service.group.AllTest
import com.example.musicstore.service.group.BasicLogicTest
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.categories.Category
import org.mockito.Mockito.mock

@Category(BasicLogicTest::class, AllTest::class)
class CartMockTest {

    private fun getProductDataMock(): ProductData {
        return mock(ProductData::class.java)
    }

    @Test
    fun addProductTwoTypes() {
        Cart.clear()
        Cart.addProduct(getProductDataMock(), null)
        Assert.assertEquals(1, Cart.getProducts().size)
    }

    @Test
    fun addProductOneType() {
        Cart.clear()
        val testProductData = getProductDataMock()
        Cart.addProduct(testProductData, null)
        Cart.addProduct(testProductData, null)
        Assert.assertEquals(2, Cart.getCount()[0])
    }

    @Test
    fun deleteProductFromEmptyCart() {
        Cart.clear()
        Cart.deleteProduct(getProductDataMock(), null)
        Assert.assertEquals(0, Cart.getProducts().size)
    }

    @Test
    fun deleteProductCart() {
        Cart.clear()
        val testProductData = getProductDataMock()
        Cart.addProduct(testProductData, null)
        Cart.deleteProduct(testProductData, null)
        Cart.deleteProduct(testProductData, null)
        Assert.assertEquals(0, Cart.getProducts().size)
    }
}