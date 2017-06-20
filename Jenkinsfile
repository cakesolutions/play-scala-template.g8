pipeline {

  environment {
    CI = 'true'
    AWS_DEFAULT_REGION = 'eu-west-2'
  }

  agent {
    label 'sbt-slave'
  }

  stages {

    stage('Generate template') {
      steps {
        ansiColor('xterm') {
          script {
            sh "mkdir template.g8; mv src template.g8/"
            sh "sbt new file://./template.g8 --name=playrepo --project_description=ci-test --organisation_domain=net --organisation=cakesolutions"
          }
        }
      }
    }

    stage('Compile') {
      steps {
        ansiColor('xterm') {
          dir("playrepo") {
            script {
              sh "sbt clean compile doc"
            }
          }
        }
      }
    }

    stage('Test') {
      steps {
        ansiColor('xterm') {
          dir("playrepo") {
            script {
              sh "sbt coverage test coverageReport"
              junit '**/test-reports/*.xml'
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
                sh "sbt coverageOff dockerComposeUp"
                def dockerip = sh(returnStdout: true, script:  $/wget http://169.254.169.254/latest/meta-data/local-ipv4 -qO-/$).trim()
                withEnv(["APP_HOST=$dockerip"]) {
                  sh "sbt it:test"
                  junit '**/test-reports/*.xml'
                }
              } finally {
                sh "sbt dockerComposeDown dockerRemove"
              }
            }
          }
        }
      }
    }
  }
}
