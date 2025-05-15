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
                // Копируем JAR-файл на удалённый сервер
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

                // Выполняем команды на удалённом сервере через SSH
                sh '''
                ssh -o StrictHostKeyChecking=no pudow@localhost << 'EOF'
                    set -e

                    echo "Копируем JAR-файл в /toList/"
                    sudo cp /home/pudow/exchange/toListService-0.0.1-SNAPSHOT.jar /toList/toListService-0.0.1-SNAPSHOT.jar
                    
                    echo "Перезапуск приложения через app.sh"
                    cd /toList
                    ./app.sh restart

                    echo "Смотрим статус"
                    ./app.sh status
                EOF
                '''
            }
        }
    }
}