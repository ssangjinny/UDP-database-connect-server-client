package UDP_Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	String driver = "org.mariadb.jdbc.Driver";
	String table = "employees";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	String Database = "test";

	public Connection connection() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:5555/" + Database, "Gugu", "xptmxm");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� �ε� ����\n");
		} catch (SQLException e) {
			System.out.println("DB ���� ����\n");
			e.printStackTrace();
		}
		return con;
	}

	public String insert(String userid, String name, int grade) {
		con = connection();
		String command = "insert into " + table + " (userid, name, grade) Values(?,?,?)";
		try {
			stmt = con.prepareStatement(command);
			stmt.setString(1, userid);
			stmt.setString(2, name);
			stmt.setInt(3, grade);
			stmt.executeUpdate();
		} catch (SQLException e) {
			String msg = "�ߺ��� id�Դϴ�.";
			return msg;
		}
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return select();
	}

	public String delete(String userid) {
		con = connection();
		String command = "delete from " + table + " where userid = ?";
		try {
			stmt = con.prepareStatement(command);
			stmt.setString(1, userid);
			stmt.executeUpdate();
		} catch (SQLException e) {
			String msg = "���̺� ���� id�Դϴ�.";
			return msg;
		}
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return select();
	}

	public String select() {
		con = connection();
		StringBuilder sb = new StringBuilder();
		String command = "select * from " + table;
		try {
			stmt = con.prepareStatement(command);
			rs = stmt.executeQuery();

			sb.append("userid");
			sb.append("\t");
			sb.append("name");
			sb.append("\t");
			sb.append("grade");
			sb.append("\n");
			sb.append("������������������������������������������������\n");

			while (rs.next()) {
				sb.append(rs.getString("userid"));
				sb.append("\t");
				sb.append(rs.getString("name"));
				sb.append("\t");
				sb.append(rs.getString("grade"));
				sb.append("\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String update(String columm, String value, String columm2, String value2) {
		con = connection();
		String msg = new String();
		int i1, i2;
		String command = "update " + table + " set " + columm + "=?" + " where " + columm2 + "=?";
		if (columm.equals(columm2) && value.equals(value2)) {
			msg = "���� ������ �ٲ� �� �����ϴ�.";
			return msg;
		}
		try {
			stmt = con.prepareStatement(command);
			if (columm.equals("grade")) {
				try {
					i1 = Integer.parseInt(value);
					stmt.setInt(1, i1);
				} catch (Exception e) {
					msg = "�˸��� �ڷ����� �ƴմϴ�.";
					return msg;
				}
			} else {
				stmt.setString(1, value);
			}
			if (columm2.equals("grade")) {
				try {
					i2 = Integer.parseInt(value2);
					stmt.setInt(2, i2);
				} catch (Exception e) {
					msg = "�˸��� �ڷ����� �ƴմϴ�.";
					return msg;
				}
			} else {
				stmt.setString(2, value2);
			}
			stmt.executeUpdate();
		} catch (SQLException e) {
			msg = "�޽����� �ν����� ���߽��ϴ�.";
			return msg;
		}
		try {
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return select();
	}
}