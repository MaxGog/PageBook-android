package ru.maxgog.pagebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import ru.maxgog.pagebook.ui.app.CombinedApp
import ru.maxgog.pagebook.ui.theme.PageBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PageBookTheme {
                CompositionLocalProvider(LocalViewModelStoreOwner provides this) {
                    CombinedApp()
                }
            }
        }
    }
}