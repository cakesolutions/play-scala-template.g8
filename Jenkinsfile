pipeline {
  agent {
    label 'sbt-slave'
  }
  stages {
    stage('Test') {
      steps {
        ansiColor('xterm') {
          script {
            sh "mkdir template.g8; mv src template.g8/"
            sh "sbt new file://./template.g8 --name=playrepo --project_description=ci-test --organisation_domain=net --organisation=cakesolutions"
          }
          dir("playrepo") {
            script {
              try {
                sh "sbt app/dockerComposeUp"
                sh "sbt app/test app/it:test"
              } finally {
                sh "sbt app/dockerComposeDown"
              }
            }
          }
        }
      }
    }
  }
}
