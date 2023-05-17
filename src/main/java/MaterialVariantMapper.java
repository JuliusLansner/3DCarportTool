

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialVariantMapper {

    public static ArrayList<MaterialVariant> getMaterialVariantByID(int IDStykliste, ConnectionPool connectionPool) throws DatabaseException {

        MaterialVariant materialVariant;
        ArrayList<MaterialVariant> variants = new ArrayList<>();
        String sql = "SELECT * FROM m_variant WHERE stykliste_idstykliste = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, IDStykliste);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int materialeID = rs.getInt("materiale_id");
                    int length = rs.getInt("længde");
                    int partslistID = rs.getInt("stykliste_idstykliste");
                    String description = rs.getString("beskrivelse");
                    int price = rs.getInt("pris");
                    materialVariant = new MaterialVariant(IDStykliste, materialeID, length, partslistID, description, price);
                    variants.add(materialVariant);
                } else {
                    throw new DatabaseException("No material variant were found with: " + IDStykliste);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error fetching materialeVariant with ID = " + IDStykliste);
        }
        return variants;
    }

    static public List<MaterialVariant> getAllMaterialVariants(ConnectionPool connectionPool) throws DatabaseException {

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

    static public int createMaterialVariant(int materialId, int length, int bonId, String description, int price, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO m_variant (materiale_id, længde, stykliste_idstykliste, beskrivelse, pris) VALUES (?, ?, ?, ?, ?)";
        ResultSet generatedKeys = null;
        int id = 0;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, materialId);
                ps.setInt(2, length);
                ps.setInt(3, bonId);
                ps.setString(4, description);
                ps.setInt(5, price);
                ps.executeUpdate();

                generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    generatedKeys.close();
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error creating material variant");
        }
        return id;
    }

    static public void updateMaterialVariant(MaterialVariant materialVariant, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "UPDATE m_variant SET materiale_id = ?, længde = ?, stykliste_idstykliste = ? WHERE idm_variant = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, materialVariant.getMaterialeID());
                ps.setInt(2, materialVariant.getLength());
                ps.setInt(3, materialVariant.getPartslistID());
                ps.setInt(4, materialVariant.getMaterialeVariantID());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Error updating material variant: " + materialVariant.getMaterialeVariantID());
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error updating material variant: " + materialVariant.getMaterialeVariantID());
        }
    }

    static public void deleteMaterialVariant(int IDMaterialVariant, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "DELETE FROM m_variant WHERE idm_variant = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, IDMaterialVariant);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("Error deleting material variant with ID: : " + IDMaterialVariant);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error deleting material variant with ID: : " + IDMaterialVariant);
        }
    }

}
