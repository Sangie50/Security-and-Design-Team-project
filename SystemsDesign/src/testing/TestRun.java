package testing;
import academics.Modules;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import features.PasswordGen;
import java.util.List;
import userclasses.Admins;
import userclasses.Students;
import userclasses.Users;
import userclasses.Users.UserTypes;
import java.sql.Date;
import userclasses.Registrars;

public class TestRun {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello");
        /* Useful code
        Date date = new Date(10/03/20);
        Users nameless = new Users("name", "ms", "a", "a", "password"); // creates an unassigned user
        Users name = new Users("nameless", "ms", "a", "a", "password");
        Admins admin = new Admins("admin", "ms", "a", "a", "password");
        Students student = new Students("nameless", "ms", "a","a","password", 1234, "COM", 120, "DIF", date, date, "saf");




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
        
        
        Students pstudent = new Students("psyStudent", "Mr", "Johnny", "Smith", "password", "PSYP01", 180, "Mpsy", date1, date2, "saf");
        Admins.updatePermission("psyStudent", UserTypes.STUDENT.toString());
        
        //Adding students to modules
        //Admins.viewAllModule();
        //String email = "SmithJon01"; // info studies
        //Registrars.linkModuleToStudent(email, "COM1001");
        
        //String email = Students.emailGen("Johnny","Smith","psyStudent");
        //String email2 = Students.emailGen("Johnny","Smith","pstudent");
        //System.out.println(email);
        //System.out.println(email2);
        
        /*
        
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