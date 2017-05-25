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
                def dockerip = sh(returnStdout: true, script:  $/wget http://169.254.169.254/latest/meta-data/local-ipv4 -qO-/$).trim()
                withEnv(["TARGET_HOST=$dockerip"]) {
                  sh "sbt app/test app/it:test"
                }
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
