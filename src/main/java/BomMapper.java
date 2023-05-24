
import java.sql.*;
import java.util.ArrayList;

public class BomMapper {

    public static ArrayList<Bom> getBoms(ConnectionPool connectionPool) throws SQLException {
        String sql = "SELECT * FROM stykliste";
        ArrayList<Bom> bomList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            Bom bom = null;
            int id = 0;
            int price = 0;
            int orderId = 0;

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                id = rs.getInt(1);
                price = rs.getInt(2);
                orderId = rs.getInt(3);

                bom = new Bom(id, price, orderId);
                bomList.add(bom);
            }
        }
        return bomList;
    }
}
