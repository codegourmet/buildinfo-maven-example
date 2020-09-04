# buildinfo-maven-example

This is an example maven project for rendering a build info/version file when compiling.

An example Main class will read the properties file from the jar and output its contents.

The resulting buildinfo.properties file is also copied into a distribution archive, for manual access.

## Build instructions

    mvn clean package

## Run instructions

    java -jar target/buildinfo-maven-example-0.0.1-SNAPSHOT.jar

## Relevant dependencies:

- maven-resources-plugin for interpolation
- maven-assembly-plugin for creating a tarball containing the jar and the properties file
