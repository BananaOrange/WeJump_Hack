import java.util.ArrayList;
import java.util.List;

public class Point {
	
	public int locationX;
	public int locationY;
	
	//��������װ�������յ�
	static List<Point> points = new ArrayList<Point>();
	
	public Point(int locationX,int locationY) {
		this.locationX = locationX;
		this.locationY = locationY;
	}
	
	public static void clearPoints() {
			points.clear();
	}
	
	
}
