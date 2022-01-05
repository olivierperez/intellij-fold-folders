package fr.o80.fold.data.model

data class Settings(
    val groupedFolders: List<FolderSettings> = listOf()
)

class FolderSettings(
    val path: String,
    val regex: String
)