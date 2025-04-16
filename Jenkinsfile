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
                sshPublisher(
                        publishers: [
                                sshPublisherDesc(
                                        configName: 'toListService',
                                        verbose: true,
                                        transfers: [
                                                sshTransfer(
                                                        sourceFiles: './test.txt',
                                                        remoteDirectory: 'exchange'
                                                )
                                        ],
                                        execCommand: '''
                    echo "Тестовая команда"
                    ''',
                                        execTimeout: 120000
                                )
                        ]
                )
            }
        }
    }

}