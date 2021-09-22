pipeline {
    agent {
        docker{
            image "maven:3.6.0-jdk-13"
            label "docker"
        }
    }

    stages {
        stage("Clean"){
            steps {
                sh "mvn -version"
                sh "mvn clean"
            }
        }

        stage("test"){
            steps {
                sh "mvn test"
            }
        }

        stage('Build War'){
            steps {
                sh 'mvn package'
                stash includes: 'target/*.war', name: 'targetfiles'
            }
        }
        stage("sonarQube"){
            steps{
                withSonarQubeEnv('sonarqube'){
                //sh "${scannerHome}/bin/sonar-scanner"
                sh 'mvn sonar:sonar'
                }
            }
        }
    }

}