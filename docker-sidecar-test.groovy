pipeline {
    agent any

    stages {
        stage('Redis Integration Test') {
            steps {
                script {
                    docker.image('redis:alpine').withRun('-p 6379:6379') { c ->

                        echo "Redis sidecar started with ID: ${c.id}"

                        docker.image('redis:alpine').inside('--link ' + c.id + ':redis') {

                            sh 'sleep 2'
                            sh 'redis-cli -h redis ping'
                        }
                    }
                }
            }
        }
    }
}
