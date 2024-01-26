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
GET /api/v1/users - возвращает список всех пользователей
````
````
POST /api/v1/users - добавляет нового пользователя. В теле запроса необходим отправить следующие параметры:

name - имя пользователя
eventsDTO - список событий пользователя
fileDTO - файлы который относится к событию
file.name - имя файла 
file.filePath - путь до файла
createAt - время создания файла
updatedAt - время обновления файла
eventsDTO.fileDTO.status - статус файла
eventsDTO.status - статус события
status - статус пользователя

Пример
{
    "name": "test",
    "status": "ACTIVE",
    "eventsDTO": [
        {
            "fileDTO": {
                "name": "test",
                "filePath": "/",
                "createAt": "01.01.01",
                "updatedAt": "01.01.01",
                "status": "ACTIVE"
            },
            "status": "ACTIVE"
        }
    ]
}
````
````
PUT  /api/v1/users/{id} - позволяет обновить существующий объект.

id - id пользователя
name - имя пользователя
events - список событий
file.name - имя файла 
file.filePath - путь до файла

Пример

{
    "name": "test",
    "status": "ACTIVE",
    "events": [
        {
            "file": {
                "name": "test",
                "filePath": "/",
                "createAt": "01.01.01",
                "updatedAt": "01.01.01",
                "status": "ACTIVE"
            },
            "status": "ACTIVE"
        }
    ]
```` 
````
DELETE /api/v1/users/{id}- удаляет пользователя по id. 
````

````
GET /api/v1/users/{id} - возвращает пользователя по id. 
````

**Events**
````
GET /api/v1/events - возвращает список всех событий
````
````
POST /api/v1/events - добавляет новое событие. В теле запроса необходим отправить следующие параметры:

name - имя файла
filePath - путь до файла
status - статус события
updatedAt - дата обновления
createAt - дата создания

Пример
{
    "fileDTO": {
        "name": "test",
        "filePath": "/",
        "createAt": "sd",
        "updatedAt": "fddf",
        "status": "ACTIVE"
    },
    "status":"ACTIVE"
}
````
````
PUT  /api/v1/events/{id} - позволяет обновить существующий объект. В теле запроса необходим отправить следующие параметры:

id - id события
file.id - id файла связанного с событием
name - имя файла 
filePath - путь до файла

Пример
{
    "file": {
        "name": "test",
        "filePath": "/",
        "createAt": "sd",
        "updatedAt": "fddf",
        "status": "ACTIVE"
    },
    "status":"ACTIVE"
}
```` 
````
DELETE //api/v1/events/{id} - удаляет событие. 
````
````
GET /api/v1/events/{id} - возвращает событие по id. 
````

**Files**
````
GET /api/v1/files - возвращает список всех файлов
````
````
POST /api/v1/files - добавляет новый файл.

file - ссылка на файл на вашей локальной машине

--form 'file=@"/C:/Users/test.txt"'
````
````
PUT  /api/v1/files/{id} - позволяет обновить существующий объект. В теле запроса необходим отправить следующие параметры:

id - id файла
name - имя файла 
filePath - путь до файла
createAt - время создания файла
updatedAt - время обновления файла
status - статус файла

Пример
{
    "name": "test",
    "filePath": "/",
    "createAt": "01.01.01",
    "updatedAt": "01.01.01",
    "status": "ACTIVE"
}
```` 
````
DELETE /api/v1/files/{id} - удаляет файл. 
````
````
GET /api/v1/files/{id}- возвращает пользователя по id. 
````

````
GET /api/v1/files/history - возвращает историю загрузки
````