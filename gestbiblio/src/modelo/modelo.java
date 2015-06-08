package modelo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class modelo extends database{

    /** Constructor de clase */
    public modelo ()throws FileNotFoundException, IOException{}

    /** Obtiene registros de la tabla usuarios y los devuelve en un DefaultTableModel*/
    public DefaultTableModel getTablaUsuario()
    {
      DefaultTableModel tablemodel = new DefaultTableModel();
      int registros = 0;
      String[] columNames = {"ncarnet","nombre","apellidos","direccion"};
      //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
      //para formar la matriz de datos
      try{
         PreparedStatement pstm = this.getConexion().prepareStatement( "SELECT count(*) as total FROM usuarios");
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }
    //se crea una matriz con tantas filas y columnas que necesite
    Object[][] data = new String[registros][5];
      try{
          //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
         PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM usuarios");
         ResultSet res = pstm.executeQuery();
         int i=0;
         while(res.next()){
                data[i][0] = res.getString( "ncarnet" );
                data[i][1] = res.getString( "nombre" );
                data[i][2] = res.getString( "apellidos" );
                data[i][3] = res.getString( "direccion" );
            i++;
         }
         res.close();
         //se añade la matriz de datos en el DefaultTableModel
         tablemodel.setDataVector(data, columNames );
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }

    /** Registra un nuevo producto */
    public boolean NuevoProducto(String ncarnet, String nombre , String apellidos, String direccion)
    {
        if( valida_datos(ncarnet, nombre, apellidos, direccion)  )
        {
            //se reemplaza "," por "."
            apellidos = apellidos.replace(",", ".");
            //Se arma la consulta
            String q=" INSERT INTO usuarios ( ncarnet , nombre , apellidos, direccion  ) "
                    + "VALUES ( '" + ncarnet + "','" + nombre + "', '" + apellidos + "'," + direccion + " ) ";
            //se ejecuta la consulta
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement(q);
                pstm.execute();
                pstm.close();
                return true;
            }catch(SQLException e){
                System.err.println( e.getMessage() );
            }
            return false;
        }
        else
         return false;
    }


    /** Elimina un registro dado su ncarnet -> Llave primaria */
    public boolean EliminarUsuario( String ncarnet )
    {
         boolean res=false;
        //se arma la consulta
        String q = " DELETE FROM usuarios WHERE  ncarnet='" + ncarnet + "' " ;
        //se ejecuta la consulta
         try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
         }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return res;
    }

    /** Metodo privado para validar datos */
    private boolean valida_datos(String ncarnet, String nombre , String apellidos, String direccion)
    {
        if( ncarnet.equals("  -   ") )
            return false;
        else if( nombre.length() > 0 && apellidos.length()>0 && direccion.length() >0)
        {
            return true;
        }
        else  return false;
    }

}
