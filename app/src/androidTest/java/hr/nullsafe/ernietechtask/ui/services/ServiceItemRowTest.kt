package hr.nullsafe.ernietechtask.ui.services

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import hr.nullsafe.ernietechtask.data.Service
import hr.nullsafe.ernietechtask.ui.theme.ErnieTechTaskTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ServiceItemRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun serviceItemRow_displaysDataAndManageClickWorks() {
        val testServiceId = "testServiceId"
        val testServiceName = "Test Service Name"
        var clickedServiceId = ""

        val service = Service(
            id = testServiceId,
            name = testServiceName,
            icon = "",
            settings = listOf()
        )

        composeTestRule.setContent {
            ErnieTechTaskTheme {
                ServiceListItem(
                    service = service,
                    onServiceClick = { id -> clickedServiceId = id }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("$testServiceName image", useUnmergedTree = true)
            .assertExists()

        composeTestRule.onNodeWithText(testServiceName).assertIsDisplayed()


        val manageButton = composeTestRule.onNodeWithText("Manage") // Or use test tag
        manageButton.assertIsDisplayed()
        manageButton.assertIsEnabled()

        manageButton.performClick()

        assert(clickedServiceId == testServiceId) {
            "Expected click for ID $testServiceId, but got $clickedServiceId"
        }
    }
}