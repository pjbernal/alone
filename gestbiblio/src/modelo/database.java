package modelo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class database {
 /* DATOS PARA LA CONEXION */
  private String db = "dam6_gestbiblioteca";
  /** usuario */
  private String user = "dam6";
  /** contraseña de MySql*/
  private String password = "salesianas";
  /** Cadena de conexion */
  private String url= "jdbc:mysql://88.26.202.99:3306/"+this.db;
  //private String ip = "88.26.202.99:3306";
  /** variable para trabajar con la conexion a la base de datos */
  private Connection conn = null;

   /** Constructor de clase */
   public database() throws FileNotFoundException, IOException{
       
       try{
         BufferedReader br = new BufferedReader (new FileReader("datosdb.txt"));
         db = br.readLine();
         user = br.readLine();
         password = br.readLine();
         url= br.readLine();
    
         
         this.url = "jdbc:mysql://88.26.202.99:3306/"+this.db;
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         conn = DriverManager.getConnection( this.url, this.user , this.password );         
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }catch(ClassNotFoundException e){
         System.err.println( e.getMessage() );
      }
   }


   public Connection getConexion()
   {
    return this.conn;
   }

}
