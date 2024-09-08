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
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TestovoeZadanie"
include(":app")
include(":presentation:login")
include(":presentation:search")
include(":presentation:vacancy")
include(":presentation:favorite")
include(":presentation:response")
include(":presentation:message")
include(":presentation:profile")
include(":domain")
include(":data")
