import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
	private static Connection conn = null;


	protected static void login(String dbName, String login, String password) throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception ex) {
			System.out.println("can't load driverObject " + ex);
			return;
		}
		conn = DriverManager.getConnection(String.format("jdbc:postgresql:%s", dbName), login, password);
	}

	protected static ArrayList<String> getHeaders() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> headers = new ArrayList<String>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"select column_name\r\n" + "from INFORMATION_SCHEMA.COLUMNS\r\n" + "where TABLE_NAME='¿√≈Õ“'");

			while (rs.next()) {
				headers.add(rs.getString(1));
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
			}
		}

		return headers;

	}

	protected static Object[][] getInfo() {
		Statement stmt = null;
		ResultSet rs = null;

		List<List<Object>> inf = new ArrayList<List<Object>>();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from ¿√≈Õ“");

			while (rs.next()) {
				List<Object> row = new ArrayList<Object>();
				row.add(rs.getInt(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getInt(4));

				inf.add(row);

			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
			}
		}

		Object[][] info = new Object[inf.size()][4];
		for (int i = 0; i < inf.size(); i++) {
			for (int j = 0; j < 4; j++) {
				info[i][j] = inf.get(i).get(j);
			}
		}

		return info;
	}

	protected static Object[][] search(String field, String value) throws SQLException {
		CallableStatement cs = null;
		ResultSet rs = null;

		List<List<Object>> inf = new ArrayList<List<Object>>();

		cs = conn.prepareCall("select * from getInfo(?,?)");

		cs.clearParameters();
		cs.setString(1, field);
		cs.setString(2, value);

		rs = cs.executeQuery();

		while (rs.next()) {
			List<Object> row = new ArrayList<Object>();
			row.add(rs.getInt(1));
			row.add(rs.getString(2));
			row.add(rs.getString(3));
			row.add(rs.getInt(4));

			inf.add(row);

		}

		try {
			if (rs != null)
				rs.close();
			if (cs != null)
				cs.close();
		} catch (SQLException ex) {
		}

		Object[][] info = new Object[inf.size()][4];
		for (int i = 0; i < inf.size(); i++) {
			for (int j = 0; j < 4; j++) {
				info[i][j] = inf.get(i).get(j);
			}
		}

		return info;
	}

	protected static void textUpdate(String field, String newValue, int id) throws SQLException {
		CallableStatement cs = null;

		cs = conn.prepareCall("call textUpdate(?,?,?)");

		cs.clearParameters();
		cs.setString(1, field);
		cs.setString(2, newValue);
		cs.setInt(3, id);

		cs.execute();

		try {
			if (cs != null)
				cs.close();
		} catch (SQLException ex) {
		}

	}

	protected static void add(int id, String name, String place, int comm) throws SQLException {
		CallableStatement cs = null;

		cs = conn.prepareCall("call onInsert(?,?,?,?)");

		cs.clearParameters();
		cs.setInt(1, id);
		cs.setString(2, name);
		cs.setString(3, place);
		cs.setInt(4, comm);

		cs.execute();

		try {
			if (cs != null)
				cs.close();
		} catch (SQLException ex) {
		}
	}

	protected static void clearTable() throws SQLException {
		CallableStatement cs = null;

		cs = conn.prepareCall("call clearTable()");
		cs.execute();

		try {
			if (cs != null)
				cs.close();
		} catch (SQLException ex) {
		}
	}

	protected static void deleteRow(int id) throws SQLException {
		CallableStatement cs = null;

		cs = conn.prepareCall("call deleteRow(?)");

		cs.clearParameters();
		cs.setInt(1, id);

		cs.execute();

		try {
			if (cs != null)
				cs.close();
		} catch (SQLException ex) {
		}
	}

	protected static void deleteDb() throws SQLException {
		CallableStatement cs = null;

		cs = conn.prepareCall("call deleteDB()");
		cs.execute();

		try {
			if (cs != null)
				cs.close();
		} catch (SQLException ex) {
		}
	}

	protected static void print(Object[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(array[i][j] + "\t");
				;
			}
			System.out.println();
		}
	}

}
