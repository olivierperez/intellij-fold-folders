package fr.o80.fold

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.psi.PsiDirectory
import fr.o80.fold.data.SettingsManager
import fr.o80.fold.data.SettingsManager.getFolderSettings
import fr.o80.fold.ui.GroupDialog
import fr.o80.fold.utils.navigatable
import fr.o80.fold.utils.refreshProjectView

private const val DECOMPOSE = "Ungroup"
private const val COMPOSE = "Group"

class GroupAction : DumbAwareAction() {

    override fun actionPerformed(actionEvent: AnActionEvent) {
        val project = actionEvent.project
        val nav = actionEvent.navigatable
        if (nav is PsiDirectory) {
            val path = nav.virtualFile.path

            if (getFolderSettings(path)?.grouped == true) {
                SettingsManager.removeGroupedFolder(path)
                project?.refreshProjectView()
            } else {
                GroupDialog(
                    project,
                    title = "Configure The Group",
                    initialRegex = "([^_-]+)[_-]([^_-]+)(:?[_-](.+))?",
                    onOk = { regex ->
                        SettingsManager.addGroupedFolder(path, regex)
                        project?.refreshProjectView()
                    }
                ).show()
            }
        }
    }

    override fun update(actionEvent: AnActionEvent) {
        var enabledAndVisible = false

        if (actionEvent.project != null) {
            val nav = actionEvent.navigatable
            if (nav is PsiDirectory) {
                val path = nav.virtualFile.path
                if (getFolderSettings(path)?.grouped == true) {
                    actionEvent.presentation.text = DECOMPOSE
                } else {
                    actionEvent.presentation.text = COMPOSE
                }
                enabledAndVisible = true
            }
        }

        actionEvent.presentation.isEnabledAndVisible = enabledAndVisible
    }
}
