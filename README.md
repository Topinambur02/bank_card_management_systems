# Описание запуска приложения Bank Card Management System

Предварительные требования:
- Установленный Java 17+
- Установленный Maven
- Установленный Docker и Docker Compose (для запуска через Docker)
- Установленный PostgreSQL (для локального запуска без Docker)

# 1. Локальный запуск (без Docker)<br/>
## 1.1. Клонирование репозитория
```
git clone https://github.com/ваш-username/bank_card_management_systems.git
cd bank_card_management_systems
```
## 1.2. Настройка переменных окружения<br/>
Создайте файл .env в корне проекта и заполните значения: <br/>
```
# Локальная база данных (замените значения на свои!)
DB_URL=jdbc:postgresql://localhost:5432/bank_card_db
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# Шифрование (придумайте свой секретный ключ)
JASYPT_ENCRYPTOR_PASSWORD=your_jasypt_master_key
SECRET=your_jwt_secret_key
```
## 1.3. Сборка проекта
``` mvn clean install ```
## 1.4. Запуск приложения
Через IDE: Запустите класс BankCardManagementSystemApplication.java. <br/>
Через командную строку: <br/> <br/>
``` mvn spring-boot:run ``` <br/>
# 2. Запуск через Docker <br/>
## 2.1. Настройка переменных окружения для Docker <br/>
Дополните файл .env параметрами для Docker: <br/>
```
# Docker-контейнер PostgreSQL
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres_password
POSTGRES_DB=bank_card_db

# Настройки подключения Spring к контейнеру
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/bank_card_db
```
## 2.2. Запуск контейнеров
```docker compose up --build```
# 3. Проверка работоспособности
После запуска приложение будет доступно по адресу:
http://localhost:8080

Дополнительно:
Для остановки Docker-контейнеров выполните:
```docker compose down```
Если используются Flyway-миграции, база данных инициализируется автоматически.
