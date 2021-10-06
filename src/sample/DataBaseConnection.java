package sample;

import sample.operator.Flying;
import sample.operator.Operator;

import java.sql.*;

public class DataBaseConnection {
    private static Connection dbConnection;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public DataBaseConnection(String name) {
        if (dbConnection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                dbConnection = DriverManager.getConnection("jdbc:sqlite:E:\\Java\\My program\\src\\data\\" + "db" + name + ".db");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public void create(int id) {
        String create =
                "CREATE TABLE " + "db" + id + "Flying " + "(" +
                Const.ID + " INTEGER PRIMARY KEY, " +
                Const.DATE + " TEXT NOT NULL, " +
                Const.WHO + " TEXT NOT NULL, " +
                Const.TIMEOFDAY + " TEXT NOT NULL, " +
                Const.CONTR + " TEXT NOT NULL, " +
                Const.HOUR + " INTEGER NOT NULL, " +
                Const.MINUTE + " INTEGER NOT NULL)";
        try {
            PreparedStatement pStmt = getDbConnection().prepareStatement(create);
            pStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Operator operator, String name) {
        String insert;
        if (name.equals(Const.OPERATORS)) {
            insert =
                    "INSERT INTO db" + Const.OPERATORS + "(" + Const.FIRSTNAME + ", " + Const.LASTNAME +
                    ", " + Const.CLASS + ", " + Const.POSITION + ") VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement pStmt = getDbConnection().prepareStatement(insert);
                pStmt.setString(1, operator.getFirstName());
                pStmt.setString(2, operator.getLastName());
                pStmt.setString(3, operator.getClassOper());
                pStmt.setString(4, operator.getPosition());
                pStmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        else {
            insert =
                    "INSERT INTO db" + name + "(" + Const.DATE + ", " + Const.WHO + ", " + Const.TIMEOFDAY + ", " + Const.CONTR + ", " + Const.HOUR +
                    ", " + Const.MINUTE+ ") VALUES (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement pStmt = getDbConnection().prepareStatement(insert);
                pStmt.setString(1, ((Flying) operator).getDate());
                pStmt.setString(2, ((Flying) operator).getWho());
                pStmt.setString(3, ((Flying) operator).getTimeOfDay());
                pStmt.setInt(4, ((Flying) operator).getContr());
                pStmt.setInt(5, ((Flying) operator).getHour());
                pStmt.setInt(6, ((Flying) operator).getMinute());
                pStmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public ResultSet select(String name) {
        ResultSet resultSet = null;

        String select =
                "SELECT * FROM db" + name;
        try {
            PreparedStatement pStmt = getDbConnection().prepareStatement(select);
            resultSet = pStmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void update(Operator operator, String name) {
       String update;
       if (name.equals(Const.OPERATORS)) {
           update =
                   "UPDATE db" + Const.OPERATORS + " SET " + Const.FIRSTNAME + " = ?, " + Const.LASTNAME + " = ?, "
                           + Const.CLASS + " = ?, " + Const.POSITION + " = ? WHERE " + Const.ID + " = ?";
           try {
               PreparedStatement pStmt = getDbConnection().prepareStatement(update);
               pStmt.setString(1, operator.getFirstName());
               pStmt.setString(2, operator.getLastName());
               pStmt.setString(3, operator.getClassOper());
               pStmt.setString(4, operator.getPosition());
               pStmt.setInt(5, Operator.id);
               pStmt.executeUpdate();
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
       }
    }

}
