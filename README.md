# Инструкция запуска автотестов
### Необходимое ПО:
- Docker
- JDK/JRE/JVM минимум 11-ой версии
- IntelliJ IDEA 
- Git
- Google Chrome

### Запуск проекта:
1. Открыть консоль **git bash** в любой директории на компьютере 
и скачать репозиторий с помощью команды `$ git clone https://github.com/MikhailPonomarev/AQA_Diploma.git`
> ВАЖНО: директория для скачивания проекта не должна содержать кириллические символы в названии пути
2. Открыть файл `build.gradle` с помощью IntelliJ IDEA
3. Запустить контейнер с БД консольной командой `docker-compose up -d`
4. Запустить приложение консольной командой `java -jar .\artifacts\aqa-shop.jar`
5. Запустить автотесты консольной командой `./gradlew test`

### Отчет Allure:
* При необходимости сформировать Allure отчет о прохождении тестов можно консольной командой `./gradlew allureReport`
* Отчет сохранится в директории проекта `build/reports/allure-report`, открывается запуском файла `index.html`


