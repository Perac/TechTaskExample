package hr.nullsafe.ernietechtask.ui.services

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hr.nullsafe.ernietechtask.viewmodel.ServicesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServicesHostScreen(
    viewModel: ServicesViewModel = koinViewModel(),
    onServiceClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        TextButton(onClick = onServiceClick) {
            Text("Services")
        }
    }
}