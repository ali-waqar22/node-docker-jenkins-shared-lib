#!/usr/bin/env groovy

pipeline {
    agent any 
    stages {
        stage('Bump Version') {
            steps{
                script {
                    dir('app') {
                        echo 'incrementing patch version...'

                        sh 'npm version patch'
                    
                        def packageJson = readJSON file: 'package.json'
                        def version = packageJson.version

                        env.IMAGE_VERSION = "$version-$BUILD_NUMBER"
                    }
                }
            }
        }

        stage('Run Tests') {
            steps{
                script{
                    dir('app') {
                        echo 'running tests...'
                        sh 'npm install'
                        sh 'npm run test'
                    }
                }
            }
        }

        stage('Build and Push Docker Image'){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', 
                    usernameVariable: 'USERNAME', passwordVariable: 'PASS')]) {
                        sh "docker build -t aliwaqarbulc/node-docker-jenkins-shared-lib:${IMAGE_VERSION} ."
                        sh "echo \${PASS} | docker login -u \${USERNAME} --password-stdin"
                        sh "docker push aliwaqarbulc/node-docker-jenkins-shared-lib:${IMAGE_VERSION}"
                    }
                }
            }
        }

        stage('Commit Version Update'){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: 'github', usernameVariable: 'USERNAME', passwordVariable: 'PASS')]) {
                        sh "git config user.email 'jenkins@example.com'"
                        sh "git config user.name 'jenkins'"
                        sh "git remote set-url origin https://${USERNAME}:${PASS}@github.com/ali-waqar22/node-docker-jenkins-shared-lib.git"
                        sh "git add package.json"
                        sh "git commit -m 'ci: version bump'"
                        sh "git push origin HEAD:main"
                    }
                }
            }
        }
    }
}