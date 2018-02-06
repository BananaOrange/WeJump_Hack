import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMonitor extends MouseAdapter {
	public static int clickFlag = 0;
	public static boolean isClicked = false;

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			clickFlag = (clickFlag + 1) % 2;
			isClicked = true;

			if (Point.points.size() == 0) {
				if(MouseMonitor.isClicked == true) {
					Point.points.add(new Point(e.getX(), e.getY()));
					isClicked = false;
				}
			}
			if (Point.points.size() == 1) {
				if (MouseMonitor.isClicked == true) {
					Point.points.add(new Point( e.getX(), e.getY()));
					isClicked = false;
				}
			}
			if(Point.points.size() == 2) {
				int pressTime = (int) (Command.jumpCoefficient*Command.getDistance(Point.points.get(0),Point.points.get(1)));
				Screen.canGetPressTime = true;
				Command.longPress(pressTime);
			}
		}
		
	}
}

