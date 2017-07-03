pipeline {

  environment {
    CI = 'true'
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
            def checkDirectory = sh(returnStdout: true, script: "if [ -d ./playrepo/play/src/main/scala/net/cakesolutions/playrepo ]; then echo 'OK'; else echo 'NOK'; fi").trim()
            if (checkDirectory == 'NOK') error("Template parameters can not be applied correctly!")
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
              def dockerip = sh(returnStdout: true, script:  $/wget http://169.254.169.254/latest/meta-data/local-ipv4 -qO-/$).trim()
              withEnv(["APP_HOST=$dockerip"]) {
                sh "sbt integrationTests"
                junit '**/test-reports/*.xml'
              }
            }
          }
        }
      }
    }

    stage('PerformanceTest') {
      steps {
        ansiColor('xterm') {
          dir("playrepo") {
            script {
              def dockerip = sh(returnStdout: true, script:  $/wget http://169.254.169.254/latest/meta-data/local-ipv4 -qO-/$).trim()
              withEnv(["APP_HOST=$dockerip"]) {
                sh "sbt performanceTests"
                junit '**/test-reports/*.xml'
              }
            }
          }
        }
      }
    }
  }
}
