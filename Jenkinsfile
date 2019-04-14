pipeline {
  agent {
    docker {
      args '-v gradle-cache:/home/gradle/.gradle'
      image 'gradle:4.10.3-jdk8-alpine'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'gradle build'
      }
    }
    stage('Archive') {
      steps {
        archiveArtifacts 'build/libs/*.jar'
      }
    }
  }
}
