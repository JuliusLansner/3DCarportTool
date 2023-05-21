import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Print class gives utility with converting DB data into 3D printable measurements
 * The methods allows you to find material variants with an order ID, and covert them intoappropiate measurements that fits our current 3D printer
 */
public class Print {

    /* Skalering forklaret:
     * 3D printer kan maks printe 20 cm.
     * Den længste stolpe vi har er 600 cm IRL.
     * Skalering = max3DPrintLængde = størrelse * (20cm/600cm)
     * Eksempel af udregning af længden på et 4m langt materiale:
     * 3DPrintLænge = 400cm * (20cm/600cm)
     *  */

    final double maxPrintLength = 20;
    final double maxIRLLength = 600;

    /**
     * @param orderID is used as a tool to match an ID.
     * @param connectionPool is used to work with the Mappers and Facades
     * @return A list of material variants associated with the order ID
     * @throws SQLException if there is a problem accessing the database
     * @throws DatabaseException if there is an error with the database connection or syntax errors
     */
    public List<MaterialVariant> findMaterialVariantsByOrderID(int orderID, ConnectionPool connectionPool) throws SQLException, DatabaseException {

        List<MaterialVariant> materialVariants = new ArrayList<>();

        // finds a BOM list with a maching order ID
        List<Bom> boms = BomMapper.getBoms(connectionPool);
        Bom startBom = null;
        for (Bom bom : boms) {
            if (bom.getOrderId() == orderID) {
                startBom = bom;
                break;
            }
        }

        // finds all material variants that machtes the BOM ID and saves them in a list
        List<MaterialVariant> materialVariantList = MaterialVariantFacade.getAllMaterialVariants(connectionPool);
        for (MaterialVariant materialVariant : materialVariantList) {
            if (startBom != null) {
                int bomID = startBom.getId();
                if (materialVariant.getPartslistID() == bomID) {
                    materialVariants.add(materialVariant);
                }
            }
        }
        return materialVariants;
    }

    /**
     * @param materialVariants is the list that you want to convert
     * @return
     */
    public List<MaterialVariant> convertMVMeasurements(List<MaterialVariant> materialVariants) {

        for (MaterialVariant materialVariant : materialVariants) {
            int materialID = materialVariant.getMaterialeID();
            double lengthIRL = materialVariant.getLength();

            double widthInCM = 0;
            double heightInCM = 0;

            if (materialID == 1) {
                widthInCM = 4.5 * (maxPrintLength / maxIRLLength);
                heightInCM = 19.5 * (maxPrintLength / maxIRLLength);
            } else if (materialID == 2) {
                widthInCM = 9.7 * (maxPrintLength / maxIRLLength);
                heightInCM = 9.7 * (maxPrintLength / maxIRLLength);
            }

            double printableLength = lengthIRL * (maxPrintLength / maxIRLLength);

            materialVariant.setWidth(widthInCM);
            materialVariant.setHeight(heightInCM);
            materialVariant.setLength((int) printableLength);
        }
        return materialVariants;
    }
}