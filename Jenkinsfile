pipeline {
  agent {
    label 'sbt-slave'
  }

  environment {
    // Ensure that build scripts recognise the environment they are running within
    CI = 'jenkins'
  }

  stages {
    stage('Generate template') {
      steps {
        ansiColor('xterm') {
          script {
            sh "mkdir template.g8; cp -fr src template.g8/"
            sh "sbt new file://./template.g8 --name=playrepo --project_description=ci-test --organisation_domain=test_net --organisation=test_cakesolutions"
            def checkDirectory = sh(returnStdout: true, script: "if [ -d ./playrepo/play/src/main/scala/test_net/test_cakesolutions/playrepo ]; then echo 'OK'; else echo 'NOK'; fi").trim()
            if (checkDirectory == 'NOK') error("Template parameters can not be applied correctly!")
          }
        }
      }
    }

    stage('Environment') {
      steps {
        ansiColor('xterm') {
          dir("playrepo") {
            script {
              sh "sbt checkExternalBuildTools"
              sh "sbt dockerComposeDown"
              sh "docker images"
              sh "docker ps -a"
            }
          }
        }
      }
    }

    stage('Compile') {
      steps {
        ansiColor('xterm') {
          dir("playrepo") {
            script {
              sh "sbt clean compile test:compile it:compile doc"
            }
          }
        }
      }
    }

    stage('Verification') {
      steps {
        ansiColor('xterm') {
          dir("playrepo") {
            script {
              // Since copyright headers are not set up for test projects, we omit headerCheck, test:headerCheck and it:headerCheck here
              sh "sbt scalastyle test:scalastyle it:scalastyle sbt:scalafmt::test scalafmt::test test:scalafmt::test it:scalafmt::test"
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
              // We intentionally avoid any coverage based checks
              sh "sbt test"
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
              // In CI environments, we use the eth0 or local-ipv4 address of the slave
              // instead of localhost
              try {
                sh "sbt dockerComposeUp"
                def dockerip = sh(returnStdout: true, script:  $/wget http://169.254.169.254/latest/meta-data/local-ipv4 -qO-/$).trim()
                withEnv(["CI_HOST=$dockerip"]) {
                  sh "sbt it:test"
                }
              } finally {
                sh "sbt dockerComposeDown"
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
              // In CI environments, we use the eth0 or local-ipv4 address of the slave
              // instead of localhost
              try {
                sh "sbt dockerComposeUp"
                def dockerip = sh(returnStdout: true, script:  $/wget http://169.254.169.254/latest/meta-data/local-ipv4 -qO-/$).trim()
                withEnv(["CI_HOST=$dockerip"]) {
                  sh "sbt gatling:test"
                }
              } finally {
                sh "sbt dockerComposeDown"
              }
            }
          }
        }
      }
    }

    // We intentionally perform no publish step
  }
}
