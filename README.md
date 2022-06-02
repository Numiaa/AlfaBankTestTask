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

# REST Endpoint's

GET /api/get-codes : запрос списка всех валют, получаем в ответ JSON.

![Снимок экрана 2022-06-02 в 13 47 49](https://user-images.githubusercontent.com/95632773/171580721-db1a3014-7397-4c62-9d7b-31b442be0cc6.png)

GET /api/get-gif/{CODE} : получаем JSON сущности в зависимости от изменения курса.

![Снимок экрана 2022-06-02 в 13 48 26](https://user-images.githubusercontent.com/95632773/171580824-53970224-ea49-402e-b839-0f0c0ac0189b.png)


# Запуск .jar файла.

java -jar /path_to_jar_file/AlfaBankTestTask-0.0.1-SNAPSHOT.jar

# Запуск docker images.

1. Выполнить команду $ docker build /path_to_dockerfile, для создания образа.
2. Выполнить команду $ docker images, чтобы убедиться в создании образа и узнать ID.
3. Запустить приложение командой $ docker run -p 8080:8080 <IMAGE_ID>.
![Снимок экрана 2022-06-02 в 13 41 14](https://user-images.githubusercontent.com/95632773/171579422-1d1e96d3-7140-49b9-be2f-b823d84b71a8.png)

# Запуск через docker hub.

1. Выгрузить образ командой $ docker pull minessota12345/alfabank_test_image:app
2. Запустить приложение командой $ docker run -p 8080:8080 minessota12345/alfabank_test_image:app

