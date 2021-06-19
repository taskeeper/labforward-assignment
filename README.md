# labforward-assignment

Assigment time watch:

    45 mins analysis
    180 mins coding
    90 mins writing test cases
    60 mins testing the application
    30 mins adding swagger API interface to the project

Total consumed time: 6 hours 45 mins

Assignment Implemented Scope:

PROD

    2 APIs / Resources (Notebook , NotebookEntry)
    2 Services (Notebook , NotebookEntry)
    2 Repositories (Notebook , NotebookEntry)
    MySQL DB
    Swagger API documentation interface + test interface
    Two profiles in resources folder, one for dev and other for prod
    1 Utility for JPA auditing to track changes

TEST

    12 Test cases for Notebook Service and NotebookEntry service (Junit 5, Mockito, AssertJ)
    H2 in-memory DB for testing purposes
    To test end points please go to swagger interface at http://localhost:8080/swagger-ui.html or use postman
    APIs definition/documentation can also be found at http://localhost:8080/swagger-ui.html

If I have more time I would have added:

    Spring Security (Authentication + Authorization on method level)
    Implemented faster algorithm to search for word in notebook entry (I assumed the entry will be small in size e.g. 500 characters) instead of sequential mode (e.g. Binary search or Exponetial search) however i used java streams which is more efficient than typical recurrsion.
    Also I would like to mention that I could have used elastic search for faster retrieval (e.g. fuzzy search algorithm to find similar words).
    I suggest to tag any notebook entry with repeated words and show top 3 tags beside the note on front end. This way the researcher can quickly identify the notebook entry he or she would like to view and relevant to the subject he or she is examining.
    tags shall be used to group multiple similar notebook entries. This will help researchers to link between entries, research steps and results.
    each time a notebook entry is added or updated or deleted, a background process (not interactive) should run to re-evaluate notebook entry tags.

UI/UX: I will either send a link for an interactive PoC or a real Angular app during this week.

Note 19 Jun 2021: I have added a new function in Notebook entry service thus resource using levenshtien method.

All My Respect to you and Thanks for your cooperation.
