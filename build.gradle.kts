plugins {
    kotlin("jvm") version "1.5.21" apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri(project.findProperty("gpr_url_java_libs") as String)
            credentials {
                username = project.findProperty("gpr_user") as String
                password = project.findProperty("gpr_public_key") as String
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri(project.findProperty("gpr_url_kotlin_libs") as String)
            credentials {
                username = project.findProperty("gpr_user") as String
                password = project.findProperty("gpr_public_key") as String
            }
        }
    }
}