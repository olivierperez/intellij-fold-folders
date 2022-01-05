package fr.o80.fold.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.application.ApplicationManager
import javax.swing.UIManager
import java.awt.Color as AWTColor

interface SwingColor {
    val background: Color
    val onBackground: Color
    val focusColor: Color
}

@Composable
fun SwingColor(): SwingColor {
    val swingColor = remember { SwingColorImpl() }

    val messageBus = remember {
        ApplicationManager.getApplication().messageBus.connect()
    }

    remember(messageBus) {
        messageBus.subscribe(
            LafManagerListener.TOPIC,
            ThemeChangeListener(swingColor::updateCurrentColors)
        )
    }

    DisposableEffect(messageBus) {
        onDispose {
            messageBus.disconnect()
        }
    }

    return swingColor
}

private class SwingColorImpl : SwingColor {
    private val _backgroundState: MutableState<Color> = mutableStateOf(getBackgroundColor)
    private val _onBackgroundState: MutableState<Color> = mutableStateOf(getOnBackgroundColor)
    private val _onFocusColor: MutableState<Color> = mutableStateOf(getFocusColor)

    override val background: Color get() = _backgroundState.value
    override val onBackground: Color get() = _onBackgroundState.value
    override val focusColor: Color get() = _onFocusColor.value

    private val getBackgroundColor get() = getColor("Panel.background")
    private val getOnBackgroundColor get() = getColor("Panel.foreground")
    private val getFocusColor get() = getColor("Component.focusedBorderColor")

    fun updateCurrentColors() {
        _backgroundState.value = getBackgroundColor
        _onBackgroundState.value = getOnBackgroundColor
        _onFocusColor.value = getFocusColor
    }

    private val AWTColor.asComposeColor: Color get() = Color(red, green, blue, alpha)
    private fun getColor(key: String): Color = UIManager.getColor(key).asComposeColor

}

internal class ThemeChangeListener(
    val updateColors: () -> Unit
) : LafManagerListener {
    override fun lookAndFeelChanged(source: LafManager) {
        updateColors()
    }
}
