## Objective

Get a text based version of all of a user's activity. The output is suposed to be user freindly, but able to be seen in any console terminal.

#
## Usage

This app is terminal only(for now).    
Open a terminal in the bin folder(right mouse button, open in terminal).    
Write or paste the following command: 
``` 
java -cp "../lib/*;." App
```
#
## Dependencies

org.json(included in the lib folder)
#
## Status

Basic functionality assured.  
Support for extracting entire accounts.  
Support for saving json data and formated data to files.  
  

No suport for non text based posts yet.    
Command line only(for now).    
#
## TODO

- improve the graph implementation(it currently relies on editing a char array);
- fix the word count function;   
- find out what is a post of kind t2.(t1=comment,t3=textpost);    
- search limitations to the ammount of posts one can fetch from reddit;    
- add an (optional) GUI.    