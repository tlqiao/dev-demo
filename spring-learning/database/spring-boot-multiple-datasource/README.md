# Put different table into different DB
How to implement it.
### 1.Configure different DataSource in config folder
### 2.configure different repository path for different DataSource config
### 3.Different Controller call different repository

How to Run the demo
### 1.install mysql,change to your mysql username and password
### 2.create two database, name is db1 and db2
### 3.start the application
### 4.call the api
#### 4.1 when you call the api "http://localhost:8080/user/all", it use db1
#### 4.2 when you call the api "http://localhost:8080/book/all", it use db2
