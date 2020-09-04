# buildinfo-maven-example

This is an example maven project for rendering a build info/version file when compiling.

For a complete explanation see http://blog.codegourmet.de
An example Main class will read the properties file from the jar and output its contents.

The resulting buildinfo file will be rendered into:

    - `target/classes/buildinfo.properties`
    - `buildinfo-maven-example-0.0.1-SNAPSHOT.jar/buildinfo.properties`
    - `buildinfo-maven-example-0.0.1-SNAPSHOT-distribution.tar.gz/buildinfo.properties`

## Build instructions

    mvn clean package

## Run instructions

    java -jar target/buildinfo-maven-example-0.0.1-SNAPSHOT.jar

    => will output properties from buildinfo.properties

## Relevant dependencies:

- maven-resources-plugin for interpolation
- maven-assembly-plugin for creating a tarball containing the jar and the properties file
