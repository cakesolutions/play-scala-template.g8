# The Cake Solutions Play Scala Giter8 Template.

This [giter8](https://github.com/foundweekends/giter8) template is designed to be an opinionated template consisting of the boilerplate required when creating a new project or service at Cake or any of our clients. It aims to make creating a new project as painless as possible in order to allow developers to spend their time focusing on solving problems.

We believe that we should try and have uniformity as much as we can across our applications and believe that this is the place where we have the conversation as to what should and should not be standardised on. Once we come to an agreement it should be added to the template.


## Using the Template

To use, ensure you have SBT 0.13.13 or higher, or g8

Then:

```
sbt new cakesolutions/play-scala-template.g8
```

You will be asked to fill out some properties required for correctly building your project, once complete `giter8` will create an SBT project layout with our opinionated layout and configuration.  You can now move to your new project it will have all of the lovely features outlined above.  This should free you up to solve the really tough problems in the project.


## Contributing to the Template

This template will be an evolving opinion, PRs are welcome.

To test the output of the template you can run it locally with the following command

```
g8 file://./play-scala-template.g8 --force
```

Or use 
```
sbt new file://./play-scala-template.g8
```