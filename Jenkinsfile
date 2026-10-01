#!/usr/bin/env groovy

@Library('my-shared-library') _

pipeline {
    agent any 
    stages {
        stage('Bump Version') {
            steps {
                script {
                    // Passes the 'app' directory parameter
                    def version = bumpVersion('app')
                    env.IMAGE_VERSION = "${version}-${BUILD_NUMBER}"
                }
            }
        }

        stage('Run Tests') {
            steps {
                runTests('app')
            }
        }

        stage('Build and Push Docker Image'){
            steps {
                // Passes the specific image tagging parameter
                buildImage("aliwaqarbulc/node-docker-jenkins-shared-lib:${IMAGE_VERSION}")
            }
        }

        stage('Commit Version Update'){
            steps {
                // Passes the specific repository URL parameter
                commitVersionUpdate("ali-waqar22/node-docker-jenkins-shared-lib.git")
            }
        }
    }
}