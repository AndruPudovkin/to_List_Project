pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                echo 'Cloning repository...'
                checkout scm
            }
        }
        stage('Build') {
            steps {
                echo 'Building the project...'
                // например, запуск скрипта
                sh 'echo Hello from Jenkins!'
            }
        }
    }
}