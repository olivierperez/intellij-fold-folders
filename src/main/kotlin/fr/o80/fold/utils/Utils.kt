package fr.o80.fold.utils

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager

object Utils {
    @JvmStatic
    val currentProject: Project?
        get() {
            val openProjects = ProjectManager.getInstance().openProjects
            return if (openProjects.isNotEmpty()) openProjects[0] else null
        }
}
