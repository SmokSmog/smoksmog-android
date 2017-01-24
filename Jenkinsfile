node {

    slackSend channel: 'quality', color: '#0080FF', message: "Started Android _${env.JOB_NAME}_ #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", teamDomain: 'smoksmog', tokenCredentialId: 'smoksmok-slack'

    stage('Prepare'){
        checkout scm
    }

    try {
        stage('Build'){
            sh '''
            ./gradlew uninstallAll || true
            ./gradlew assemble -PignoreFailures=true
            wake-devices
            ./gradlew check connectedCheck -PignoreFailures=true
            '''

            slackSend channel: 'quality', color: '#80FF00', message: "Success Android _${env.JOB_NAME}_ #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", teamDomain: 'smoksmog', tokenCredentialId: 'smoksmok-slack'
        }
    } catch (error) {
        slackSend channel: 'quality', color: '#FF0000', message: "Failed Android _${env.JOB_NAME}_ #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", teamDomain: 'smoksmog', tokenCredentialId: 'smoksmok-slack'
    }

    stage('Artifacts'){

        androidLint canComputeNew: false, canRunOnFailed: true, defaultEncoding: '', healthy: '', pattern: '**/build/outputs/lint-results-*.xml', unHealthy: ''

        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'app/build/reports/androidTests/connected/', reportFiles: 'index.html', reportName: 'Android tests'])

        junit '**/build/**/TEST-*.xml'
    }
}