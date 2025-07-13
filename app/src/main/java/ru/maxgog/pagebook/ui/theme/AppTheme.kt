package ru.maxgog.pagebook.ui.theme

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration

object AppTheme {
    val horizontalPadding = 16.dp
    val verticalPadding = 8.dp
    val itemSpacing = 8.dp

    enum class ThemeMode { SYSTEM, LIGHT, DARK }

    var themeMode by mutableStateOf(ThemeMode.SYSTEM)

    @Composable
    fun getSystemThemeMode(): ThemeMode {
        val configuration = LocalConfiguration.current
        return remember(configuration) {
            when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> ThemeMode.DARK
                else -> ThemeMode.LIGHT
            }
        }
    }

    @Composable
    fun ApplyTheme(content: @Composable () -> Unit) {
        val systemTheme = getSystemThemeMode()
        val selectedTheme = if (themeMode == ThemeMode.SYSTEM) systemTheme else themeMode

        MaterialTheme(
            colorScheme = when (selectedTheme) {
                ThemeMode.LIGHT -> LightColorScheme
                ThemeMode.DARK -> DarkColorScheme
                else -> LightColorScheme
            },
            typography = Typography,
            content = content
        )
    }
}
