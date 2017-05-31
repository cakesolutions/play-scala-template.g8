# $name$

## $project_description$

***

Before writing any code in this repository be sure to read the documentation in the [docs](./doc) directory, this provides guidelines and how to configure the local development environment and how to contribute to the appliction.

Guides to developing code on this project can be found here:

[Contributions](./doc/contribution.md)

[Local Development](./doc/localDevelopment.md)

## API Documenation

The Api's are documented with swagger and are served on at [`/docs/api`](htpp://localhost:9000/docs/api/).

Swagger docs are generated from reading the annotations in the controllers and it these should be kept up to date during support and enhancement of the application.

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
