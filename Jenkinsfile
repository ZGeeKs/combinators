node {

    def maven = Artifactory.newMavenBuild()

    stage "first step on first node"

    stage('Clone repository') {
        checkout scm
    }

    stage('Compile + Tests') {
        maven.run goals: 'clean install'
    }

    stage 'Post-build actions'
}
