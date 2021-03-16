# Gitlet     
## What is Gitlet   
A version-control system that mimics some of the basic features of the popular system Git.      
This is a project from CS61B. Link is [here](https://inst.eecs.berkeley.edu/~cs61b/sp20/materials/proj/proj3/index.html)      
## Classes and Data Structures     
### Blob     
The class stores the contents of files.    
1. String fileName: the name of file
2. Byte[] content: the content of file
### Commit     
The class is a combanation of a log message, timestamp, a mapping of file names to blob references, a parent reference, and (for merges) a second parent reference.    
1. String log: log message
2. String timestamp: timestamp when creating this commit
3. Map<String, String> files: the file (blob) references of its files
4. String parent: a parent reference      
5. String parent2: a second parent reference for merge     
### Init     
The class is designed for Gitlet initialization. It creates a new Gitlet version-control system in the current directory.     
### Remove    
The class is designed for Gitlet removal. Unstage the file if it is currently staged for addition. If the file is tracked in the current commit, stage it for removal and remove the file from the working directory if the user has not already done so (do not remove it unless it is tracked in the current commit).    
### Log    
The class is designed for printing log of the current branch.    
### Checkout    
The class is designed for checkout command.
### Branch    
The class is designed for creating a new branch and removing a branch.    
### Reset    
The class is designed for reseting files, according to the given commit ID.    


## Algorithms     
### Commit     
1. Commit(): The class constructor. Save the initial commit state.    
2. Commit(String log, String timestamp, String parent, List\<String\> blobs): The class constructor. According to the given message, save the commit state.     
### Init 
1. init(): Create `.gitlet` directory and accomplish the initialization. If `.gitlet` directory has existed, print the error message.     
### Add    
1. add(String[] fileNames): Adds a copy of the file as it currently exists to the staging area.     
### Remove
1. remove(String[] fileNames): Unstage the file if it is currently staged for addition. If the file is tracked in the current commit, stage it for removal and remove the file from the working directory if the user has not already done so (do not remove it unless it is tracked in the current commit).    
### Log
1. log(): Starting at the current head commit, display information about each commit backwards along the commit tree until the initial commit.    
2. global_log(): Like log, except displays information about all commits ever made. The order of the commits does not matter.    
3. print(Commit commit, Commit commitID): Print commit information.
## Persistence     
In order to know which pointer is currently active, we use `master` file to save the SHA-1 string of the lastest commit of `master` branch.    
In order to know which branch is currently active, we use `HEAD` file to save the name of the currently active branch.      

