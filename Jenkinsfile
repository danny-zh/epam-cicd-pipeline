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
                echo "building"
            }
        }
        stage('Test') {
            steps {
                sh 'npm test'
            }
        }
        stage('Build-image') {
            when{
                expression{
                    env.BRANCH_NAME == 'main'
                }
            }
            steps{
                sh "docker build -t $IMAG_TAG -f Dockerfile ."
            }
        }
        stage('deploy-main') {
            when{
                branch 'main'
            }
            steps{
                sh "(docker rm -f $MAIN_NAME || true ) && docker run --name $MAIN_NAME -dp 3000:3000 $IMAG_TAG"
            }
        }
        stage('deploy-dev') {
            when{
                branch 'dev'
            }
            steps{
                sh "(docker rm -f $DEV_NAME || true ) && docker run --name $DEV_NAME -dp 3001:3001 $IMAG_TAG"
            }
        }
    }
}
