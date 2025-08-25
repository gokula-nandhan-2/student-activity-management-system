import java.util.*;
import java.io.*;

public class Program {

    //maximum places to register students
    static Student[] studentArray = new Student[100];
    //count of the registered students
    static int studentCount = 0;



    /*the following method calculates the available seats to register students
     which means the total count of remaining index in student array*/
    public static void availableSeats() {
        int availableSeats = studentArray.length - studentCount;
        System.out.println("\n" + availableSeats + " seat(s) available!");
    }




    /*this method checks the registered student count with comparing to length
     of student array.if there is a place in student array the entered details will
     be stored.Otherwise prints error message*/
    public static void registerStudent() {
        Scanner studentDetails = new Scanner(System.in);
        try {
            if (studentCount <= studentArray.length) {
                //gets user inputs to store Name and ID
                System.out.print("Enter student name :");
                String studentName = studentDetails.nextLine();

                System.out.print("Enter student ID (e.g.w1234567) :");
                String studentID = studentDetails.nextLine();

                //creates new student object and stores inputs to the studentArray
                Student student = new Student(studentID, studentName);
                studentArray[studentCount] = student;
                System.out.println("Student registered successfully!");
                studentCount++;
                storeDetails();
            }
        } catch (Exception ex1) {
            System.out.println(ex1);
        }
    }




    /*the method prompts the student id from user and checks from student array
     to delete.if it is there,student delails will be deleted from the array and
     student count decreased.otherwise prints error message*/
    public static void deleteStudent() {
        //gets input as student ID and checks
        Scanner deleteStudent = new Scanner(System.in);
        System.out.print("Enter student ID :");
        String deleteDetails = deleteStudent.nextLine();

        //to detect if the student found for deletion
        boolean found=false;

        //deletes the student details and decreases the student count
        for (int i = 0; i < studentCount; i++) {
            if (studentArray[i].getStudentID().equals(deleteDetails)) {
                studentArray[i] = null;
                found=true;

                //shift the elements to the left index
                for (int j = i; j < studentCount - 1; j++) {
                    studentArray[j] = studentArray[j + 1];
                }
                studentArray[studentCount - 1] = null;
                studentCount--;
                System.out.println("Student details deleted successfully!");
                storeDetails();
                break;
            }
        }
        //to print the error message if student not identified
        if(!found){
            System.out.print("Please enter correct Student ID!");
        }
    }




    /*this method prompts the student ID to genarate the details of the relavent
     student.if it available from student array,the details will be displayed.
     otherwise prints error message*/
    public static void findStudent() {
        //gets input as student ID to check the student is already registered from the system
        Scanner findStudent = new Scanner(System.in);
        System.out.print("Enter student ID :");
        String findDetails = findStudent.nextLine();

        //to track the student was found
        boolean found=false;

        //prints the details of found student or not found
        for (int i = 0; i < studentCount; i++) {
            if (studentArray[i].getStudentID().equals(findDetails)) {
                System.out.println("\nStudent details as follows,");
                System.out.println("Student name :" + studentArray[i].getStudentName());
                System.out.print("Student ID :" + studentArray[i].getStudentID());
                found=true;
                break;
            }
        }
        //prints the error if the student not found
        if(!found){
            System.out.println("Student ID not found!");
        }
    }



    /*the method stores the details from the student array to another text file.if
     there is a problem to write the file the error message will be displayed*/
    public static void storeDetails() {
        try {
            //creates a new text file calls studentDetails.txt as file
            File writeFile = new File("StudentDetails.txt");
            boolean fileCreated = writeFile.createNewFile();
            FileWriter storedDetails = new FileWriter("StudentDetails.txt");

            for (int i = 0; i < studentCount; i++) {
                if (studentArray[i] == null) {
                    continue;
                } else {
                    //writes the student name and ID to the file
                    storedDetails.write(studentArray[i].getStudentID() + " ");
                    storedDetails.write(studentArray[i].getStudentName() + " ");

                    //get the module marks to write
                    int[] modulemarks=studentArray[i].getModule().getModuleMarks();
                    storedDetails.write(modulemarks[0]+" ");
                    storedDetails.write(modulemarks[1]+" ");
                    storedDetails.write(+modulemarks[2]+"\n");
                }
            }
            //close the filewriter after writing
            storedDetails.close();
            System.out.println("Details stored Successfully!");

        } catch (IOException ex2) {
            System.out.println("Something went wrong in store details!");
        }
    }




    /*the method loads the stored details from the text file to the system.
     if there is any problem to load file,error message will be displayed*/
    public static void loadDetails() {
        try {
            //creates a file object to read from text file
            File readFile = new File("StudentDetails.txt");
            Scanner toReadFile = new Scanner(readFile);

            //read the text file line by line
            while (toReadFile.hasNextLine()) {
                String fileData = toReadFile.nextLine();
                //split each line into an array
                String[] dataArray = fileData.split(" ");

                //checks the data array has minimum 5 elements
                if (dataArray.length >= 5) {
                    //inisialize variables from data array to store
                    String studentID = dataArray[0];
                    String studentName = dataArray[1];
                    int moduleMark1=Integer.parseInt(dataArray[2]);
                    int moduleMark2=Integer.parseInt(dataArray[3]);
                    int moduleMark3=Integer.parseInt(dataArray[4]);

                    //stores the element to an object
                    Student loadStudent= new Student(studentID, studentName);
                    loadStudent.getModule().setModuleMarks1(moduleMark1);
                    loadStudent.getModule().setModuleMarks2(moduleMark2);
                    loadStudent.getModule().setModuleMarks3(moduleMark3);

                    //add the student details to student array
                    studentArray[studentCount]=loadStudent;
                    studentCount++;
                }
            }
            //close the scanner after readings
            toReadFile.close();
            System.out.print("Details loaded successfully!");
        } catch (IOException ex3) {
            System.out.println("Something went wrong with load file to system!");
        }
    }




    /*the method sort the list of students by their name's alphabetical order
     and displays the student ID and name */
    public static void viewList() {
        //checks if there are students available to display
        if (studentCount > 0) {
            System.out.println("\n------Student Details------");
            Student swapElement;

            //bubble sort algorithm for student details by the student name alphebatical order
            for (int i = 0; i < studentCount; i++) {
                for (int j = 0; j < studentCount - i - 1; j++) {
                    //compares the studentName elements to sort
                    if (studentArray[j].getStudentName().compareTo(studentArray[j + 1].getStudentName()) > 0) {
                        //swaps the elements to the alphabetical ascending order of StudentName
                        swapElement = studentArray[j];
                        studentArray[j] = studentArray[j + 1];
                        studentArray[j + 1] = swapElement;
                    } else {
                        continue;
                    }
                }
            }

            //prints the sorted list of students details
            for (int i = 0; i < studentCount; i++) {
                System.out.println("\nStudent No." + (i + 1));
                System.out.println("Student Name :" + studentArray[i].getStudentName());
                System.out.println("Student ID :" + studentArray[i].getStudentID());
            }
        }else{
            System.out.print("No more students to list out!");
        }
    }




    //submenu for another list of choices to generate summary of the system and student progress
    private static void additionalOptions() {
        //variable for subchoice selection
        String subChoice;

        //iterates until the choice equals to "e"
        do{
            Scanner subMenuScanner = new Scanner(System.in);
            System.out.print("""
                    \n
                    \n
                     ******** ADDITIONAL OPTIONS ********
                                        
                    Choices as follows,
                                        
                    a - Recorrect name
                    b - Add module marks for Students
                    c - Summary of Student Activity Management System
                    d - Progress Report of the students
                    e - Main Menu
                                        
                    Enter choice :""");

            //stores chosen choice with the lowercase letter
            subChoice = subMenuScanner.nextLine().toLowerCase();

            switch (subChoice) {
                case "a":
                    addStudent();
                    break;
                case "b":
                    addModuleMarks();
                    break;
                case "c":
                    systemSummary();
                    break;
                case "d":
                    studentSummary();
                    break;
                case "e":
                    break;
                default:
                    System.out.print("Please enter a valid choice!");
            }
        }while(!(subChoice.equals("e")));

    }




    /*the following method can update the student name when the registered name incorrect.
    it takes student id as input.if the id matched user can update the name.otherwise system
    prints the error message*/
    public static void addStudent() {

        Scanner toCheck = new Scanner(System.in);

        System.out.print("Enter student ID :");
        String checkID = toCheck.nextLine();
        boolean found=false;

        //checks student id of stunet with iteration
        for (int i = 0; i < studentCount; i++) {
            if (studentArray[i].getStudentID().equals(checkID)) {
                System.out.print("Enter student name :");
                String updatedName = toCheck.nextLine();
                studentArray[i].setStudentName(updatedName);
                found=true;
                System.out.println("Student name entered successfully!");
                storeDetails();
            }
        }
        if(!found){
            System.out.print("Student not found!");
        }

    }




    /*the method get user input as student id.if the student id is available from
    student array the marks for every three modules will be prompted from user and stored*/
    public static void addModuleMarks(){
        try {
            Scanner addMarks = new Scanner(System.in);
            boolean found=false;

            System.out.print("Enter student ID :");
            String ID = addMarks.nextLine();


            //iteration for each student from array
            for (int i = 0; i < studentCount; i++) {
                if (studentArray[i].getStudentID().equals(ID)) {

                    //initialize variables for marks validation
                    int module1Marks=101;
                    int module2Marks=101;
                    int module3Marks=101;

                    //validation of marks for module 1
                    while (0 > module1Marks || 100 < module1Marks) {
                        System.out.print("Enter marks for Module-1 :");
                        module1Marks = addMarks.nextInt();

                        if(module1Marks<0 || module1Marks>100){
                            System.out.println("\nInvalid marks!The marks should be between 0 to 100!");
                        }
                    }
                    //stores the module 1 marks student array
                    studentArray[i].getModule().setModuleMarks1(module1Marks);


                    //validation of marks for module 2
                    while (0 > module2Marks || 100 < module2Marks) {
                        System.out.print("Enter marks for Module-2 :");
                        module2Marks = addMarks.nextInt();

                        if(module2Marks<0 || module2Marks>100){
                            System.out.println("\nInvalid marks!The marks should be between 0 to 100!");
                        }
                    }
                    //stores the module 2 marks to student array
                    studentArray[i].getModule().setModuleMarks2(module2Marks);

                    //validation of marks for module 3
                    while (0 > module3Marks || 100 < module3Marks) {
                        System.out.print("Enter marks for Module-3 :");
                        module3Marks = addMarks.nextInt();

                        if(module3Marks<0 || module3Marks>100){
                            System.out.println("\nInvalid marks!The marks should be between 0 to 100!");
                        }
                    }
                    //stores the module 3 marks to student array
                    studentArray[i].getModule().setModuleMarks3(module3Marks);

                    found=true;
                    storeDetails();
                    break;
                }
            }
            if(!found){
                System.out.print("Invalid Student ID!");
            }
        }catch (Exception ex5){
            System.out.print("Error!");
        }
    }




    /*this method provides the complete progress report of the students
     and the system summary*/
    public static void systemSummary(){
        //print the summary of the system
        System.out.println("\n");
        System.out.println("\nSummary of the system");
        System.out.println("-----------------------------");

        System.out.println("    The system can store 100 students at one time.");
        System.out.println("    "+studentCount+" Student(s) already registered to the system.");


        //initialize counters for module summary
        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;

        //iteration for every registered  students
        for(int i=0;i<studentCount;i++){
            int[] marks=studentArray[i].getModule().getModuleMarks();
            boolean validMarks=false;



            //count students scoring above 40 in each and every modules
            if(marks[0]>40){
                count1++;
            }
            if(marks[1]>40){
                count2++;
            }
            if(marks[2]>40){
                count3++;
            }
            if(marks[0]>40 && marks[1]>40 && marks[2]>40){
                count4++;
            }
        }

        //prints the summary of module marks
        System.out.println("\nSummary of module marks");
        System.out.println("-----------------------------");

        System.out.print("    Total count of students scored above 40 marks in module 1     : ");
        System.out.print(count1);

        System.out.print("\n    Total count of students scored above 40 marks in module 2     : ");
        System.out.print(count2);

        System.out.print("\n    Total count of students scored above 40 marks in module 3     : ");
        System.out.print(count3);

        System.out.print("\n    Total count of students scored above 40 marks in all modules  : ");
        System.out.print(count4);
    }




    /*the method gets the student details from student array and displays
     the student summary.if there are no students registered prints
     the relevant message*/
    public static void studentSummary(){
        if (studentCount > 0) {
            System.out.println("\n");
            System.out.println("\n      Student Progress Report");
            System.out.print("...................................");

            //intialize temporary variable to swap
            Student swapAverage;

            //bubble sort algorithm for student details by the average discending order
            for (int i = 0; i < studentCount - 1; i++) {
                for (int j = 0; j < studentCount - i - 1; j++) {
                    if (studentArray[j].getModule().average() < studentArray[j + 1].getModule().average()) {
                        //swaps the elements to the discending order of the average
                        swapAverage = studentArray[j];
                        studentArray[j] = studentArray[j + 1];
                        studentArray[j + 1] = swapAverage;
                    }
                }
            }

            //prints the sorted list of students details
            for (int i = 0; i < studentCount; i++) {
                System.out.println("");
                System.out.println("\nStudent No." + (i + 1));

                //stores the student detail by initializing variables and array
                int total=studentArray[i].getModule().total();
                float average=studentArray[i].getModule().average();
                String grade=studentArray[i].getModule().grade();
                int[] moduleMarks=studentArray[i].getModule().getModuleMarks();

                //prints complete student progress report for each student
                System.out.print("Student Name        :");
                System.out.println(studentArray[i].getStudentName());

                System.out.print("Student ID          :");
                System.out.println(studentArray[i].getStudentID());

                System.out.print("Marks of Module 01  :");
                System.out.println(moduleMarks[0]);

                System.out.print("Marks of Module 02  :");
                System.out.println(moduleMarks[1]);

                System.out.print("Marks of Module 03  :");
                System.out.println(moduleMarks[2]);

                System.out.print("Total               :");
                System.out.println(total);

                System.out.print("Average             :");
                System.out.println(average);

                System.out.print("Grade               :");
                System.out.println(grade);
            }
        }
        else{
            //print the message for the empty stuent details
            System.out.print("No students to genarate progress report!");
        }
    }




    /*method for main menu of the programme.if there are any issue with user
     input,the exception message will be displayed and iterates until exit*/
    public static void main (String[] args){
        int choice = 0;
        while (choice != 9) {
            Scanner mainMenuScanner = new Scanner(System.in);
            System.out.print("""
                        \n
                        =========================================================
                                    STUDENT ACTIVITY MANAGEMENT SYSTEM 
                        =========================================================                    
                        
                        
                       Numbers of the choices as follows,
                                            
                        1 - Check available seats
                        2 - Register student(With student ID)
                        3 - Delete student
                        4 - Find student(With student ID)
                        5 - Store student details into a file
                        6 - Load student details from the file to the system
                        7 - View the list of students based on their names
                        8 - Additional options
                        9 - Exit
                                            
                       Enter number of the  choice :""");

            try {
                choice = mainMenuScanner.nextInt();

                switch (choice) {
                    case 1:
                        availableSeats();
                        break;
                    case 2:
                        registerStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        findStudent();
                        break;
                    case 5:
                        storeDetails();
                        break;
                    case 6:
                        loadDetails();
                        break;
                    case 7:
                        viewList();
                        break;
                    case 8:
                        additionalOptions();
                        break;
                    case 9:
                        System.out.println("The programme is being terminated!");
                        break;
                    default:
                        System.out.println("Please enter a valid choice!");
                }
            } catch (Exception ex4) {
                System.out.print("Invalid input.Please try again!");
            }
        }
    }
}


