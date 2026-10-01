#!/usr/bin/env groovy

def call(String imageName) {
    withCredentials([usernamePassword(credentialsId: 'Dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASS')]) {
        sh "docker build -t ${imageName} ."
        sh "echo \${PASS} | docker login -u \${USERNAME} --password-stdin"
        sh "docker push ${imageName}"
    }
}