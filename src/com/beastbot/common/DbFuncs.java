package com.beastbot.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.pmw.tinylog.*;
import org.pmw.tinylog.writers.FileWriter;

import com.beastbot.list.FormulaData;
import com.beastbot.list.Scriptsdetail;

import org.h2.jdbcx.JdbcDataSource;

public class DbFuncs {
	Connection conn = null; 
    Statement stmt = null;
    private String configprop=System.getProperty("user.dir")+File.separator+"conf"+File.separator+"feed.properties";
    public static String dbName;
    public static String USER, PASS;
    String logpath;
	

	public Connection CheckandConnectDB(Connection condb)
	{
		try
		{
			if ((condb == null) || (condb.isClosed()))
			{
				JdbcDataSource ds = new JdbcDataSource();
		        ds.setURL("jdbc:h2:"+dbName);
		        condb = ds.getConnection(USER,PASS);
		        conn = condb;
			}
		}
		catch(Exception ex)
		{
			Logger.info(ex);
		}
		return condb;
	}
	
	public DbFuncs() {
		Properties prop = new Properties();
		InputStream input = null;
		try
		{
			input =new FileInputStream(configprop);
			prop.load(input);
			dbName = System.getProperty("user.dir")+prop.getProperty("DB_HOST_PATH").replace("/", File.separator);
			USER = prop.getProperty("DB_USER");
			PASS = prop.getProperty("DB_PASS");
			logpath = configlogfile("BEASTBOT_LOG");
		}
		catch(Exception ex)
		{
			Logger.error(ex);
			System.out.println(ex.getMessage());
		}
		finally
		{
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getRowCount(Connection conn,String Query)
	{
		int rowCount=0;
		try {
			 conn = CheckandConnectDB(conn);
	         stmt = conn.createStatement(); 
	         ResultSet rs = stmt.executeQuery(Query); 
	         rs.last();
	         rowCount = rs.getRow();
	         rs.close();
		}
		catch(Exception ex){
			Logger.error(ex);
			System.out.println(ex.getMessage() +"QUERY :"+Query);
			
		}
		finally {
			try { 
	            if(stmt!=null) stmt.close(); 
	         } catch(SQLException se2) { 
	         }		
		}
		return rowCount;
	}
	
	public List<FormulaData> getFormulaData(Connection conn,String Querystr)
	{
		
		List<FormulaData> set=new ArrayList<FormulaData>();  
		try {
			 conn = CheckandConnectDB(conn);
	         stmt = conn.createStatement();
	         stmt.execute(Querystr);
	         ResultSet rs =stmt.getResultSet(); 
	         while (rs.next()) {
	        	 FormulaData record = new FormulaData(rs.getInt("id"),rs.getDouble("X"),rs.getDouble("Y"),rs.getString("st"),rs.getString("mt"),rs.getString("et"),rs.getInt("lcount"),rs.getInt("round"),rs.getInt("qty"),rs.getBoolean("tradeswitch"));
	             set.add(record);
	         }
	         
	         if (rs != null) {
	                rs.close();
	            }
	        
		}
		catch(Exception ex){
			System.out.println(ex.getMessage() +"QUERY :"+Querystr);
			Logger.error(ex);
		}
		finally {
			try {
	            
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            Logger.error("Ignored", e);
	        }	
		}
		return set;
	}
	
	public List<Scriptsdetail> getContractdata(Connection conn,String Querystr)
	{
		
		List<Scriptsdetail> set=new ArrayList<Scriptsdetail>();  
		try {
			 conn = CheckandConnectDB(conn);
	         stmt = conn.createStatement();
	         stmt.execute(Querystr);
	         ResultSet rs =stmt.getResultSet(); 
	         while (rs.next()) {
	        	 Scriptsdetail record = new Scriptsdetail(rs.getString("SECID"),rs.getString("SYMBOL"),rs.getString("EXCHANGE"),rs.getString("INSTRUMENT")
	        			 ,rs.getString("LOTSIZE"),rs.getString("TICKSIZE"),rs.getString("EXPDD"),rs.getString(9),rs.getString("OPTTYPE"),rs.getString("STRIKE"));
	             set.add(record);
	         }
	         
	         if (rs != null) {
	                rs.close();
	            }
	        
		}
		catch(Exception ex){
			System.out.println(ex.getMessage() +"QUERY :"+Querystr);
			Logger.error(ex);
		}
		finally {
			try {
	            
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            Logger.error("Ignored", e);
	        }	
		}
		return set;
	}
	
	
	
	public ArrayList<String> getSingleColumnRecords(Connection conn, String Querystr)
	{
		ArrayList<String> data = new ArrayList<String>();
		try
		{
			 conn = CheckandConnectDB(conn);
	         stmt = conn.createStatement();
	         stmt.execute(Querystr);
	         ResultSet rs =stmt.getResultSet(); 
	         while (rs.next()) {
	        	 
	        	 data.add(rs.getString(1));
	        
	         }
	         
	         if (rs != null) {
	                rs.close();
	            }
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage() +"QUERY :"+Querystr);
			Logger.error(ex);
		}
		finally {
			try {
	            
	            if (stmt != null) {
	                stmt.close();
	            }
	        
	        } catch (SQLException e) {
	            Logger.error("Ignored", e);
	        }	
		}
		return data;
	}
	
	public String getSingleCell(Connection conn, String Querystr)
	{
		String data ="";
		try
		{
			 conn = CheckandConnectDB(conn);
	         stmt = conn.createStatement();
	         stmt.execute(Querystr);
	         ResultSet rs =stmt.getResultSet(); 
	         while (rs.next()) {
	        	 
	        	 data = rs.getString(1);
	        	 break;
	        
	         }
	         
	         if (rs != null) {
	                rs.close();
	            }
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage() +"QUERY :"+Querystr);
		}
		finally {
			try {
	            
	            if (stmt != null) {
	                stmt.close();
	            }
	        
	        } catch (SQLException e) {
	            Logger.error("Ignored", e);
	        }	
		}
		return data;
	}
	
	
	
	public String[][] getMultiColumnRecords(Connection conn, String Querystr)
	{
		String [][] data=null;
		
		try
		{
			 conn = CheckandConnectDB(conn);
	         stmt = conn.createStatement();
	         stmt.execute(Querystr);
	         ResultSet rs =stmt.getResultSet(); 
	         rs.last();
	         int rowSize = rs.getRow();
	         //rs.first();
	         ResultSetMetaData rsmd = rs.getMetaData();
	         int columnSize = rsmd.getColumnCount();
	         int i =0;
	         data = new String[rowSize][columnSize];
	         stmt.execute(Querystr);
	         rs =stmt.getResultSet(); 
	         while(rs.next() && i != rowSize)
	         {
	             for(int j=0;j<columnSize;j++){
	            	 data[i][j] = rs.getString(j+1);
	             }
	             i++;                    
	         }
	         
	         if (rs != null) {
	                rs.close();
	            }
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage() +"QUERY :"+Querystr);
			Logger.error(ex);
		}
		finally {
			try {
	            
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            Logger.error("Ignored", e);
	        }	
		}
		return data;
	}
	
	public boolean executeNonQuery(Connection conn, String Query)
	{
		boolean isSucess = true;
		try {
			 conn = CheckandConnectDB(conn);
	         stmt = conn.createStatement(); 
	         stmt.execute(Query); 
		}
		catch(Exception ex)
		{
			isSucess = false;
			Logger.error(ex);
			System.out.println(ex.getMessage() +"QUERY :"+Query);
		}
		finally
		{
			try { 
	            if(stmt!=null) stmt.close(); 
	         } 
			catch(SQLException se2) { 
	         } 
		}
		return isSucess;
	}
	public int executeBatchStatement(Connection conn, String [] statements){
       
        Statement stmt = null;
        int stmtscount=0;
        try {
         
        	 conn = CheckandConnectDB(conn);
            stmt = conn.createStatement();
            for(int i=0; i<statements.length; i++)
            {
            		stmt.addBatch(statements[i]);
            }          
            int[] countWithoutException = stmt.executeBatch();
            System.out.println("Inserted = " + countWithoutException.length);
            conn.commit();
            stmtscount = countWithoutException.length;
        } catch (BatchUpdateException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage() +"QUERY :"+statements);
				e.printStackTrace();
			}
            
        }
        return stmtscount;
    }
	
	private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
        		JdbcDataSource ds = new JdbcDataSource();
	        ds.setURL("jdbc:h2:"+dbName);
	        dbConnection = ds.getConnection(USER,PASS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return dbConnection;
    }
	
	public String readconfigprop(String propkey)
	{
		String strResult= null;
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try
		{
			input =new FileInputStream(configprop);
			prop.load(input);
			strResult=prop.getProperty(propkey);
		}
		catch(Exception ex)
		{
			Logger.error(ex.toString());
		}
		finally
		{
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return strResult;
	}
    /*
	* According to the log name and path defined in config.propertise file, it will configure tiny log and return the file path
	*/
	public String configlogfile(String logname)
	{
		String path = null;
		try 
		{
			path = System.getProperty("user.dir") + readconfigprop(logname).replace("/", File.separator);
			Configurator.defaultConfig().writer(new FileWriter(path,false,true)).activate();
			//Logger.info("Log Initiated --> "+path);
		
		}
		catch(Exception ex)
		{
			Logger.error(ex.toString());
		}
		finally 
		{
			
		}
		return path;
	}
}