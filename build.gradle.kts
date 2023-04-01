plugins {
    `java-library`
    `maven-publish`
}

group = "dev.isxander"
version = "1.0.0"

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

val nativePresentSourceset by sourceSets.creating {
    compileClasspath += sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().runtimeClasspath

    resources {
        srcDirs(
            "res",
            "libs/linux32",
            "libs/linux64",
            "libs/macosx32",
            "libs/macosx64",
            "libs/windows32",
            "libs/windows64"
        )
    }
}

dependencies {
    //implementation("com.badlogicgames.gdx:gdx-jnigen:1.9.10")
}

val deleteJniFolder by tasks.registering(Delete::class) {
    delete("jni")
    delete("docs")
}

tasks["clean"].dependsOn(deleteJniFolder)

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = "dev.isxander"
            artifactId = "sdl2-jni"
        }
    }
}
