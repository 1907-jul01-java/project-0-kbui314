# Console Bank Application
## Kenny Bui
This is a console Bank Application where the application simulates real-world behaviors of a banking system. User interactions
with the banking system are through text menus where the user inputs the selection desired based on the options given. The database used for
this application is Postgres.

# User Stories
- As a user, I am able to register for an account with a username and password.
- As a user, I am able sign in to my account.
- As a user, I am able to apply for an account.
- As a user, I am able to apply for joint accounts.
- As a user, I am able to deposit funds from the account.
- As a user, I am able to withdraw funds into the account.
- As a user, I am able to transfer funds between accounts and to other user's accounts.
- As an employee, I am able to view all of the customer's information.
- As an employee, I am able to approve or deny open applications for regular accounts.
- As an employee, I am able to approve or deny open applications for joint accounts.
- As an employee, I am able to view all customer's account information.
- As an administrator, I am able deposit from all accounts in the database.
- As an administrator, I am able withdraw from all accounts in the database.
- As an administrator, I am able transfer from and to all accounts in the database.
- As an administrator, I am able to view all customer's account information.
- As an administrator, I am able to approve or deny open applications for regular accounts.
- As an administrator, I am able to approve or deny open applications for joint accounts.

# Instructions

#### Build Postgres moviedb
Change directory into /db and run:
```
docker build -t moviedb.
```
Then run a container:
```
docker run -d -p 5432:5432 moviedb
```

#### Compile, Package, and Execute with Maven
To compile and execute, run:
```
mvn compile
mvn exec:java
```
To package an executable jar and execute, run:
```
mvn clean package
java -jar target/project0.jar
```


