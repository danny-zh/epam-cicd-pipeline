pipeline {
    agent {
        label 'wsl' // Runs on a node with the label 'wsl'
    }
    options {
        skipStagesAfterUnstable()
    }
    environment { 
        IMAG_TAG="nodemain:v1"
        CONT_NAME="nodemain"
    }
    stages {
        stage('Build') {
            steps {
                sh 'npm install'
            }
        }
        stage('Test') {
            steps {
                sh 'npm test'
            }
        }
        stage('Build2') {
            /*when{
                expression{
                    env.BRANCH_NAME = "main"
                }
            }*/
            steps{
                sh "docker build -t $IMAG_TAG -f Dockerfile ."
            }
        }
        stage('deploy') {
            steps{
                sh "(docker rm -f $CONT_NAME || true ) && docker run --name $name -dp 3000:3000 $IMAG_TAG"
            }
        }
    }
}
