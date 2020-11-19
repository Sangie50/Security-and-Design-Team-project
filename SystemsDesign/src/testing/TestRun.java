package testing;
import java.sql.*;

import userclasses.Users;

public class TestRun {
	public static void main(String[] args) throws SQLException {
		System.out.println("Hello");
		Users nameless = new Users("name", "ms", "a", "a", "password"); // creates an unassigned user
		System.out.println(nameless);
	}
}