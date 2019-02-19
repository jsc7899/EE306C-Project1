
Hi,

This is a script for testing your code with all of the test cases at once. 
It simply checks your output with the expected output and gives you a score.




Project structure for the script to work is :

Project Folder
   |
   |-----ExpectedOutputs
   |               |-   small 
   |               |-   large
   |              
   | 
   |-----ProvidedFiles
   |               |-   script.sh  
   |               |-   Program1.java             
   |               |-   Other Java files
   |               
   | 
   |-----ProvidedInputs
   |               |-   small 
   |               |-   large





WORKING OUTPUT:

Testing 2-4-4.in
Testing 4-8-5.in
Testing 5-10-8.in
Testing 8-8-8.in
Testing 50-100-100.in
Testing 80-80-80.in
Testing 100-200-180.in
Testing 200-250-200.in
----------------------------
   Set   |   Number Correct
----------------------------
  small  |        4/4
  large  |        4/4
----------------------------




If there is any test case that doesn't match their test case, then the differences between both are stored in a file called differences.txt in the same directory.