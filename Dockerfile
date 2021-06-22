FROM openjdk:11

EXPOSE 8088:8088

RUN mkdir /app

COPY ./build/install/stock-analyzer/ /app/

WORKDIR /app/bin

CMD ["./stock-analyzer"]