import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Bodeguero extends JFrame{
    public JPanel winemakerPanel;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField textID;
    private JTextField textPrecio;
    private JTextField textNombre;
    private JTextField textCantidad;
    private JTextField textCiudad;
    private JButton cleanButton;
    private JButton buscarButton;
    private JLabel textMensaje;
    private JButton EXITButton;


    public Bodeguero() {

        Connect();

        //Datos de la pantalla aparecer
        setContentPane(winemakerPanel);
        setTitle("Wine Marker Mode");
        setSize(700, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Create();
                limpiar();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update();
            }
        });

        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bodeguero.this.setVisible(false);
                Principal p1= new Principal();
                p1.setVisible(true);
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buscar();
            }
        });
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
    }

    Connection con;
    PreparedStatement pst;

    //Funcion conectar a la BDD
    public void Connect(){

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";

        try{
            Connection conn= DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt= conn.createStatement();

            System.out.print("Conexión exitosa");

        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.print("Sql incorrecto");

        }

    }




    //Fin de conectar a la BDD


    //Funcion añadir producto

    public void Create(){
        String nombre, precio,ciudad, id, cantidad;
        nombre=textNombre.getText();
        precio=textPrecio.getText();
        ciudad=textCiudad.getText();
        id= textNombre.getText();
        cantidad=textCantidad.getText();
        System.out.println(nombre);
        System.out.println(precio);
        System.out.println(ciudad);
        System.out.println(id);
        System.out.println(cantidad);

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "0998927563MMPe";


        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="insert into productos(pnombre, pciudad,pprecio,pcantidad)values(?,?,?,?)";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,nombre);
            pst.setString(2,ciudad);
            pst.setString(3,precio);
            pst.setString(4,cantidad);
            //ResultSet resultSet=pst.executeQuery();
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null,"Registro Guardado");


            //////////////
            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }
    }
    //Fin de añadir producto


    //Funcion de buscar
    public void Buscar(){
        // String nombre, precio,ciudad, id, cantidad;

        String id;
        id = textID.getText();

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "0998927563MMPe";

        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="select * from productos where pid=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,id);


            ResultSet rs=pst.executeQuery();
            //pst.executeQuery();

            if(rs.next()==true){
                String nombre,ciudad,precio,cantidad;
                nombre= rs.getString(2);
                ciudad= rs.getString(3);
                precio=rs.getString(4);
                cantidad = rs.getString(5);

                System.out.println();
                textNombre.setText(nombre);
                textCiudad.setText(ciudad);
                textPrecio.setText(precio);
                textCantidad.setText(cantidad);

            }else {

                textMensaje.setText("No se encuentra el producto");
                limpiar();
                //textMensaje.setText("");
            }

            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }


    }
    //Fin de buscar

    //Funcion de actulizar/update
    public void Update(){

        String id, nombre,ciudad,precio,cantidad;

        id = textID.getText();
        nombre=textNombre.getText();
        ciudad = textCiudad.getText();
        precio = textPrecio.getText();
        cantidad = textCantidad.getText();

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "0998927563MMPe";


        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="update productos set pnombre=?, pciudad=?,pprecio=?,pcantidad=? where pid=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,nombre);
            pst.setString(2,ciudad);
            pst.setString(3,precio);
            pst.setString(4,cantidad);
            pst.setString(5,id);
            //ResultSet resultSet=pst.executeQuery();
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null,"Registro Actualizado");

            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }




    }
    //Fin de actualizar


    //Funcion de eliminar
    public void Delete(){
        String borrarId= textID.getText();

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "0998927563MMPe";



        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="delete from productos where pid=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,borrarId);

            //ResultSet resultSet=pst.executeQuery();
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Registro Borrado");

            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }


    }
    //Fin de eliminar producto



    public void limpiar(){

        textNombre.setText("");
        textPrecio.setText("");
        textCiudad.setText("");
        textID.setText("");
        textCantidad.setText("");
    }

    public static void main(String[] args) {

        Bodeguero b1= new Bodeguero();

    }
}
