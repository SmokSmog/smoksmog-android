node {

    stage('Build'){
        sh '''
        ./gradlew uninstallAll || true
        ./gradlew assemble -PignoreFailures=true
        wake-devices
        ./gradlew check connectedCheck -PignoreFailures=true

        '''
    }
}