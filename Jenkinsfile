pipeline {
  options {
    buildDiscarder(logRotator(artifactNumToKeepStr: '10'))
  }
  agent {
    docker {
      args '-v gradle-cache:/home/gradle/.gradle'
      image 'gradle:4.9-jdk8-alpine'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'gradle build --no-daemon'
      }
    }
    stage('Archive') {
      steps {
        archiveArtifacts 'build/libs/*.jar'
      }
    }
  }
}
