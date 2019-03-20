import java.util.ArrayList;
import java.util.Iterator;

public class ttt {
	public static void main(String[] args) {
		User2 user = new User2();
		user.name

		= "张三";
		user.age = 32;
		user.job = "工程师";
		user.password = "123";
		user.email = "abc@126.com";
		user.salary = 10.0;
		user.show1();
	}
}

class User1 {
	String name;
	int age;
	String job;

	void show1() {
		System.out.println(name + "," + age + "," + job);
	}

}

class User2 extends User1 {
	String password;
	String email;
	double salary;

	void show2() {
		System.out.println(name + "," + age + "," + job + "," + password + "," + email + "," + salary);
	}

}
