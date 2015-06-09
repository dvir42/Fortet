package user.golf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class that helps connect to an SQL database and update it / retrieve
 * information from it
 * 
 * @author dvir42
 *
 */
public class Database {

	private Connection con;

	public Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/fortet", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function performs one direction SQL commands
	 * 
	 * @param command
	 * @return number of updated rows
	 */
	public int insertUpdateDelete(String command) {
		int num = 0;
		try {
			Statement st = con.createStatement();
			num = st.executeUpdate(command);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * This function performs Select SQL commands
	 * 
	 * @param command
	 * @return result array of the data
	 */
	public String[][] select(String command) {
		String result[][] = null;
		try {
			Statement st = con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet res = st.executeQuery(command);
			int m = res.getMetaData().getColumnCount();
			res.last();
			int n = res.getRow();
			result = new String[n][m];
			res.beforeFirst();
			int i = 0;
			while (res.next()) {
				for (int j = 0; j < m; j++)
					result[i][j] = res.getString(j + 1);
				i++;
			}
			res.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
