package fr.o80.fold.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.unit.dp
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import java.awt.Dimension
import javax.swing.JComponent

class GroupDialog(
    project: Project?,
    initialRegex: String,
    title: String,
    private val onOk: (regex: String) -> Unit
) : DialogWrapper(project) {

    private var regex: String = initialRegex

    init {
        setTitle(title)
        init()
    }

    override fun createCenterPanel(): JComponent {
        return ComposePanel().apply {
            preferredSize = Dimension(400, 200)
            setContent {
                var regex by remember { mutableStateOf(regex) }

                FoldTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Enter the cutting regex below:")
                            Spacer(Modifier.size(8.dp))
                            Row {
                                TextField(
                                    regex,
                                    placeholder = { Text("Regex") },
                                    onValueChange = { value ->
                                        regex = value
                                        saveRegex(value)
                                    },
                                    modifier = Modifier.weight(1f, fill = true)
                                )
                            }
                            Spacer(Modifier.size(8.dp))
                            Text("Group 1: unused\nGroup 2: the folder name")
                        }
                    }
                }
            }
        }
    }

    private fun saveRegex(value: String) {
        regex = value
    }

    override fun doOKAction() {
        super.doOKAction()
        onOk(regex)
    }

}
