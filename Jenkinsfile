pipeline {
    agent any

    tools {
        gradle 'gradle 7.6'     // Убедись, что настроено в Jenkins → Global Tool Configuration
        jdk 'Java 17'            // Или другой, если используется
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Deploy') {
            steps {
                echo '📦 Копируем .jar и деплоим на сервер...'

                // Этот блок использует Publish over SSH
                step([
                    $class: 'PublishOverSSH',
                    continueOnError: false,
                    failOnError: true,
                    alwaysPublishFromMaster: true,
                    hostConfigurationAccess: [
                        $class: 'HostConfigurationAccess',
                        accessType: 'PUBLIC',
                        name: 'toListService' // <--- имя сервера из Publish over SSH
                    ],
                    delegate: [
                        $class: 'SSHTransfer',
                        sourceFiles: 'build/libs/*.jar',
                        removePrefix: 'build/libs',
                        remoteDirectory: '/home/pudow/exchange',
                        execCommand: '''
                            echo "🔁 Перезапуск приложения..."
                            pkill -f your-app.jar || true
                            nohup java -jar your-app.jar > app.log 2>&1 &
                        ''',
                        execTimeout: 120000,
                        flatten: true
                    ]
                ])
            }
        }
    }

    post {
        success {
            echo '✅ Успешный билд и деплой!'
        }
        failure {
            echo '❌ Ошибка во время билда или деплоя.'
        }
    }
}