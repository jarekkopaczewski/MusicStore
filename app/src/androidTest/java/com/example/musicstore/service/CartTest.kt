package com.example.musicstore.service

import android.content.Context
import com.example.musicstore.data.ProductData
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock

class CartTest{

    @Test
    fun addProductTwoTypes(){
        Cart.addProduct( ProductData("a","a","a",1,1,1,"1"), null)
        Cart.addProduct( ProductData("b","b","b",2,2,2,"2"), null)
        Assert.assertEquals(2, Cart.getProducts().size)
    }

    @Test
    fun addProductOneType(){
        Cart.addProduct( ProductData("a","a","a",1,1,1,"1"), null)
        Cart.addProduct( ProductData("a","a","a",1,1,1,"1"), null)
        Assert.assertEquals(2, Cart.getCount()[0])
    }

    @Test
    fun deleteProductFromEmptyCart(){
        Cart.deleteProduct(ProductData("c","c","c",3,3,3,"3"), null)
        Assert.assertEquals(0,Cart.getProducts().size)
    }

    @Test
    fun deleteProductCart(){
        Cart.addProduct( ProductData("a","a","a",1,1,1,"1"), null)
        Cart.deleteProduct( ProductData("a","a","a",1,1,1,"1"), null)
        Cart.deleteProduct( ProductData("a","a","a",1,1,1,"1"), null)
        Assert.assertEquals(0,Cart.getProducts().size)
    }
}