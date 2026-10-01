#!/usr/bin/env groovy

def call(String repoPath) {
    withCredentials([usernamePassword(credentialsId: 'github', usernameVariable: 'USERNAME', passwordVariable: 'PASS')]) {
        sh "git config user.email 'jenkins@example.com'"
        sh "git config user.name 'jenkins'"
        // The \$ stops Groovy from breaking the URL string before passing it to bash
        sh "git remote set-url origin https://\$USERNAME:\$PASS@github.com/${repoPath}"
        sh "git add app/package.json"
        sh "git commit -m 'ci: version bump'"
        sh "git push origin HEAD:master"
    }
}