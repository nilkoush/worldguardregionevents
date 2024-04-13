plugins {
    id("java")
    id("maven-publish")
}

val majorVersion = 1
val minorVersion = 0
val patchVersion = 0

group = "dev.nilkoush"
version = "$majorVersion.$minorVersion.$patchVersion"
description = "Adds more events for world guard regions."

ext {
    set("name", "WorldGuardRegionEvents")
    set("main", "$group.worldguardregionevents.WorldGuardRegionEventsPlugin")
    set("version", version)
    set("description", description)
    set("author", "nilkoush")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            repositories {
                maven {
                    val releasesRepoUrl = "https://repo.nilkoush.dev/releases/"
                    val snapshotsRepoUrl = "https://repo.nilkoush.dev/snapshots/"
                    url = uri(if ((project.version as String).endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
                    credentials {
                        username =
                            if (project.hasProperty("repoUsername")) project.property("repoUsername").toString() else System.getenv("REPO_USERNAME")
                        password =
                            if (project.hasProperty("repoPassword")) project.property("repoPassword").toString() else System.getenv("REPO_PASSWORD")
                    }
                }
            }

            groupId = group.toString()
            artifactId = project.name
            version = version
            from(components["java"])

            pom {
                name.set("WorldGuardRegionEvents")
                url.set("https://theproject.nilkoush.dev/")
                developers {
                    developer {
                        id.set("nilkoush")
                        name.set("nilkoush")
                        email.set("hello@nilkoush.dev")
                        timezone.set("Europe/Prague")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/nilkoush/worldguardregionevents.git")
                    developerConnection.set("scm:git:git@github.com:nilkoush/worldguardregionevents.git")
                    url.set("https://github.com/nilkoush/worldguardregionevents")
                }
            }
        }
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.8")

    compileOnly("org.checkerframework:checker-qual:3.8.0")
    compileOnly("org.jetbrains:annotations:20.1.0")
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filteringCharset = "UTF-8"
        from(sourceSets.main.get().resources.srcDirs) {
            filter(org.apache.tools.ant.filters.ReplaceTokens::class, "tokens" to mapOf(
                    "name" to project.ext.get("name"),
                    "main" to project.ext.get("main"),
                    "version" to project.ext.get("version"),
                    "description" to project.ext.get("description"),
                    "author" to project.ext.get("author")
            ))
        }
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withSourcesJar()
    withJavadocJar()
}