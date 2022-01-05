package fr.o80.fold.data

import com.google.gson.Gson
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import fr.o80.fold.Utils.currentProject
import fr.o80.fold.data.model.FolderSettings
import fr.o80.fold.data.model.Settings

object SettingsManager {

    private const val KEY_SETTINGS_FOLDERS = "SETTINGS_FOLDERS"

    fun getFolderSettings(folder: String): FolderSettings? {
        return currentProject?.let { project ->
            val settings = getSettings(project)
            settings.groupedFolders.firstOrNull { it.path == folder }
        }
    }

    fun isGrouped(path: String): Boolean {
        return getFolderSettings(path) != null
    }

    fun addGroupedFolder(folder: String, regex: String) {
        currentProject?.let { project ->
            val gson = Gson()
            val settings = getSettings(project)
            val newSettings = settings.copy(groupedFolders = settings.groupedFolders + FolderSettings(folder, regex))
            PropertiesComponent.getInstance(project).setValue(KEY_SETTINGS_FOLDERS, gson.toJson(newSettings))
        }
    }

    fun removeGroupedFolder(folder: String) {
        currentProject?.let { project ->
            val gson = Gson()
            val settings = getSettings(project)
            val newSettings = settings.copy(groupedFolders = settings.groupedFolders.filterNot { it.path == folder })
            PropertiesComponent.getInstance(project).setValue(KEY_SETTINGS_FOLDERS, gson.toJson(newSettings))
        }
    }

    private fun getSettings(currentProject: Project): Settings {
        return PropertiesComponent.getInstance(currentProject)
            .getValue(KEY_SETTINGS_FOLDERS)
            ?.let { json ->
                try {
                    Gson().fromJson(json, Settings::class.java)
                } catch (e: Exception) {
                    Settings()
                }
            }
            ?: Settings()
    }
}