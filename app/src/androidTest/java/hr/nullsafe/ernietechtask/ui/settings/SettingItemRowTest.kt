package hr.nullsafe.ernietechtask.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import hr.nullsafe.ernietechtask.data.Settings
import hr.nullsafe.ernietechtask.ui.theme.ErnieTechTaskTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingItemRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun settingItemRow_displaysDataAndTogglesCorrectly() {
        val settingName = "Enable Awesome Feature"

        var isCheckedState by mutableStateOf(false)

        var settings by mutableStateOf(
            Settings(
                id = "settingsTestId",
                name = settingName,
                enabled = isCheckedState
            )
        )

        composeTestRule.setContent {
            ErnieTechTaskTheme {
                SettingsListItem(
                    settings,
                    onCheckedChange = { newState ->
                        isCheckedState = newState
                        settings = settings.copy(enabled = newState)
                    }
                )
            }
        }

        val textNode = composeTestRule.onNodeWithText(settingName)
        val checkboxNode = composeTestRule.onNode(withRole(Role.Checkbox))
        val rowNode = composeTestRule.onNodeWithTag("settingRow")

        textNode.assertIsDisplayed()
        rowNode.assertIsDisplayed()
        checkboxNode.assertIsOff()

        rowNode.performClick()

        checkboxNode.assertIsOn()
        assert(isCheckedState) { "Internal state variable did not update to true" }

        rowNode.performClick()

        checkboxNode.assertIsOff()
        assert(!isCheckedState) { "Internal state variable did not update to false" }
    }

    fun withRole(role: Role) = SemanticsMatcher.expectValue(SemanticsProperties.Role, role)
}