package BackEnd;

import java.sql.Connection;
import java.sql.SQLException;

public class pruebaConection {
    public static void main(String args[]) {
        try {
            Connection conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
