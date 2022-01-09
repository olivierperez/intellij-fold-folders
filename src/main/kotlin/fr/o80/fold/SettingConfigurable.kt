package fr.o80.fold

import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import fr.o80.fold.utils.Utils.currentProject
import org.jetbrains.annotations.Nls
import java.awt.event.ActionEvent
import javax.swing.AbstractButton
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class SettingConfigurable : Configurable {

    private lateinit var mPanel: JPanel
    private lateinit var useCustomPatternCheckBox: JCheckBox
    private lateinit var customPattern: JTextField
    private lateinit var hideFoldingPrefix: JCheckBox

    private var isModified = false

    override fun getDisplayName(): @Nls String {
        return "Fold Folders"
    }

    override fun getHelpTopic(): String {
        return "null:"
    }

    override fun createComponent(): JComponent {
        useCustomPatternCheckBox.addActionListener { actionEvent ->
            val selected = getCheckBoxStatus(actionEvent)
            customPattern.isEnabled = selected
            isModified = true
        }
        customPattern.document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent) {
                isModified = true
            }

            override fun removeUpdate(e: DocumentEvent) {
                isModified = true
            }

            override fun changedUpdate(e: DocumentEvent) {
                isModified = true
            }
        })
        hideFoldingPrefix.addActionListener { isModified = true }
        reset()
        return mPanel
    }

    private fun getCheckBoxStatus(actionEvent: ActionEvent): Boolean {
        val abstractButton = actionEvent.source as AbstractButton
        return abstractButton.model.isSelected
    }

    override fun isModified(): Boolean {
        return isModified
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        PropertiesComponent.getInstance().run {
            setValue(
                PREFIX_CUSTOM_USE, java.lang.Boolean.valueOf(
                    useCustomPatternCheckBox.isSelected
                ).toString()
            )

            setValue(PREFIX_PATTERN, customPattern.text)

            setValue(
                PREFIX_HIDE, java.lang.Boolean.valueOf(
                    hideFoldingPrefix.isSelected
                ).toString()
            )
        }

        if (isModified) {
            currentProject?.let { project -> ProjectView.getInstance(project).refresh() }
            isModified = false
        }
    }

    override fun reset() {
        val customPrefix = PropertiesComponent.getInstance().getBoolean(PREFIX_CUSTOM_USE, false)
        useCustomPatternCheckBox.isSelected = customPrefix
        customPattern.isEnabled = customPrefix
        customPattern.text = PropertiesComponent.getInstance()
            .getValue(PREFIX_PATTERN, DEFAULT_PATTERN_DOUBLE)
        hideFoldingPrefix.model.isSelected =
            PropertiesComponent.getInstance().getBoolean(PREFIX_HIDE, false)
    }

    override fun disposeUIResources() {}

    companion object {
        const val PREFIX_PATTERN = "folding_plugin_prefix_pattern"
        const val PREFIX_CUSTOM_USE = "folding_plugin_prefix_custom_use"
        const val PREFIX_HIDE = "folding_plugin_prefix_hide"
        const val DEFAULT_PATTERN = "[^_]{1,}(?=_)"
        const val DEFAULT_PATTERN_DOUBLE = "[^_]{1,}_[^_]{1,}(?=_)"
    }
}