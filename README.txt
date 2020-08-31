Cake Manager Micro Service (fictitious)
=======================================

A summer intern started on this project but never managed to get it finished.
The developer assured us that some of the above is complete, but at the moment accessing the /cakes endpoint
returns a 404, so getting this working should be the first priority.

Requirements:
* By accessing the root of the server (/) it should be possible to list the cakes currently in the system.
This must be presented in an acceptable format for a human to read.
[Shrey] : Accessing the root of the server provides a text summary of the available cakes as simple text,
although this is odd for a REST endpoint to return a summary, its inline with the response returned by CakeServlet and fulfills the requirement
of response being presented in human readable format.
In real world application this scenario can be better modeled by rendering available cakes in HTML, I have provided a simple
implementation of this through index.html which can be used to retrieve available cakes in HTML and also add new cakes.

* It must be possible for a human to add a new cake to the server.
[Shrey] : This can be done through http://localhost:8282/index.

* By accessing an alternative endpoint (/cakes) with an appropriate client it must be possible to download a list of
the cakes currently in the system as JSON data.
[Shrey] : /cakes (HTTP GET) endpoint provides a list of cakes in the system as JSON.

* The /cakes endpoint must also allow new cakes to be created.
[Shrey] : /cakes (HTTP POST) endpoint can be used to create new cakes in the system.

Comments:
* We feel like the software stack used by the original developer is quite outdated, it would be good to migrate the entire application to something more modern.
* Would be good to change the application to implement proper client-server separation via REST API.

Bonus points:
* Tests
* Authentication via OAuth2
* Continuous Integration via any cloud CI system
* Containerisation


Original Project Info
=====================

To run a server locally execute the following command:

`mvn jetty:run`

and access the following URL:

`http://localhost:8282/`

Feel free to change how the project is run, but clear instructions must be given in README
You can use any IDE you like, so long as the project can build and run with Maven or Gradle.

The project loads some pre-defined data in to an in-memory database, which is acceptable for this exercise.  There is
no need to create persistent storage.


Submission
==========

Please provide your version of this project as a git repository (e.g. Github, BitBucket, etc).

Alternatively, you can submit the project as a zip or gzip. Use Google Drive or some other file sharing service to
share it with us.

Please also keep a log of the changes you make as a text file and provide this to us with your submission.

Good luck!

=========================================*************************************==========================================

Assumptions, Design Considerations and changes made:

1. All the fields in CakeEntity class have been renamed to reflect real world cake attributes.
2. I have modified the code to load the list of cakes from cakes.json (in main/resources/) instead of loading the cakes from github URL (as per the code in CakeServlet),
   although this would mean that initial set of cakes cannot be modified easily, but loading the cakes through cakes.json file is more reliable.
3. cake.json from github has duplicate cakes, I have assumed that cakes titles are unique and hence removed duplicate cakes from original cakes.json file.
4. I have ported the application to Spring boot, this provides quick start and out of the box configuration to run the application along with other
    capabilities of Spring framework (IOC). The cakes application can be run locally using spring-boot:run.
5. Integration tests have been implemented using SpringBootTest and unit tests are implemented using Junit with Mockito as mocking framework.
6. Hibernate configuration which was provided in hibernate.cfg.xml has been replaced by Spring boot JPA configuration through application.properties.
7. WebApp directory has been removed as Spring boot can be configured using annotation and component scanning.
8. OAuth configuration for the application is also provided but commented out, OAuth configuration for the application is set up on GitHub,
   and can be enabled by un-commenting the relevant jars in pom.xml and renaming application_OAuth2.yml to application.yml.
