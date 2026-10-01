#!/usr/bin/env groovy

def call(String workingDir) {
    dir(workingDir) {
        sh 'npm version patch'
        def packageJson = readJSON file: 'package.json'
        return packageJson.version
    }
}