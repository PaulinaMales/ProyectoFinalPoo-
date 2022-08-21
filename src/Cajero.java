import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Cajero extends JFrame {
    public JPanel cashierPanel;
    private JTextField textNombreCliente;
    private JTextField textApellido;
    private JTextField textCedula;
    private JTextField textFecha;
    private JTextField textProducto;
    private JTextField textCantidad;
    private JButton consultarButton;
    private JButton ejecutarTransaccionButton;
    private JTextField textID;
    private JTextField textPrecio;
    private JLabel totalCompra;
    private JButton totalButton;
    private JLabel textMensaje;
    private JButton limpiarButton;
    private JButton exitButton;

    public Cajero() {
        Connect();

        ejecutarTransaccionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buscar();
            }
        });
        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("EXIT PRINCIPAL PAGE");
                dispose();
            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
    }

    public void Connect() {
        final String DB_URL = "jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "0998927563MMPe";


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            System.out.println("Conexion exitosa");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }
    }


    //FUNCIONES BOTONES CAJERO
    public void Buscar(){
        String id;
        id = textID.getText();

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="0998927563MMPe";

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
                //ciudad= rs.getString(3);
                precio=rs.getString(4);
                cantidad = rs.getString(5);

                System.out.println();
                textProducto.setText(nombre);
                //textCiudad.setText(ciudad);
                textPrecio.setText(precio);
                textCantidad.setText(cantidad);

            }else {

                textMensaje.setText("No se encuentra el producto");
                //limpiar();
                //textMensaje.setText("");
            }

            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }


    }

    public void limpiar(){
        textProducto.setText("");
        textPrecio.setText("");
        textID.setText("");
        textCantidad.setText("");
    }

    public void totalCompra(){

    }











}

