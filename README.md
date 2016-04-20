HEIGVD-RES-2016-Labo-SMTP
==
Madolyne Dupraz & Julien Leroy   
https://github.com/limayankee/Teaching-HEIGVD-RES-2016-Labo-SMTP
____
### The goal of the project

  The goal of this project is to make a Java application that will send e-mails to preset victims' email addresses. The software will automatically generate groups of victims, of minimum 3 persons, one sender and two or more receivers. The software user can choose how many groups he wants.

### How to use it

### Implementation

  

### Installation of the Mock server

  1. Clone the repo on your PC and go to the cloned report /release  
      `git clone https://github.com/tweakers-dev/MockMock`  
      `cd MockMock/release`
  1. Run the server with:`java -jar MockMock.jar`  
     If the server didn't works run it as root.
  1. The web interface can be reach at `http://localhost:8282`
    and the smtp server will run on the port 25.
  1. To use the Mock server just sent your email to the localhost:25 SMTP server.
