
import java.util.List;

public class MaterialVariantFacade {

    public static List<MaterialVariant> getAllMaterialVariants(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialVariantMapper.getAllMaterialVariants(connectionPool);
    }
}



