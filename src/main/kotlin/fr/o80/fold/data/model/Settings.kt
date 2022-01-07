package fr.o80.fold.data.model

data class Settings(
    val folders: Map<String, FolderSettings> = mapOf()
)

data class FolderSettings(
    val path: String,
    val regex: String,
    val grouped: Boolean
)