pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven(url="https://maven.aliyun.com/repository/public")
        maven(url="https://maven.aliyun.com/repository/gradle-plugin")
//        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
//        mavenCentral()
        maven(url="https://maven.aliyun.com/repository/public")
    }
}

rootProject.name = "TVProxy"
include(":app")
 