package testing;
import java.sql.*;

import features.PasswordGen;
//import userclasses.Admins;
import userclasses.Users;

public class TestRun {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello");
        Users nameless = new Users("name", "ms", "a", "a", "password"); // creates an unassigned user
        System.out.println(nameless);
        System.out.println(nameless.getPassword());

        //check password
        String salt = nameless.getSalt();
        boolean passwordMath = PasswordGen.verifyUserPassword("password", nameless.getPassword(), salt);

        System.out.println(passwordMath);

        //testing using data for physcology and modern language
        
        //Admins.addDepartment("PSY","Psychology","1");
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