FROM maven:3.6-jdk-8-slim

ARG workspace="none"

USER root

RUN apt-get update && apt-get install --assume-yes wget

# Pre build commands
RUN wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/backend-project/pre-build.sh
RUN chmod 775 ./pre-build.sh
RUN sh pre-build.sh

# Install Workspace for Java

RUN if [ $workspace = "theia" ] ; then \
	wget -O ./pre-build.sh https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/theia/pre-build.sh \
    && chmod 775 ./pre-build.sh && sh pre-build.sh ; fi

WORKDIR /var/

RUN if [ $workspace = "theia" ] ; then \
	wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/theia/build.sh \
    && chmod 775 ./build.sh && sh build.sh ; fi


WORKDIR /var/theia/

RUN if [ $workspace = "theia" ] ; then \
	wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/theia/java/run.sh \
    && chmod 775 ./run.sh ; fi

COPY src /tmp/src/
COPY build.gradle /tmp/
COPY gradlew /tmp/
COPY gradle /tmp/gradle/
WORKDIR /tmp/

EXPOSE 8080

# Build the app
RUN wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/backend-project/springboot/gradle/2.x/build.sh
RUN chmod 775 ./build.sh
RUN sh build.sh

# Add extra docker commands here (if any)...

# Run the app
RUN wget https://codejudge-starter-repo-artifacts.s3.ap-south-1.amazonaws.com/backend-project/springboot/gradle/2.x/run.sh
RUN chmod 775 ./run.sh
CMD sh run.sh
