// ��һ������ͼ���ֻ�����
// �ڶ��������ֻ���ͼpull�����Զ�
// ��������gui��ȡ���Զ˽�ͼ
// ���Ĳ�����������֮�䳤��ʱ��
// ���岽��ģ����ָ����

public class Launch {
	
	public static void stepOneTwo() {
			Command.screenShot();
			Command.Pull();
	}
	
	public static void main(String[] args) {
		Screen screen = new Screen();
		screen.createScreen();
		//��һ����
		if(Command.step == 0) {
			Launch.stepOneTwo();
		}
		//������
		if(Command.step == 2) {
			screen.guiGetPng();
		}
	}
}
