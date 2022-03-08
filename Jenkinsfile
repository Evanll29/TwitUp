pipeline {
    agent any
    stages {
        stage('clean') {
            steps {
                bat 'mvn clean'
            }
        }
        stage('compile') {
            steps {
                bat 'mvn compile'
            }
        }
        stage('install') {
            steps {
                bat 'mvn install'
            }
        }
        stage('verify') {
            steps {
                bat 'mvn clean verify sonar:sonar -Dsonar.login=cd4fd00e4b78d9b43f2c96d4285278a8de498a91'
            }
        }
    }
}