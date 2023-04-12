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

- make sure color is only printed if the variable supports color(check if user set it on);
- add support for all post tipes(currently it only handles text and some image and video types.);
- switch to a more object oriented approach;
- look into the post_hint element in the json to check if type is an image;
- improve the graph implementation(it currently relies on editing a char array);
- ask if json object and if the posts should be printed in the terminal;
- make the app keep running until closed;
- request terminal size for the graph;
- add separate graph for posts/time in adition to the current words/time;
- ~~prevent divide by 0 in accounts without posts/comments;~~
- check if the URL response = 200(check if account/subreddit exists and there is a connection);
- fix the word count function;   
- find out what is a post of kind t2.(t1=comment,t3=textpost);    
- search limitations to the ammount of posts one can fetch from reddit;    
- add an (optional) GUI.    