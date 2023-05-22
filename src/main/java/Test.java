import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Test
{
	public static void main(String[] args) throws DatabaseException, SQLException {
		ConnectionPool connectionPool = new ConnectionPool();
		JavaCSG csg = JavaCSGFactory.createDefault();
		Print print = new Print();

		// OBS ordre ID 66 har stykliste ID 146
		// Da der findes flere MV'er med samme material ID og længde genereres der "kun" 2 objekter
		// I dette tilfælde er remme og spær det "samme".

		System.out.println("Indtast et ordre ID :");
		Scanner scanner = new Scanner(System.in);
		int orderID = scanner.nextInt();


//		int orderID = 60;

		try {
			// find and convert the material variants
			List<MaterialVariant> materialVariants = print.findAndConvertMVsByOrderID(orderID, connectionPool);

			// filter the unique mv's
			List<MaterialVariant> filteredMVs = print.filterUniqueMaterialVariants(materialVariants);

			// create the beam based on the MVs
			List<Geometry3D> beams = print.createBeams(filteredMVs,csg);

			// create a collection of beams
			Geometry3D collection = csg.union3D(beams);

			// view the collection
			csg.view(collection);

			// shows the user how many of each should be made to complete the project
			print.printMVCounts(materialVariants);
		} catch (SQLException |DatabaseException e) {
			e.printStackTrace();
		}
	}
}

//		for (MaterialVariant convertedMaterialVariant : materialVariants) {
//			System.out.println("Materiale ID: " + convertedMaterialVariant.getMaterialeID());
//			System.out.println("Beskrivelse: " + convertedMaterialVariant.getDescription());
//			System.out.println("Brede: " + convertedMaterialVariant.getWidth());
//			System.out.println("Højde: " + convertedMaterialVariant.getHeight());
//			System.out.println("Længde: " + convertedMaterialVariant.getLength());
//			System.out.println("--------------------");
//		}


