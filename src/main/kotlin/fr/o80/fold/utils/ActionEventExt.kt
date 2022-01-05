package fr.o80.fold.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.pom.Navigatable

val AnActionEvent.navigatable: Navigatable?
    get() = this.getData(CommonDataKeys.NAVIGATABLE)
