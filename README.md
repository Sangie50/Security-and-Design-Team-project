c# COM 2008 Systems Design and Security Assignment 1
## Team 028

To launch software is in the root folder of the src, called _"launchSoftware.java"_. There are 4 types of premade user types to access the different functionalities of the software depending on the user.

| User type | Username  | Password |
|-----------|-----------|----------|
| Student   | nameless  | password | 
| Teacher   | ordinary  | password |
| Admin     | normal    | password |
| Registrar | registrar | password | 

## Student
The student account can only view their personal information, such as forename, surname, registration ID and degree ID.

## Registrar
The registrar account has 3 main functionalities: add and remove a user from being a student account type, assign and un-assign modules from the selected student, check and edit a student's registration details.

Within each functionality, the registrar is provided with a form to fill to update the database with student information.

## Admin

## Teacher
The teacher account can view students taking the modules they teach. They can select the student's email to view the information of the modules they take; module name, initial mark and resit mark. They can edit the student's initial and resit mark separately.