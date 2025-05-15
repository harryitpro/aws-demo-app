pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', url: 'https://github.com/harryitpro/springbootapp.git'

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
    }
}
