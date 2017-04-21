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
