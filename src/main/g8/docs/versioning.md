#### Versioning

[Semantic versioning](http://semver.org/) is a very simple scheme built around the ``MAJOR.MINOR.PATCH`` concept, we increment:

* `MAJOR` version when we make incompatible API changes,
* `MINOR` version when we add functionality in a backwards-compatible manner, and
* `PATCH` version when we make backwards-compatible bug fixes.

We also support **pre-release** versions. Please consult the [Semantic versioning documentation](http://semver.org/) for more information.

When using Semantic versioning with GitFlow, SBT and Docker, here's our suggested acceptance criteria.

For development:

* Given the current branch is `develop`
* When a commit is made
* Then the version should always include `-SNAPSHOT` (e.g. `2.6.0-SNAPSHOT`)
* When publishing a docker image
* Then `-SNAPSHOT` should be expanded to `-{DATE}-{TIME}-{ZONE}`

For release candidates:

* Given the current branch is a `release` or `hotfix` branch
* When a commit is made
* Then the version should always include `-rc{NUM}` (e.g. `2.6.0-rc1`)
* When publishing a docker image
* Then `-rc{NUM}` should always be included (e.g. `2.6.0-rc1`)

For releases:

* Given the current branch is `master`
* When a commit is made
* Then the version should always be unqualified  (e.g. `2.6.0`)
* When publishing a docker image
* Then the version should always be unqualified (e.g. `2.6.0`)

For feature development:

* Given the current branch is a `feature` branch
* When a commit is made
* Then the version should always include `-SNAPSHOT`
* And the version may include `.{HASH}` (e.g. `2.6.0-SNAPSHOT.6a91d83`)
* When publishing a docker image
* Then `-SNAPSHOT` should be expanded to `-{DATE}-{TIME}-{ZONE}`
* And the version should include `.{HASH}` if it is provided (e.g. `2.6.0-20080207-230803-1.6a91d83`)

As a rule of thumb:

* Snapshots should only be installed into *Dev* environments. They should not be deployed to *Qa* or *Prod* environments
* Release candidate should be installed in the dedicated *Qa* environments. In an emergency, a release candidate may be installed into a *Prod* environment
* A release can be installed anywhere - and they are the only kind of build that should be installed into the *Prod* environment

## Git tags

The version is managed with git tags and [dynver](https://github.com/dwijnand/sbt-dynver). Dynver uses git tags to compute the full version

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
