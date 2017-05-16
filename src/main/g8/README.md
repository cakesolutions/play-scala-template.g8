# $name$

## $project_description$

***



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

## Integration Tests

Create docker images for individual projects with `sbt-native-packager`, `sbt docker:publishLocal`.

We investigated [sbt-docker-compose](https://github.com/Tapad/sbt-docker-compose) to control `docker-compose` for our integration tests, but concluded that it was more than we needed and didn't handle composition of docker-compose files. Therefore, we created our own tasks.

To start the container, needed by our integration tests, make sure to run `proj/dockerComposeUp` before :

```
sbt> app/dockerComposeUp
sbt> app/it:test
```

To stop the container:

```
sbt> app/dockerComposeDown
```

You can also run raw `dockerComposeUp` or `dockerComposeDown` to start/stop all the containers.

## Versioning

Versioning scheme based on [dynver](https://github.com/dwijnand/sbt-dynver). Uses git tags to compute full version

### Examples

`version in ThisBuild` and `isSnapshot in ThisBuild` will be automatically set to:

```
| Case                                                                 | version                        | isSnapshot |
| -------------------------------------------------------------------- | ------------------------------ | ---------- |
| when on tag v1.0.0, w/o local changes                                | 1.0.0                          | false      |
| when on tag v1.0.0 with local changes                                | 1.0.0-20140707-1030            | true       |
| when on tag v1.0.0 +3 commits, on commit 1234abcd, w/o local changes | 1.0.0-3-1234abcd               | false      |
| when on tag v1.0.0 +3 commits, on commit 1234abcd with local changes | 1.0.0-3-1234abcd-20140707-1030 | true       |
| when there are no tags, on commit 1234abcd, w/o local changes        | 1234abcd                       | true       |
| when there are no tags, on commit 1234abcd with local changes        | 1234abcd-20140707-1030         | true       |
| when there are no commits, or the project isn't a git repo           | HEAD-20140707-1030             | true       |
```

### Tag Requirements

In order to be recognized by sbt-dynver, tags must begin with the lowercase letter 'v' followed by a digit.

If you're not seeing what you expect, then start with this:

    git tag -a v0.0.1 -m "Initial version tag for sbt-dynver"
    git push origin v0.0.1

### The process

* Everything is driven by git tags, thus we need an initial tag as `vX.Y.Z`
* When a PR is merged into master, Jenkins automatically does the build and cuts artifacts (i.e. Docker images)
* Version is calculated as per table above
* If a new version number is required (i.e. `1.1.0`), then a new git tag `v1.1.0` needs to be created against git commit SHA
* Jenkins build can be triggered manually to cut new version
* Deployment to dev happens automatically at the end of Jenkins build, deployment to higher envs (qa, prod) is triggered manually

## Branching and Pull Request Hygiene

We have chosen to follow the [gitflow](http://nvie.com/posts/a-successful-git-branching-model/) workflow (branching strategy and release management).

Feature branches are on the master repo (no forks) and should contain the JIRA ticket number followed by a short title, raising a PR.

The first commit's message's summary should repeat the JIRA Ticket number followed by a short title (we squash merge Pull Requests so this appears as the title of the PR).

Additional information should go into the commit message's description, read [How to Write a Git Commit Message](http://chris.beams.io/posts/git-commit/) for more information.

**Examples:**
```
TASK-105 Build Docker image with sbt
TASK-106 Add health check endpoint
TASK-120 Add scoverage to build
TASK-106 Add Build Info to the `/build-info` endpoint
```