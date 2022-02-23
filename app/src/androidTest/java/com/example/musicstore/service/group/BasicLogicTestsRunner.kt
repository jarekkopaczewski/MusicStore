package com.example.musicstore.service.group

import com.example.musicstore.service.logic.AnimateViewTest
import com.example.musicstore.service.logic.CartTest
import com.example.musicstore.service.logic.DataBaseSupportTest
import org.junit.experimental.categories.Categories
import org.junit.runner.RunWith
import org.junit.runners.Suite

@Suite.SuiteClasses(AnimateViewTest::class, CartTest::class, DataBaseSupportTest::class)
@RunWith(Categories::class)
@Categories.IncludeCategory(BasicLogicTest::class)

class BasicLogicTestsRunner {}



