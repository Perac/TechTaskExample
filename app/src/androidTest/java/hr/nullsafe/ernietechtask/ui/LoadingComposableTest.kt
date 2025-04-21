package hr.nullsafe.ernietechtask.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import hr.nullsafe.ernietechtask.ui.theme.ErnieTechTaskTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingComposable_displaysIndicatorAndText() {
        val loadingMessage = "Please wait..."
        composeTestRule.setContent {
            ErnieTechTaskTheme {
                LoadingComposable(loadingMessage = loadingMessage)
            }
        }

        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()

        composeTestRule.onNodeWithText(loadingMessage).assertIsDisplayed()
    }
}