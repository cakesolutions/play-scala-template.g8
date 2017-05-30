## Integration Tests

Create docker images for individual projects with `sbt-native-packager`, `sbt docker:publishLocal`.

We investigated [sbt-docker-compose](https://github.com/Tapad/sbt-docker-compose) to control `docker-compose` for our integration tests, but concluded that it was more than we needed and didn't handle composition of docker-compose files. Therefore, we created our own tasks.

To start the container, needed by our integration tests, make sure to run `app/dockerComposeUp` before :

```
sbt> app/dockerComposeUp
sbt> app/it:test
```

To stop the container:

```
sbt> app/dockerComposeDown
```

You can also run raw `dockerComposeUp` or `dockerComposeDown` to start/stop all the containers.
