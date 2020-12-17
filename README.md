# Gitlet     
## What is Gitlet   
A version-control system that mimics some of the basic features of the popular system Git.      
This is a project from CS61B. Link is [here](https://inst.eecs.berkeley.edu/~cs61b/sp20/materials/proj/proj3/index.html)      
## Classes and Data Structures     
### Blob     
The class stores the contents of files.    
1. String fileName: the name of file
2. String content: the content of file
### Commit     
The class is a combanation of a log message, timestamp, a mapping of file names to blob references, a parent reference, and (for merges) a second parent reference.    
1. String log: log message
2. String timestamp: timestamp when creating this commit
3. List\<String\> blobs: a list of Blob SHA-1 hashcode
4. String parent: a parent reference
### Init     
The class is designed for Gitlet initialization. It creates a new Gitlet version-control system in the current directory.     
### 

## Algorithms     
### Commit     
1. Commit(): The class constructor. Save the initial commit state.    
2. Commit(String log, String timestamp, String parent, List\<String\> blobs): The class constructor. According to the given message, save the commit state.     
### Init 
1. init(): Create `.gitlet` directory and accomplish the initialization. If `.gitlet` directory has existed, print the error message.     
### 
## Persistence     
In order to 
In order to know which pointer is currently active, we use `HEAD` file to save the SHA-1 string of the currently active pointer.   
