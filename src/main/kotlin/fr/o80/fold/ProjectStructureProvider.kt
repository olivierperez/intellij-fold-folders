package fr.o80.fold

import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import fr.o80.fold.data.SettingsManager
import fr.o80.fold.fs.GroupingNodeFactory
import fr.o80.fold.utils.Utils

class ProjectStructureProvider : TreeStructureProvider {

    override fun modify(
        parent: AbstractTreeNode<*>,
        children: Collection<AbstractTreeNode<*>>,
        viewSettings: ViewSettings
    ): Collection<AbstractTreeNode<*>> {
        val parentValue = parent.value
        return if (parentValue is PsiDirectory) {
            modifyDirectory(parentValue, children, viewSettings)
        } else {
            children
        }
    }

    private fun modifyDirectory(
        parent: PsiDirectory,
        children: Collection<AbstractTreeNode<*>>,
        viewSettings: ViewSettings
    ): Collection<AbstractTreeNode<*>> {
        val path = parent.virtualFile.path
        val folderSettings = SettingsManager.getFolderSettings(path)

        return folderSettings
            ?.takeIf { it.grouped }
            ?.let { createChildrenGroups(children, it.regex, viewSettings) }
            ?: children
    }

    private fun createChildrenGroups(
        children: Collection<AbstractTreeNode<*>>,
        groupingRegex: String,
        viewSettings: ViewSettings
    ): List<AbstractTreeNode<*>> {
        val project: Project? = Utils.currentProject
        val regex = groupingRegex.toRegex()

        return if (project == null) {
            System.err.println("Project is null: $children")
            listOf()
        } else {
            children.filterIsInstance<PsiDirectoryNode>()
                .groupBy {
                    regex.find(it.virtualFile!!.name)
                        ?.let { match -> match.groups[2]?.value }
                        ?: "unknown"
                }
                .toSortedMap { p0, p1 -> p1.compareTo(p0) }
                .map { (name, children) ->
                    GroupingNodeFactory.create(name, children, project, viewSettings)
                }
        }
    }
}
