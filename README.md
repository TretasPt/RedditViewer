## Objective

Get a text based version of all of a user's activity. 

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

org.json
#
## Status

Basic functionality assured.  
Support for extracting entire accounts.  
Support for saving json data and formated data to files.  
  

No suport for non text based posts yet.   
No support for comments either.   
Command line only(for now).    
~~No support for extracting more than 25 posts **yet**.~~   
~~No support to save to an external file **yet**.~~
#
## TODO

- fix the word count function;   
- fix the extra whitespace on t1 kind posts(comments) -> they are not currently being printed, but the space that separetes them is;  
- find out what is a post of kind t2.(t1=comment,t3=textpost);   
- search limitations to the ammount of posts one can fetch from reddit;
- add an (optional) GUI.