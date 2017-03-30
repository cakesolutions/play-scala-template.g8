# Local Development

## Scalafmt
We use [`scalafmt`](https://olafurpg.github.io/scalafmt) to format our scala source code.

The `sbt-scalafmt` sbt plugin is introduced into the project as a small wrapper around `scalafmt` CLI, we can run
`sbt scalafmt` to format the code, or `sbt scalafmtTest` to test for mis-formatted code.

A few things to note about `sbt-scalafmt`:
 * The plugin will automatically pick up your Configuration in `.scalafmt.conf`
 * The configuration must be defined in `.scalafmt.conf` in the root directory of your project
 * The sbt plugin does not provide reformat on compile settings
 * **As of this writing (`sbt-scalafmt v0.5.6`), if `sbt-coursier` is being used, make sure it is on version `1.0.0-M15-1`**

## Setup IDE (Intellij)

### Scalafmt
Install `scalafmt` plugin, it will read `.scalafmt.conf` automatically

It could be set to triggered on save in `Preferences > Tools > scalafmt`

### Ensure end of line at end of file
Set `Preferences > Editor > General > Others > Ensure line feed at file end on save` to true
