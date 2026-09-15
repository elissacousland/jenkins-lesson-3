pipeline {
    agent none

    stages {
        stage('Go Test') {
            agent {
                docker {
                    image 'golang:1.24-alpine'
                }
            }

            steps {
                echo '=== Running in Go Container ==='
                sh 'go version'
            }
        }

        stage('Java Test') {
            agent {
                docker {
                    image 'eclipse-temurin:21-jdk-alpine'
                }
            }

            steps {
                echo '=== Running in Java Container ==='
                sh 'java -version'
                sh 'javac -version'
            }
        }
    }
}
