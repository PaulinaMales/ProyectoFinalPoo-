import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Principal extends JFrame {
    private JComboBox comboBox1;
    private JPanel Main;
    private JComboBox comboBoxUsuarios;
    private JTextField emailTF;
    private JPasswordField passwordTF;
    private JButton ingresarButton;
    private JButton cancelButton;


    //TIPOS DE USUARIOS
    public User user;

    public Principal() {

        //Datos que aparecera en la ventana
        add(Main);
        setSize(700,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                escogerUsuario();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Boton Cancel");
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        //PANTALLA PRINCIPAL
        Principal p1= new Principal();
    }


    public void escogerUsuario () {

        switch (comboBoxUsuarios.getSelectedIndex()) {

            case 0:
                opcionAdministrador();
                break;
            case 1:
                opcionBodeguero();


                break;
            case 2:
                opcionCajeros();

                break;

        }


    }



    //CREAR AUTENTICACION USARIOS(elementos)
    private User getAuthenticationCajeros(String email, String password){
        User user = null;
        final String DB_URL= "jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt = conn.createStatement();

            System.out.println("Conexion Exitosa");
            String sql= "SELECT * FROM cajeros WHERE email =? AND password =?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = new User();
            }

            stmt.close();
            conn.close();


        } catch (Exception e){

            System.out.println("Error de .....");
            e.printStackTrace();
        }

        return user;
    }
    private User getAuthenticationBodegueros(String email, String password){
        User user = null;
        final String DB_URL= "jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt = conn.createStatement();

            System.out.println("Conexion Exitosa");
            String sql= "SELECT * FROM bodegueros WHERE email =? AND password =?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = new User();
            }

            stmt.close();
            conn.close();


        } catch (Exception e){

            System.out.println("Error de .....");
            e.printStackTrace();
        }

        return user;
    }
    private User getAuthenticationAdministradores(String email, String password){
        User user = null;
        final String DB_URL= "jdbc:mysql://localhost/mi_tienda?serverTimezone=UTC";
        final String USERNAME= "PaulinaMales";
        final String PASSWORD = "Males2001";

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt = conn.createStatement();

            System.out.println("Conexion Exitosa");
            String sql= "SELECT * FROM administradores WHERE email =? AND password =?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = new User();
            }

            stmt.close();
            conn.close();


        } catch (Exception e){

            System.out.println("Error de .....");
            e.printStackTrace();
        }

        return user;
    }



    //FUNCIONES COMBOBOX
    public void opcionCajeros(){
        String email = emailTF.getText();
        String password = String.valueOf(passwordTF.getPassword()); //Asi obtenemos el password sin saber cual es (CASTEO)
        System.out.println("Boton Ok");
        user = getAuthenticationCajeros(email, password);


        if (user != null) {
            JOptionPane.showMessageDialog(null, "Instrucciones Cajero"+"\nPara consultar precios y cantidad de los productos colocar solo ID");
            //PANTALLA CAJERO
            Principal.this.setVisible(false);
            Cajero c1= new Cajero();
            c1.setVisible(true);


        } else {
            JOptionPane.showMessageDialog(null, "email o password incorrectos, asegurese de elegir correctamente el tipo de usuario",
                    "Intente nuevamente", JOptionPane.ERROR_MESSAGE);

            passwordTF.setText("");
            System.out.println("Error de conexion");

        }


    }

    public void opcionAdministrador(){
        String email = emailTF.getText();
        String password = String.valueOf(passwordTF.getPassword()); //Asi obtenemos el password sin saber cual es (CASTEO)
        System.out.println("Boton Ok");
        user = getAuthenticationAdministradores(email, password);


        if (user != null) {
            JOptionPane.showMessageDialog(null, "Instrucciones para Administrador: \n- Puede entrar en los modulos de Cajero y Bodeguero.\n- Asi mismo podra añadir, actualizar datos, buscar, y eliminar productos. \n - Tambien podra añadir, actualizar datos, buscar, y eliminar usuarios ya sean bodegueros o cajeros" +
                    "");
            //PANTALLA ADMINISTRADOR
            Principal.this.setVisible(false);
            Administrador a1= new Administrador();
            a1.setVisible(true);


        } else {
            JOptionPane.showMessageDialog(null, "email o password incorrectos, asegurese de elegir correctamente el tipo de usuario",
                    "Intente nuevamente", JOptionPane.ERROR_MESSAGE);

            passwordTF.setText("");
            System.out.println("Error de conexion");

        }


    }


    public void opcionBodeguero(){
        String email = emailTF.getText();
        String password = String.valueOf(passwordTF.getPassword()); //Asi obtenemos el password sin saber cual es (CASTEO)
        System.out.println("Boton Ok");
        user = getAuthenticationBodegueros(email, password);


        if (user != null) {
            JOptionPane.showMessageDialog(null, "Instrucciones para Bodeguero:\n- Tendra la opcion de añadir, actualizar datos, buscar, y eliminar productos.\n- Asi tambien para buscar algun producto tendra solo poner el ID" + "");
            Principal.this.setVisible(false);
            Bodeguero b1= new Bodeguero();
            b1.setVisible(true);



        } else {
            JOptionPane.showMessageDialog(null, "email o password incorrectos, asegurese de elegir correctamente el tipo de usuario",
                    "Intente nuevamente", JOptionPane.ERROR_MESSAGE);
            passwordTF.setText("");
            System.out.println("Error de conexion");

        }


    }


}
