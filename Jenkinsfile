pipeline {
    agent any

    environment {
        APP_NAME       = 'keycloak-admin-adapter'
        ENVIRONMENT    = 'diar'
        IMAGE_REPO     = '406327436908.dkr.ecr.eu-north-1.amazonaws.com/agenatech/keycloak-admin-adapter'
        REGION         = 'eu-north-1'
        ACCOUNT        = '406327436908'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout GIT') {
            steps {
                script {
                    def scmVars = checkout([
                      $class: 'GitSCM',
                      branches: scm.branches,
                      doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
                      extensions: scm.extensions + [[$class: 'CleanBeforeCheckout'],[$class: 'CleanCheckout']],
                      userRemoteConfigs: scm.userRemoteConfigs
                    ])
                    currentBuild.displayName = "${env.BUILD_ID}"
                    env.DOCKER_TAG = "${env.BUILD_ID}"
                    env.IMAGE_NAME = "${env.IMAGE_REPO}:${DOCKER_TAG}"
                    env.IMAGE_NAME_LATEST = "${env.IMAGE_REPO}:latest"
                }
            }
        }

        stage('Build artifact') {
            steps {
                sh 'mvn -Dmaven.test.skip=true package'
            }
        }

        stage('Run tests') {
            steps {
                 sh 'mvn test'
            }
        }

        stage('Build Image') {
            steps {
                sh "aws ecr get-login-password --region ${env.REGION} | docker login --username AWS --password-stdin ${env.ACCOUNT}.dkr.ecr.${env.REGION}.amazonaws.com"

                sh 'docker build --no-cache -t ${IMAGE_NAME} .'

                sh 'docker tag ${IMAGE_NAME} ${IMAGE_NAME_LATEST}'
            }
        }

        stage('Publish Image') {
            steps {
                sh "docker push ${IMAGE_NAME}"
                sh "docker push ${IMAGE_NAME_LATEST}"
            }
        }

        stage('Remove images') {
            steps {
                sh "docker ps -a | awk '{ print \$1,\$2 }' | grep ${IMAGE_NAME} | awk '{print \$1 }' | xargs -I {} docker rm {}"
                sh "docker rmi ${IMAGE_NAME}"
                sh "docker rmi ${IMAGE_NAME_LATEST}"
            }
        }

        stage('Helm Deploy') {
            steps {
                sh 'helm upgrade --install --atomic ${APP_NAME}-${ENVIRONMENT} helm \
                    --set environment=${ENVIRONMENT}'
                }
            }
        }
}


