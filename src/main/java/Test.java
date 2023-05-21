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


		System.out.println("Indtast ordre ID'et på den ordre du gerne vil 3D printe: ");
		Scanner scanner = new Scanner(System.in);
		int ordreID = scanner.nextInt();


//		int ordreID = 60; // ordre id 60 har stykliste id 140. Består af 10 materiale varianter
		List<MaterialVariant> materialVariants = print.findMaterialVariantsByOrderID(ordreID,connectionPool);

		print.convertMVMeasurements(materialVariants);


//		List<MaterialVariant> convertList = print.convertMVMeasurements(materialVariants);
//
//		MaterialVariant tegneTest = convertList.get(0);
//
//		double tegneW = tegneTest.getWidth();
//		double tegneH = tegneTest.getHeight();
//		double tegneD = tegneTest.getLength();
//
//
//		var beam = csg.box3D(tegneW, tegneH, tegneD, false);
//		csg.view(beam);
//
////
		for (MaterialVariant convertedMaterialVariant : materialVariants) {
			System.out.println("Materiale ID: " + convertedMaterialVariant.getMaterialeID());
			System.out.println("Beskrivelse: " + convertedMaterialVariant.getDescription());
			System.out.println("Brede: " + convertedMaterialVariant.getWidth());
			System.out.println("Højde: " + convertedMaterialVariant.getHeight());
			System.out.println("Længde: " + convertedMaterialVariant.getLength());
			System.out.println("--------------------");
		}


	}
}