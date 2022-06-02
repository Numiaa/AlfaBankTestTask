# AlfaBankTestTask

# Задача: создать сервис, который взаимодействует с сервисом курса валют и сервисом gif изображений.

- Если курс валюты по отношению к USD стал выше, то пользователь получает рандомную gif отсюда: https://giphy.com/search/rich
- Если ниже, отсюда: https://giphy.com/search/broke
- Сервис на Spring Boot 2 + Java/Kotlin
- Код валюты, по отношению с которой сравнивается USD передается на HTTP Endpoint
- Внешнее взаимодействие с помощью Feign
- Все параметры вынесены в application properties
- Тесты (мок внешних через @Mockbean или WireMock)

Результат: репозиторий на GitHub с инструкцией по запуску.


# Запуск .jar файла.
java -jar /path_to_jar_file/AlfaBankTestTask-0.0.1-SNAPSHOT.jar

# Запуск docker images.

1. Выполнить команду $sudo docker build /path_to_dockerfile, для создания образа.
2. Выполнить команду $sudo docker images, чтобы убедиться в создании образа и узнать ID.
3. Запустить приложение командой $sudo docker run -p 8080:8080 <IMAGE_ID>.
[Снимок экрана 2022-06-02 в 13 37 27](https://user-images.githubusercontent.com/95632773/171578641-c6c786fb-5b4b-437e-bee4-73dea3af19cc.png)


