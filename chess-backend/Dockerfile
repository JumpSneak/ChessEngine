FROM gradle:7.5.1-jdk17 AS build

EXPOSE 7999 8000

ARG GPR_USER
ARG GPR_TOKEN
ENV GITHUB_ACTOR=$GPR_USER
ENV GITHUB_TOKEN=$GPR_TOKEN

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

CMD ["gradle", "run"]