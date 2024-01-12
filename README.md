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
**Users**
````
GET /users - возвращает список всех пользователей
````
````
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
````
````
PUT  /users - позволяет обновить существующий объект. В теле запроса необходим отправить следующие параметры:

id - id пользователя
name - имя пользователя
events - список событий
file.name - имя файла 
file.filePath - путь до файла

Пример
{   "id":4,
    "name": "4",
    "events": [
        {
            "file": {
                "name": "4",
                "filePath": "4"
            }
        }
    ]
}
```` 
````

DELETE /users - удаляет пользователя. 

Пример

http://localhost:8081/REST_API_/users?user_id=9
user_id - id пользователя.
````

````
GET /users/id - возвращает пользователя по id. 

Пример
http://localhost:8081/REST_API_/users/id?user_id=9
user_id - id пользователя.
````

**Events**
````
GET /events - возвращает список всех событий
````
````
POST /events - добавляет новое событие. В теле запроса необходим отправить следующие параметры:

name - имя файла
filePath - путь до файла

Пример
  {
        "file": {
            "name": "test",
            "filePath": "/",
        }
    }
````
````
PUT  /events - позволяет обновить существующий объект. В теле запроса необходим отправить следующие параметры:

id - id события
file.id - id файла связанного с событием
name - имя файла 
filePath - путь до файла

Пример
{
    "id": 10,
    "file": {
        "id":4,
        "name": "TEST",
        "filePath": "/TEST"
    }
}
```` 
````

DELETE /events - удаляет событие. 

Пример

http://localhost:8081/REST_API_/users?event_id=9
event_id - id события.
````
````
GET /events/id - возвращает пользователя по id. 

Пример
http://localhost:8081/REST_API_/events/id?event_id=9
event_id - id пользователя.
````

**Files**
````
GET /files - возвращает список всех файлов
````
````
POST /files - добавляет новый файл. В теле запроса необходим отправить следующие параметры:

name - имя файла
filePath - путь до файла

Пример
 {
    "name": "test",
    "filePath": "/"
}
````
````
PUT  /files - позволяет обновить существующий объект. В теле запроса необходим отправить следующие параметры:

id - id файла
name - имя файла 
filePath - путь до файла

Пример
{   
    "id":21,
    "name": "123test",
    "filePath": "/"
}
```` 
````

DELETE /files - удаляет событие. 

Пример

http://localhost:8081/REST_API_/files?file_id=9
file_id - id файла.
````
````
GET /files/id - возвращает пользователя по id. 

Пример
http://localhost:8081/REST_API_/files/id?file_id=9
file_id - id пользователя.
````

````
GET /files/history - возвращает историю загрузки

Пример
http://localhost:8081/REST_API_/files/history
````