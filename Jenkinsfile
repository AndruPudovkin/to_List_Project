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
                // Копируем файл на удалённый сервер
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

                // Выполняем команды на удалённом сервере
                sshCommand remote: [
                        name: 'toListService'
                ], command: '''
            echo "Копируем файл в /srv/toList/"
            sudo cp /home/pudow/exchange/toListService-0.0.1-SNAPSHOT.jar /srv/toList/toListService-0.0.1-SNAPSHOT.jar

            echo "Ищем и убиваем старый процесс..."
            PID=$(ps aux | grep toListService-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}')
            if [ ! -z "$PID" ]; then
                echo "Останавливаем процесс PID=$PID"
                sudo kill -9 $PID
            else
                echo "Процесс не найден, ничего не останавливаем"
            fi

            echo "Запускаем приложение..."
            cd /srv/toList
            nohup java -jar toListService-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
            echo "Приложение запущено"
        '''
            }
        }
    }

}