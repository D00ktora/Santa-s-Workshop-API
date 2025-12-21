# Santa-s-Workshop-API
This app is made to cover exam criteria of Sirma Academy in module Spring - Rest

Notes for last:
1. Database is created if not exist. If user do not want to create database its need to remove this text (?createDatabaseIfNotExist=true) from line 3 in application.properties file.
2. In order to start the app user needs to add env variable in his project for DB_PASSWORD, or to replace DB_PASSWORD with his MYSQL password and to replace username root with his username for MYSQL. This two fields are in application.properties file.
3. Application will run on port 8080, if user wants to change the part, it can do it in application.properties file at row 8.
4. Mapstruct dependency is added to this project and setup of IDE is needed. In intellij Settings --> Build, Execution, Deployment --> Compiler --> Annotation Processors --> Select "Enable annotation processing" checkmark and select "Obtain processor from project class path".
5. 
