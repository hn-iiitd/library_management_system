Library_Management_System_v1

To run the project , 
1.] Run the Main file in Library_Management_System -> src -> main -> java -> lib_code -> Main.java
				            Or
2.] generate jar file using pom.xml, by running command  mvn clean install  in terminal.
3.] run the jar file.

Some Important points :-
1.] Every member can be added with a unique mobile no. only. No two members can have same mobile no.
2.] Member id and Book id is always an integer.
3.] Member id and Book id is automatically given to a member or a book respectively.
4.] Librarian can view the members id, books they have registered and fine pending when the function is called.
5.] librarian cannot remove a book untill it is returned by the member.
6.] Librarian cannot remove a member untill it has cleared all dues and returned all books issued.
7.] Member can pay fine only when it has returned the book.
8.] All books irrespective of copies will have a unque id , given by the system.
9.] Member cannot issue a new book , either when it has already 2 books issued or have pending dues or it has not returned the book after 10 days or more.
10.]Member can only see available books in the library.
11.] librarian can view all available books as well as borrowed books in the library.
12.] Member can also view books issued by him .