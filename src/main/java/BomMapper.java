
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

    public static Bom makeBom(int price, int orderId, ConnectionPool connectionPool) throws SQLException, DatabaseException {
        String sql = "INSERT INTO stykliste (pris, ordre_idordre) VALUES (?, ?)";
        ResultSet generatedKeys = null;
        int id = 0;
        Bom bom = null;

        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, price);
            statement.setInt(2, orderId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected != 1) {
                throw new DatabaseException("Failed to insert bom into database");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
                generatedKeys.close();
            }

            if (id == 0) {
                System.out.println("Id not found");
            }

            bom = new Bom(id, price, orderId);
        }
        return bom;
    }

}
