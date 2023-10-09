# api-training

the database of this application is runner in docker container
  run the follow command to start the database instance of application

  **important**: to run the commands bellow, firt you need have docker installed on your computer

  
 `` docker pull mysql ``

  
  `` docker run -p 3306:3306 --name mysql_database -e MYSQL_ROOT_PASSWORD=root -d --restart always mysql ``
