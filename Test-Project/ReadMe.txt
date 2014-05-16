Project Name: Test-project

Folder Structure: 
  lib- jar files for running JUnit test.
  src- contains source code.
  test- contains unit test cases

Main Class- LoadExecuteFile.java
  Pass the input file's path to be processed as the 1st argument to main.
  
To run all the test cases at once use AllTest.java class under test folder.
The input file path for test cases is being read from sharecompare-testing.properties file under resources folder.
Please update this file with correct file path to run the tests positively.

Things in ToDo list:
1. Try adding an Executor service and implement a fixed size Thread_Pool to read and load the data concurrently. It will make it more scalable.
2. Add utilities like IOUtils to read/write files more easily and efficiently.
3. Add more test cases.
