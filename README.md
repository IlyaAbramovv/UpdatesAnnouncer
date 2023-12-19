## Updates announcer telegram bot

#### Проект разделен на три части: bot, scrapper, link-parser, которые общаются по HTTP

#### API для _bot_ задокументировано в [bot_spec.yaml](bot%2Fsrc%2Fmain%2Fresources%2Fbot_spec.yaml)
#### API для _scrapper_ задокументировано в [scrapper_spec.yaml](scrapper%2Fsrc%2Fmain%2Fresources%2Fscrapper_spec.yaml)

#### Бот предоставляет возможность отслеживания ссылки на репозиторий GitHub или на вопрос StackOverFlow.
После обновления контента по отслеживаемой ссылке (коммит в GitHub или ответ на вопрос StackOverFlow) пользователю приходит telegram-уведомление об этом

#### В проекте используются следующие технологии

* __Spring__ - внедрение зависимостей, обработка HTTP запросов
* __PostgreSQL__ - храние информации о пользователях и ссылок (поднимается с
  помощью [docker-compose.yml](docker-compose.yml))
* Так как проект учебный, взаимодействие с базой данных реализовано тремя способами
  
    * __jdbc__
    * __jooq__
    * __jpa__
* __Liquibase__ - миграции


#### Для тестов используются библиотеки

* __JUnit 5__
* __Mockito__
* __Testcontainers__
