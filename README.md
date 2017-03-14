my-health-partner-android-data-set
==================================
Description :
-------------
*************

The learning application (or developper application) will provide data to the learning database. The purpose is to furnish enough data to have a good classification algorithm.

### Profile

The user is able to inform his profile information (gender,height, weight, birthday). It will help the algorithm to improve the final users' follow-up by adapting calculations according to their profile.

### Data Collecting

The person who uses the application will have to inform the time and the type of the activity that he wants to do. At the end of this time-limit, the data collected will be sent to the server.

Deployement :
-------------
*************

The application will work from Android Studio 4.4 (KitKat)

To get the project, create a new Android Studio Project :
File -> New -> Project From Version Control -> GitHub
Here is the Git Repository URL : https://github.com/Serli/my-health-partner-android-data-set.git

Developer help :
-------------
*************

To use your own server, just change the Retrofit baseUrl attribute in the sendAcquisition method of the MainController. 

For example : retrofit.baseUrl(String.valueOf("Your URL"))
