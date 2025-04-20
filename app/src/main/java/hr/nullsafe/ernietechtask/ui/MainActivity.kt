package hr.nullsafe.ernietechtask.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hr.nullsafe.ernietechtask.Services
import hr.nullsafe.ernietechtask.Settings
import hr.nullsafe.ernietechtask.ui.services.ServicesHostScreen
import hr.nullsafe.ernietechtask.ui.theme.ErnieTechTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ErnieTechTaskTheme {
                MainScreenHost()
            }
        }
    }
}

@Composable
fun MainScreenHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Services
    ) {
        composable<Services> {
            ServicesHostScreen {
                navController.navigate(Settings)
            }
        }

        composable<Settings> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Settings")
            }
        }
    }
}