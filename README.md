# The Cake Solutions Play Scala Giter8 Template.

This [giter8](https://github.com/foundweekends/giter8) template is designed to be an opinionated template consisting of
the boilerplate required when creating a new project or service at Cake or any of our clients. It aims to make creating
a new project as painless as possible in order to allow developers to spend their time focusing on solving problems.

We believe that we should try and have uniformity as much as we can across our applications and believe that this is the
place where we have the conversation as to what should and should not be standardised on. Once we come to an agreement
it should be added to the template.

## Using the Template

To use, ensure that you have SBT 0.13.13 or higher installed.

Then:

```
sbt new cakesolutions/play-scala-template.g8
```

You will be asked to fill out some properties required for correctly building your project, once complete `giter8` will
create an SBT project layout with our opinionated layout and configuration.  You can now move to your new project it
will have all of the lovely features outlined above.  This should free you up to solve the really tough problems in the
project.

Following the creation of a project, you will need to configure the following additional items (they are all identified
by ```FIXME```s):
* ```sbt.resolvers``` - this file should ideally be pinned to use a project specific Nexus or Artifactory for caching
  and managing project library dependencies. **All** resolver settings should be placed in this file.
* ```build.sbt``` - this file needs the following autoplugin setting keys to be configured:
  * ```snapshotRepositoryResolver``` - this URL should point to the Nexus or Artifactory snapshot repository
  * ```repositoryResolver``` - this URL should point to the Nexus or Artifactory publishing repository
  * ```issueManagementUrl``` - this URL should point to the Jira project management API
  * ```issueManagementProject``` - this string should name the Jira project against which the project code is managed.
* ```project/ProjectPlugin.scala``` - this file needs the following autoplugin setting keys to be configured:
  * ```startYear``` - this integer list should be set to the yers that copyrights will be enforced in and from
  * ```licenses``` - this should be altered to the particular commercial or open source license that a project will use
  * ```headerLicense``` - this should be altered to the particular commercial or open source license comment header that
    will be added to each file.

## Contributing to the Template

This template will be an evolving opinion, PRs are welcome.

To test the output of the template you can run it locally with the following command

```
sbt new file://./play-scala-template.g8
```

# Releasing of the `play-scala-template.g8` Template

In order to release version `X.Y.Z` of the `play-scala-template.g8` template, perform the following actions:
```text
git tag -s vX.Y.Z
git push --tags
```

Releasing of templates is simply a matter of adding tags to the github repository.
