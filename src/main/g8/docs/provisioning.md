## Provisioning

Create docker images using sbt native packager by running
`sbt docker:publishLocal`

Run the fleet of services within the working directory of a docker-compose yaml configuration file by using
`docker-compose up`

Or specify a docker-compose yaml file to use for configuration by passing the `-f` flag.

For example, working from the root directory of the project run
`docker-compose -f ./docker/app.yml up`

Bring the fleet down gracefully by passing the down argument instead of up 
`docker-compose -f ./provisioning/app.yml down`		

Or run the application in detached mode by passing the -d flag
`docker-compose -f ./docker/app.yml up -d --remove-orphans`
