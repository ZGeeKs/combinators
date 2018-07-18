node {

    def server = Artifactory.server "SERVER_ID"
    def maven = Artifactory.newMavenBuild()

    stage "first step on first node"

    stage('Clone repository') {
        checkout scm
    }

    stage('Artifactory configuration') {
        // Tool name from Jenkins configuration
        maven.tool = "Maven-3.3.9"
        // Set Artifactory repositories for dependencies resolution and artifacts deployment.
        maven.deployer releaseRepo:'libs-release-local', snapshotRepo:'libs-snapshot-local', server: server
        maven.resolver releaseRepo:'libs-release', snapshotRepo:'libs-snapshot', server: server
    }

    stage('Compile + Tests') {
        maven.run goals: 'clean install'
    }

    stage 'Post-build actions'
}
