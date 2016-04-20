HEIGVD-RES-2016-Labo-SMTP
==
Madolyne Dupraz & Julien Leroy   
https://github.com/limayankee/Teaching-HEIGVD-RES-2016-Labo-SMTP
____
### The goal of the project

  The goal of this project is to make a Java application that will send e-mails to preset victims' email addresses. The software will automatically generate groups of victims, of minimum 3 persons, one sender and two or more receivers. The software user can choose how many groups he wants.

### How to use it

First of all you will need different files. The first one will contain a list of email addresses, one email address per line. The seconde file will contain the email messages and their objects. You will have to respect the following structure:       
\#\#EMAIL    
object= put_here_the_object_of_the_email   
put_here_the_email_message    
it_can_be_written_in_different_lines   
\#\#EMAIL    
object= put_here_the_object_of_the_2nd_email   
put_here_the_2nd_email_message    
\#\#EMAIL    

It has to start with this separator: `##EMAIL` and end with it too, and each email are separated with this separator as well.    

The programm has 3 arguments; the first one is the number of groups you want, the second is the file that contains the emails, and the last one is the file which contains the email messages.   

To send the emails to your victims open a Terminal on the repository. go in the folder out/production/Teaching-HEIGVD-RES-2016-Labo-SMTP/ and write `java LaboSMTP` following by the 3 arguments.


### Implementation

![Diagram](https://raw.githubusercontent.com/limayankee/Teaching-HEIGVD-RES-2016-Labo-SMTP/master/res_SMTP.png "Class Diagram")

  For the laboratory, we named the main class as LaboSMTP. This class contains a table of email addresses and pranks. Those are read from text files. The Email class is a representation of an email, it has a sender, a table of recipients, an object and a message. We got a Group class which got a static method to generate N groups randomly from a list of persons, the N can be set. The main class generates emails with a group as sender, recipients and a prank as object and message. The main create a SMTPCLient and connects to the SMTP server then use it to send the emails.

  #### Client - Server communication
  ![Client-Server](https://raw.githubusercontent.com/limayankee/Teaching-HEIGVD-RES-2016-Labo-SMTP/master/screen_shot.png "Client Server communication")




### Installation of the Mock server

  1. Clone the repo on your PC and go to the cloned report /release  
      `git clone https://github.com/tweakers-dev/MockMock`  
      `cd MockMock/release`
  1. Run the server with:`java -jar MockMock.jar`  
     If the server didn't works run it as root.
  1. The web interface can be reach at `http://localhost:8282`
    and the smtp server will run on the port 25.
  1. To use the Mock server just sent your email to the localhost:25 SMTP server.
