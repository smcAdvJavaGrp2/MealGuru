package user;

public class TestUser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = new User("name", "pass");
		
		System.out.println(user.isPasswordCorrect("pass"));
	}

}
