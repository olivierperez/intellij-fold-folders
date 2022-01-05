package fr.o80.fold.fs

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.openapi.project.Project
import com.intellij.psi.impl.file.PsiDirectoryFactory

object GroupingNodeFactory {

    private val fakeFileSystem = FakeFileSystem()

    fun create(
        name: String,
        children: List<PsiDirectoryNode>,
        project: Project,
        viewSettings: ViewSettings
    ): GroupingNode {
        val fakeVirtualDirectory = fakeFileSystem.createRoot(name)
        val fakePsiDirectory = PsiDirectoryFactory.getInstance(project).createDirectory(fakeVirtualDirectory)
        val directoryNode = PsiDirectoryNode(project, fakePsiDirectory, viewSettings)

        return GroupingNode(
            project,
            viewSettings,
            directoryNode,
            name,
            children,
            AllIcons.ObjectBrowser.FlattenPackages
        )

        // Other interesting icons:
        // AllIcons.Actions.Colors
        // AllIcons.Nodes.Plugin
    }
}