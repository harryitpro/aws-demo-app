pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
    pipeline {
        agent any
        stages {
            stage('Checkout') {
                steps {
                    git 'https://your-git-repo-url.git'
                }
            }
            stage('Build') {
                steps {
                    sh 'mvn clean package'
                }
            }
            stage('Build Docker Image') {
                steps {
                    sh 'docker build -t springboot-app:latest .'
                }
            }
            stage('Deploy') {
                steps {
                    sh 'docker-compose -f docker-compose.yml up -d --build springboot-app'
                }
            }
        }


        stage('Checkout') {
            steps {
                // Get source from a GitHub repository
                git branch: 'main', url: 'https://github.com/harryitpro/springbootapp.git'
            }
        }
        stage('Build') {
            steps {
                // Run Maven on a Unix agent.
                withMaven(maven: 'M3') {
                    sh "mvn -Dmaven.test.failure.ignore=true clean package"
                }
            }
            post {
                always {
                    // Publish JUnit test results
                    junit '**/target/surefire-reports/*.xml'
                    // Publish JaCoCo coverage report
                    recordCoverage tools: [[parser: 'JACOCO', pattern: '**/target/site/jacoco/jacoco.xml']],
                                   sourceCodeRetention: 'EVERY_BUILD',
                                   qualityGates: [[metric: 'LINE', threshold: 80.0, type: 'PROJECT', unstable: true]]
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t springboot-app:latest .'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker-compose -f docker-compose.yml up -d --build springboot-app'
            }
        }

    }
}
