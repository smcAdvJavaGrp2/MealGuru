/**
 * This is the User class. It has
 */

package user;

import java.util.GregorianCalendar;

import utility.DataFormat;

public class User {

	// VARIABLES

	private int userID;

	private String username;
	private String encryptedPassword;

	private String pictureExtension;

	private String email;
	private String phoneNumber;

	private String gender;

	private String dateOfBirth;

	private int height;
	private int weight;

	private Diet diet;

	// CONSTRUCTORS

	public User() {

	}

	public User(String username, String password) {

		this.setUsername(username);
		this.setPassword(password);
		// this.passwordEncryptor = new BasicPasswordEncryptor();
		// this.encryptedPassword =
		// this.passwordEncryptor.encryptPassword(password);

	}

	public User(String username, String password, String gender, String month, int day, int year, int weight,
			String pictureExtension, Diet diet) {

		this(username, password);

		this.setGender(gender);
		this.setDateOfBirth(month, day, year);
		this.setWeight(weight);
		this.setPictureExtension(pictureExtension);

		this.setDiet(diet);

	}

	// GETTERS

	public int getID() {
		return this.userID;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEncryptedPassword() {
		return this.encryptedPassword;
	}

	public String getGender() {
		return this.gender;
	}

	public String getDateOfBirth() {
		return this.dateOfBirth;
	}

	public String getAge() {
		return null;
	}

	public int getWeight() {
		return this.weight;
	}

	public int getHeight() {
		return this.height;
	}

	public Diet getDiet() {
		return this.diet;
	}

	public String getPictureExtension() {

		return this.pictureExtension;

	}

	// SETTERS

	public void setID(int userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return this.email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPassword(String password) {
		// this.passwordEncryptor = new BasicPasswordEncryptor();
		// this.encryptedPassword =
		// this.passwordEncryptor.encryptPassword(password);

		this.encryptedPassword = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {

		this.phoneNumber = phoneNumber;

	}

	public void setGender(String gender) {

		gender = gender.toUpperCase();

		this.gender = gender;

	}

	public void setDateOfBirth(String string) {
		this.dateOfBirth = string;
	}

	public void setDateOfBirth(String month, int day, int year) {

		if (month.equalsIgnoreCase("january"))
			this.setDateOfBirth(0, day, year);
		else if (month.equalsIgnoreCase("february"))
			this.setDateOfBirth(1, day, year);
		else if (month.equalsIgnoreCase("march"))
			this.setDateOfBirth(2, day, year);
		else if (month.equalsIgnoreCase("april"))
			this.setDateOfBirth(3, day, year);
		else if (month.equalsIgnoreCase("may"))
			this.setDateOfBirth(4, day, year);
		else if (month.equalsIgnoreCase("june"))
			this.setDateOfBirth(5, day, year);
		else if (month.equalsIgnoreCase("july"))
			this.setDateOfBirth(6, day, year);
		else if (month.equalsIgnoreCase("august"))
			this.setDateOfBirth(7, day, year);
		else if (month.equalsIgnoreCase("september"))
			this.setDateOfBirth(8, day, year);
		else if (month.equalsIgnoreCase("october"))
			this.setDateOfBirth(9, day, year);
		else if (month.equalsIgnoreCase("november"))
			this.setDateOfBirth(10, day, year);
		else if (month.equalsIgnoreCase("december"))
			this.setDateOfBirth(11, day, year);

	}

	public void setDateOfBirth(int month, int day, int year) {

		this.dateOfBirth = DataFormat.transformDateToString(new GregorianCalendar(year, month, day).getTime());

	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setPictureExtension(String pictureExtension) {
		this.pictureExtension = pictureExtension;
	}

	public void setDiet(Diet diet) {
		this.diet = diet;
	}

	// METHODS

	public boolean isPasswordCorrect(String inputPassword) {
		// if (this.passwordEncryptor.checkPassword(inputPassword,
		// this.encryptedPassword))
		// return true;
		// else
		// return false;
		return inputPassword.equals(this.encryptedPassword);
	}

	@Override
	public String toString() {

		String nl = "\n";

		return "Username: " + this.username + nl + "Password: " + " (" + this.getEncryptedPassword() + ")" + nl
				+ "Picture: " + this.getPictureExtension() + nl + "Email: " + this.getEmail() + nl + "Phone Number: "
				+ this.getPhoneNumber() + nl + "Gender: " + this.getGender().toString() + nl + "Date of Birth: "
				+ this.getDateOfBirth() + nl + "Weight: " + this.weight + nl + "Height: " + this.height;

	}

}
