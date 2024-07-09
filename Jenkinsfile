pipeline {
    agent {
        label 'wsl' // Runs on a node with the label 'wsl'
    }
    options {
        skipStagesAfterUnstable()
    }
    environment { 
        IMAG_TAG="nodemain:v1"
        MAIN_NAME="nodemain"
        DEV_NAME="nodedev"

    }
    stages {
        stage('Build') {
            steps {
                //sh 'npm install'
                echo "Building"
            }
        }
        stage('Test') {
            steps {
                //sh 'npm test'
                echo "Testing"
            }
        }
        stage('Build-image') {
            /*when{
                expression{
                    env.BRANCH_NAME = "main"
                }
            }*/
            steps{
                echo "${env.BRANCH_NAME}"
                //sh "docker build -t $IMAG_TAG -f Dockerfile ."
            }
        }
        stage('deploy-main') {
            when{
                branch 'main'
            }
            steps{
                echo "Hello from main"
                //sh "(docker rm -f $MAIN_NAME || true ) && docker run --name $MAIN_NAME -dp 3000:3000 $IMAG_TAG"
            }
        }
        stage('deploy-dev') {
            when{
                branch 'dev'
            }
            steps{
                echo "Hello from dev"
                //sh "(docker rm -f $DEV_NAME || true ) && docker run --name $DEV_NAME -dp 3001:3001 $IMAG_TAG"
            }
        }
    }
}
