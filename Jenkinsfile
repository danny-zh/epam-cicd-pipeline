pipeline {
    agent {
        label 'wsl' // Runs on a node with the label 'wsl'
    }
    options {
        skipStagesAfterUnstable()
    }
    environment { 
        MAIN_IMAG_TAG="nodemain:v1.0"
        DEV_IMAG_TAG="nodedev:v1.0"
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
            steps{               
                script {
                    if (env.BRANCH_NAME == 'main') {
                        sh "docker build -t $MAIN_IMAG_TAG -f Dockerfile ."
                    } else if (env.BRANCH_NAME == 'dev') {
                        sh "docker build -t $DEV_IMAG_TAG -f Dockerfile ."
                    } else {
                        error 'Unknown build environment: ${BUILD_ENV}'
                    }
                }
            }
        }
        stage('deploy-main') {
            steps{
                script {
                    if (env.BRANCH_NAME == 'main') {
                        sh "(docker rm -f $MAIN_NAME || true ) && docker run --name $MAIN_NAME -dp 3000:3000 $MAIN_IMAG_TAG"
                    } else if (env.BRANCH_NAME == 'dev') {
                        sh "(docker rm -f $DEV_NAME || true ) && docker run --name $DEV_NAME -dp 3000:3000 $DEV_IMAG_TAG"
                    } else {
                        error 'Unknown build environment: ${BUILD_ENV}'
                    }
                }
            }
        }
    }
}
