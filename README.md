## Начало работы
1. При запуске приложения необходимо заполнить следующие параметры в файле который находится по пути:
```src/main/resources/hibernate.cfg.xml``` 

``` 
connection.url - ссылка на хост где запущена MySQL в формате jdbc:mysql://<host>:<port>/<database>
connection.username - логин для подключения
connection.password - пароль для подключения
db - имя вашей базы данных, которую нужно будет предварительно создать в вашей локальной базе данных

Пример заполненных данных:

        <property name="connection.url">jdbc:mysql://localhost:8080/test</property>
        <property name="connection.username">test</property>
        <property name="connection.password">root</property>
```
2. Необходимо выполнить команду ``flyway:baseline`` с помощью maven, после успешного выполнения выполните вторую команду  maven``flyway:migrate`` \
   Обратите внимание, что при запуске приложения в указанной вами базе данных, будут созданы следующие таблицы:
   User, Event, File, events_files. \
   Для корректной работы приложения убедитесь, что в вашей БД отсутствовали таблицы с похожими именами.

3. Установить локально сервер apache tomcat


## Запуск приложения
````
1. Запустите сервер apache tomcat на локальной машине
2. Перейдите по ссылке на которой был запущен сервер
Пример
http://localhost:8081/REST_API_/<endpoint>
````

## Endpoint and example request
````
Приложение поддерживает 3 типа объектов User, Event,File

Для каждого объекта поддерживается 4 типа запросов: GET, POST, PUT, объект
GET - возвращает список всех объектов
POST - добавляет новый объект
PUT - позволяет обновить существующий объект
DELETE - удаляет объект

Пример Post запроса для объекта File

curl --location 'http://localhost:8081/REST_API_/files/history' 
--header 'Content-Type: application/json' 
--data '{
    "name": "test",
    "filePath": "/test"
}'
````

##  Endpoint list with params

````
GET /users - возвращает список всех пользователей
POST /users - добавляет нового пользователя. В теле запроса необходим отправить следующие параметры:
name - имя пользователя
events - список событий
file.name - имя файла 
file.filePath - путь до файла

Пример
{
    "name": "testAPi",
    "events": [
        {
            "file": {
                "name": "test23api",
                "filePath": "/api"
            }
        }
    ]
}

PUT - позволяет обновить существующий объект
DELETE - удаляет объект
 
````