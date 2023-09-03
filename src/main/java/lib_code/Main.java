package lib_code;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

class Book{    Scanner sc = new Scanner(System.in);
    public ArrayList<Integer> book_ids = new ArrayList<>();
    final String name;
    final long book_issued_time;
    final String author;
    private int copies;
    Book(int book_id_d){
        System.out.print("Enter Book Name : ");
        this.name = sc.nextLine().trim();
        System.out.print("Enter Author Name : ");
        this.author = sc.nextLine().trim();
        System.out.print("No. of copies available : ");
        try {
            this.copies = sc.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Wrong! , only enter Integer value!");
            System.out.print("No. of copies available : ");
            sc.nextLine();
            this.copies = sc.nextInt();

        }
        this.book_issued_time = 0;
        for(int cp = 0 ;cp<this.copies;cp++){
            this.book_ids.add(book_id_d++);
        }
        Main.book_id += copies-1;
    }
    Book(int book_id_d,String Name,String Author, long time){
        this.name = Name;
        this.author = Author;
        this.copies = 1;
        this.book_issued_time = time;
        book_ids.add(book_id_d);
    }

}
class Member{
    Scanner sc = new Scanner(System.in);
    ArrayList<Book> book_issued_by_user = new ArrayList<>();
    final String name;
    private final String age;
    final String mob;
    final int id ;

    public HashMap<Integer,String> books_issued = new HashMap<>();
    public int fine = 0;

    Member(int id){
        System.out.print("Enter Name : ");
        this.name = sc.nextLine().trim();
        System.out.print("Enter Mobile No.: ");
        this.mob = sc.nextLine().trim();
        System.out.print("Enter age : ");
        this.age = sc.nextLine().trim();
        this.id = id;
    }

}
public class Main{
    //----global------------
    static int member_id = 1;
    static int book_id = 1;
    static ArrayList<Book> book_issued = new ArrayList<>(); //book issued
    static ArrayList<Member> member = new ArrayList<>(); //memberlist
    static ArrayList<Book> book = new ArrayList<>(); //booklist
    //-----------------------------------------------------
    //librarians view codes
    //-----------------------------------------------------
    public static void librarians(){
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Register a member\n" +
                "2. Remove a member\n" +
                "3. Add a book\n" +
                "4. Remove a book\n" +
                "5. View all members along with their books and fines to be paid\n" +
                "6. View all books\n" +
                "7. Back");
        System.out.println("-----------------------------------");
        try {
            int n = sc.nextInt();
            if(n==1){
                System.out.println("-----------------------------------");
                System.out.println("Register a Member");
                System.out.println("-----------------------------------");
                Member m = new Member(member_id++);
                if(check_if_already_registered(m)) {
                    member.add(m);
                    System.out.println("Member Successfully Registered with Member ID - "+m.id + " !");
                    System.out.println("-----------------------------------");


                }
                else{
                    member_id--;
                    System.out.println("Mobile Number Already Registered ! ");
                    System.out.println("-----------------------------------");

                }
                librarians();
            }
            else if(n==2){
                System.out.println("-----------------------------------");
                System.out.println("Remove a Member ");
                System.out.println("-----------------------------------");
                boolean removed = false;
                System.out.print("Enter Name : ");
                sc.nextLine();
                String Name = sc.nextLine().trim();
                System.out.print("Enter Id : ");
                int id = sc.nextInt();
                for(int m1 = 0 ;m1<member.size();m1++){
                    if(member.get(m1).name.compareTo(Name)==0 && member.get(m1).id==id){
                        if(member.get(m1).book_issued_by_user.isEmpty() && member.get(m1).fine==0){
                            member.remove(m1);
                            System.out.println("Member with Name : " + Name + " and ID: " + id + " removed Successfully !");
                            System.out.println("-----------------------------------");
                            removed = true;
                            break;}
                        else{
                            System.out.println("Member can't be removed. ");
                            System.out.println("Reason - Member has books_issued / previous Dues.");
                            System.out.println("-----------------------------------");
                            removed = true;
                            break;

                        }
                    }
                }
                if(!removed){
                    System.out.println("Member can't be found!");
                    System.out.println("-----------------------------------");

                }
                librarians();

            }
            else if(n==3){
                System.out.println("-----------------------------------");
                System.out.println("Add a Book");
                System.out.println("-----------------------------------");
                Book b = new Book(book_id++);
                book.add(b);
                System.out.println("Book Added Successfully!");
                System.out.println("-----------------------------------");
                librarians();

            }
            else if(n==4){
                boolean deleted = false;
                System.out.println("-----------------------------------");
                System.out.println("Remove a Book");
                System.out.println("-----------------------------------");
                System.out.print("Enter Id: ");
                int id = sc.nextInt();
                if(check_if_book_issued(id)){
                    System.out.println("Book can't be deleted!");
                    System.out.println("Reason - it is issued to a member!");
                }
                else{
                    for(int bii = 0 ; bii<book.size();bii++){
                        if(book.get(bii).book_ids.contains(id)){
                            book.get(bii).book_ids.remove(book.get(bii).book_ids.indexOf(id));
                            deleted = true;
                            break;
                        }
                    }
                    if(deleted){
                        System.out.println("Book with id : " + id + " removed successfully!");
                    }
                    else if(!deleted){
                        System.out.println("Book not available wit id -: " + id + " !");
                    }

                }
                System.out.println("-----------------------------------");
                librarians();

            }
            else if(n==5){
                int i =0;
                System.out.println("-----------------------------------");
                System.out.println("Member ID " + "    " + " Books Issued " + "   "+ " Fine ");
                long current_time = System.currentTimeMillis()/1000;
                while(i<member.size()){
                    System.out.println("    "+member.get(i).id + "              "+member.get(i).books_issued.keySet() + "             " +                     calculate_fine_dont_update(member.get(i),current_time));
                    i++;
                }
                System.out.println("-----------------------------------");

                librarians();

            }

            else if(n==6) {
                System.out.println("-----------------------------------");
                System.out.println("Available Books : ");
                print_available_books();
                System.out.println("-----------------------------------");
                if (!book_issued.isEmpty()) {
                    System.out.println("Borrowed Books : ");

                    for(int bi = 0; bi < book_issued.size(); bi++) {
                        System.out.println("Book Id : " + book_issued.get(bi).book_ids.get(0));
                        System.out.println("Book Title : " + book_issued.get(bi).name);
                        System.out.println("Author : " + book_issued.get(bi).author);
                        System.out.println("-----------------------------------");
                    }
                }
                librarians();
            }
            else if(n==7){
                starting();
            }
            else{
                System.out.println("Invalid Option,Try Again!");
                System.out.println("-----------------------------------");
                librarians();
            }
        }
        catch (InputMismatchException e){
            System.out.println("Wrong input!");
            System.out.println("-----------------------------------");

            librarians();
        }

    }
    //------------------------------
    //librarian helper functions
    //------------------------------
    public static boolean check_if_already_registered(Member m){  //checking if mobile no. already registered
        for(int bi = 0 ;bi< member.size();bi++){
            if(m.mob.compareTo(member.get(bi).mob) == 0){
                return false;
            }
        }
        return true;

    }
    public static void print_available_books() { //printing available books in library
        int pab = 0;
        while(pab< book.size()) {
            for(int bi = 0 ; bi<book.get(pab).book_ids.size();bi++){
                System.out.println("Book Id : " + book.get(pab).book_ids.get(bi));
                System.out.println("Book Title : " + book.get(pab).name);
                System.out.println("Author : " + book.get(pab).author);
                System.out.println("-----------------------------------");

            }
            pab++;
    }}

    public static void Pay_fine(Member user){ //fine payment
        System.out.println("You had a total fine of Rs. " + user.fine +" It has been paid successfully!");
    }
    public static void calculate_fine(Member user, long time,int book_id_f){ //calculating and updating user.fine (in issue book)
        for(int b = 0 ; b<user.book_issued_by_user.size() ; b++ ){
            if(user.book_issued_by_user.get(b).book_ids.get(0)==book_id_f && time - user.book_issued_by_user.get(b).book_issued_time >10){
                user.fine += (int)(time - user.book_issued_by_user.get(b).book_issued_time -10)*3;

        }
    }}
    public static int calculate_fine_dont_update(Member user, long time){  //calculating fine but not updating (in view members / issue new book)
        int k = user.fine;
        for(int b = 0 ; b<user.book_issued_by_user.size() ; b++ ){
            if(time - user.book_issued_by_user.get(b).book_issued_time >10){
                k += (int)(time - user.book_issued_by_user.get(b).book_issued_time -10)*3;
            }
        }
    return k;
    }
   
    public static boolean check_if_book_issued(int id){  //checking if book is already issued or not
        for(int bii = 0 ; bii< book_issued.size(); bii++){
            if(book_issued.get(bii).book_ids.get(0) == id){
                return true;
            }
        }
        return false;
    }

    public static Member login(int member_id_login,String Name){ //checking login credentials for a member
        for(int i = 0 ; i<member.size();i++){
            if(member.get(i).id == member_id_login && (member.get(i).name).compareTo(Name)==0){
                return member.get(i);
            }
        }
        Member m = null;
        return m;
    }
    public static Book check_if_book_available(String book_name , int id){ //checking if book exists in library
        for(int bs = 0 ; bs<book.size();bs++){
            if(book_name.compareTo(book.get(bs).name)==0){
                for(int n = 0 ; n<book.get(bs).book_ids.size();n++) {
                    if (book.get(bs).book_ids.get(n) == id) {
                        return book.get(bs);
                    }
                }
            }
        }
        Book b1 = null; //if book not exists, returning a null book.
        return b1;
    }
    public static void issue_book(Book b1 ,Member user,int id){ //issuing a book
        user.books_issued.put(id,b1.name);
        b1.book_ids.remove(b1.book_ids.indexOf(id));
        long current_time = System.currentTimeMillis()/1000;
        Book book_issue = new Book(id,b1.name, b1.author,current_time);
        book_issued.add(book_issue);
        user.book_issued_by_user.add(book_issue);

    }
    public static void member_logged(Member user){ //member interface after logging in.
        long current_time = System.currentTimeMillis()/1000;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome " +  user.name + " Member ID " + user.id);
        System.out.println("---------------------------------");
        System.out.println("1. List Available Books\n" +
                "2. List My Books\n" +
                "3. Issue book\n" +
                "4. Return book\n" +
                "5. Pay Fine\n" +
                "6. Back");
        System.out.println("---------------------------------");
        try {
            int n = sc.nextInt();
            if (n == 1) {
                System.out.println("-----------------------------------");
                System.out.println("Available Books ");
                System.out.println("-----------------------------------");
                print_available_books();
                member_logged(user);
            } else if (n == 2) {
                System.out.println("-----------------------------------");
                System.out.println("My Issued Books  : ");
                System.out.println("-----------------------------------");
                System.out.println(user.books_issued.keySet());
                member_logged(user);
            } else if (n == 3) {
                System.out.println("-----------------------------------");
                System.out.println("Issue Book");
                System.out.println("-----------------------------------");
                if (user.fine==0 && calculate_fine_dont_update(user, current_time)==0 && user.book_issued_by_user.size() < 2) {
                    System.out.print("Enter Book Name : ");
                    sc.nextLine();
                    String book_name = sc.nextLine().trim();
                    System.out.print("Enter book_id : ");
                    int book_id_s = sc.nextInt();
                    Book b1 = check_if_book_available(book_name, book_id_s);
                    if (b1 != null) {
                        issue_book(b1, user, book_id_s);
                        System.out.println("Book Name : " + book_name + " and ID : " + book_id_s + " issued successfully.");
                        System.out.println("-------------------------------------");
                    } else {
                        System.out.println("Sorry! , Book Name : " + book_name + " with ID : " + book_id_s + " is not available !");
                        System.out.println("--------------------------------");

                    }
                } else {
                    System.out.println("Fine pending or Max no. of books already issued!");
                    System.out.println("--------------------------------");

                }

                member_logged(user);
            } else if (n == 4) {
                boolean returned = false;
                    System.out.println("-----------------------------------");
                    System.out.println("Return Book ");
                    System.out.println("-----------------------------------");
                    System.out.print("Enter Book Id : ");
                    int book_id_s = sc.nextInt();
                    calculate_fine(user, current_time,book_id_s);

                    for (int b1 = 0; b1 < user.book_issued_by_user.size(); b1++) { //removing book from book_issued_by_user
                        if (book_id_s == user.book_issued_by_user.get(b1).book_ids.get(0)) {
                            for (int j = 0; j < book_issued.size(); j++) {
                                if (book_issued.get(j).book_ids.get(0) == book_id_s) {
                                    book_issued.remove(j);
                                }
                            }
                            for (int j = 0; j < book.size(); j++) {
                                if (book.get(j).name.compareTo(user.book_issued_by_user.get(b1).name) == 0 && book.get(j).author.compareTo(user.book_issued_by_user.get(b1).author)==0) {
                                    book.get(j).book_ids.add(book_id_s);
                                    break;
                                }
                            }
                            user.book_issued_by_user.remove(b1);
                            user.books_issued.remove(book_id_s);
                            returned = true;
                            System.out.println("Book with ID : " + book_id_s + " returned Successfully! ");
                            System.out.println("--------------------------------");
                            if(user.fine!=0){
                                System.out.println("You have been charged with Rs. " + user.fine + " for delay in returning the book by "   + (int)user.fine/3 + " days.");
                                System.out.println("--------------------------------");
                            }
                            break;
                        }
                    }
                    if (returned){
                        System.out.println("Book returned Successfully !");
                    } else {
                        System.out.println("Book with ID : " + book_id_s + " not issued!");
                        System.out.println("-----------------------------------");

                    }
                    member_logged(user);
            } else if (n == 5) {
                if (user.fine == 0) {
                    System.out.println("All Dues Cleared! No fine can be found!");
                    System.out.println("-----------------------------------");
                } else {
                    Pay_fine(user);
                    user.fine = 0;
                }
                member_logged(user);


            } else if (n == 6) {
                System.out.println("-----------------------------------");
                starting();
            } else {
                System.out.println("Wrong choice, Try Again !");
                System.out.println("-----------------------------------");
                member_logged(user);
            }
        }
        catch (InputMismatchException e){
            System.out.println("Wrong choice, Try Again !");
            System.out.println("-----------------------------------");
            member_logged(user);
        }
    }
    public static void members(){
        try {


        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Name : ");
        String Name = sc.nextLine().trim();
        System.out.print("Enter ID : ");
        int member_id_login = sc.nextInt();

        Member user = login(member_id_login,Name);
        if(user!=null){
            member_logged(user);
        }
        else{
            System.out.println("Member with Name : " + Name + " and ID : " + member_id_login + " does not exist! ");
            starting();
        }}
        catch (InputMismatchException e){
            System.out.println("Wrong Input Type! , Try Again");
            members();
        }
        return;
    }
    public static void exit(){
        System.out.println("Thanks for visiting!");
        System.out.println("-----------------------------------");
        return;
    }
    public static void starting(){
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Enter as a Librarian");
        System.out.println("2. Enter as a Member");
        System.out.println("3. Exit");
        try{
        int n = sc.nextInt();
        if(n==1){
            System.out.println("-----------------------------------");
            System.out.println("Entered As librarian");
            System.out.println("-----------------------------------");
            librarians();
        }
        else if(n==2){
            System.out.println("-----------------------------------");
            System.out.println("Login as a Member");
            System.out.println("-----------------------------------");
            members();
        }
        else if(n==3){
            System.out.println("-----------------------------------");
            exit();
            return;
        }
        else{
            System.out.println("Invalid Option,Try Again!");
            System.out.println("-----------------------------------");
            starting();
        }}
        catch (InputMismatchException e){
            System.out.println("Wrong Input, Try Again!");
            System.out.println("-----------------------------------");
            starting();
        }
    }
    public static void main(String[] args) {
        System.out.println("Library Portal initialised.....");
        System.out.println("-----------------------------------");
        starting();
        return;
    }
}