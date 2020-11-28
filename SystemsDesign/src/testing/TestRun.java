package testing;
import academics.Modules;
import java.sql.*;

import features.PasswordGen;
import java.util.List;
import userclasses.Admins;
import userclasses.Users;
import userclasses.Users.UserTypes;

public class TestRun {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello");
        //Users nameless = new Users("name", "ms", "a", "a", "password"); // creates an unassigned user
        //Users name = new Users("nameless", "ms", "a", "a", "password");
        //Admins admin = new Admins("admin", "ms", "a", "a", "password");
        //System.out.println(nameless);
        //System.out.println(nameless.getPassword());

        //check password
        //String salt = nameless.getSalt();
        //boolean passwordMath = PasswordGen.verifyUserPassword("password", nameless.getPassword(), salt);

        //Admins.updatePermissions("nameless",UserTypes.STUDENT.toString());
        //System.out.println(passwordMath + " saved pw: " + nameless.getPassword());

        //testing using data for psychology and modern language
        
        //-Adding department works 
        
        /* Useful code
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
        }
        */
        Admins.addModule("MGT388","Finance and Law for Engineers", 10, "COM", 40, true);
        Admins.setCoreModule("MGT388", "COM", "1");
        Admins.viewAllModule();
        //Admins.removeModule("COM2108");
        
        /*
        
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