package com.Server.Model;

import java.sql.DriverManager;
import com.Common.ServerFiles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFileUpload implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public ArrayList<ServerFiles> getUserFile(long userid) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/jdbc?" + "user=root&password=");
			statement = connect.createStatement();
			String sql = "SELECT * FROM jdbc.files" + " WHERE userid =" + userid;
			resultSet = statement.executeQuery(sql);

			ArrayList<ServerFiles> filelist = new ArrayList<ServerFiles>();
			while (resultSet.next()) {
				double id = resultSet.getDouble(1);
				String sfilename = resultSet.getString(2);
				String sfilepath = resultSet.getString(3);

				ServerFiles user = new ServerFiles(id, sfilename, sfilepath);

				filelist.add(user);
			}
			return filelist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean UploadFile(String filename, String filepath, long userid) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/jdbc?" + "user=root&password=");
			statement = connect.createStatement();

			preparedStatement = connect.prepareStatement("insert into  jdbc.files values (?, ?, ?)");
			preparedStatement.setDouble(1, userid);
			preparedStatement.setString(2, filename);
			preparedStatement.setString(3, filepath);
			preparedStatement.executeUpdate();
			System.out.println("File Uploaded");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean DownloadFile(String filename, String filepath, long userid) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/jdbc?" + "user=root&password=");
			statement = connect.createStatement();

			String sql = "SELECT * FROM jdbc.files" + " WHERE userid =" + userid;
			resultSet = statement.executeQuery(sql);
			boolean authorized = false;
			while (resultSet.next()) {
				double id = resultSet.getDouble(1);
				String sfilename = resultSet.getString(2);
				String sfilepath = resultSet.getString(3);

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", Filename: " + sfilename);
				System.out.println(", Filepath: " + sfilepath);
				if (sfilename.equals(filename)) {
					authorized = true;
				}
			}
			return authorized;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
