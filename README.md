## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

## Список выполненных задач:
- Удалить социальные сети: vk, yandex (задание 2)

  Удалены VkOAuth2UserDataHandler и YandexOAuth2UserDataHandler, почищены register.html, login.html, application.yaml и ProfileTestData.java.

- "Чувствительная" информация вынесена в отдельный файл (задание 3).

  В ```application.yaml``` конфенденциальные данные загружаются из переменных окружения. 
  В файл ```secure.env``` перечислены эти переменные. 
  Этот же файл используется в заданиях, связанных с докером (см. ниже).
  Только для того, чтобы было проще проверять проект и запускать сразу из Idea 
  не прописывая переменные вручную в ```application.yaml``` импортируется этот файл. 

- Добалена возможность использования для тестов in memory БД H2 (задание 4).
  
  Согласно условию добавлены два бина, см. ```src/main/java/com/javarush/jira/common/internal/config/DataSourceConfig.java```.
  Исправлен ```src/test/resources/data.sql```, чтобы H2 его мог воспринимать.
  Добавлен новый changelog для тестовой базы (```src/test/resources/changelog-test.sql```).
  В принципе, можно было бы переписать старый, чтобы и H2 и Postgress с ним заводились, но вся загвоздка в
  последней команде c заданием частичного индекса 
``` sql 
  create unique index UK_USER_BELONG on USER_BELONG (OBJECT_ID, OBJECT_TYPE, USER_ID, USER_TYPE_CODE) where ENDPOINT is null;
```
 
- Написаны тесты для всех публичных методов контроллера ProfileRestController (задание 5).

  Отредактирован файл ```src/test/java/com/javarush/jira/profile/internal/web/ProfileRestControllerTest.java```.

- Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload (задание 6).  

- Добавлен новый функционал: добавление тегов к задаче (REST API + реализация на сервисе) (задание 7).

  См методы ```addTags``` в ```src/main/java/com/javarush/jira/bugtracking/task/TaskService.java``` и 
 ```com/javarush/jira/bugtracking/task/TaskController.java```.

- Добавлен Dockerfile для основного сервера (задание 9).

  Для запуска в докер написан простенький скрипт ```jar_image_run_container.sh```,
  в нем проект упаковывается в jar, создается dockeр образ и запускается контейнер. 
  



  
  
    