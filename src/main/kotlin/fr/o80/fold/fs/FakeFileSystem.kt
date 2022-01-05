package fr.o80.fold.fs

import com.intellij.openapi.vfs.NonPhysicalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileListener
import com.intellij.openapi.vfs.VirtualFileSystem
import com.intellij.util.EventDispatcher

private const val PROTOCOL = "fake"

class FakeFileSystem : VirtualFileSystem(), NonPhysicalFileSystem {

    private val eventDispatcher = EventDispatcher.create(
        VirtualFileListener::class.java
    )

    override fun getProtocol(): String = PROTOCOL

    override fun findFileByPath(path: String): VirtualFile? = null

    override fun refresh(asynchronous: Boolean) {
    }

    override fun refreshAndFindFileByPath(path: String): VirtualFile? {
        refresh(false)
        return findFileByPath(path)
    }

    override fun addVirtualFileListener(listener: VirtualFileListener) {
        eventDispatcher.addListener(listener)
    }

    override fun removeVirtualFileListener(listener: VirtualFileListener) {
        eventDispatcher.removeListener(listener)
    }

    override fun deleteFile(requestor: Any?, vFile: VirtualFile) {
        TODO("Not yet implemented")
    }

    override fun moveFile(requestor: Any?, vFile: VirtualFile, newParent: VirtualFile) {
        TODO("Not yet implemented")
    }

    override fun renameFile(requestor: Any?, vFile: VirtualFile, newName: String) {
        TODO("Not yet implemented")
    }

    override fun createChildFile(requestor: Any?, vDir: VirtualFile, fileName: String): VirtualFile {
        TODO("Not yet implemented")
    }

    override fun createChildDirectory(requestor: Any?, vDir: VirtualFile, dirName: String): VirtualFile {
        TODO("Not yet implemented")
    }

    override fun copyFile(
        requestor: Any?,
        virtualFile: VirtualFile,
        newParent: VirtualFile,
        copyName: String
    ): VirtualFile {
        TODO("Not yet implemented")
    }

    override fun isReadOnly(): Boolean {
        TODO("Not yet implemented")
    }

    fun createRoot(name: String): VirtualFile {
        return FakeDirectory(this, null, name)
    }
}
