/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puregoldmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Jaspher
 */
public class FXMLDocumentController implements Initializable {
    
    private double x = 0;
    private double y = 0;
    
    @FXML
    private AnchorPane main_form;
    
    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    
    @FXML
    private Button loginBtn;
    
    @FXML
    private Button close;
    
    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    public void loginAdmin(){
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            
            result = prepare.executeQuery();
            Alert alert;
            
            if (username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all");
                alert.showAndWait();
    
            }else{
                if (result.next()){
                    //if correct, go dashboard
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Success");
                    alert.showAndWait();
                    
                    loginBtn.getScene().getWindow().hide();
                    
                    //dashboard ^w^
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    root.setOnMousePressed((MouseEvent event) -> { //BAKA ERROR POSSIBLE ERROR
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });
                    
                    root.setOnMouseDragged((MouseEvent event) -> { //BAKA ERROR POSSIBLE ERROR
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

                        
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);
                    
                    stage.setScene(scene);
                    stage.show();
                }else{
                    //error msg
                     alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong user/pass");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){e.printStackTrace();}   
    }
    
    
    public void close(){
        System.exit(0);
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
