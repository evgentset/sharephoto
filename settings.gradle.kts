pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "SharePhoto test"

include(":app")
include(":core-domain")
include(":core-data")
include(":core-database")
include(":core-testing")
include(":core-ui")
include(":feature-photolist:ui")
include(":feature-photolist:api")
include(":test-app")
