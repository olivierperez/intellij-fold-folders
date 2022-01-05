package fr.o80.fold.utils

import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.project.Project

fun Project.refreshProjectView() {
    ProjectView.getInstance(this).refresh()
}
