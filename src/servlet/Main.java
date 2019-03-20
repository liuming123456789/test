package servlet;

class Banana {
	void peel(int i) {
		System.out.print(i);
	}

	private Integer test11111() {
		return 1;
	}
	
	
}

public class Main{
	static int value = 33;
	private  Integer test11131() {
		return 1;
	}
	public static void main(String[] args) throws Exception {
		/*
		 * String _mongoServerCfg =
		 * "192.168.123.201:10000#192.168.123.201:20000#192.168.123.201:30000";
		 * String[] arrMGDB = _mongoServerCfg.split("#");
		 * System.out.print(arrMGDB.length); String[] arrSubMGDB;
		 * 
		 * // mongoDB服务器ip String[] mongoServerIP = new String[arrMGDB.length];
		 * 
		 * // mongoDB服务器port int[] mongoServerPort = new int[arrMGDB.length];
		 */
		Banana a = new Banana();
		a.peel(2);
		Main b = new Main();
		b.test11131();
	}

	/*
	 * private void printValue() { int value = 3;
	 * System.out.println(this.value); }
	 */
}
