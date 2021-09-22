pipeline {
    agent {
        docker{
            image "maven:3.8.2-jdk-11"
        }
    }

    stages {
        stage("Clean"){
            steps {
                bat "mvn -version"
                bat "mvn clean"
            }
        }

        stage("test"){
            steps {
                bat "mvn test"
            }
        }

        stage('Build War'){
            steps {
                bat 'mvn package'
                stash includes: 'target/*.war', name: 'targetfiles'
            }
        }
        stage("sonarQube"){
            steps{
                withSonarQubeEnv('sonarqube'){
                //sh "${scannerHome}/bin/sonar-scanner"
                bat 'mvn sonar:sonar'
                }
            }
        }
    }

}