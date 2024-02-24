/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puregoldmanagementsystem;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Jaspher
 */
public class dashboardController implements Initializable {

    @FXML
    private Button addProd_btn;

    @FXML
    private Button addProducts_addBtn;

    @FXML
    private TextField addProducts_brand;

    @FXML
    private TableColumn<productData, String> addProducts_col_productBrand;

    @FXML
    private TableColumn<productData, String> addProducts_col_productName;

    @FXML
    private TableColumn<productData, String> addProducts_col_productPrice;

    @FXML
    private TableColumn<productData, String> addProducts_col_productStatus;

    @FXML
    private TableColumn<productData, String> addProducts_col_productType;

    @FXML
    private TableColumn<productData, String> addProducts_col_productid;

    @FXML
    private Button addProducts_deleteBtn;

    @FXML
    private AnchorPane addProducts_form;

    @FXML
    private ImageView addProducts_imageView;

    @FXML
    private Button addProducts_importBtn;

    @FXML
    private TextField addProducts_name;

    @FXML
    private TextField addProducts_price;

    @FXML
    private ComboBox<?> addProducts_productType;

    @FXML
    private TextField addProducts_productid;

    @FXML
    private Button addProducts_resetBtn;

    @FXML
    private TextField addProducts_searchBar;

    @FXML
    private ComboBox<?> addProducts_stats;

    @FXML
    private TableView<productData> addProducts_tableView;

    @FXML
    private Button addProducts_updateBtn;

    @FXML
    private Button close;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button home_btn;

    @FXML
    private AreaChart<?, ?> home_incomeChart;

    @FXML
    private BarChart<?, ?> home_orderChart;

    @FXML
    private Label home_totalIncome;

    @FXML
    private Label home_totalOrder;

    @FXML
    private Label home_totalProduct;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private TextField orders_amount;

    @FXML
    private Label orders_balance;

    @FXML
    private ComboBox<?> orders_brandName;

    @FXML
    private Button orders_btn;

    @FXML
    private TableColumn<customerData, String> orders_col_ProductName;

    @FXML
    private TableColumn<customerData, String> orders_col_brand;

    @FXML
    private TableColumn<customerData, String> orders_col_price;

    @FXML
    private TableColumn<customerData, String> orders_col_quantity;

    @FXML
    private TableColumn<customerData, String> orders_col_type;

    @FXML
    private AnchorPane orders_form;

    @FXML
    private Button orders_payBtn;

    @FXML
    private Button orders_addBtn;

    @FXML
    private ComboBox<?> orders_productName;

    @FXML
    private ComboBox<?> orders_productType;

    @FXML
    private Spinner<Integer> orders_quantity;

    @FXML
    private Button orders_receiptBtn;

    @FXML
    private Button orders_resetBtn;

    @FXML
    private TableView<customerData> orders_tableView;

    @FXML
    private Label orders_total;

    @FXML
    private Label username;

    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement stataement;
    private ResultSet result;
    private Image image;

    
    public void homeDisplayTotalOrders(){
        
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT COUNT(id) FROM customer WHERE date = '" +sqlDate+ "'";
        
        connect = database.connectDb();
        
        int countOrders = 0;
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery(); 
            
            while (result.next()){
                countOrders = result.getInt("COUNT(id)");
            }
            
            home_totalOrder.setText(String.valueOf(countOrders));
            
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void homeTotalIncome(){
        String sql = "SELECT SUM(total) FROM customer_receipt";
        
        connect = database.connectDb();
        
        double totalIncome = 0;
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                totalIncome = result.getDouble("SUM(total)") ;
            }
            
            home_totalIncome.setText("Php"+String.valueOf(totalIncome)); 
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void homeAvailableProducts(){
        String sql = "SELECT COUNT(id) FROM product WHERE status = 'Available'";
        
        connect = database.connectDb();
        
        int countAP = 0;
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countAP = result.getInt("COUNT(id)");   
            }
            home_totalProduct.setText(String.valueOf(countAP));
            
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        
        

    }
    
    public void homeIncomeChart(){
        home_incomeChart.getData().clear();
        
        String sql = "SELECT date, SUM(total) FROM customer_receipt GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 6";
        
        connect = database.connectDb();
        
        try{
            
            XYChart.Series chart = new XYChart.Series();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
            home_incomeChart.getData().add(chart);
            
        }catch(Exception e){e.printStackTrace();}
    }
    
    
    
    public void homeOrdersChart(){
        home_orderChart.getData().clear();
        String sql = "SELECT date, COUNT(id) FROM customer GROUP BY date ORDER BY date ORDER BY TIMESTAMP (date) ASC limit 5";
        
                connect = database.connectDb();
                
                try{
                    
                    XYChart.Series chart = new XYChart.Series();
                    
                    prepare = connect.prepareStatement(sql);
                    result = prepare.executeQuery();
                    
                    while(result.next()){
                        chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
                    }
                    
                    home_orderChart.getData().add(chart);
                }catch(Exception e){e.printStackTrace();}

    }
    
    
    public void addProductsAdd() {
        String sql = "INSERT into product (product_id, type, brand, productName, price, status, image, date)"
                + "VALUES(?,?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {

            Alert alert;

            if (addProducts_productid.getText().isEmpty()
                    || addProducts_productType.getSelectionModel().getSelectedItem() == null
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_name.getText().isEmpty()
                    || addProducts_price.getText().isEmpty()
                    || addProducts_stats.getSelectionModel().getSelectedItem() == null
                    || getData.path == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("FILL ALL");
                alert.showAndWait();
            } else {

                //SAVE SEARCH BAR SAVESTATE SEARCHBAR SAVE
                //SAVE SEARCH BAR SAVESTATE SEARCHBAR SAVE
                //SAVE SEARCH BAR SAVESTATE SEARCHBAR SAVE
                //check if product EXIST
                String checkData = "SELECT product_id FROM product WHERE product_id = '"
                        + addProducts_productid.getText() + "'";

                stataement = connect.createStatement();
                result = stataement.executeQuery(checkData); //error checkdata

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("PRODUCT ID: " + addProducts_productid.getText() + "ALREADY EXIST");
                    alert.showAndWait();
                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addProducts_productid.getText());
                    prepare.setString(2, (String) addProducts_productType.getSelectionModel().getSelectedItem());
                    prepare.setString(3, addProducts_brand.getText());
                    prepare.setString(4, addProducts_name.getText());
                    prepare.setString(5, addProducts_price.getText());
                    prepare.setString(6, (String) addProducts_stats.getSelectionModel().getSelectedItem());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(7, uri);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    //UPDATE TABLEVIEW
                    addProductsShowListData();

                    //CLEAR FIELDS
                    addProductsReset();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProductsUpdate() {

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "UPDATE product SET type = '"
                + addProducts_productType.getSelectionModel().getSelectedItem()
                + "', brand = '" + addProducts_brand.getText()
                + "', productName = '" + addProducts_name.getText()
                + "', price = '" + addProducts_price.getText()
                + "', status = '" + addProducts_stats.getSelectionModel().getSelectedItem()
                + "', price = '" + addProducts_price.getText()
                + "', image = '" + uri + "', date = '" + sqlDate + "' WHERE product_id = '"
                + addProducts_productid.getText() + "'";
        connect = database.connectDb();
        try {
            Alert alert;

            if (addProducts_productid.getText().isEmpty()
                    || addProducts_productType.getSelectionModel().getSelectedItem() == null
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_name.getText().isEmpty()
                    || addProducts_price.getText().isEmpty()
                    || addProducts_stats.getSelectionModel().getSelectedItem() == null
                    || getData.path == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("FILL ALL");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Fooking share to update? product ID: " + addProducts_productid.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    stataement = connect.createStatement();
                    stataement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("TITE");
                    alert.setHeaderText(null);
                    alert.setContentText("SUCCESSFULLY UPDATED");
                    alert.showAndWait();

                    addProductsShowListData();
                    addProductsReset();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addProductsDelete() {

        String sql = "DELETE FROM product WHERE product_id = '" + addProducts_productid.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (addProducts_productid.getText().isEmpty()
                    || addProducts_productType.getSelectionModel().getSelectedItem() == null
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_name.getText().isEmpty()
                    || addProducts_price.getText().isEmpty()
                    || addProducts_stats.getSelectionModel().getSelectedItem() == null
                    || getData.path == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("FILL ALL");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Fooking share to DELETE? product ID: " + addProducts_productid.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    stataement = connect.createStatement();
                    stataement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("TITE");
                    alert.setHeaderText(null);
                    alert.setContentText("SUCCESSFULLY DELETED");
                    alert.showAndWait();

                    addProductsShowListData();
                    addProductsReset();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addProductsReset() {
        addProducts_productid.setText("");
        addProducts_productType.getSelectionModel().clearSelection();
        addProducts_brand.setText("");
        addProducts_name.setText("");
        addProducts_price.setText("");
        addProducts_stats.getSelectionModel().clearSelection();
        addProducts_imageView.setImage(null);
        getData.path = "";

    }

    public void addProductsImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Image file");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 131, 131, false, true);
            addProducts_imageView.setImage(image);
        }
    }

    private String[] listType = {"Snacks", "Drinks", "Dessert", "Meals", "Candies", "More Products"};

    public void addProductsListType() {
        List<String> listT = new ArrayList<>();

        for (String data : listType) {
            listT.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listT);
        addProducts_productType.setItems(listData);
    }

    private String[] listStatus = {"Available", "Not Available"};

    public void addProductsListStatus() {
        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        addProducts_stats.setItems(listData);
    }

    public void addProductsSearch() {
        FilteredList<productData> filter = new FilteredList<>(addProductsList, e -> true);

        addProducts_searchBar.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateProductData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateProductData.getProductId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getBrand().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getProductName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getPrice().toString().contains(searchKey)) {
                    return true;
                } else if (predicateProductData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }

            });

        });

        SortedList<productData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addProducts_tableView.comparatorProperty());
        addProducts_tableView.setItems(sortList);
    }

    public ObservableList<productData> addProductsListData() {
        ObservableList<productData> productList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product ";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            productData prodD;

            while (result.next()) {
                prodD = new productData(
                        result.getInt("product_id"),
                        result.getString("type"),
                        result.getString("brand"),
                        result.getString("productName"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                productList.add(prodD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    private ObservableList<productData> addProductsList;

    public void addProductsShowListData() {
        addProductsList = addProductsListData();

        addProducts_col_productid.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addProducts_col_productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        addProducts_col_productBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addProducts_col_productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProducts_col_productStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        addProducts_tableView.setItems(addProductsList);

    }
    //SAVED STATE SAVE STATE SAVESTATE CHECK CNTRL C
    //SAVED STATE SAVE STATE SAVESTATE CHECK CNTRL C
    //SAVED STATE SAVE STATE SAVESTATE CHECK CNTRL C

    public void addProductsSelect() {
        productData prodD = addProducts_tableView.getSelectionModel().getSelectedItem();
        int num = addProducts_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addProducts_productid.setText(String.valueOf(prodD.getProductId())); //error
        addProducts_brand.setText(prodD.getBrand());
        addProducts_name.setText(prodD.getProductName());
        addProducts_price.setText(String.valueOf(prodD.getPrice()));

        String uri = "file:" + prodD.getImage();

        image = new Image(uri, 131, 131, false, true);
        addProducts_imageView.setImage(image);
        getData.path = prodD.getImage();

    }

    public void ordersAdd() {
        customerId();
        String sql = "INSERT INTO customer(customer_id, type, brand, productName, quantity, price, date) "
                + "VALUES(?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            String checkData = "SELECT * FROM product WHERE productName ='"
                    + orders_productName.getSelectionModel().getSelectedItem() + "'";

            double priceData = 0;

            stataement = connect.createStatement();
            result = stataement.executeQuery(checkData);

            if (result.next()) {
                priceData = result.getDouble("price");
            }

            double totalPData = (priceData * qty);
            Alert alert;

            if (orders_productType.getSelectionModel().getSelectedItem() == null
                    || (String) orders_brandName.getSelectionModel().getSelectedItem() == null
                    || (String) orders_productName.getSelectionModel().getSelectedItem() == null
                    || totalPData == 0) {
                //continue 43:57
                //continue 43:57
                //continue 43:57
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("CHOOSE PRODUCT FIRST");
                alert.showAndWait();

            } else {

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(customerid));
                prepare.setString(2, (String) orders_productType.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String) orders_brandName.getSelectionModel().getSelectedItem());
                prepare.setString(4, (String) orders_productName.getSelectionModel().getSelectedItem());
                prepare.setString(5, String.valueOf(qty));

                prepare.setString(6, String.valueOf(totalPData));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(7, String.valueOf(sqlDate));

                prepare.executeUpdate();

                ordersShowListData();
                ordersDisplayTotal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ordersPay() {
        customerId();
        String sql = "INSERT INTO customer_receipt (customer_id, total, amount, balance, date)" //1:02:54
                + "VALUES (?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;
            if (totalP > 0 || orders_amount.getText().isEmpty() || amountP == 0) {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Logout?");
                alert.setHeaderText(null);
                alert.setContentText("Sure? LOGOUT?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerid));
                    prepare.setString(2, String.valueOf(totalP));
                    prepare.setString(3, String.valueOf(amountP));
                    prepare.setString(4, String.valueOf(balanceP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("INFO Message");
                    alert.setHeaderText(null);
                    alert.setContentText("SUCCESS");
                    alert.showAndWait();


                    balanceP = 0;
                    amountP = 0;

                    orders_balance.setText("Php0.0");
                    orders_amount.setText("");
                } else {
                    return;
                }

            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("FILL ALL");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void orderReceipt() {
        HashMap hash = new HashMap();
        hash.put("inventoryP", customerid);

        try {

            JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\Jaspher\\Documents\\NetBeansProjects\\puregoldManagementSystem\\src\\puregoldmanagementsystem\\report.jrxml");
            JasperReport jReport = JasperCompileManager.compileReport(jDesign);
            JasperPrint jPrint = JasperFillManager.fillReport(jReport, hash, connect);

            JasperViewer.viewReport(jPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    } //IMPORT ALL OF THAT :)

    public void ordersReset() {
        customerId();
        String sql = "DELETE FROM customer WHERE customer_id = '" + customerid + "'"; //1:13:11 LATEST MARX

        connect = database.connectDb();

        try {
            if (!orders_tableView.getItems().isEmpty()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("CONFIRM");
                alert.setHeaderText(null);
                alert.setContentText("You want to reset?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    stataement = connect.createStatement();
                    stataement.executeUpdate(sql); //CONTITNUE I LOVE YOU 1:15:28

                    ordersShowListData();

                    balanceP = 0;
                    amountP = 0;

                    orders_balance.setText("Php0.0");
                    orders_total.setText("Php0.0");

                    orders_amount.setText("");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double amountP;
    private double balanceP;

    public void ordersAmount() {
        Alert alert;

        if (!orders_amount.getText().isEmpty()) {

            amountP = Double.parseDouble(orders_amount.getText());

            if (totalP > 0) {
                if (amountP >= totalP) {
                    balanceP = (amountP - totalP);

                    orders_balance.setText("Php" + String.valueOf(balanceP));
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("INVALD");
                    alert.showAndWait();

                    orders_amount.setText("");
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Logout?");
                alert.setHeaderText(null);
                alert.setContentText("Sure? LOGOUT?");
                Optional<ButtonType> option = alert.showAndWait();
            }
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Logout?");
            alert.setHeaderText(null);
            alert.setContentText("Sure? LOGOUT?");
            Optional<ButtonType> option = alert.showAndWait();
        }

    }

    private double totalP;

    public void ordersDisplayTotal() {
        customerId();
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '" + customerid + "'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }

            orders_total.setText("Php" + String.valueOf(totalP));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] ordersListType = {"Snacks", "Drinks", "Dessert", "Meals", "Candies", "More Products"};

    public void ordersListType() {
        List<String> listT = new ArrayList<>();

        for (String data : ordersListType) {
            
            listT.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listT);
        orders_productType.setItems(listData);
        ordersListBrand();
    }

    public void ordersListBrand() {

        String sql = "SELECT brand FROM product WHERE type = '"
                + orders_productType.getSelectionModel().getSelectedItem()
                + "' and status = 'Available'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("brand"));
            }
            orders_brandName.setItems(listData);

            ordersListProductName();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ordersListProductName() {
        String sql = "SELECT productName FROM product WHERE brand ='"
                + orders_brandName.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("productName"));
            }
            orders_productName.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private SpinnerValueFactory<Integer> spinner; //continue

    public void ordersSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);

        orders_quantity.setValueFactory(spinner);
    }

    private int qty;

    public void ordersShowSpinnerValue() {
        qty = orders_quantity.getValue();
    }

    public ObservableList<customerData> ordersListData() {
        customerId();
        ObservableList<customerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM  customer WHERE customer_id = '" + customerid + "'";

        connect = database.connectDb();

        try {
            customerData customerD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerD = new customerData(result.getInt("customer_id"),
                        result.getString("type"),
                        result.getString("brand"),
                        result.getString("productName"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getDate("date"));
                listData.add(customerD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<customerData> ordersList;

    public void ordersShowListData() {
        ordersList = ordersListData();

        orders_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        orders_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        orders_col_ProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        orders_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orders_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        orders_tableView.setItems(ordersList);
        ordersDisplayTotal();
    }

    private int customerid;

    public void customerId() {

        String customId = "SELECT * FROM customer";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(customId);
            result = prepare.executeQuery();

            int checkId = 0; //cont

            while (result.next()) {
                //get last customer id
                customerid = result.getInt("customer_id");
            }

            String checkData = "SELECT * FROM customer_receipt";

            stataement = connect.createStatement();
            result = stataement.executeQuery(checkData);

            while (result.next()) {
                checkId = result.getInt("customer_id");
            }

            if (customerid == 0) {
                customerid += 1;
            } else if (checkId == customerid) {
                customerid += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            dashboard_form.setVisible(true);
            addProducts_form.setVisible(false);
            orders_form.setVisible(false);

            home_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473, #89892b);");
            addProd_btn.setStyle("-fx-background-color: transparent;");
            orders_btn.setStyle("-fx-background-color: transparent;");
            
            homeDisplayTotalOrders();
            homeTotalIncome();
            homeAvailableProducts();
            homeIncomeChart();
            homeOrdersChart();

        } else if (event.getSource() == addProd_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(true);
            orders_form.setVisible(false);

            home_btn.setStyle("-fx-background-color: transparent;");
            addProd_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473, #89892b);");
            orders_btn.setStyle("-fx-background-color: transparent;");

            
            
            addProductsShowListData();
            addProductsListStatus();
            addProductsListType();
            addProductsSearch();

        } else if (event.getSource() == orders_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(false);
            orders_form.setVisible(true);

            home_btn.setStyle("-fx-background-color: transparent;");
            addProd_btn.setStyle("-fx-background-color: transparent;");
            orders_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #25a473, #89892b);");

            ordersShowListData();
            ordersListType();
            ordersListBrand();
            ordersListProductName();
            ordersSpinner();

        }

    }
    private double x = 0;
    private double y = 0;

    public void logout() {
        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Logout?");
            alert.setHeaderText(null);
            alert.setContentText("Sure? LOGOUT?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();
                //Link login form
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        System.exit(0);
    }

    public void testBtn() {
        System.err.println("DONE");
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
        homeDisplayTotalOrders();
        homeTotalIncome();
        homeAvailableProducts();
        homeIncomeChart();
        homeOrdersChart();
        
        addProductsShowListData();
        addProductsListStatus();
        addProductsListType();

        ordersShowListData();
        ordersListType();
        ordersListBrand();
        ordersListProductName();
        ordersSpinner();
    }

}
