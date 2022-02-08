pluginManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven { url = uri("https://repo.spring.io/release") }
        maven { url = uri("https://repository.jboss.org/maven2") }
        gradlePluginPortal()
    }
}
rootProject.name = "tasks"
