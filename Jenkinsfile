pipeline {
    agent any

    tools {
        maven 'Maven 3.9.9'
        jdk 'Java 21'
    }

    environment {
        JAVA_HOME = "${tool 'Java 21'}"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Build') {
            steps {
                // echo 'Compilando proyecto...'
                //sh 'mvn clean install'
            }
        }

        stage('Tests') {
            steps {
                echo 'Ejecutando tests...'
                sh 'mvn test'
            }
        }

        stage('Empaquetar') {
            steps {
                echo 'Empaquetando...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Análisis de Código') {
            steps {
                echo 'Analizando código con SonarQube...'
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://172.18.0.6:9000 -Dsonar.login=admin -Dsonar.password=admin'
                }
            }
        }

        stage('Publicar en Nexus') {
            steps {
                echo 'Publicando artefactos en Nexus...'
                withCredentials([usernamePassword(credentialsId: 'nexus-admin', 
                                                 usernameVariable: 'NEXUS_USER', 
                                                 passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh '''
                        mvn deploy -DskipTests \
                        -DaltDeploymentRepository=nexus::default::http://172.18.0.3:8081/repository/maven-releases/ \
                        -DrepositoryId=nexus \
                        -Durl=http://172.18.0.3:8081/repository/maven-releases/ \
                        -Dusername=${NEXUS_USER} \
                        -Dpassword=${NEXUS_PASSWORD}
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Desplegar en Desarrollo') {
            when {
                branch 'develop'
            }
            steps {
                echo 'Desplegando en ambiente de desarrollo...'
            }
        }

        stage('Desplegar en Producción') {
            when {
                branch 'main'
            }
            steps {
                echo 'Desplegando en producción...'
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado con éxito!'
        }
        failure {
            echo 'El pipeline ha fallado!'
        }
    }
}
