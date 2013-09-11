Social Job Search
=================

With this project, we develop a wide amount of techniques to analyse the expertise of social networks users. The RESEARCH target is, in fact, to use social data to provide the best experts able to answer to a generic question.

Under the path  " social-job-search / src / main / java / it " we can find the various classes that were built. In particular:

* /core: contains the classes in charge of the analysis of the collected resources (we call Resource any source of information connected to the user).
* /datamodel: we classify the information of users, resources and queries
* /groundtruth: a groundtruth was built to validate our thesis: analysing social data we can find the best candidates.
* /servlets: some servlets that analyse input from HTML files and return results
* /utils: contains all the services we used (Freebase, Alchemy, Tagme, MongoDB, ...) to access their API and analyse results.

In the " social-job-search / src / main / webapp " it is possible to find jsp for querying (pretty essntial) and showing results.

