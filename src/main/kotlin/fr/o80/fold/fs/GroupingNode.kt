package fr.o80.fold.fs

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class GroupingNode(
    project: Project,
    viewSettings: ViewSettings,
    directoryNode: PsiDirectoryNode,
    private val overrideName: String,
    private val children: List<PsiDirectoryNode>,
    private val presentationIcon: Icon
) : ProjectViewNode<AbstractTreeNode<*>>(project, directoryNode, viewSettings) {

    override fun update(presentation: PresentationData) {
        presentation.presentableText = overrideName
        presentation.setIcon(presentationIcon)
    }

    override fun getChildren(): Collection<AbstractTreeNode<*>> {
        return children
    }

    override fun contains(file: VirtualFile): Boolean {
        for (childNode in children) {
            val treeNode = childNode as ProjectViewNode<*>
            if (treeNode.contains(file)) {
                return true
            }
        }
        return false
    }
}

