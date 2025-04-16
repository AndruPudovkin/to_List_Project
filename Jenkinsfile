pipeline {
    agent any
    tools {
        gradle 'gradle 7.6'
        jdk 'Java 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Deploy') {
            steps {
                sh 'ls -l ./test.txt'  // Проверка наличия тестового файла
                sshPublisher(publishers: [
                        sshPublisherDesc(
                                configName: 'toListService',
                                verbose: true,
                                transfers: [
                                        sshTransfer(
                                                sourceFiles: 'build/libs/toListService-0.0.1-SNAPSHOT.jar',
                                                remoteDirectory: 'exchange'
                                        )
                                ]
                        )
                ])
            }
        }
    }

}