pipeline {
    agent any
    stages {
        stage('clean compile') {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage('test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('install') {
            steps {
                bat 'mvn install'
            }
        }
    }
}