pipeline {
  agent {
    label 'sbt-slave'
  }
  stages {
    stage('Deploy') {
      steps {
        ansiColor('xterm') {
          script {
            sh "mkdir template.g8; mv src template.g8/"
            sh "sbt new file://./template.g8 --name=playrepo --project_description=ci-test --organisation_domain=net --organisation=cakesolutions"
          }
        }
      }
    }
    stage('Test') {
      steps {
        ansiColor('xterm') {
          dir("playrepo") {
            script {
              sh "sbt scalafmt::test app/test"
            }
          }
        }
      }
    }
    stage('IntegrationTest') {
      steps {
        ansiColor('xterm') {
          dir("playrepo") {
            script {
              try {
                sh "sbt app/dockerComposeUp"
                def dockerip = sh(returnStdout: true, script:  $/wget http://169.254.169.254/latest/meta-data/local-ipv4 -qO-/$).trim()
                withEnv(["APP_HOST=$dockerip"]) {
                  sh "sbt app/it:test"
                }
              } finally {
                sh "sbt app/dockerComposeDown app/dockerRemove"
              }
            }
          }
        }
      }
    }
  }
}
