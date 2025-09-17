import java.sql.*;

public class AccessDB {

    static final String DATABASE_URL ="jdbc:mysql://localhost:3306/kailua_car_rental";
    static final String USER = "root";
    static final String PASSWORD = "Secret";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }


}
