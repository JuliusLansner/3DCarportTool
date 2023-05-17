import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class Test
{
	public static void main(String[] args) throws DatabaseException {
		ConnectionPool connectionPool = new ConnectionPool();


		JavaCSG csg = JavaCSGFactory.createDefault();
		ArrayList<MaterialVariant> getid = MaterialVariantMapper.getMaterialVariantByID(23,connectionPool);
		System.out.println(getid);







		










	}
}
