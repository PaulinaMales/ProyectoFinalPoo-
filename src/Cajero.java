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
    private JLabel textTotal;
    private JButton totalButton;
    private JLabel textMensaje;
    private JButton limpiarButton;
    private JButton exitButton;
    private JTextField textcantidadVendida;
    private JTextField totalCompra;

    public int cantidadRestante;

    //CONSTRUCTOR CON LAS ACCIONES DE CADA BOTON A REALIZAR
    public Cajero() {
        Connect();

        //Datos que aparecera en la pantalla
        setContentPane(cashierPanel);
        setTitle("Cashier Mode");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        ejecutarTransaccionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textMensaje.setText("");

                generarFactura();
                UpdateStock();
                CreateFact();
                limpiar();

                /*CreateFact();
                UpdateStock();
                generarFactura();
                limpiar();*/
            }
        });
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textMensaje.setText("");
                Buscar();
            }
        });
        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textMensaje.setText("");
                totalCompra();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("EXIT PRINCIPAL PAGE");
                Cajero.this.setVisible(false);
                Principal p1= new Principal();
                p1.setVisible(true);
            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textMensaje.setText("");
                limpiar();
            }
        });
    }

    public void Connect() {
        final String DB_URL = "jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            System.out.println("Conexion exitosa");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }
    }
    //FIN DE CONSTRUCTOR


    //FUNCIONES BOTONES CAJERO
    public void Buscar(){
        String id;
        id = textID.getText();

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";

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

    public void limpiar(){
        textProducto.setText("");
        textPrecio.setText("");
        textID.setText("");
        textCantidad.setText("");
        textNombreCliente.setText("");
        textApellido.setText("");
        textCedula.setText("");
        textFecha.setText("");
        totalCompra.setText("");
        textcantidadVendida.setText("");
    }

    public void totalCompra(){

        int cantidad, entero;

        cantidad=Integer.parseInt(textcantidadVendida.getText());
        entero=Integer.parseInt(textPrecio.getText());

        float precio= entero;
        float total= cantidad*precio;



        totalCompra.setText(""+total+"$");
    }

    //FUNCION PARA GENERAR LA FACTURA CON LOS DATOS OBTENIDOS
    public void generarFactura(){
        String nombre, apellido, cedula, fecha;


        nombre= textNombreCliente.getText();
        apellido= textApellido.getText();
        cedula= textCedula.getText();
        fecha= textFecha.getText();


        JOptionPane.showMessageDialog(null ,"\tFACTURA ELECTRONICA\n"+"Nombre: "+nombre+"\nApellido: "+apellido+"\nCedula: "
                +cedula+"\nFecha Emision: "+fecha+"\nDETALLES DE COMPRA "+"\nProducto: "+textProducto.getText()+"\nCantidad: "+textcantidadVendida.getText()
                +"\nTotal Compra: "+ totalCompra.getText());



        //GUARDAR CONTENIDO PARA IR RESTANDO STOCK
        cantidadRestante= Integer.parseInt(textCantidad.getText());
        //Delete();

    }


    //FUNCION PARA GUARDAR LOS REGISTROS DE CADA FACTURA DADA
    public void CreateFact(){
        String idProducto,nombre, apellido,cedula, fecha, cantidadVendida,totalVenta;
        idProducto=textID.getText();
        nombre=textNombreCliente.getText();
        apellido=textApellido.getText();
        cedula=textCedula.getText();
        fecha= textFecha.getText();
        cantidadVendida=textcantidadVendida.getText();
        totalVenta= totalCompra.getText();


        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";


        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="insert into factura(pid, nombreCli,apellidoCli,cedulaCli,fechaEmi,cantVendida,totalVenta)values(?,?,?,?,?,?,?)";
            PreparedStatement pst=conn.prepareStatement(sql);

            //GUARDAR EN LA TABLA FACTURACION
            pst.setString(1,idProducto);
            pst.setString(2,nombre);
            pst.setString(3,apellido);
            pst.setString(4,cedula);
            pst.setString(5,fecha);
            pst.setString(6,cantidadVendida);
            pst.setString(7,totalVenta);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null,"Registro Guardado");
            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }
    }
    //FIN DE GENERACION DE FACTURA

    public void UpdateStock(){

        String id, nombre,ciudad,precio;
        int cantidadVendida,cantidad;

        id = textID.getText();
        nombre=textProducto.getText();
        //ciudad = textCiudad.getText();
        precio = textPrecio.getText();
        cantidadVendida = Integer.parseInt(textcantidadVendida.getText());
        cantidad=Integer.parseInt(textCantidad.getText());
        cantidadRestante=cantidad-cantidadVendida;


        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";


        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="update productos set pnombre=?,pprecio=?,pcantidad=? where pid=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,nombre);
            //pst.setString(2,ciudad);
            pst.setString(2,precio);
            pst.setString(3,""+cantidadRestante);
            pst.setString(4,id);
            //ResultSet resultSet=pst.executeQuery();
            pst.executeUpdate();

            //JOptionPane.showMessageDialog(null,"Registro Actualizado");

            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }

    }
    public static void main(String[] args) {
        //PANTALLA CAJERO

        Cajero c1= new Cajero();

    }


}












