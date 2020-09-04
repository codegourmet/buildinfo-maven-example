# buildinfo-maven-example

This is an example maven project for rendering a build info/version file when compiling.

This document and example code repository describes how to render a properties file containing build timestamp and more information using maven.

## Howto

### Template file

The `buildinfo.properties` file template is placed in the resources directory
and can contain any [Maven property](http://maven.apache.org/ref/3.6.3/maven-model-builder/#Model_Interpolation).

Example `src/main/resources/buildinfo.properties`:

    version=${project.version}
    date=${maven.build.timestamp}

### Filtering

Use `maven-resources-plugin` to filter the file and replace it with interpolated variables.

Example `pom.xml`:

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>${maven-resources-plugin.version}</version>
            </plugin>

            ...

        <plugins>

        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <includes>
                    <include>**/buildinfo.properties</include>
                </includes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>false</filtering>
                <excludes>
                    <exclude>**/buildinfo.properties</exclude>
                </excludes>
            </resource>
        </resources>
    <build>

The filtered file will be included in the compile result, instead of the template:

`target/classes/buildinfo.properties`:

    version=0.0.1-SNAPSHOT
    date=2020-09-04T08:11:02Z

NOTE: if `maven.build.timestamp` is not replaced with a timestamp, your maven-resources-plugin version might be outdated.

From there (either at runtime if packaged into a jar, or at test stage) it can be read like any resource. See example code below or in this repository's `Main.java`.

### Packaging (optional)

The above will provide build information inside the packaged jar, primarily to be read by the application at runtime.

To have build information readily available in your distribution tarball (for example to inspect it via `cat buildinfo.properties` on the server), include it via `src/assembly/distribution.xml`:

    <?xml version="1.0" encoding="UTF-8"?>
    <assembly
        xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2 http://maven.apache.org/xsd/assembly-1.1.2.xsd">
        <id>distribution</id>
        <formats>
            <format>tar.gz</format>
        </formats>
        <includeBaseDirectory>false</includeBaseDirectory>
        <files>
            <file>
                <source>${project.build.directory}/classes/buildinfo.properties</source>
                <outputDirectory>.</outputDirectory>
            </file>
        </files>

        ...

    </assembly>

This definition will store the `buildinfo.properties` file at toplevel in the distributable tarball.

### Results

After these steps, the buildinfo.properties file can be found at the following locations:

- `target/classes/buildinfo.properties`
- `buildinfo-maven-example-0.0.1-SNAPSHOT.jar/buildinfo.properties`
- `buildinfo-maven-example-0.0.1-SNAPSHOT-distribution.tar.gz/buildinfo.properties`


### Accessing the properties at runtime

The file can be loaded like any resource, see example code in `Main.java`:

        Properties buildInfo = new Properties();

        try (InputStream inputStream = Main.class.getResourceAsStream("/buildinfo.properties")) {
            buildInfo.load(inputStream);
        }

        System.out.println("build date: " + buildInfo.getProperty("date"));
        System.out.println("build version: " + buildInfo.getProperty("version"));

### Relevant dependencies:

- [maven-resources-plugin](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-resources-plugin) for interpolation
- [maven-assembly-plugin](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-assembly-plugin) for creating a tarball containing the jar and the properties file

## Example code

This repository contains a fully functional example demonstrating rendering of a buildinfo file via maven.

### Build instructions

    mvn clean package

=> will render the buildinfo.properties file

### Run instructions

    java -jar target/buildinfo-maven-example-0.0.1-SNAPSHOT.jar

=> will output properties from buildinfo.properties

## Further reading

Any maven property can be used for interpolation. For an overview, start here:

http://maven.apache.org/ref/3.6.3/maven-model-builder/#Model_Interpolation

There are also plugins available that will expose the current git commit hash as a maven property.
