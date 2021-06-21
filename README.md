# labforward-assignment

Assigment time watch:

    1. 45 mins analysis
    2. 180 mins coding
    3. 90 mins writing test cases
    4. 60 mins testing the application
    5. 30 mins adding swagger API interface to the project

Total consumed time: 6 hours 45 mins

Assignment Implemented Scope:

PROD

    1. 2 APIs / Resources (Notebook , NotebookEntry)
    2. 2 Services (Notebook , NotebookEntry)
    3. 2 Repositories (Notebook , NotebookEntry)
    4. MySQL DB
    5. Swagger API documentation interface + test interface
    6. Two profiles in resources folder, one for dev and other for prod
    7. 1 Utility for JPA auditing to track changes

TEST

    1. 12 Test cases for Notebook Service and NotebookEntry service (Junit 5, Mockito, AssertJ)
    2. H2 in-memory DB for testing purposes
    3. To test end points please go to swagger interface at http://localhost:8080/swagger-ui.html or use postman
    4. APIs definition/documentation can also be found at http://localhost:8080/swagger-ui.html

If I have more time I would have added:

    1. Spring Security (Authentication + Authorization on method level)
    2. Implemented faster algorithm to search for word in notebook entry (I assumed the entry will be small in size e.g. 500 characters) instead of sequential mode (e.g. Binary search or Exponetial search) however i used java streams which is more efficient than typical recurrsion.
    3. Also I would like to mention that I could have used elastic search for faster retrieval (e.g. fuzzy search algorithm to find similar words).
    4. I suggest to tag any notebook entry with repeated words and show top 3 tags beside the note on front end. This way the researcher can quickly identify the notebook entry he or she would like to view and relevant to the subject he or she is examining.
    5. tags shall be used to group multiple similar notebook entries. This will help researchers to link between entries, research steps and results.
    6. each time a notebook entry is added or updated or deleted, a background process (not interactive) should run to re-evaluate notebook entry tags.

UI/UX: I will either send a link for an interactive PoC or a real Angular app during this week.

Note 19 Jun 2021: I have added a new function in Notebook entry service thus resource using levenshtien method.

Extra: To access UI please go to https://www.figma.com/proto/Ab7xwMwWZgyjDiUszwzxl7/java-frameworks-cude?node-id=1060%3A75&scaling=min-zoom&page-id=31%3A27

To test the UI flow (Interactive Prototype using Figma) please do the following:

    1. click COVID-19 entry 3
    2. place the mouse on search box
    3. a drop down menu will show up
    4. select find number of occurences from list
    5. then select where text from list
    6. then select equals from list
    7. then click search button
    8. results will be shown.

All My Respect to you and Thanks for your cooperation.
