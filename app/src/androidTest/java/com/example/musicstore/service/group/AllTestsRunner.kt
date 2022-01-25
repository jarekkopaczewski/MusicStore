package com.example.musicstore.service.group

import com.example.musicstore.service.logic.AnimateViewTest
import com.example.musicstore.service.logic.CartMockTest
import com.example.musicstore.service.logic.CartTest
import com.example.musicstore.service.logic.DataBaseSupportTest
import com.example.musicstore.service.ui.testUI
import org.junit.experimental.categories.Categories
import org.junit.runner.RunWith
import org.junit.runners.Suite

@Suite.SuiteClasses(testUI::class, AnimateViewTest::class, CartTest::class, CartMockTest::class, DataBaseSupportTest::class )
@RunWith(Categories::class)
@Categories.IncludeCategory(AllTest::class)

class AllTestsRunner {}