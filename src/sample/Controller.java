package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import sample.operator.Flying;
import sample.operator.Operator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {
    private int ID;

    @FXML
    private DatePicker dt_date;
    @FXML
    private ComboBox<String> cmb_class, cmb_position; // Классность и должность
    @FXML
    private ComboBox<String> cmb_timeDay1, cmb_timeDay2, cmb_timeDay3; // Время суток
    @FXML
    private ComboBox<String> cmb_who1, cmb_who2, cmb_who3; // В качестве кого
    @FXML
    private ImageView btn_newO, btn_newF, btn_viewF, btn_shutdown; //Иконки верхнего меню
    @FXML
    private AnchorPane ap_addOper, ap_addFly, ap_viewFly; //Меню добавления оператора, налета оператора в БД
        @FXML
        private TextField txt_verifyFirstName, txt_verifyLastName, txt_firstName, txt_lastName, txt_firstNameForChange, txt_lastNameForChange; // Проверить фамилию, Имя, Фамилия, поиск фамилии для изм., общ. кол. опер.
        @FXML
        private TextField txt_hour1, txt_hour2, txt_hour3, txt_minute1, txt_minute2, txt_minute3, txt_contr1, txt_contr2, txt_contr3;
        @FXML
        private TextField txt_verifyFlyFirstName, txt_verifyFlyLastName;
        @FXML
        private Label lbl_allRight, lbl_allOper, lbl_operFind, lbl_verifyText, lbl_allContr, lbl_allHour, lbl_allMinute;
        @FXML
        private Button btn_verifybd, btn_addap, btn_removeap, btn_saveOper, btn_findOper, btn_saveChangeOper, btn_saveFlying, btn_verifyFlybd; // Кнопки - проверить, + , -, сохранить оператора, найти оператора
        @FXML
        private Button btn_flyMonth, btn_monthFlyOK, btn_flyYear, btn_flyPeriod, btn_flyAllTime;
        @FXML
        private ComboBox<Month> cmb_month;
        @FXML
        private AnchorPane ap_monthFly, ap_yearFly, ap_periodFly, ap_alltimeFly;
        @FXML
        private Label lbl_verifyFlyText;

        private ObservableList<Flying> usersData = FXCollections.observableArrayList();

            @FXML
            private TableView<Flying> tableFlying;
            @FXML
            private TableColumn<Flying, String> tc_date;
            @FXML
            private TableColumn<Flying, String> tc_who;
            @FXML
            private TableColumn<Flying, String> tc_daytime;
            @FXML
            private TableColumn<Flying, Integer> tc_contr;
            @FXML
            private TableColumn<Flying, Integer> tc_hour;
            @FXML
            private TableColumn<Flying, Integer> tc_minute;

        private ObservableList<Flying> usersData1 = FXCollections.observableArrayList();

            @FXML
            private TableView<Flying> tableFlying1;
            @FXML
            private TableColumn<Flying, String> tc_date1;
            @FXML
            private TableColumn<Flying, String> tc_who1;
            @FXML
            private TableColumn<Flying, String> tc_daytime1;
            @FXML
            private TableColumn<Flying, Integer> tc_contr1;
            @FXML
            private TableColumn<Flying, Integer> tc_hour1;
            @FXML
            private TableColumn<Flying, Integer> tc_minute1;

        @FXML
        private AnchorPane ap_timeOfDay1, ap_timeOfDay2, ap_timeOfDay3, ap_flyMenu; // 3 слоя добавления налета

    @FXML
    void initialize() {
        List<Month> list = new ArrayList<>();
        list.add(Month.JAN);
        list.add(Month.FEB);
        cmb_month.setItems(FXCollections.observableArrayList(list));

        btn_saveOper.setOnAction(event -> addOperator());

        btn_findOper.setOnAction(event -> findOperator());

        btn_saveChangeOper.setOnAction(event -> updateOperator());

        btn_saveFlying.setOnAction(event -> {
            saveFlying();
            txt_verifyFirstName.setText("");
            txt_verifyLastName.setText("");
            ap_timeOfDay1.setVisible(false);
            ap_timeOfDay2.setVisible(false);
            ap_timeOfDay3.setVisible(false);
            dt_date.setValue(null);
            txt_hour1.setText("");
            txt_hour2.setText("");
            txt_hour3.setText("");
            txt_minute1.setText("");
            txt_minute2.setText("");
            txt_minute3.setText("");
            txt_contr1.setText("");
            txt_contr2.setText("");
            txt_contr3.setText("");
            cmb_who1.setValue("");
            cmb_who2.setValue("");
            cmb_who3.setValue("");
            cmb_timeDay1.setValue("");
            cmb_timeDay2.setValue("");
            cmb_timeDay3.setValue("");
        });

        btn_monthFlyOK.setOnAction(event -> viewMonthFly());

        btn_shutdown.setOnMousePressed(event -> System.exit(0));
    }

    public void visibilityWindow(MouseEvent event) {
        if (event.getSource() == btn_newO) {
            ap_addOper.setVisible(true);
            ap_addFly.setVisible(false);
            ap_viewFly.setVisible(false);
            ap_monthFly.setVisible(false);
            ap_yearFly.setVisible(false);
            ap_periodFly.setVisible(false);
            ap_alltimeFly.setVisible(false);
        } else if (event.getSource() == btn_newF) {
            ap_addFly.setVisible(true);
            ap_addOper.setVisible(false);
            ap_viewFly.setVisible(false);
            ap_monthFly.setVisible(false);
            ap_yearFly.setVisible(false);
            ap_periodFly.setVisible(false);
            ap_alltimeFly.setVisible(false);
        } else if (event.getSource() == btn_viewF) {
            ap_viewFly.setVisible(true);
            ap_addOper.setVisible(false);
            ap_addFly.setVisible(false);
        }
    }

    public int getID() {
        return ID;
    }

    public void visibilityPaneForAddFly(ActionEvent event) {
        DataBaseConnection dbConnection = new DataBaseConnection(Const.OPERATORS);
        String firstName = txt_verifyFirstName.getText().toUpperCase();
        String lastName = txt_verifyLastName.getText().toUpperCase();
        ResultSet result = dbConnection.select(Const.OPERATORS);
        try {
            while (result.next()) {
                if (result.getString(Const.FIRSTNAME).equals(firstName) && result.getString(Const.LASTNAME).equals(lastName)) {
                    this.ID = result.getInt(Const.ID);
                    if (event.getSource() == btn_verifybd) {
                        lbl_verifyText.setText("Оператор найден в базе данных");
                        lbl_verifyText.setStyle("-fx-text-fill: #d3d3d3");
                        ap_timeOfDay1.setVisible(true);
                    } else
                    if (event.getSource() == btn_addap) { // Кнопка +
                        if (!ap_timeOfDay2.isVisible())
                            ap_timeOfDay2.setVisible(true);
                        else
                            ap_timeOfDay3.setVisible(true);
                    } else if (event.getSource() == btn_removeap) { // Кнопка -
                        if (ap_timeOfDay3.isVisible())
                            ap_timeOfDay3.setVisible(false);
                        else
                            ap_timeOfDay2.setVisible(false);
                    }
                    break;
                } else {
                    ap_timeOfDay1.setVisible(false);
                    lbl_verifyText.setText("Оператор " + firstName + " " + lastName + " НЕ найден");
                    lbl_verifyText.setStyle("-fx-text-fill: red");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void visibilityPaneForViewFly(ActionEvent event) {
            DataBaseConnection dbConnection = new DataBaseConnection(Const.OPERATORS);
            String firstName = txt_verifyFlyFirstName.getText().toUpperCase();
            String lastName = txt_verifyFlyLastName.getText().toUpperCase();
            ResultSet result = dbConnection.select(Const.OPERATORS);
            try {
                while (result.next()) {
                    if (result.getString(Const.FIRSTNAME).equals(firstName) && result.getString(Const.LASTNAME).equals(lastName)) {
                        this.ID = result.getInt(Const.ID);
                        if (event.getSource() == btn_verifyFlybd) {
                            lbl_verifyFlyText.setText("Оператор найден в базе данных");
                            lbl_verifyFlyText.setStyle("-fx-text-fill: #d3d3d3");
                            ap_flyMenu.setVisible(true);
                        } else if (event.getSource() == btn_flyMonth) { // Кнопка Месяц
                            ap_monthFly.setVisible(true);
                            ap_yearFly.setVisible(false);
                            ap_periodFly.setVisible(false);
                            ap_alltimeFly.setVisible(false);
                        } else if (event.getSource() == btn_flyYear) { // Кнопка Год
                            ap_monthFly.setVisible(false);
                            ap_yearFly.setVisible(true);
                            ap_periodFly.setVisible(false);
                            ap_alltimeFly.setVisible(false);
                        } else if (event.getSource() == btn_flyPeriod) { // Кнопка Период
                            ap_monthFly.setVisible(false);
                            ap_yearFly.setVisible(false);
                            ap_periodFly.setVisible(true);
                            ap_alltimeFly.setVisible(false);
                        } else if (event.getSource() == btn_flyAllTime) { // Кнопка Всё время
                            DataBaseConnection dbConnAllTime = new DataBaseConnection(getID() + Const.FLYING);
                            ResultSet resultAllTime = dbConnAllTime.select(getID() + Const.FLYING);

                            tc_date.setCellValueFactory(new PropertyValueFactory<Flying, String>(Const.DATE));
                            tc_who.setCellValueFactory(new PropertyValueFactory<Flying, String>(Const.WHO));
                            tc_daytime.setCellValueFactory(new PropertyValueFactory<Flying, String>(Const.TIMEOFDAY));
                            tc_contr.setCellValueFactory(new PropertyValueFactory<Flying, Integer>(Const.CONTR));
                            tc_hour.setCellValueFactory(new PropertyValueFactory<Flying, Integer>(Const.HOUR));
                            tc_minute.setCellValueFactory(new PropertyValueFactory<Flying, Integer>(Const.MINUTE));

                            tableFlying.setItems(usersData);
                            while (resultAllTime.next()) {
                                usersData.add(new Flying(
                                        resultAllTime.getString(Const.DATE),
                                        resultAllTime.getString(Const.WHO),
                                        resultAllTime.getString(Const.TIMEOFDAY),
                                        resultAllTime.getInt(Const.CONTR),
                                        resultAllTime.getInt(Const.HOUR),
                                        resultAllTime.getInt(Const.MINUTE)));
                            }

                            ap_monthFly.setVisible(false);
                            ap_yearFly.setVisible(false);
                            ap_periodFly.setVisible(false);
                            ap_alltimeFly.setVisible(true);
                        }
                        break;
                    } else {
                        ap_timeOfDay1.setVisible(false);
                        lbl_verifyFlyText.setText("Оператор " + firstName + " " + lastName + " НЕ найден");
                        lbl_verifyFlyText.setStyle("-fx-text-fill: red");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    public void addOperator() {
        DataBaseConnection dbConnection = new DataBaseConnection(Const.OPERATORS);
        ResultSet result = dbConnection.select(Const.OPERATORS);

        String firstName = txt_firstName.getText().toUpperCase();
        String lastName = txt_lastName.getText().toUpperCase();
        String classOper = cmb_class.getValue();
        String position = cmb_position.getValue();

        String lname = null;
        String fname = null;

        try {
            while (result.next()) {
                if (result.getString(Const.LASTNAME).equals(lastName) && result.getString(Const.FIRSTNAME).equals(firstName)) {
                    lbl_allRight.setText("Oператор " + firstName + " " + lastName + " уже в базе данных");
                    lbl_allRight.setStyle("-fx-text-fill: yellow");
                    lname = result.getString(Const.LASTNAME);
                    fname = result.getString(Const.FIRSTNAME);
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            if (!lastName.equals(lname) && !firstName.equals(fname)) {
                Operator operator = new Operator(firstName, lastName, classOper, position);

                dbConnection.insert(operator, Const.OPERATORS);
                ResultSet resultSet = dbConnection.select(Const.OPERATORS);
                ArrayList<Integer> idList = new ArrayList<>();
                while (resultSet.next()) {
                    idList.add(resultSet.getInt(Const.ID));
                }
                Collections.sort(idList);

                lbl_allRight.setText("Оператор добавлен в базу данных");
                lbl_allRight.setStyle("-fx-text-fill: #d3d3d3");
                dbConnection.create(idList.get(idList.size()- 1));
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public void findOperator() {
        DataBaseConnection dbConnection = new DataBaseConnection(Const.OPERATORS);
        String firstName = txt_firstNameForChange.getText().toUpperCase();
        String lastName = txt_lastNameForChange.getText().toUpperCase();
        boolean flag = false;

        ResultSet result = dbConnection.select(Const.OPERATORS);
        Operator operator = new Operator();
        try {
            while (result.next()) {
                if (result.getString(Const.FIRSTNAME).equals(firstName) && result.getString(Const.LASTNAME).equals(lastName)) {

                    Operator.id = result.getInt(Const.ID);
                    operator.setFirstName(result.getString(Const.FIRSTNAME));
                    operator.setLastName(result.getString(Const.LASTNAME));
                    operator.setClassOper(result.getString(Const.CLASS));
                    operator.setPosition(result.getString(Const.POSITION));

                    txt_firstName.setText(operator.getFirstName());
                    txt_lastName.setText(operator.getLastName());
                    cmb_class.setValue(operator.getClassOper());
                    cmb_position.setValue(operator.getPosition());

                    btn_saveChangeOper.setVisible(true);
                    btn_saveOper.setVisible(false);

                    flag = true;
                    break;
                }
            }
            if (flag) {
                lbl_operFind.setText("Оператор найден в базе данных");
                lbl_operFind.setStyle("-fx-text-fill:  #d3d3d3");
                lbl_allRight.setText("Измените данные оператора и нажмите СОХРАНИТЬ");
            } else {
                lbl_operFind.setText("Оператор НЕ найден в базе данных");
                lbl_operFind.setStyle("-fx-text-fill: red");
                btn_saveChangeOper.setVisible(false);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateOperator() {
        DataBaseConnection dbConnection = new DataBaseConnection(Const.OPERATORS);

        String firstName = txt_firstName.getText().toUpperCase();
        String lastName = txt_lastName.getText().toUpperCase();
        String classOper = cmb_class.getValue();
        String position = cmb_position.getValue();

        Operator operator = new Operator(firstName, lastName, classOper, position);
        dbConnection.update(operator, Const.OPERATORS);

        lbl_allRight.setText("Данные оператора изменены");
        lbl_allRight.setStyle("-fx-text-fill: yellow");

        btn_saveChangeOper.setVisible(false);
        btn_saveOper.setVisible(true);
    }

    private void saveFlying() {
        Flying flying2 = null;
        Flying flying3 = null;

        LocalDate date;
        int allContr = 0, allHour = 0, allMinute = 0, hour1, hour2, hour3, minute1, minute2, minute3, contr1, contr2, contr3;
        String who1, who2, who3, timeOfDay1, timeOfDay2, timeOfDay3;

        if (ap_timeOfDay1.isVisible()) {
            if (ap_timeOfDay2.isVisible()) {
                if (ap_timeOfDay3.isVisible()) {
                    date = dt_date.getValue();
                    who3 = cmb_who3.getValue();
                    timeOfDay3 = cmb_timeDay3.getValue();
                    hour3 = Integer.parseInt(txt_hour3.getText());
                    minute3 = Integer.parseInt(txt_minute3.getText());
                    contr3 = Integer.parseInt(txt_contr3.getText());
                    allHour += hour3;
                    allMinute += minute3;
                    allContr += contr3;

                    flying3 = new Flying(date, who3, timeOfDay3, contr3, hour3, minute3);
                }
                date = dt_date.getValue();
                who2 = cmb_who2.getValue();
                timeOfDay2 = cmb_timeDay2.getValue();
                hour2 = Integer.parseInt(txt_hour2.getText());
                minute2 = Integer.parseInt(txt_minute2.getText());
                contr2 = Integer.parseInt(txt_contr2.getText());
                allHour += hour2;
                allMinute += minute2;
                allContr += contr2;

                flying2 = new Flying(date, who2, timeOfDay2, contr2, hour2, minute2);
            }
            date = dt_date.getValue();
            who1 = cmb_who1.getValue();
            timeOfDay1 = cmb_timeDay1.getValue();
            hour1 = Integer.parseInt(txt_hour1.getText());
            minute1 = Integer.parseInt(txt_minute1.getText());
            contr1 = Integer.parseInt(txt_contr1.getText());
            allHour += hour1;
            allMinute += minute1;
            allContr += contr1;

            //lbl_allContr.setText(String.valueOf(allContr));
            //lbl_allMinute.setText(String.valueOf(allMinute));
            //lbl_allHour.setText(String.valueOf(allHour));

            Flying flying1 = new Flying(date, who1, timeOfDay1, contr1, hour1, minute1);

            DataBaseConnection dbConnection = new DataBaseConnection(getID() + Const.FLYING);
            if (flying3 != null)
                dbConnection.insert(flying3, getID() + Const.FLYING);
            if (flying2 != null)
                dbConnection.insert(flying2, getID() + Const.FLYING);
            dbConnection.insert(flying1, getID() + Const.FLYING);
        }
    }

    private void viewMonthFly() {
        DataBaseConnection dbConnAllTime = new DataBaseConnection(196 + Const.FLYING);
        ResultSet resultMonth = dbConnAllTime.select(196 + Const.FLYING);

        tc_date1.setCellValueFactory(new PropertyValueFactory<Flying, String>(Const.DATE));
        tc_who1.setCellValueFactory(new PropertyValueFactory<Flying, String>(Const.WHO));
        tc_daytime1.setCellValueFactory(new PropertyValueFactory<Flying, String>(Const.TIMEOFDAY));
        tc_contr1.setCellValueFactory(new PropertyValueFactory<Flying, Integer>(Const.CONTR));
        tc_hour1.setCellValueFactory(new PropertyValueFactory<Flying, Integer>(Const.HOUR));
        tc_minute1.setCellValueFactory(new PropertyValueFactory<Flying, Integer>(Const.MINUTE));

        tableFlying1.setItems(usersData1);

        try {
            while (resultMonth.next()) {
                if (resultMonth.getString(Const.DATE).split("-")[1].equals(cmb_month.getValue().getNum())) {
                    usersData1.add(new Flying(
                            resultMonth.getString(Const.DATE),
                            resultMonth.getString(Const.WHO),
                            resultMonth.getString(Const.TIMEOFDAY),
                            resultMonth.getInt(Const.CONTR),
                            resultMonth.getInt(Const.HOUR),
                            resultMonth.getInt(Const.MINUTE)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
