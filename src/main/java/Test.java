import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class Test
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		JavaCSG csg = JavaCSGFactory.createDefault();


		int length = 240;
		int width = 240;

		if(length==240 && width==240){
			int stolpeAmount = 4;
			for (int i = 0; i < stolpeAmount ; i++) {

		}
		}

		Geometry3D box = csg.box3D(200,200,20,true);//bottom

		Geometry3D rectroof1 = csg.box3D(20,200,150,false);//roof1
		rectroof1 = csg.translate3D(130,0,-10).transform(rectroof1);//right roof
		rectroof1 = csg.rotate3DY(csg.degrees(-45)).transform(rectroof1);



		Geometry3D rectroof2 = csg.box3D(20,200,150,false);//roof2
		rectroof2 = csg.translate3D(-130,0,-10).transform(rectroof2);//left roof
		rectroof2 = csg.rotate3DY(csg.degrees(45)).transform(rectroof2);

		Geometry3D rect = csg.box3D(20,200,80,false);//ylong
		Geometry3D rect3 = csg.box3D(20,200,80,false);//ylong
		Geometry3D rect4 = csg.box3D(200,20,80,false);//xlong

		rect = csg.translate3D(-90,0,10).transform(rect);//ylong
		rect3 = csg.translate3D(90,0,10).transform(rect3);//ylong
		rect4 = csg.translate3D(0,90,10).transform(rect4);//xlong
		var shapetri = csg.intersection3D(rectroof1,triangle); // making the backside triangle


		//putting it all together
		var shape1 = csg.union3D(box,rect);
		var shape2=csg.union3D(shape1,rectroof1);
		var shape3 = csg.union3D(shape2,rect3);
		var shape4 = csg.union3D(shape3,rect4);
		var shape5=csg.union3D(shape4,rect4);
		var shape6=csg.union3D(shape5,rectroof2);
		var shape7 = csg.union3D(shape6,shapetri);

		ArrayList<Geometry3D> shapes = new ArrayList();
		shapes.add(box);
		shapes.add(rect);
		shapes.add(rect3);
		shapes.add(rect3);
		shapes.add(rect4);
		shapes.add(rectroof1);
		shapes.add(rectroof2);


		for (int i = 0; i < shapes.size() ; i++) {
			var shapeFinal = csg.union3D(shapes.get(i),shapes.get(i++));

		}

		csg.view(shape7);
	}
}
