
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialVariantMapper {

    public static List<MaterialVariant> getAllMaterialVariants(ConnectionPool connectionPool) throws DatabaseException {

        List<MaterialVariant> materialVariants = new ArrayList<>();

        String sql = "SELECT * FROM m_variant";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int IDMaterialVariant = rs.getInt("idm_variant");
                    int materialeID = rs.getInt("materiale_id");
                    int length = rs.getInt("længde");
                    int partslistID = rs.getInt("stykliste_idstykliste");
                    int price = rs.getInt("pris");
                    String description = rs.getString("beskrivelse");
                    materialVariants.add(new MaterialVariant(IDMaterialVariant, materialeID, length, partslistID, description, price));
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error fetching all material variants");
        }
        return materialVariants;
    }
}
