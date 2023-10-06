# algafood-api

the database of this application is runner in docker container
  run the follow command to start the database instance of application
  docker pull mysql
  docker run -p 3306:3306 --name mysql_database -e MYSQL_ROOT_PASSWORD=root -d --restart always mysql
