/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.configuration

import com.intellij.openapi.extensions.Extensions
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.libraries.Library
import org.jetbrains.kotlin.idea.framework.JavaRuntimeLibraryDescription
import org.jetbrains.kotlin.idea.versions.LibraryJarDescriptor
import org.jetbrains.kotlin.idea.versions.isKotlinJavaRuntime
import org.jetbrains.kotlin.resolve.TargetPlatform
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatform
import org.jetbrains.kotlin.utils.PathUtil

open class KotlinJavaModuleConfigurator internal constructor() : KotlinWithLibraryConfigurator() {

    override fun isConfigured(module: Module): Boolean {
        return hasKotlinJvmRuntimeInScope(module)
    }

    override val libraryName: String
        get() = JavaRuntimeLibraryDescription.LIBRARY_NAME

    override val dialogTitle: String
        get() = JavaRuntimeLibraryDescription.DIALOG_TITLE

    override val libraryCaption: String
        get() = JavaRuntimeLibraryDescription.LIBRARY_CAPTION

    override val messageForOverrideDialog: String
        get() = JavaRuntimeLibraryDescription.JAVA_RUNTIME_LIBRARY_CREATION

    override val presentableText: String
        get() = "Java"

    override val name: String
        get() = NAME

    override val targetPlatform: TargetPlatform
        get() = JvmPlatform

    override val libraryJarDescriptors: List<LibraryJarDescriptor>
        get() = listOf(LibraryJarDescriptor.RUNTIME_JAR,
                       LibraryJarDescriptor.REFLECT_JAR,
                       LibraryJarDescriptor.RUNTIME_SRC_JAR,
                       LibraryJarDescriptor.TEST_JAR)

    override val libraryMatcher: (Library) -> Boolean
        get() = ::isKotlinJavaRuntime

    companion object {
        val NAME = "java"

        val instance: KotlinJavaModuleConfigurator
            get() = Extensions.findExtension(KotlinProjectConfigurator.EP_NAME, KotlinJavaModuleConfigurator::class.java)
    }
}
