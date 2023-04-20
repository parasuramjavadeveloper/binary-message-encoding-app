# MessageEncodingApp

Message encoding app. Encodes the given input request header and payload and decodes the encoded string. 

# Technologies/Tools Used 

1) Java 8 
2) Intellij Idea for Development
3) Maven Build Tool
4) Lombok Libraries

## To run the test:

```
javac src/*.java
java -cp src se.sinch.binaryencodingmessage.EncodeMessageApplication
```

# Implementation details
1. Implemented business logic using Java8 and maven.
2. This application deals with encoding and decoding message.
3. The message will consist of headers and payload.
4. Junits for all the scenarios have been covered.

KEEP BELOW POINTS IF REQUIRED
1) Converted into Maven Project and Build the code
2) Changed the Data Models of the Application according to the Business Requirements and Written the JUnit Tests
3) Main Business Logic is write the APIs to encode the given Message and to decode the encoded message.
4) Packaging and Refactoring done in all the classes but still its open for Refactoring and Improvements.
   a) Might add several junit tests to test all edge cases 
   b) Can use JDK 8 and its features like lambda expressions etc.
   c) Can Introduce InMemory/Persistent DB to save encoded information and to keep a track of encoded messages.

# Future Code Improvements

1) Can be developed as SpringBoot REST APIs which will give you individual APIs for encoding and decoding data .
2)  Expose the endpoint with swagger documentation.
