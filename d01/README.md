# Module 01 – Piscine Java

## OOP/Collections

Summary: Today you will learn how to model the operation of various collections
correctly, and create a full-scale money transfer application


## Contents

- I Introduction to exercises
- II Exercise 00 : Models
- III Exercise 01 : ID Generator
- IV Exercise 02 : List of Users
- V Exercise 03 : List of transactions


# Chapter I

# Introduction to exercises

An internal money transfer system is an integral part of many corporate applications.
Your today’s task is to automate a business process associated with transfers of certain
amounts between participants of our system.
Each system user can transfer a certain amount to another user. We need to make sure
that even if we lose the history of incoming and outgoing transfers for a specific user, we
shall still be able to recover this information.
Inside the system, all money transactions are stored in the form of debit/credit pairs.
For example, John has transferred \$500 to Mike. System saves the transaction for both
users:
John -> Mike, -500, OUTCOME, transaction ID
Mike -> John, +500, INCOME, transaction ID
To recover the connection within such pairs, identifiers of each transaction should be
used.
A transfer entry may obviously be lost in such a complex system it may not be recorded
for one of the users (to emulate and debug such a situation, a developer needs to be able to
remove the transfer data from one of users individually). Since such situations are realistic,
functionality is required for displaying all "unacknowledged transfers" (transactions recorded
for one user only) and resolving such issues.
Below is a set of exercises you can do one by one to solve the task.


# Chapter II

# Exercise 00 : Models

```
Exercise 00
```
```
Models
Turn-in directory : ex00/
Files to turn in : User.java, Transaction.java, Program.java
Allowed functions :
User classes can be employed, along with:
Types (+ all methods of these types) : Integer, String, UUID, enumerations
```
Your first task is to develop basic domain models namely, User and Transaction classes.
It is quite likely for different users to have the same name in the system. This problem
should be solved by adding a special field for a user’s unique ID. This ID can be any
integer number. Specific ID creation logic is described in the next exercise.
Thus, the following set of states (fields) is typical for User class:

- Identifier
- Name
- Balance

```
Transaction class describes a money transfer between two users. Here, a unique
identifier should also be defined. Since the number of such transactions can be very
large, let us define the identifier as an UUID string. Thus, the following set of states
(fields) is typical for Transaction class:
```
- Identifier
- Recipient (User type)
- Sender (User type)
- Transfer category (debits, credits)
- Transfer amount


It is necessary to check the initial user balance (it cannot be negative), as well as the
balance for the outgoing (negative amounts only) and incoming (positive amounts only)
transactions (use of get/set methods).
An example of use of such classes shall be contained in Program file (creation, initialization,
printing object content on a console). All data for class fields must be hardcoded in
Program.


# Chapter III

# Exercise 01 : ID Generator

```
Exercise 01
```
```
ID Generator
Turn-in directory : ex01/
Files to turn in : UserIdsGenerator.java, User.java, Program.java
Allowed functions : All permissions from the previous exercise can be used
```
Make sure that each user ID is unique. To do so, create UserIdsGenerator class. Behavior
of the object of this class defines the functionality for generating user IDs.
State-of-the-art database management systems support autoincrement principle where
each new ID is the value of the previously generated ID +1.
So, UserIdsGenerator class contains the last generated ID as its state. UserIdsGenerator
behavior is defined by int generateId() method that returns a newly generated ID each
time it is called.
An example of use of such classes shall be contained in Program file (creation, initialization,
printing object content on a console).
Notes:

- Make sure only one UserIdsGenerator object exists (see the Singleton pattern). It
    is required because existence of several objects of this class cannot guarantee that
    all user identifiers are unique.
- User identifier must be read-only since it is initialized only once (when the object
    is created) and cannot be modified later during the program execution.
- Temporary logic for identifier initialization should be added to User class construc-
    tor:

public User(...) {
this. id = UserIdsGenerator.getInstance().generateId();
}


# Chapter IV

# Exercise 02 : List of Users

```
Exercise 02
```
```
List of Users
Turn-in directory : ex02/
Files to turn in : UsersList.java, UsersArrayList.java, User.java,Program.java, etc.
Allowed functions : All permissions from the previous exercise + throw can be used.
```
Now we need to implement a functionality for storing users while the program runs.
At the moment, your application has no persistent storage (such as a file system or
a database). However, we want to avoid the dependence of your logic on user storage
implementation method. To ensure more flexibility, let us define UsersList interface that
describes the following behavior:

- Add a user
- Retrieve a user by ID
- Retrieve a user by index
- Retrieve the number of users

This interface will enable to develop the business logic of your application so that a
specific storage implementation does not affect other system components.
We shall also implement UsersArrayList class that implements UsersList interface.
This class shall use an array to store user data. The default array size is 10. If the array
is full, its size is increased by half. The user-adding method puts an object of User type
in the first empty (vacant) cell of the array.
In case of an attempt to retrieve a user with a non-existent ID, an unchecked UserNotFoundException
must be thrown.
An example of use of such classes shall be contained in Program file (creation, initialization,
printing object content on a console).
Note:
Nested ArrayList<T> Java class has the same structure. By modeling behavior of this
class on your own, you will learn how to use mechanisms of this standard library class.


# Chapter V

# Exercise 03 : List of transactions

```
Exercise 03
```
```
List of transactions
Turn-in directory : ex03/
Files to turn in : TransactionsList.java, TransactionsLinkedList.java, User.java,
Program.java, etc.
Allowed functions : All permissions from the previous exercise can be used
```
Unlike users, a list of transactions requires a special implementation approach. Since the
number of transaction creation operations can be very large, we need a storage method
to avoid a costly array size extension.
In this task, we offer you to create TransactionsListinterface describing the following
behavior:

- Add a transaction
- Remove a transaction by ID (in this case, UUID string identifier is used)
- Transform into array (ex. Transaction[] toArray())

```
A list of transactions shall be implemented as a linked list (LinkedList)
in TransactionsLinkedList class. Therefore, each transaction shall contain a field
with a link to the next transaction object.
If an attempt is made to remove a transaction with non-existent ID,
TransactionNotFoundException runtime exception must be thrown.
An example of use of such classes shall be contained in Program file (creation,
initialization, printing object content on a console).
```
```
Note:
```
- We need to add transactions field of TransactionsList type to User class so that
    each user can store the list of their transactions.
- A transaction must be added with a SINGLE operation (O(1))
- LinkedList<T> nested Java class has the same structure, a bidirectional linked list.
