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
        JFrame frame = new JFrame("FARMACIA M/M");
        frame.setSize(700,500);
        frame.setContentPane(new Principal().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


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
        final String USERNAME = "PaulinaMales";
        final String PASSWORD="Males2001";

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
        final String USERNAME = "PaulinaMales";
        final String PASSWORD="Males2001";

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
        final String USERNAME = "PaulinaMales";
        final String PASSWORD="Males2001";

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
            JFrame frame2 = new JFrame("Cashier Mode");
            frame2.setSize(800, 400);
            frame2.setContentPane(new Cajero().cashierPanel);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.setVisible(true);


        } else {
            JOptionPane.showMessageDialog(null, "email o password incorrectos, asegurese de elegir correctamente el tipo de usuario",
                    "Intente nuevamente", JOptionPane.ERROR_MESSAGE);
            emailTF.setText("");
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
           JOptionPane.showMessageDialog(null, "Instrucciones para Administrador");
            //PANTALLA ADMINISTRADOR
            JFrame frame = new JFrame("Administrator Mode");
            frame.setSize(700, 500);
            frame.setContentPane(new Administrador().administratorPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);


        } else {
            JOptionPane.showMessageDialog(null, "email o password incorrectos, asegurese de elegir correctamente el tipo de usuario",
                    "Intente nuevamente", JOptionPane.ERROR_MESSAGE);
            emailTF.setText("");
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
            JOptionPane.showMessageDialog(null, "Instrucciones para Administrador");
            //PANTALLA WINEMAKER
            JFrame frame1 = new JFrame("Winemaker Mode");
            frame1.setSize(700, 500);
            frame1.setContentPane(new Bodeguero().winemakerPanel);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.setVisible(true);


        } else {
            JOptionPane.showMessageDialog(null, "email o password incorrectos, asegurese de elegir correctamente el tipo de usuario",
                    "Intente nuevamente", JOptionPane.ERROR_MESSAGE);
            emailTF.setText("");
            passwordTF.setText("");
            System.out.println("Error de conexion");

        }


    }


}
