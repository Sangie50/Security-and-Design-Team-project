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

public class TestRun {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello");
        
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

        //testing using data for physcology and modern language
        
        //-Adding department works 
        //Admins.addDepartment("LAN","Modern Languages","1"); 
        Admins.viewDepartment(); //works but need a way to get stuff from objects
        // Adding degree works
        //Admins.addDegree("PSYP01","PSY","1","MPsy","Cognitive Science","1"); //String degreeID, String departmentID, String entryLevel, String difficulty, String degreeName
        //Add partners for degree
        //Admins.addDegreePartner("PSYP01", "COMM");
        Admins.viewDegree();
        //Add module works 
        //Admins.addModule("PSY601","Cognitive Studies Seminar", 1, 20, "PSY", 40);
        //Core modules works
        //Admins.setCoreModule("PSY601", "PSYP01", true);
        Admins.viewCoreModule();
        List<Modules> mod = Admins.viewAllModule();
        for (Modules m : mod) {
            System.out.println(m.getModuleName()); // i really don't understand what is happening
        }
        /*
        Add the four departments Business School (BUS), Computer Science (COM), Psychology
        (PSY) and Modern Languages (LAN).
        • Add the degrees MSc in Business Administration (lead BUS), MEng Software Engineering
        with a Year in Industry (lead COM), BSc Information Systems (lead COM, partners BUS, LAN),
        MPsy Cognitive Science (lead PSY, partner COM).
        • Add realistic core and optional modules for each of the above degrees (take inspiration from
        the Sheffield University module-guide), ensuring that modules are supplied by all relevant
        partner-departments; the MSc degree will be all-core, but other degrees will have 20cr free
        choice at level 1, all-core at level 2, and 40cr free choice at levels 3 and 4.
        • Register a student for each of the above degrees, and select both suitable and unsuitable
        options for their free-choice modules, showing how your system prevents administrators
        from picking the wrong modules and checks that a student’s credit-totals are correct.
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