package hr.fer.zemris.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Listaj {

	public static void main(String[] args) {

		String dbName = "votingDB";
		String connectionURL = "jdbc:derby://localhost:1527/" + dbName;

		Properties dbProperties = new Properties();

		dbProperties.setProperty("user", "ivica");
		dbProperties.setProperty("password", "ivo");

		Connection con = null;
		try {
			con = DriverManager.getConnection(connectionURL, dbProperties);
		} catch (SQLException e) {
			return;
		}
		System.out.println(con);

		PreparedStatement pst = null;

		try {
			// pst = con.prepareStatement("SELECT * FROM Poruke ORDER BY id");

			pst = con.prepareStatement("SELECT * FROM Polls ORDER BY id");
			ResultSet rset = pst.executeQuery();
			try {
				while (rset.next()) {
					System.out.println("Naziv: " + rset.getString("title"));
					System.out.println("Poruka: " + rset.getString("message"));
					/*
					 * long id = rset.getLong("id"); String title =
					 * rset.getString("title"); String message =
					 * rset.getString("message"); Date createdOn =
					 * rset.getDate("createdOn"); String userEMail =
					 * rset.getString("userEMail"); System.out.println("Zapis "
					 * + id);
					 * System.out.println("==================================="
					 * ); System.out.println("Naziv: " + title);
					 * System.out.println("Poruka: " + message);
					 * System.out.println("Stvoreno: " + createdOn);
					 * System.out.println("EMail: " + userEMail);
					 * System.out.println("");
					 */

				}
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
}
