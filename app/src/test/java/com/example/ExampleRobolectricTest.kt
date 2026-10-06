package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.IconCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Walko Icons", appName)
  }

  @Test
  fun `verify icon catalog has 80 or more curated icons`() {
    assertTrue(IconCatalog.allIcons.size >= 80)
  }

  @Test
  fun `verify categories contain amoled and cyber neon`() {
    assertTrue(IconCatalog.categories.contains("Minimal AMOLED"))
    assertTrue(IconCatalog.categories.contains("Cyber Neon"))
  }
}
