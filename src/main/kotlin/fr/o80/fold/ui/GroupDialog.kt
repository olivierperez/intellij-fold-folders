package fr.o80.fold.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.CellBuilder
import com.intellij.ui.layout.GrowPolicy
import com.intellij.ui.layout.ValidationInfoBuilder
import com.intellij.ui.layout.panel
import java.awt.event.ActionEvent
import java.util.regex.PatternSyntaxException
import javax.swing.AbstractAction
import javax.swing.Action
import javax.swing.JComponent

class GroupDialog(
    project: Project?,
    title: String,
    initialRegex: String,
    private val defaultRegex: String,
    private val onOk: (regex: String) -> Unit
) : DialogWrapper(project) {

    private lateinit var regexField: CellBuilder<JBTextField>

    var regex: String = initialRegex

    init {
        setTitle(title)
        init()
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row {
                label("Enter the cutting regex below:")
            }
            row("Regex") {
                regexField = textField(::regex)
                    .focused()
                    .growPolicy(GrowPolicy.MEDIUM_TEXT)
                    .withValidationOnInput { validateRegex(it) }
            }
            row {
                comment("Group 1: unused<br/>Group 2: the folder name<br/>Group x: unused")
            }
        }
    }

    private fun ValidationInfoBuilder.validateRegex(it: JBTextField): ValidationInfo? =
        try {
            it.text.toRegex()
            null
        } catch (e: PatternSyntaxException) {
            error("Invalid regex")
        }

    override fun doOKAction() {
        super.doOKAction()
        onOk(regex)
    }

    override fun createLeftSideActions(): Array<Action> {
        return arrayOf(ResetAction())
    }

    inner class ResetAction : AbstractAction("Reset") {
        override fun actionPerformed(e: ActionEvent?) {
            regexField.component.text = defaultRegex
            println("Reseted!! $defaultRegex")
        }
    }

}
