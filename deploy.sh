#!/bin/bash
#先赋予可执行权限 chmod +x deploy.sh
#接收外部参数  ./deploy.sh [项目名] [标签]
PROJECT_NAME=$1
TAG=$2
PROJECT_PATH=$3
#删除以前的容器和镜像
cd PROJECT_PATH
docker rm -f ${PROJECT_NAME}
docker rmi -f ${PROJECT_NAME}:${TAG}
#使用Dockerfile构建镜像
docker build -t ${PROJECT_NAME}:${TAG} PROJECT_PATH
#运行镜像
docker run --name="${PROJECT_NAME}" -p 8082:8082 -d ${PROJECT_NAME}:${TAG}
#如果有为<none>的镜像打开注释
# 删除为none的镜像和容器
#停止容器
#docker ps -a | grep "Exited" | awk '{print $1 }'|xargs docker stop
#删除容器
#docker ps -a | grep "Exited" | awk '{print $1 }'|xargs docker rm
#删除none镜像
#docker images|grep none|awk '{print $3 }'|xargs docker rmi
#删除临时镜像
#docker rmi $(docker images -f "dangling=true" -q)