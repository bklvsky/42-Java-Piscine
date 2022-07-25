# Module 03 – Piscine Java

## Threads

Summary: Today you will learn how to use basic multithreading mechanisms in Java


## Contents

- I Exercise 00 : Egg, Hen... or Human?
- II Exercise 01 : Egg, Hen, Egg, Hen...
- III Exercise 02 : Real Multithreading


# Chapter I

# Exercise 00 : Egg, Hen... or Human?

```
Exercise 00
```
```
Egg, Hen... or Human?
Turn-in directory : ex 00 /
Files to turn in : *.java
Allowed functions : All
Recommended types and their methods : Object, Thread, Runnable
```
The truth is born in a disputelet’s assume that each thread provides its own answer.
The thread that has the last word is right.
You need to implement the operation of two threads. Each of them must display its
answer a few times, for example, 50:

```
$ java Program --count=
Egg
Hen
Hen
Hen
...
Egg
```
In this case, the egg thread wins. However, the program also contains main thread. Inside
the thread, public static void main(String args[]) method is executed. We need this
thread to display all its responses at the end of program execution. Thus, the ultimate
variant is as follows:

```
$ java Program --count=
Egg
Hen
Hen
...
Egg
Hen
...
Human
...
...
Human
```

- It means that the program outputs Human message 50 times, which main thread
    prints.


# Chapter II

# Exercise 01 : Egg, Hen, Egg, Hen...

```
Exercise 01
```
```
Egg, Hen, Egg, Hen...
Turn-in directory : ex 01 /
Files to turn in : *.java
Allowed functions : All
Recommended types and their methods : Object, Thread, Runnable
Keywords : Synchronized
```
Let’s orchestrate the argument. Now, each thread can provide its answer only after
another thread has done so. Let’s assume that the egg thread always answers first.

```
$ java Program --count=
Egg
Hen
Egg
Hen
Egg
Hen
...
```
Note:

- To solve this task, we recommend to explore Producer-Consumer model operation
    principle


# Chapter III

# Exercise 02 : Real Multithreading

```
Exercise 02
```
```
Real Multithreading
Turn-in directory : ex 02 /
Files to turn in : *.java
Allowed functions : All
Recommended types and their methods : Object, Thread, Runnable
Keywords : Synchronized
```
Try to use multithreading for its intended purpose: distribute computations across the
program.

Let’s assume there is an array of integer values. Your goal is to calculate the sum of array
elements using several "summing" threads. Each thread computes a certain section inside
the array. The number of elements in each section is constant, except for the last one (its
size can differ upward or downward).

The array shall be randomly generated each time. Array length and the number of threads
are passed as command-line arguments.

To make sure the program operates correctly, we should start by calculating the sum of
array elements using a standard method.

Maximum number of array elements is 2,000,000. Maximum number of threads is no
greater than current number of array elements. Maximum modulo value of each array
element is 1,000. All data is guaranteed to be valid.

Example of the program operation (each array element equals 1):

```
$ java Program --arraySize=13 --threadsCount=
Sum: 13
Thread 1: from 0 to 4 sum is 5
Thread 2: from 5 to 9 sum is 5
Thread 3: from 10 to 12 sum is 3
Sum by threads: 13
```

Note:

- In the above example, the size of the last summing-up section used by the third
    thread is less than others.
- Threads can output the results of operation inconsistently.

