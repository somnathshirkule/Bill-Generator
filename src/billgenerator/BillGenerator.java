/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billgenerator;

import com.ruggil.billgenerator.dataobjects.Db;
import com.ruggil.billgenerator.modules.DatabaseSetting;
import com.ruggil.billgenerator.modules.MainScreen;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Somnath Shirkule
 */
public class BillGenerator {
    public static boolean filesFlag;
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        try
        {
            if(isConnectedToDatabse())
            {
                new  MainScreen().show();
            }
            else
            {
                new DatabaseSetting().show();
            }
        }
        catch(ClassNotFoundException ex)
        {
        }
    }
   private static boolean isConnectedToDatabse() throws ClassNotFoundException, SQLException
    {
        Properties ps = new Properties();
        try
        {
            //String currentDirectory = System.getProperty("user.dir");
            //System.out.println(currentDirectory);
            ps.load(new FileReader(new File(System.getProperty("user.dir") + "\\dbbill.properties")));
            Db.setJdbcUrl(ps.getProperty("url"));
            Db.setJdbcDriver(ps.getProperty("driver"));
            Db.setJdbcUsername(ps.getProperty("username"));
            Db.setJdbcPassword(ps.getProperty("password"));
            return hitToDatabase();
        }
        catch(IOException ex)
        {
            return false;
        }
    }
    public static boolean hitToDatabase() throws ClassNotFoundException, SQLException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            Class.forName(Db.getJdbcDriver());
            //System.out.print(Db.getJdbcUrl() + Db.getJdbcDriver() + Db.getJdbcUsername() + Db.getJdbcPassword());
            con = DriverManager.getConnection(Db.getJdbcUrl(),Db.getJdbcUsername(),Db.getJdbcPassword());
            ps = con.prepareStatement("show tables");
            rs = ps.executeQuery();
            return rs.next();
        }
        catch(SQLException ex)
        {
            Logger.getLogger(BillGenerator.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally
        {
            con.close();
        }
    }
    
}
