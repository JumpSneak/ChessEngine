FROM gradle:7.5.1-jdk17 AS build

EXPOSE 7999

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

CMD ["gradle", "run"]