@Library("JenkinsTestLib") _

pipeline {
    agent {
        label 'wsl' // Runs on a node with the label 'wsl'
    }
    options {
        skipStagesAfterUnstable()
    }
    environment { 

        DOCKER_COMMON_CREDS = credentials('docker_hub_creds')
        APP_PORT="3000"
        
        MAIN_IMAG_TAG="${DOCKER_COMMON_CREDS_USR}/nodemain:v1.0"
        MAIN_NAME="nodemain"
        MAIN_PORT="3000"

        DEV_IMAG_TAG="${DOCKER_COMMON_CREDS_USR}/nodedev:v1.0"
        DEV_NAME="nodedev"
        DEV_PORT="3001"

    }

    stages {
        stage('Build') {
            steps {
                echo "Building from branch $env.BRANCH_NAME"
                sh 'npm install'
            }
        }
        stage('Test') {
            steps {
                TestBuild(branch: "${env.BRANCH_NAME}")
            }
        }
         stage('Scan Dockerfile'){
            steps{
                script{
                    sh 'docker run --rm -i ghcr.io/hadolint/hadolint < Dockerfile'
                }
            }
        }
        stage('Docker Build') {
            steps{
                script {
                    if (env.BRANCH_NAME == 'main') {
                        echo "Building docker image $MAIN_IMAG_TAG"   
                        sh "docker build -t $MAIN_IMAG_TAG -f Dockerfile ."
                    } else if (env.BRANCH_NAME == 'dev') {
                        echo "Building docker image $DEV_IMAG_TAG"   
                        sh "docker build -t $DEV_IMAG_TAG -f Dockerfile ."
                    } else {
                        error 'Unknown build environment: ${BUILD_ENV}'
                    }
                }
            }
        }
        stage('Docker Scan')
        {
            steps{
                script{
                    if (env.BRANCH_NAME == 'main') {
                        echo "Scanning docker image $MAIN_IMAG_TAG"   
                        sh "trivy image $MAIN_IMAG_TAG"
                    } else if (env.BRANCH_NAME == 'dev') {
                        echo "Scanning docker image $DEV_IMAG_TAG"   
                        sh "trivy image $DEV_IMAG_TAG"
                }
            }
        }
        stage('Docker Push') {
            steps{
                sh 'echo $DOCKER_COMMON_CREDS_PSW | docker login -u $DOCKER_COMMON_CREDS_USR --password-stdin'
                script{
                    if (env.BRANCH_NAME == 'main') {
                        echo "Pushing docker image $MAIN_IMAG_TAG"   
                        sh "docker push $MAIN_IMAG_TAG"
                    } else if (env.BRANCH_NAME == 'dev') {
                        echo "Pushing docker image $DEV_IMAG_TAG"
                        sh "docker push $DEV_IMAG_TAG" 
                    }
                }
            }
        }
        stage('Deploy') {
            steps{
                script{
                    if (env.BRANCH_NAME == 'main')
                    {
                        env.IMAGE_TAG = "$MAIN_IMAG_TAG"
                        env.CONT_NAME = "$MAIN_NAME"
                        env.PORT = "$MAIN_PORT"
                    }
                    else if (env.BRANCH_NAME == "dev")
                    {
                        env.IMAGE_TAG = "$DEV_IMAG_TAG"
                        env.CONT_NAME = "$DEV_NAME"
                        env.PORT = "$DEV_PORT"
                    }
                    
                    DeployToMain(BRANCH:"${env.BRANCH_NAME}", IMAGE_TAG:"${env.IMAGE_TAG}", CONT_NAME:"${env.CONT_NAME}", PORT: "${env.PORT}", APP_PORT: "${env.APP_PORT}")
                }
            }
        }
        
    }
}
