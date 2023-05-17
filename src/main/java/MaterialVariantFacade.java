

import java.util.ArrayList;
import java.util.List;

public class MaterialVariantFacade {

    public static ArrayList<MaterialVariant> getMaterialVariantByID(int IDMaterialVariant, ConnectionPool connectionPool) throws DatabaseException {
        return MaterialVariantMapper.getMaterialVariantByID(IDMaterialVariant, connectionPool);
    }

    static public List<MaterialVariant> getAllMaterialVariants(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialVariantMapper.getAllMaterialVariants(connectionPool);
    }

    static public void createMaterialVariant(int materialId, int length, int bonId, String description, int price, ConnectionPool connectionPool) throws DatabaseException {
        MaterialVariantMapper.createMaterialVariant(materialId, length, bonId, description, price, connectionPool);
    }

    static public void updateMaterialVariant(MaterialVariant materialVariant, ConnectionPool connectionPool) throws DatabaseException {
        MaterialVariantMapper.updateMaterialVariant(materialVariant, connectionPool);
    }

    static public void deleteMaterialVariant(int IDMaterialVariant, ConnectionPool connectionPool) throws DatabaseException {
        MaterialVariantMapper.deleteMaterialVariant(IDMaterialVariant, connectionPool);
    }
}



