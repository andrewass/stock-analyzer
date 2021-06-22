FROM openjdk:11

RUN mkdir /app

COPY ./build/install/stock-analyzer/ /app/

WORKDIR /app/bin

EXPOSE 8080

CMD ["./stock-analyzer"]