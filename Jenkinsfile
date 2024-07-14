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
                echo "Testing from branch $env.BRANCH_NAME"
                sh 'npm test'
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
        stage('Docker Push'){
            steps{
                if (env.BRANCH_NAME == 'main') {
                        echo "Pushing docker image $MAIN_IMAG_TAG"   
                        sh "docker push $MAIN_IMAG_TAG"
                } else if (env.BRANCH_NAME == 'dev') {
                    echo "Pushing docker image $DEV_IMAG_TAG"
                    sh "docker push $DEV_IMAG_TAG" 
                }
            }
        }
        stage('Deploy') {
            steps{
                script{
                    if (env.BRANCH == 'main')
                    {
                        env.CONT_NAME = "Production"
                        env.PORT = '3000'
                    }
                    else if (env.BRANCH == "dev")
                    {
                        env.CONT_NAME = "Development"
                        env.PORT = '3001'
                    }
                    
                    def downstreamJobName = "deploy_to_$env.branch"
                    def downstreamBuild = build job: downstreamJobName, wait: true, parameters: [
                        string(name: 'IMAGE_TAG', value: "$IMAGE_TAG"),
                        string(name: 'NAME', value: "$env.CONT_NAME"),
                        string(name: 'PORT', value: "$env.PORT"),
                        string(name: 'APP_PORT', value: "$APP_PORT")
                    ]
                    
                    echo "Downstream build result: ${downstreamBuild.result}"
                    
                    //sh "(docker rm -f $env.CONT_NAME || true ) && docker run --name $env.CONT_NAME -dp $PORT:$APP_PORT $IMAGE_TAG"
                }
                
            }
        }
        
    }
}
