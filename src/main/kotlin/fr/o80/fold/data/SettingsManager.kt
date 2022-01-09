package fr.o80.fold.data

import com.google.gson.Gson
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import fr.o80.fold.Utils.currentProject
import fr.o80.fold.data.model.FolderSettings
import fr.o80.fold.data.model.Settings

object SettingsManager {

    private val gson = Gson()

    private const val KEY_SETTINGS_FOLDERS = "SETTINGS_FOLDERS"

    fun getFolderSettings(folder: String): FolderSettings? {
        return currentProject?.let { project ->
            getSettings(project).folders[folder]
        }
    }

    fun addGroupedFolder(folder: String, regex: String) {
        currentProject?.let { project ->
            val settings = getSettings(project).withGrouped(folder, regex)
            PropertiesComponent.getInstance(project).setValue(KEY_SETTINGS_FOLDERS, gson.toJson(settings))
        }
    }

    fun removeGroupedFolder(folder: String) {
        currentProject?.let { project ->
            val newSettings = getSettings(project).withUngrouped(folder)
            PropertiesComponent.getInstance(project).setValue(KEY_SETTINGS_FOLDERS, gson.toJson(newSettings))
        }
    }

    private fun getSettings(currentProject: Project): Settings {
        return PropertiesComponent.getInstance(currentProject)
            .getValue(KEY_SETTINGS_FOLDERS)
            ?.let { json ->
                try {
                    gson.fromJson(json, Settings::class.java)
                } catch (e: Exception) {
                    Settings()
                }
            }
            ?: Settings()
    }
}

private fun Settings.withGrouped(folder: String, regex: String): Settings {
    val previousFolderSettings = this.folders[folder]

    return if (previousFolderSettings == null) {
        val settingsToInsert = folder to FolderSettings(folder, regex = regex, grouped = true)
        this.copy(folders = this.folders + settingsToInsert)
    } else {
        val settingsToInsert = folder to previousFolderSettings.copy(regex = regex, grouped = true)
        this.copy(folders = this.folders + settingsToInsert)
    }
}

fun Settings.withUngrouped(folder: String): Settings {
    val previousFolderSettings = this.folders[folder]

    return if (previousFolderSettings == null) {
        this.copy(folders = this.folders - folder)
    } else {
        val settingsToUpdate = folder to previousFolderSettings.copy(grouped = false)
        this.copy(folders = this.folders + settingsToUpdate)
    }
}
