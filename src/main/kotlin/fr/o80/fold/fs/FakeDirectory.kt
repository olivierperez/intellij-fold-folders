package fr.o80.fold.fs

import com.intellij.analysis.AnalysisBundle
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileSystem
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class FakeDirectory(
    private val fileSystem: VirtualFileSystem,
    private val parent: VirtualFile?,
    private val name: String,
    private val children: List<VirtualFile> = listOf()
) : VirtualFile() {

    override fun getName(): String = name

    override fun getFileSystem(): VirtualFileSystem = fileSystem

    override fun getPath(): String =
        if (parent == null) name
        else parent.path + "/" + name

    override fun isWritable(): Boolean = true

    override fun isDirectory(): Boolean = true

    override fun isValid(): Boolean = true

    override fun getParent(): VirtualFile? = parent

    override fun getChildren(): Array<VirtualFile> = children.toTypedArray()

    override fun getOutputStream(requestor: Any?, newModificationStamp: Long, newTimeStamp: Long): OutputStream {
        throw IOException(AnalysisBundle.message("file.write.error", url))
    }

    override fun contentsToByteArray(): ByteArray {
        throw IOException(AnalysisBundle.message("file.read.error", url))
    }

    override fun getInputStream(): InputStream {
        throw IOException(AnalysisBundle.message("file.read.error", url))
    }

    override fun getTimeStamp(): Long = -1

    override fun getLength(): Long = -1

    override fun refresh(asynchronous: Boolean, recursive: Boolean, postRunnable: Runnable?) {
    }
}
