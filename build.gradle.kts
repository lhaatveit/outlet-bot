import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("com.google.cloud.tools.jib") version "3.2.1"
}

group = "no.haatveit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation (group = "com.fasterxml.jackson.core", name = "jackson-core", version = "2.13.1")
    implementation (group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.13.1")
    implementation("io.projectreactor:reactor-core:3.4.16")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    implementation("org.whispersystems:signal-protocol-java:2.8.1")
    implementation("org.slf4j:log4j-over-slf4j:1.7.36")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.2")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("io.projectreactor:reactor-test:3.4.16")
}

tasks.test {
    useJUnitPlatform()
}

jib {
    from {
        platforms {
            platform {
                architecture = "amd64"
                os = "linux"
            }
            platform {
                architecture = "arm"
                os = "linux"
            }
        }
    }
    to {
        image = "ghcr.io/lhaatveit/bolia-com-outlet-bot:latest"
        auth.password = System.getenv("JIB_AUTH_PASSWORD")
        auth.username = System.getenv("JIB_AUTH_USERNAME")
    }
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    languageVersion = "1.4"
}
