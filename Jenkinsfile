pipeline {
    agent {
        label 'wsl' // Runs on a node with the label 'wsl'
    }
    options {
        skipStagesAfterUnstable()
    }
    environment { 
        tag="nodemain:v1"
        name="nodemain"
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
                sh "docker build -t $tag -f Dockerfile ."
            }
        }
        stage('deploy') {
            steps{
                sh '(docker rm -f $name || true ) && docker run --name $name -dp 3000:3000 $tag'
            }
        }
    }
}
