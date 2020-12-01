package testing;
import academics.Grades;
import academics.Modules;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import features.PasswordGen;
import java.util.List;
import userclasses.Admins;
import userclasses.Students;
import userclasses.Users;
import java.sql.Date;
import java.util.HashMap;
import userclasses.Registrars;
import userclasses.Teachers;
import userclasses.Users.UserTypes;

public class TestRun {
    public static void main(String[] args) throws SQLException {
//    	Registrars reg = new Registrars("registrar", "ms", "balding", "senna", "password");
//        Users nameless = new Users("name", "ms", "a", "a", "password"); // creates an unassigned user
        Users name = new Users("nameless", "ms", "a", "a", "password");


        /*
        System.out.println("Hello");
        Date date = new Date(10/03/20);
        Students student = new Students("student", "ms", "Doe","Jane","password", "COM", 120, "DIF", date, date, "saf");
        Students student1 = new Students("stuff", "ms", "Doe","Jane Erin","password", "COM", 120, "DIF", date, date, "saf");
        System.out.println("email: " + student1.getEmail() + " reg id: " + student1.getRegistrationId());

         Useful code
       
        Users nameless = new Users("name", "ms", "a", "a", "password"); // creates an unassigned user
        Users name = new Users("nameless", "ms", "a", "a", "password");

        Admins admin = new Admins("admin", "ms", "a", "a", "password");
        



        //check password
        String salt = nameless.getSalt();
        boolean passwordMath = PasswordGen.verifyUserPassword("password", nameless.getPassword(), salt);


//        Admins.updatePermissions("nameless", UserTypes.STUDENT.toString());
        System.out.println(passwordMath + " saved pw: " + nameless.getPassword());

        //testing using data for psychology and modern language

        
        String salt = nameless.getSalt();
        boolean passwordMath = PasswordGen.verifyUserPassword("password", nameless.getPassword(), salt);
        Admins.updatePermission("nameless", UserTypes.STUDENT.toString());
        System.out.println(passwordMath + " saved pw: " + nameless.getPassword());
        Admins.addDepartment("LAN","Modern Languages","1"); 
        Admins.viewDepartment();
        Admins.addDegree("PSYP01","PSY","1","MPsy","Cognitive Science","1"); //String degreeID, String departmentID, String entryLevel, String difficulty, String degreeName, String lastLevel
        Admins.addDegreePartner("PSYP01", "COMM");
        Admins.viewDegree();
        Admins.addModule("PSY601","Cognitive Studies Seminar", 20, "PSY", 40, true); //String moduleID, String moduleName, Integer creditWorth, String departmentID, Integer passMark, Boolean isTaught
        Admins.setCoreModule("PSY601", "PSYP01", "1");
        Admins.viewCoreModule();
        List<Modules> mod = Admins.viewAllModule();
        for (Modules m : mod) {
            System.out.println(m.getModuleName());
        }*/
        //Create users, update to student, add to student table - create business student
        /*
        Admins.removeUser("infStudent");
        Admins.removeStudent("infStudent");
        Admins.removeUser("busStudent");
        Admins.removeStudent("busStudent"); 
        Admins.removeUser("sofStudent");
        Admins.removeStudent("sofStudent"); 
        Admins.removeUser("psyStudent");
        Admins.removeStudent("psyStudent"); 
        Date date1=Date.valueOf("2020-09-20");
        Date date2=Date.valueOf("2021-06-31");
        Date date3=Date.valueOf("2023-06-31");
        Date date4=Date.valueOf("2024-06-31");
        Students bstudent = new Students("busStudent", "Mr", "John", "Smith", "password", "BUSP01", 180, "Msc", date1, date2, "saf");
        Admins.updatePermission("busStudent", UserTypes.STUDENT.toString());
        Students sstudent = new Students("sofStudent", "Mr", "Johnny", "Smith", "password", "COMU01", 120, "MEng", date1, date4, "saf");
        Admins.updatePermission("sofStudent", UserTypes.STUDENT.toString());
        Students istudent = new Students("infStudent", "Mr", "Jon", "Smith", "password", "COMU02", 120, "Bsc", date1, date3, "saf");
        Admins.updatePermission("infStudent", UserTypes.STUDENT.toString());
        Students pstudent = new Students("psyStudent", "Mr", "Joe", "Smith", "password", "PSYP01", 180, "Mpsy", date1, date2, "saf");
        Admins.updatePermission("psyStudent", UserTypes.STUDENT.toString()); */
        //String email = Students.emailGen("Johnny","Smith","psyStudent");
        //String email2 = Students.emailGen("Johnny","Smith","pstudent");
        //System.out.println(email);
        //System.out.println(email2);
        
        //Adding students to modules
        //Admins.viewAllModule();
        String email = "SmithJohnny01"; //Software student 
        //Registrars.linkModuleToStudent(email, "COM1001");
        // Teachers.deleteGrades();
        
        
        
        
        /*Teachers.updateGrades("COM1001", email,75);
        Teachers.updateGrades("COM1002", email,75);
        Teachers.updateGrades("COM1003", email,75);
        Teachers.updateGrades("COM1005", email,75);
        Teachers.updateGrades("COM1006", email,75);
        Teachers.updateGrades("COM1008", email,75);
        Teachers.updateGrades("COM1009", email,75);
        Teachers.updateGrades("FCE1001", email,75);*/
        
        // registrar; generic, admin; normal, teacher; ordinary
        
        /*Users teacher = new Users("ordinary", "Mr", "Incredibly", "Generic", "password");
        Admins.updatePermission("ordinary", UserTypes.TEACHER.toString());
        Teachers.addJustTeachers("ordinary", 29, "COM");
        // Absolutely not working
        HashMap<String, Boolean> pass = Grades.passModule(email, "1");
        pass.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());  
        });
        
        • Progress these students through the levels, such that
        o the MSc student gets a conceded pass on taught modules, eventually passing MSc
        with merit;
        o the MEng student passes through all levels, including the year in industry, getting a
        1
        st class result;
        o the BSc student takes resits at level 1 and passes, but fails at level 2 resits, and also
        after repeating level 2, so is prevented from progressing;
        o the MPsy student passes through three levels, but fails catastrophically at level 4, so
        is graduated with a BSc class 2/i instead.
         */
        
        
        
        
    }
}