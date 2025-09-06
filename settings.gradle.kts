pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") } // JitPack repository
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven {
            url = uri("https://jfrog.anythinktech.com/artifactory/overseas_sdk")
        }
        maven {
            url = uri("https://jfrog.anythinktech.com/artifactory/debugger")
        }

    }
}
rootProject.name = "DramaShort"
include(":app")
 