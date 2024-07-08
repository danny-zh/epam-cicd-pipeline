pipeline {
    agent {
        label 'wsl' // Runs on a node with the label 'wsl'
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                echo "hello fro build"
            }
        }
        stage('Test'){
            steps {
                echo "hello fro build"
            }
        }
        stage('Deploy') {
            steps {
                echo "hello fro build"
            }
        }
    }
}
