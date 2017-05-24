pipeline {
  agent {
    label 'sbt-slave'
  }

  stages {
    stage('Test') {
      steps {
        ansiColor('xterm') {
          script {
            currentDir = pwd()
          }
          sh "cd ${currentDir}; mkdir template.g8; mv src template.g8/"
          sh "sbt new file://./template.g8 --name=playrepo --project_description=ci-test --organisation_domain=net --organisation=cakesolutions"
          sh "cd playrepo; sbt test it:test"
        }
      }
    }

  }
}
