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
include(":feature:login")
include(":feature:search")
include(":feature:vacancy")
include(":data:vacancies")
include(":feature:favorite")
include(":feature:response")
include(":feature:message")
include(":feature:profile")
