import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Command {
	public static int step = 0;

	public static final double jumpCoefficient = 4.2;

	static String cmd_screenshot = "adb shell screencap -p /sdcard/jump.png";
	static String cmd_pull = "adb pull /sdcard/jump.png";
	static String cmd_touchscreen = "adb shell input touchscreen swipe 300 400 300 400 ";
	//static String cmd_k = "cmd /k start ";
	static String cmd_c = "cmd /c ";
	static String adbURL = "f:\\adb_kit\\";
	static String localImg = "f:\\WeJump_temp";

	public static void screenShot() {
		try {
			Process p1 = Runtime.getRuntime().exec(cmd_c + adbURL + cmd_screenshot);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p1.getErrorStream()));
			String s;
			while ((s = bufferedReader.readLine()) != null)
			System.out.println(s);
			p1.waitFor();
			step = 1;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void Pull() {
		try {
			Process p2 = Runtime.getRuntime().exec(cmd_c + adbURL + cmd_pull + " " + localImg);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
			String s;
			while ((s = bufferedReader.readLine()) != null)
			System.out.println(s);
			p2.waitFor();
			step = 2;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static double getDistance(Point p1,Point p2) {	
			return  Math.sqrt((p1.locationX-p2.locationX)*(p1.locationX-p2.locationX)+(p1.locationY-p2.locationY)*(p1.locationY-p2.locationY));
	}
	

	public static void longPress(int pressTime) {
		try {
			Process p5 = Runtime.getRuntime().exec(cmd_c + adbURL + cmd_touchscreen + pressTime);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p5.getErrorStream()));
			String s;
			while ((s = bufferedReader.readLine()) != null)
			System.out.println(s);
			p5.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
