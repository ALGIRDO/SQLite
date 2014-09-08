package sqlite;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteJDBC {
    
    public static void createTable_COMPANY(Connection c) throws SQLException{
        Statement stmt = null;
    try {
                stmt = c.createStatement();
                String sql = "CREATE TABLE COMPANY "
                        + "(ID INT PRIMARY KEY     NOT NULL,"
                        + " NAME           TEXT    NOT NULL, "
                        + " AGE            INT     NOT NULL, "
                        + " ADDRESS        CHAR(50), "
                        + " SALARY         REAL)";
                stmt.executeUpdate(sql);
                System.out.println("Table created successfully");
            } catch (Exception e) {
                System.out.println("Table not created: " + e.getMessage());
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
    }
    public static void addData_COMPANY(Connection c) throws SQLException{
        Statement stmt = null;
        try {
                stmt = c.createStatement();
                String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                        + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
                stmt.executeUpdate(sql);

                sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                        + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
                stmt.executeUpdate(sql);

                sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                        + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
                stmt.executeUpdate(sql);

                sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                        + "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
                stmt.executeUpdate(sql);

                System.out.println("Records created successfully");
            } catch (Exception e) {
                System.out.println("Records wasn't created: " + e.getMessage());
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
    }
    public static void getData_COMPANY(Connection c) throws SQLException{
        Statement stmt = null;
        try {
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String address = rs.getString("address");
                    float salary = rs.getFloat("salary");
                    System.out.println("ID = " + id);
                    System.out.println("NAME = " + name);
                    System.out.println("AGE = " + age);
                    System.out.println("ADDRESS = " + address);
                    System.out.println("SALARY = " + salary);
                    System.out.println();
                }
                rs.close();
                System.out.println("Operation done successfully");
            } catch (Exception e) {
                System.out.println("No results: " + e.getMessage());
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
    }
    public static void getVersion(Connection c) throws SQLException{
        Statement stmt = null;
        try {
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT sqlite_version() AS 'SQLite Version';");
                
                    System.out.println("SQLite version: " + rs.getString(1));
                
                rs.close();
                System.out.println("Version retreaved succesfully");
            } catch (Exception e) {
                System.out.println("No version: " + e.getMessage());
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
    }
    public static void getTableList(Connection c) throws SQLException{
        Statement stmt = null;
        try {
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master\n" +
                                                    "WHERE type='table' AND name!='sqlite_sequence' \n" +
                                                    "ORDER BY name;");
                System.out.println("Table names:");
                while(rs.next()){
                    System.out.println("\t" + rs.getString(1));
                }
                rs.close();
//                ResultSet r = stmt.executeQuery("PRAGMA table_info("+table+");");
//                    while(r.next()){
//                        System.out.println("\t\t"+r.getString(2));
//                    }
//                r.close();
            } catch (Exception e) {
                System.out.println("No result: " + e.getMessage());
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
    }
    
    public static void main(String args[]) throws SQLException {
        File s = new File(SQLiteJDBC.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String appLocation = s.getPath();
        if(appLocation.lastIndexOf(".jar")== -1) {
            appLocation = "";
        } else {
            appLocation = appLocation.substring(0, appLocation.lastIndexOf("\\")+1);
        }
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+appLocation+"databases\\SQLiteTest.db");
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("\"yyyy.MM.dd HH:mm:ss\"");
            long time = dNow.getTime()/1000;
            System.out.println(ft.format(dNow)+" - "+time);
            System.out.println("Opened database successfully");

//            createTable_COMPANY(c);
//            addData_COMPANY(c);
//            getData_COMPANY(c);
//            getVersion(c);
            getTableList(c);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        
    }
}
