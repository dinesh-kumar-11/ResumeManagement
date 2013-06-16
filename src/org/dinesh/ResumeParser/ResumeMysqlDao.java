package org.dinesh.ResumeParser;

import java.sql.DatabaseMetaData;

import java.sql.Timestamp;
import java.util.Date;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResumeMysqlDao {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/resume_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private Connection mysqlDbConnection;

    public boolean isTableExists(String tableName) throws SQLException {
	if (mysqlDbConnection != null) {
	    DatabaseMetaData dbm = mysqlDbConnection.getMetaData();
	    // check if "employee" table is there
	    ResultSet tables = dbm.getTables(null, null, tableName, null);
	    if (tables.next()) {
		return true;
	    } else {
		return false;
	    }
	}
	return false;
    }

    public static ResumeMysqlDao getResume() {
	return new ResumeMysqlDao();
    }

    public ResumeMysqlDao() {
	mysqlDbConnection = getDBConnection();
	if (mysqlDbConnection == null) {

	}
    }

    public void closeConnection() {
	try {
	    if (mysqlDbConnection != null) {
		mysqlDbConnection.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    protected void finalize() {
	try {
	    if (mysqlDbConnection != null) {
		mysqlDbConnection.close();
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public int insertBatchResume(String filename) throws SQLException {
	PreparedStatement preparedStatementInsert = null;
	String insertResume = "INSERT INTO  `resume`"
		+ "(`filename` ,`created` ,`changed`) VALUES" + "(?,?,?)";
	ResultSet generatedKeys = null;
	try {
	    preparedStatementInsert = mysqlDbConnection.prepareStatement(
		    insertResume, Statement.RETURN_GENERATED_KEYS);
	    preparedStatementInsert.setString(1, filename);
	    preparedStatementInsert.setLong(2, getCurrentTimeStamp());
	    preparedStatementInsert.setLong(3, getCurrentTimeStamp());
	    preparedStatementInsert.executeUpdate();
	    generatedKeys = preparedStatementInsert.getGeneratedKeys();
	    if (generatedKeys.next()) {
		return (int) generatedKeys.getLong(1);
	    }
	} finally {
	    if (generatedKeys != null)
		try {
		    generatedKeys.close();
		} catch (SQLException logOrIgnore) {
		}
	    if (preparedStatementInsert != null)
		try {
		    preparedStatementInsert.close();
		} catch (SQLException logOrIgnore) {
		}
	}

	return (Integer) null;
    }
    
    
    public int insertResume(String filename) throws SQLException {
	PreparedStatement preparedStatementInsert = null;
	String insertResume = "INSERT INTO  `resume`"
		+ "(`filename` ,`created` ,`changed`) VALUES" + "(?,?,?)";
	ResultSet generatedKeys = null;
	try {
	    preparedStatementInsert = mysqlDbConnection.prepareStatement(
		    insertResume, Statement.RETURN_GENERATED_KEYS);
	    preparedStatementInsert.setString(1, filename);
	    preparedStatementInsert.setLong(2, getCurrentTimeStamp());
	    preparedStatementInsert.setLong(3, getCurrentTimeStamp());
	    preparedStatementInsert.executeUpdate();
	    generatedKeys = preparedStatementInsert.getGeneratedKeys();
	    if (generatedKeys.next()) {
		return (int) generatedKeys.getLong(1);
	    }
	} finally {
	    if (generatedKeys != null)
		try {
		    generatedKeys.close();
		} catch (SQLException logOrIgnore) {
		}
	    if (preparedStatementInsert != null)
		try {
		    preparedStatementInsert.close();
		} catch (SQLException logOrIgnore) {
		}
	}

	return (Integer) null;
    }

    public int updateResume(String filename, int rid) throws SQLException {
	PreparedStatement preparedStatementUpdate = null;
	String insertResume = "UPDATE `resume` SET filename =? , changed=?"
		+ "WHERE `rid`  = ?";
	ResultSet generatedKeys = null;
	try {
	    preparedStatementUpdate = mysqlDbConnection.prepareStatement(
		    insertResume, Statement.RETURN_GENERATED_KEYS);
	    preparedStatementUpdate.setString(1, filename);
	    preparedStatementUpdate.setLong(2, getCurrentTimeStamp());
	    preparedStatementUpdate.setInt(3, rid);
	    preparedStatementUpdate.executeUpdate();
	    generatedKeys = preparedStatementUpdate.getGeneratedKeys();
	    if (generatedKeys.next()) {
		return (int) generatedKeys.getLong(1);
	    }
	} finally {
	    if (generatedKeys != null)
		try {
		    generatedKeys.close();
		} catch (SQLException logOrIgnore) {
		}
	    if (preparedStatementUpdate != null)
		try {
		    preparedStatementUpdate.close();
		} catch (SQLException logOrIgnore) {
		}
	}

	return (Integer) null;
    }

    public void startTransaction() throws SQLException {
	if (mysqlDbConnection != null)
	    mysqlDbConnection.setAutoCommit(false);
    }

    public void endTrasaction() throws SQLException {
	if (mysqlDbConnection != null)
	    mysqlDbConnection.commit();
    }

    public void rollback() throws SQLException {
	if (mysqlDbConnection != null)
	    mysqlDbConnection.rollback();
    }

    public static void main(String[] argv) throws SQLException {
	ResumeMysqlDao resume = null;
	try {
	    resume = ResumeMysqlDao.getResume();
	    resume.startTransaction();
	    int lastid = resume.insertResume("test");
	    resume.endTrasaction();
	    System.out.println("Done!" + lastid);

	} finally {
	    resume.closeConnection();

	}

    }

    private static Connection getDBConnection() {

	Connection mysqlDbConnection = null;

	try {

	    Class.forName(DB_DRIVER);

	} catch (ClassNotFoundException e) {

	    System.out.println(e.getMessage());

	}

	try {

	    mysqlDbConnection = DriverManager.getConnection(DB_CONNECTION,
		    DB_USER, DB_PASSWORD);
	    return mysqlDbConnection;

	} catch (SQLException e) {

	    System.out.println(e.getMessage());

	}

	return mysqlDbConnection;

    }

    private static long getCurrentTimeStamp() {
return System.currentTimeMillis() / 1000L;

    }
}
