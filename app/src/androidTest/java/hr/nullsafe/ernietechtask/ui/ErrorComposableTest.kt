package hr.nullsafe.ernietechtask.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import hr.nullsafe.ernietechtask.ui.theme.ErnieTechTaskTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(AndroidJUnit4::class)
class ErrorComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun errorComposable_displaysElementsAndRetryWorks() {
        val errorMessage = "Network Failed"
        val retryClicked = AtomicBoolean(false)

        composeTestRule.setContent {
            ErnieTechTaskTheme {
                ErrorComposable(
                    errorMessage = errorMessage,
                    onRetry = { retryClicked.set(true) }
                )
            }
        }

        // Assert Icon is displayed (using test tag)
        composeTestRule.onNodeWithTag("errorIcon").assertIsDisplayed()

        // Assert error message is displayed
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        // Or using test tag: composeTestRule.onNodeWithTag("errorMessage").assertTextEquals(errorMessage)

        // Assert Retry button is displayed and enabled
        val retryButton = composeTestRule.onNodeWithTag("retryButton") // Use test tag
        retryButton.assertIsDisplayed()
        retryButton.assertIsEnabled()

        // Perform click on Retry button
        retryButton.performClick()

        // Assert the onRetry lambda was called
        assert(retryClicked.get()) { "Retry lambda was not called" }
    }
}