FROM mysql:8.1
ENV APP mysql-docker
ENV MYSQL_USER study
ENV MYSQL_PASSWORD studydocker1
ENV MYSQL_ROOT_PASSWORD studydocker1
ENV MYSQL_DATABASE study
#docker run -d -p 3307:3306 --name mysql-docker  -e='MYSQL_ROOT_PASSWORD=studydocker1' mysql8.1:latest
#docker run --name mysql-docker -e MYSQL_ROOT_PASSWORD='studydocker1!' -d -p 3307:3306 mysql:latest