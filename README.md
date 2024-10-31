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

[//]: # (  Удалены VkOAuth2UserDataHandler и YandexOAuth2UserDataHandler, почищены register.html, login.html, application.yaml и ProfileTestData.java.)

- Добалена возможность использования для тестов in memory БД H2 (задание 4).
  
  Согласно условию добавлены два бина, см. ```src/main/java/com/javarush/jira/common/internal/config/DataSourceConfig.java```.
  Исправлен ```src/test/resources/data.sql```, чтобы H2 его мог воспринимать.

[//]: # (TODO РАЗОБРАТЬСЯ УСЛОВНЫМ ОГРАНИЦЕНИЕМ В АШ2 )

- Написаны тесты для всех публичных методов контроллера ProfileRestController (задание 5).

- Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload (задание 6).

- Добавлен новый функционал: добавление тегов к задаче (REST API + реализация на сервисе) (задание 7).

[//]: # (TODO РАЗОБРАТЬСЯ С ВАЛИДАЦИЕЙ, написать тесты)


  
  
    