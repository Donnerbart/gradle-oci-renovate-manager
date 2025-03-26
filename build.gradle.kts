plugins {
    java
    id("io.github.sgtsilvio.gradle.oci") version "0.22.0"
}

group = "de.donnerbart"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

oci {
    registries {
        dockerHub {
            optionalCredentials()
        }
    }

    imageDefinitions {
        register("main") {
            imageTag = provider { project.version.toString().lowercase() }
            allPlatforms {
                dependencies {
                    runtime("library:alpine:sha256!56fa17d2a7e7f168a043a2712e63aed1f8543aeafdcee47c58dcffe38ed51099") // 3.21.2
                }
                config {
                    configAnnotations = mapOf(
                            "org.opencontainers.image.vendor" to "donnerbart",
                            "org.opencontainers.image.source" to "gradle-oci-renovate-manager"
                    )
                }
            }
            specificPlatform(platform("linux", "amd64"))
            specificPlatform(platform("linux", "arm64", "v8"))
        }
    }
}
