FROM maven:3.8.4-jdk-11 as build
# cria uma pasta build
WORKDIR /build
# copia tudo para a pasta build
COPY . .
# executa o mvn clean package -DsskipTests
RUN mvn clean package -DsskipTests

# depdencia java (imagem)
FROM openjdk:11
WORKDIR /app
# copia o arquivo do jar para o WORDIR /app/application.jar
COPY --from=build ./build/target/*jar ./spring_rest_api_car.jar
# porta da imagem
EXPOSE 9090
# comando para executar o projeto
ENTRYPOINT java -jar spring_rest_api_car.jar