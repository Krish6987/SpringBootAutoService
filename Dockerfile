FROM maven:3.6.0-ibmjava-alpine

USER root

RUN apk add --update --no-cache openssh sshpass

EXPOSE 22

WORKDIR /usr/src/app
 
RUN mkdir root

RUN mkdir .ssh

COPY Springboot/keys/ /usr/src/app/root/.ssh/

RUN chmod 600 /usr/src/app/root/.ssh/

COPY Springboot/ /usr/src/app/

RUN apk add --no-cache --virtual .build-deps g++ python3-dev libffi-dev openssl-dev && \
    apk add --no-cache --update python3 && \
    pip3 install --upgrade pip setuptools


RUN pip3 install --upgrade pip cffi && pip3 install ansible ansible-lint

EXPOSE 8080

CMD cd /usr/src/app/SpringBootAutoService && mvn clean install && mvn spring-boot:run
