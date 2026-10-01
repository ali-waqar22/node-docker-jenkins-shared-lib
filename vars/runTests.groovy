#!/usr/bin/env groovy

def call(String workingDir) {
    dir(workingDir) {
        sh 'npm install'
        sh 'npm run test'
    }
}