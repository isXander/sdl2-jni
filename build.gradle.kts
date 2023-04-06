plugins {
    `java-library`
    `maven-publish`
}

group = "dev.isxander"
version = "2.26.4-1"

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

val linuxNatives by tasks.registering(JavaExec::class) {
    dependsOn(tasks["classes"])
    mainClass.set("NativesBuild")
    args = listOf("build-linux", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

val windowsNatives by tasks.registering(JavaExec::class) {
    dependsOn(tasks["classes"])
    mainClass.set("NativesBuild")
    args = listOf("build-windows", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

val macNatives by tasks.registering(JavaExec::class) {
    dependsOn(tasks["classes"])
    mainClass.set("NativesBuild")
    args = listOf("build-OSX", "system-SDL2")
    classpath = nativeBuildSourceset.runtimeClasspath
    standardInput = System.`in`
}

val allNatives by tasks.registering(JavaExec::class) {
    dependsOn(tasks["classes"])
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
        create<MavenPublication>("maven") {
            artifact(tasks.jar.get())
            artifact(sourcesJar.get())

            groupId = "dev.isxander"
            artifactId = "sdl2-jni"
        }
    }
}
