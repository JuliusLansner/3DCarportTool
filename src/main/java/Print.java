import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The print class provides methods that can convert material variants into 3D printable measurements, and draw the project in the related OpenSCAD file.
 * It is used by users of this applikation, who wants to create a 3D version of a given carport.
 * An object of this class is made the Main class
 */
public class Print {

    private static final double maxPrintLength = 20;
    private static final double maxIRLLength = 60;

    /**
     * @param orderID        the ID that is entered by the user
     * @param connectionPool allows for database access
     * @return a list of material variants associated with the order ID
     * @throws SQLException      if there is an error accesing the database
     * @throws DatabaseException if there is an error with the database connection
     */
    public List<MaterialVariant> findMaterialVariantsByOrderID(int orderID, ConnectionPool connectionPool) throws SQLException, DatabaseException {
        List<MaterialVariant> materialVariants = new ArrayList<>();

        // finds a BOM list with a macthing order ID
        List<Bom> boms = BomFacade.getBoms(connectionPool);
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
     * @param materialVariants the list of material variants to convert
     */
    public void convertMVMeasurements(List<MaterialVariant> materialVariants) {

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
    }

    /**
     * @param orderID        is the ID that is entered by the user
     * @param connectionPool allows for database acces
     * @return a list of material variants that has been converted into 3D printable measurements
     * @throws SQLException      if there is an error accesing the database
     * @throws DatabaseException if there is an error with the database connection
     */
    public List<MaterialVariant> findAndConvertMVsByOrderID(int orderID, ConnectionPool connectionPool) throws SQLException, DatabaseException {
        List<MaterialVariant> materialVariants = findMaterialVariantsByOrderID(orderID, connectionPool);
        convertMVMeasurements(materialVariants);
        return materialVariants;
    }

    /**
     * @param materialVariants the list of material variants to create beams from
     * @param csg              is used to create 3D objects in OpenSCAD
     * @return a list of 3D beams
     */
    public List<Geometry3D> createBeams(List<MaterialVariant> materialVariants, JavaCSG csg) {
        List<Geometry3D> beamsToDraw = new ArrayList<>();

        // start position on SCAD axis
        double x = 0;
        double y = 0;
        double z = 0;

        // draws every beam
        for (MaterialVariant tegneMV : materialVariants) {
            double width = tegneMV.getWidth();
            double height = tegneMV.getHeight();
            double length = tegneMV.getLength();

            // create beams
            var beam = csg.box3D(length, width, height, false);

            // moves the placement of the beam
            beam = csg.translate3D(x, y, z).transform(beam);

            beamsToDraw.add(beam);

            // updates the beams position for every beam
            y += 10;
        }
        return beamsToDraw;
    }

    /**
     * @param materialVariants is the list of material variants the user wants to count and print out
     */
    public void printMVCounts(List<MaterialVariant> materialVariants) {
        // count the number of each type of material variant that is needed to build the project
        int rafterCount = 0;
        int beamCount = 0;
        int postCount = 0;

        for (MaterialVariant materialVariant : materialVariants) {
            int materialID = materialVariant.getMaterialeID();
            String description = materialVariant.getDescription();

            if (materialID == 1 && description.equals("Spær til taget")) {
                rafterCount++;
            } else if (materialID == 1 && description.equals("Rem til oven på stolper")) {
                beamCount++;
            } else if (materialID == 2 && description.equals("Stolper til længde") || description.equals("Stolper til brede")) {
                postCount++;
            }
        }
        // shows the user how many of each item should be used to complete the project
        System.out.println("For at bygge projektet skal du bruge: ");
        System.out.println("Spær: " + rafterCount);
        System.out.println("Remme: " + beamCount);
        System.out.println("Stolper: " + postCount);
    }

    /**
     * @param orderID        the ID that is entered by the user
     * @param connectionPool allows for database access
     * @param csg            is used to create 3D objects in OpenSCAD
     */
    public void printAndDrawProject(int orderID, ConnectionPool connectionPool, JavaCSG csg) {
        try {
            // find and convert the material variants
            List<MaterialVariant> materialVariants = findAndConvertMVsByOrderID(orderID, connectionPool);

            // create the materials based on the MVs
            List<Geometry3D> allMaterials = createBeams(materialVariants, csg);

            // create a collection of beams
            Geometry3D collection = csg.union3D(allMaterials);

            // view the collection
            csg.view(collection);

            // shows the user how many of each should be made to complete the project
            printMVCounts(materialVariants);
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
        }
    }
}