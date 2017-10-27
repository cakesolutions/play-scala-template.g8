# $name$

## $project_description$

Before writing any code in this repository be sure to read the documentation in the [docs](./doc) directory, this
provides guidelines and how to configure the local development environment and how to contribute to the application.

Guides to developing code on this project can be found here:

[Contributions](./doc/contribution.md)

[Local Development](./doc/localDevelopment.md)

## API Documenation

The Api's are documented with swagger and are served on at [`/docs`](http://localhost:9000/docs/).

Swagger docs are generated from reading the annotations in the controllers and it these should be kept up to date during
support and enhancement of the application.

## The build system

See the following info for building the app.

```bash
> sbt compile
> sbt test
```

The commands for running integration tests are as follows:

```bash

> sbt play/dockerComposeUp # start the service which will be tested
> sbt it:test # run the integration tests
> sbt play/dockerComposeDown # clean up the services
```

There is a predefined command alias `validate` that consists of
following commands;

```bash
;reload plugins; sbt:scalafmt::test; scalafmt::test; reload return; sbt:scalafmt::test; scalafmt::test; test:scalafmt::test; it:scalafmt::test; scalastyle; test:scalastyle; it:scalastyle; headerCheck; test:headerCheck; it:headerCheck
```

To generate the headers use the following command: `sbt headerCreate test:headerCreate it:headerCreate`

To format the code use: `sbt sbt:scalafmt scalafmt test:scalafmt it:scalafmt`

Note: The `;reload plugins; sbt:scalafmt::test; scalafmt::test; reload return;` is for formatting
meta-build files.

## Code Coverage

[sbt-scoverage](https://github.com/scoverage/sbt-scoverage) is chosen as the code coverage tool for the project.

To run test with coverage:
```scala
> sbt clean coverage test
> sbt clean coverage it:test   // for integration test
```

To generate the coverage report:
```scala
> sbt coverageReport
```

#### Note:

`coverage` command is sticky in a `sbt` session, use `coverageOff` to turn it off.

## Running the App

To start the Play app locally run the following and open a browser [here](http://localhost:9000)

```bash
> sbt run
```

## Gatling Performance Test

- [Gatling Performance Testing Tool](http://gatling.io/) is chosen as the load testing tool for the project.
- A simple example for testing endpoints are adapted from
  [play-scala-rest-api-example](https://github.com/playframework/play-scala-rest-api-example) repo.
- Performance code is a separate project and depends on the Play project.
- Performance code is packaged into a Docker container, which can be run separate from the Play project.
- You can run both the Play project and the performance tests using docker-compose:
```scala
> sbt perf/dockerComposeUp
```
- More examples can be found at [gatling tutorials](http://gatling.io/docs/current/advanced_tutorial/#advanced-tutorial)
- You can check more scenarios at
  [Gatling simulation](http://gatling.io/docs/2.2.2/general/simulation_structure.html#simulation-structure).
