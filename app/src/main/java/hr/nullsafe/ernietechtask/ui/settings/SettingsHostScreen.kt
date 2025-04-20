package hr.nullsafe.ernietechtask.ui.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hr.nullsafe.ernietechtask.data.Settings
import hr.nullsafe.ernietechtask.ui.ErrorComposable
import hr.nullsafe.ernietechtask.ui.LoadingComposable
import hr.nullsafe.ernietechtask.ui.UiState
import hr.nullsafe.ernietechtask.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsHostScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    when (uiState.state) {
        is UiState.Error -> {
            ErrorComposable(
                errorMessage = uiState.state.errorMessage,
                onRetry = {}
            )
        }

        is UiState.Loading<*> -> {
            LoadingComposable()
        }

        is UiState.Success<List<Settings>> -> {
            SettingsScreen(
                settingsList = uiState.state.data,
                onEnableToggle = { settingsId, enabled ->
                    viewModel.toggleSettings(settingsId, enabled)
                }
            )
        }
    }
}

@Composable
fun SettingsScreen(
    settingsList: List<Settings>,
    onEnableToggle: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeContent,
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(settingsList, key = { it.id }) {
                SettingsListItem(it, { enabled ->
                    onEnableToggle(it.id, enabled)
                })
            }
        }
    }
}

@Composable
fun SettingsListItem(
    settings: Settings,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .toggleable(
                value = settings.enabled,
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = settings.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Checkbox(
            checked = settings.enabled,
            onCheckedChange = null
        )
    }
}