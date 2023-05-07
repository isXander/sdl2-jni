import org.gradle.internal.os.OperatingSystem
import org.jetbrains.gradle.plugins.upx.UpxTask

plugins {
    `java-library`
    `maven-publish`
    id("org.jetbrains.gradle.upx") version "1.6.0-RC.5"
}

group = "dev.isxander"
version = "2.26.4-5"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDirs("src")
        }
    }
    test {
        java {
            srcDirs()
        }
        resources {
            srcDirs()
        }
    }
}

val nativeBuildSourceset by sourceSets.creating {
    compileClasspath += sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().runtimeClasspath

    java {
        srcDirs("natives-build")
    }
}

dependencies {
    implementation("com.badlogicgames.gdx:gdx-jnigen:2.3.1")
}

val deleteJniFolder by tasks.registering(Delete::class) {
    delete("jni")
    delete("docs")
}

tasks["clean"].dependsOn(deleteJniFolder)

upx {
    // plugin broken - won't unzip itself on unix
    if (!OperatingSystem.current().isWindows) {
        executableProvider.set(buildDir.resolve("upx/exec/upx"))
    }
}

val buildLinuxNatives by tasks.registering(JavaExec::class) {
    group = "natives"

    dependsOn(tasks["clean"], tasks["classes"])
    //finalizedBy(compressLinuxNatives)

    mainClass.set("NativesBuild")
    args = listOf("build-linux", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

val compressLinuxNatives by tasks.registering(UpxTask::class) {
    enabled = false // broken
    group = "natives"

    inputExecutable.set(file("libs/uncompressed/linux64/sdl2-jni-natives-linux64.so"))
    outputExecutable.set(file("libs/natives/linux64/sdl2-jni-natives-linux64.so"))
}

val buildWindowsNatives by tasks.registering(JavaExec::class) {
    group = "natives"

    dependsOn(tasks["clean"], tasks["classes"])
    finalizedBy(compressWindowsX64Natives, compressWindowsX86Natives)

    mainClass.set("NativesBuild")
    args = listOf("build-windows64", "build-windows32", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

val buildWindowsX64Natives by tasks.registering(JavaExec::class) {
    group = "natives"

    dependsOn(tasks["clean"], tasks["classes"])
    finalizedBy(compressWindowsX64Natives)

    mainClass.set("NativesBuild")
    args = listOf("build-windows64", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

val buildWindowsX86Natives by tasks.registering(JavaExec::class) {
    group = "natives"

    dependsOn(tasks["clean"], tasks["classes"])
    finalizedBy(compressWindowsX86Natives)

    mainClass.set("NativesBuild")
    args = listOf("build-windows32", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

val compressWindowsX86Natives by tasks.registering(UpxTask::class) {
    group = "natives"

    inputExecutable.set(file("libs/uncompressed/windows32/sdl2-jni-natives-win32.dll"))
    outputExecutable.set(file("libs/natives/windows32/sdl2-jni-natives-win32.dll"))
}

val compressWindowsX64Natives by tasks.registering(UpxTask::class) {
    group = "natives"

    inputExecutable.set(file("libs/uncompressed/windows64/sdl2-jni-natives-win64.dll"))
    outputExecutable.set(file("libs/natives/windows64/sdl2-jni-natives-win64.dll"))
}

val buildMacNatives by tasks.registering(JavaExec::class) {
    group = "natives"

    dependsOn(tasks["clean"], tasks["classes"])
    finalizedBy(compressMacArmNatives, compressMacIntelNatives)

    mainClass.set("NativesBuild")
    args = listOf("build-mac-x86_64", "build-mac-arm64", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

val buildMacIntelNatives by tasks.registering(JavaExec::class) {
    group = "natives"

    dependsOn(tasks["clean"], tasks["classes"])
    finalizedBy(compressMacIntelNatives)

    mainClass.set("NativesBuild")
    args = listOf("build-mac-x86_64", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

val buildMacArmNatives by tasks.registering(JavaExec::class) {
    group = "natives"

    dependsOn(tasks["clean"], tasks["classes"])
    finalizedBy(compressMacArmNatives)

    mainClass.set("NativesBuild")
    args = listOf("build-mac-arm64", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

//val compressMacNatives by tasks.registering(UpxTask::class) {
//    group = "natives"
//
//    inputExecutable.set(file("libs/uncompressed/macosx64/sdl2-jni-natives-mac64.so"))
//    outputExecutable.set()
//}
val compressMacIntelNatives by tasks.registering(Copy::class) {
    group = "natives"

    from(file("libs/uncompressed/macosx64/sdl2-jni-natives-mac64.dylib"))
    into(file("libs/natives/macosx64/"))
}
val compressMacArmNatives by tasks.registering(Copy::class) {
    from(file("libs/uncompressed/macosxarm64/sdl2-jni-natives-macArm64.dylib"))
    into(file("libs/natives/macosxarm64/"))
}

val allNatives by tasks.registering(JavaExec::class) {
    group = "natives"

    dependsOn(tasks["clean"], tasks["classes"])
    //finalizedBy(compressLinuxNatives, compressWindowsX86Natives, compressWindowsX64Natives)

    mainClass.set("NativesBuild")
    args = listOf("build-linux", "build-windows", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

tasks.jar {
    from(sourceSets["main"].output)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {
    repositories {
        val username = "XANDER_MAVEN_USER".let { System.getenv(it) ?: findProperty(it) }?.toString()
        val password = "XANDER_MAVEN_PASS".let { System.getenv(it) ?: findProperty(it) }?.toString()
        if (username != null && password != null) {
            maven(url = "https://maven.isxander.dev/releases") {
                name = "XanderReleases"
                credentials {
                    this.username = username
                    this.password = password
                }
            }
        } else {
            println("Xander Maven credentials not satisfied.")
        }
    }

    publications {
        create<MavenPublication>("javaLibrary") {
            artifact(tasks.jar.get())
            artifact(sourcesJar.get())

            groupId = "dev.isxander"
            artifactId = "sdl2-jni"
        }

        create<MavenPublication>("natives") {
            val nativesPaths = project.files(
                    "libs/natives/windows32",
                    "libs/natives/windows64",
                    "libs/natives/linux64",
                    "libs/natives/macosx64",
            )
            for (nativeFolder in nativesPaths) {
                if (!nativeFolder.exists())
                    continue

                for (nativeFile in nativeFolder.listFiles() ?: emptyArray()) {
                    artifact(nativeFile) {
                        classifier = nativeFolder.nameWithoutExtension.substringAfterLast('-')
                    }
                }
            }

            groupId = "dev.isxander"
            artifactId = "sdl2-jni-natives"
        }
    }
}

val buildAndPublishNatives by tasks.registering {
    group = "natives"

    dependsOn(allNatives)
    dependsOn("publishNativesPublicationToXanderReleasesRepository")
}

val compressAndPublishNatives by tasks.registering {
    group = "natives"

    dependsOn(compressLinuxNatives, compressWindowsX86Natives, compressWindowsX64Natives)
    dependsOn("publishNativesPublicationToXanderReleasesRepository")
}
