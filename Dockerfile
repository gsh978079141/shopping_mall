# 版本信息
#阿里云镜像仓库java8基础镜像
FROM registry.cn-beijing.aliyuncs.com/gsh_repertory/java-jdk-8:latest
#作者
MAINTAINER gsh "978079141@qq.com"
#docker镜像内jar包的路径
ENV PROJ_DIR /usr/app/
#JVM最大内存
ENV JVM_MEMORY 512M
#工作目录
WORKDIR /usr/app/
#ADD shopping_mall-1.0.jar $PROJ_DIR
ADD ./target/*.jar app.jar
#开启内部服务端口
EXPOSE 8082
#CMD ["java","-Xmx${JVM_MEMORY}","-jar","app.jar","&"]
CMD ["java","-jar","app.jar","&"]


