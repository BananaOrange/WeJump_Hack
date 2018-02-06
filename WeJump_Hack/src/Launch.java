// 第一步，截图至手机本地
// 第二步，将手机截图pull至电脑端
// 第三步，gui获取电脑端截图
// 第四步，计算两点之间长按时间
// 第五步，模拟手指长按

public class Launch {
	
	public static void stepOneTwo() {
			Command.screenShot();
			Command.Pull();
	}
	
	public static void main(String[] args) {
		Screen screen = new Screen();
		screen.createScreen();
		//第一二步
		if(Command.step == 0) {
			Launch.stepOneTwo();
		}
		//第三步
		if(Command.step == 2) {
			screen.guiGetPng();
		}
	}
}
