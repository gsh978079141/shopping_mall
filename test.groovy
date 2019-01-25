node {
    def mvnHome
    def port = "8091:8080"
    def DOCKER_NAME= "${JOB_NAME}"
    def SRCPATH = "."
    def JAR_NAME = "shopping_mall"
    def DESTPATH = "/usr/app/${JOB_NAME}"
    stage('代码拉取') { // for display purposes
        // Get some code from a GitHub repository
        //git branch:"${GIT_BRANCH}", url:'https://github.com/gsh978079141/shopping_mall.git'
        git 'https://github.com/gsh978079141/shopping_mall.git'
        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'mvn3.6'
    }
    stage('maven构建') {
        // Run the maven build
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
        } else {
            bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
        }
    }
    stage('服务检查'){
//        steps {
            echo "检查docker容器进程是否存在"
            script {
                try{
//                    sh "ansible ${REGION} -m shell -a 'ls'"
                    def resultUpdateshell = sh "ansible ${REGION} -m shell -a 'docker ps -a |grep ${DOCKER_NAME}|grep -v grep'"
                    if (resultUpdateshell != 0) {
                        sh 'ansible ${REGION} -m shell -a "docker rm -f ${DOCKER_NAME}"'
                    }
                }
                catch(e){
                    echo "no docker image"
                }
            }
//        }
    }
    stage('代码推送') {
//        steps {
            echo "code sync"
            script {
                try{
                    sh 'ls'
//                    sh 'mv ${SRCPATH}/target/*.jar  ${SRCPATH}/target/${JOB_NAME}.jar'
                    sh 'mv target/*.jar  target/${JOB_NAME}.jar'
//                    def isDir = sh script: 'ansible ${REGION} -m shell -a "ls -f ${DESTPATH}/webapps/${JAR_NAME}"'
                    if (resultUpdateshell != 0) {
//                        sh 'ansible ${REGION} -m shell -a "rsync -avz --remove-source-files  ${DESTPATH}/webapps/${JAR_NAME}  /tmp"'
//                        sh 'ansible ${REGION} -m shell -a "rm -fr ${DESTPATH}/webapps/${JAR_NAME}"'
                    }
                }catch(e){
                    echo "no jar"
                }
                sh "ansible ${REGION} -m synchronize -a 'src=target/${JOB_NAME}.jar dest=${DESTPATH}/'"
                sh "ansible ${REGION} -m synchronize -a 'src=target/Dockerfile dest=${DESTPATH}/'"
                sh "ansible ${REGION} -m synchronize -a 'src=target/deploy.sh dest=${DESTPATH}/'"
            }
//        }
    }
    stage('Results') {
        archive 'target/*.jar'
    }
}