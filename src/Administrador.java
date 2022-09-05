import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Administrador extends JFrame{
    public JPanel administratorPanel;
    private JRadioButton cajeroRadioButton;
    private JRadioButton bodegueroRadioButton;
    private JTextField cedulaTf;
    private JTextField emailTf;
    private JTextField nombreTf;
    private JTextField direccionTf;
    private JTextField celularTF;
    private JTextField passwordTf;
    private JButton createButton;
    private JButton searchButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton cleanButton;
    private JButton winemakerActionsButton;
    private JButton cashierActionsButton;
    private JButton SALIRButton;


    public Administrador() {
        Connect();
        //Datos de la pantalla aparecer
        setContentPane(administratorPanel);
        setTitle("Administrador Mode");
        setSize(700, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cajeroRadioButton.isSelected()){
                    Crear();
                }else {
                    Crear1();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cajeroRadioButton.isSelected()){
                    eliminar();
                }else {
                    eliminar1();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cajeroRadioButton.isSelected()){
                    buscar();
                }else {
                    buscar1();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cajeroRadioButton.isSelected()){
                    actualizar();
                }else {
                    actualizar1();
                }
            }
        });
        RadioBTN();
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
        winemakerActionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //PANTALLA WINEMAKER
                Administrador.this.setVisible(false);
                Bodeguero b1 = new Bodeguero();
                b1.setVisible(true);


            }
        });
        cashierActionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //PANTALLA CAJERO
                Administrador.this.setVisible(false);
                Cajero c1= new Cajero();
                c1.setVisible(true);
            }
        });
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Administrador.this.setVisible(false);
                Principal p1= new Principal();
                p1.setVisible(true);
            }
        });
    }

    public void RadioBTN(){
        ButtonGroup botones = new ButtonGroup();
        botones.add(cajeroRadioButton);
        botones.add(bodegueroRadioButton);
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

    //Funcion añadir cajero

    public void Crear(){
        String cedula, email, nombre, direccion, celular, password;

        cedula= cedulaTf.getText();
        email= emailTf.getText();
        nombre= nombreTf.getText();
        direccion= direccionTf.getText();
        celular= celularTF.getText();
        password= passwordTf.getText();


        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";


        try{
            Connection conn= DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt= conn.createStatement();

            String sql= "insert into cajeros(cedula, email, nombre, direccion, celular, password)values(?,?,?,?,?,?)";
            PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1, cedula);
            pst.setString(2, email);
            pst.setString(3, nombre);
            pst.setString(4, direccion);
            pst.setString(5, celular);
            pst.setString(6, password);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cajero agredado correctamente");


        }catch (SQLException ex){

            ex.printStackTrace();
            System.out.println("ERROR");
        }

    }
    //Fin de añadir cajero

    //Funcion añadir bodeguero

    public void Crear1(){
        String cedula, email, nombre, direccion, celular, password;

        cedula= cedulaTf.getText();
        email= emailTf.getText();
        nombre= nombreTf.getText();
        direccion= direccionTf.getText();
        celular= celularTF.getText();
        password= passwordTf.getText();


        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";


        try{
            Connection conn= DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt= conn.createStatement();

            String sql= "insert into bodegueros(cedula, email, nombre, direccion, celular, password)values(?,?,?,?,?,?)";
            PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1, cedula);
            pst.setString(2, email);
            pst.setString(3, nombre);
            pst.setString(4, direccion);
            pst.setString(5, celular);
            pst.setString(6, password);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Bodeguero agredado correctamente");


        }catch (SQLException ex){

            ex.printStackTrace();
            System.out.println("ERROR");
        }

    }
    //Fin de añadir bodeguero

    //Funcion de buscar cajero
    public void buscar(){

        String  cedula;
        cedula = cedulaTf.getText();

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";


        try{
            Connection conn= DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt= conn.createStatement();
            String sql= "select * from cajeros where cedula = ?";
            PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1, cedula);

            ResultSet rs=pst.executeQuery();
            //pst.executeQuery();
            if (rs.next() ==  true){
                String email, nombre, direccion, celular, password;
                email = rs.getString(2);
                nombre = rs.getString(3);
                direccion = rs.getString(4);
                celular = rs.getString(5);
                password = rs.getString(6);

                System.out.println();
                emailTf.setText(email);
                nombreTf.setText(nombre);
                direccionTf.setText(direccion);
                celularTF.setText(celular);
                passwordTf.setText(password);

            }
            else{

                JOptionPane.showMessageDialog(null, "Cajero no encontrado, intente con otro");

            }



            stmt.close();
            conn.close();


        }catch (SQLException ex){

            ex.printStackTrace();
            System.out.println("SQL incorrecto");
        }
    }
    //Fin de buscar cajero


    //Funcion de buscar cajero
    public void buscar1(){

        String  cedula;
        cedula = cedulaTf.getText();

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";


        try{
            Connection conn= DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt= conn.createStatement();
            String sql= "select * from bodegueros where cedula = ?";
            PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1, cedula);

            ResultSet rs=pst.executeQuery();
            //pst.executeQuery();
            if (rs.next() ==  true){
                String email, nombre, direccion, celular, password;
                email = rs.getString(2);
                nombre = rs.getString(3);
                direccion = rs.getString(4);
                celular = rs.getString(5);
                password = rs.getString(6);

                System.out.println();
                emailTf.setText(email);
                nombreTf.setText(nombre);
                direccionTf.setText(direccion);
                celularTF.setText(celular);
                passwordTf.setText(password);

            }
            else{

                JOptionPane.showMessageDialog(null, "Bodeguero no encontrado, intente con otro");

            }



            stmt.close();
            conn.close();


        }catch (SQLException ex){

            ex.printStackTrace();
            System.out.println("SQL incorrecto");
        }
    }
    //Fin de buscar bodeguero


    //Funcion de actulizar cajero

    public void actualizar(){
        String cedula, email, nombre, direccion, celular, password;
        cedula=cedulaTf.getText();

        email=emailTf.getText();
        nombre=nombreTf.getText();
        direccion=direccionTf.getText();
        celular=celularTF.getText();
        password=passwordTf.getText();

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";


        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="update cajeros set email=?, nombre=?,descripcion=?,celular=, password=?? where cedula=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,email);
            pst.setString(2,nombre);
            pst.setString(3,direccion);
            pst.setString(4,celular);
            pst.setString(5,password);
            pst.setString(6,cedula);


            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Datos de cajero actualizado");
            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("ERROR");

        }

    }

    //Fin de actualizar cajero


    //Funcion de actulizar cajero

    public void actualizar1(){
        String cedula, email, nombre, direccion, celular, password;
        cedula=cedulaTf.getText();

        email=emailTf.getText();
        nombre=nombreTf.getText();
        direccion=direccionTf.getText();
        celular=celularTF.getText();
        password=passwordTf.getText();

        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";


        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="update bodegueros set email=?, nombre=?,descripcion=?,celular=, password=?? where cedula=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,email);
            pst.setString(2,nombre);
            pst.setString(3,direccion);
            pst.setString(4,celular);
            pst.setString(5,password);
            pst.setString(6,cedula);


            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Datos de bodeguero actualizado");
            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("ERROR");

        }

    }

    //Fin de actualizar bodeguero


    //Funcion de eliminar cajero
    public void eliminar(){
        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";
        String borrarCajero=cedulaTf.getText();

        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="delete from cajeros where cedula=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,borrarCajero);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Cajero borrado");
            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("ERROR");

        }
    }
    //Fin de eliminar cajero


    //Funcion de eliminar bodeguero
    public void eliminar1(){
        final String DB_URL="jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";
        String borrarBodeguero=cedulaTf.getText();

        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="delete from bodegueros where cedula=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,borrarBodeguero);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Bodeguero borrado");
            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("ERROR");

        }
    }
    //Fin de eliminar bodeguero

    public void limpiar(){
        cedulaTf.setText("");
        emailTf.setText("");
        direccionTf.setText("");
        nombreTf.setText("");
        celularTF.setText("");
        passwordTf.setText("");

    }

    public static void main(String[] args) {

        Administrador a1= new Administrador();

    }
}
