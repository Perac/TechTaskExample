package hr.nullsafe.ernietechtask.ui.services

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import hr.nullsafe.ernietechtask.R
import hr.nullsafe.ernietechtask.data.Service
import hr.nullsafe.ernietechtask.ui.Dimensions.CARD_ELEVATION
import hr.nullsafe.ernietechtask.ui.Dimensions.PADDING_DEFAULT
import hr.nullsafe.ernietechtask.ui.Dimensions.PADDING_MINI
import hr.nullsafe.ernietechtask.ui.Dimensions.PADDING_SMALL
import hr.nullsafe.ernietechtask.ui.Dimensions.SERVICE_IMAGE_SIZE
import hr.nullsafe.ernietechtask.ui.ErrorComposable
import hr.nullsafe.ernietechtask.ui.LoadingComposable
import hr.nullsafe.ernietechtask.ui.UiState
import hr.nullsafe.ernietechtask.viewmodel.ServicesViewModel

@Composable
fun ServicesHostScreen(
    viewModel: ServicesViewModel = hiltViewModel(),
    onServiceClick: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    when (uiState.state) {
        is UiState.Error -> {
            ErrorComposable(
                errorMessage = uiState.state.errorMessage,
                onRetry = {
                    viewModel.retryDataFetch()
                }
            )
        }

        is UiState.Loading<*> -> {
            LoadingComposable()
        }

        is UiState.Success<List<Service>> -> {
            ServicesScreen(uiState.state.data, onServiceClick)
        }
    }
}

@Composable
fun ServicesScreen(
    serviceList: List<Service>,
    onServiceClick: (String) -> Unit,
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
            items(serviceList, key = { it.id }) {
                ServiceListItem(it, onServiceClick)
            }
        }
    }
}

@Composable
fun ServiceListItem(
    service: Service,
    onServiceClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = PADDING_SMALL.dp, vertical = PADDING_MINI.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_DEFAULT.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = service.icon,
                contentDescription = "${service.name} image",
                modifier = Modifier
                    .size(SERVICE_IMAGE_SIZE.dp)
                    .clip(CircleShape)
                    .testTag("serviceImage"),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_placeholder),
                error = painterResource(id = R.drawable.ic_error)
            )

            Spacer(modifier = Modifier.width(PADDING_DEFAULT.dp))

            Text(
                text = service.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .testTag("serviceName")
            )

            Spacer(modifier = Modifier.width(PADDING_DEFAULT.dp))

            Button(
                onClick = { onServiceClick(service.id) },
                modifier = Modifier.testTag("manageButton")
            ) {
                Text(stringResource(R.string.label_manage))
            }
        }
    }
}