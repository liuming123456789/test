package servlet;

public class aaa {
	public static void main(String[] s) {
		for (int i = 0; i < 10; i++) {
			System.out.println(i);
			if (i == 2) {
				if (i == 2) {
					if (i == 2) {
						System.out.println("1 i==2");
						break;
					}
					System.out.println("2 i==2");// 条件满足时在continue后面不执行
				}
				System.out.println("3 i==2");// 条件满足时在continue后面不执行
			}
			System.out.println("name");// 条件满足时在continue后面不执行
		}
	}
}
