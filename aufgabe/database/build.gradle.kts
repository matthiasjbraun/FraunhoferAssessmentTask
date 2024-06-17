plugins {
    id("java")
}

group = "org.mb"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.hibernate:hibernate-core:6.0.2.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:2.2.3")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("javax.el:javax.el-api:3.0.0")
    implementation("org.glassfish.expressly:expressly:5.0.0")
}

tasks.test {
    useJUnitPlatform()
}