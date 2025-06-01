plugins {
    id("java")
    java
    application
}
group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    implementation("org.springframework.boot:spring-boot-starter")
//    compileOnly("org.projectlombok:lombok")
//    annotationProcessor("org.projectlombok:lombok")
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

application {
    mainClass.set("com.example.ApiGateway")
}

tasks.test {
    useJUnitPlatform()
}