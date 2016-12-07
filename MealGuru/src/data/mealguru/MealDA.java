package data.mealguru;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import edible.Meal;
import edible.MealComponent;
import utility.DataFormat;

public class MealDA extends JDBC {

	private MealComponentDA mealComponentDA = new MealComponentDA();

	/**
	 * add a meal
	 *
	 * @return new meal_id
	 */
	public int saveMeal(Meal meal) {
		int ret_id = 0;
		try {

			Connection conn = super.getMysqlConnection();
			Statement stmt = conn.createStatement();

			String SQLStr = "INSERT INTO Meal (meal_name, pictureExtension, categories, directions, lastEdit) VALUES ('"
					+ meal.getName() + "','" + meal.getPictureExtension() + "','"
					+ DataFormat.transformToString(meal.getCategories()) + "','" + meal.getDirections() + "','"
					+ DataFormat.transformDateToString(new Date()) + "')";

			stmt.executeUpdate(SQLStr);
			ResultSet res = stmt.executeQuery("select last_insert_rowid() newid;");

			if (res.next())
				ret_id = res.getInt(1);

			if (ret_id != 0) {

				ArrayList<MealComponent> mealComponentList = meal.getMealComponents();
				MealComponent mealComponent;

				if (meal.getMealComponents() != null)

					for (int i = 0; i < mealComponentList.size(); i++) {

						mealComponent = mealComponentList.get(i);
						this.mealComponentDA.saveMealComponent(mealComponent, ret_id);

					}

			}

			stmt.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return ret_id;

	}

	/**
	 * Get meal based on meal_id
	 *
	 * @param meal_id
	 * @return Meal class
	 */
	public Meal findMealById(int meal_id) {

		Meal meal = new Meal();

		try {

			if (meal_id == 0)
				return null;

			Connection conn = super.getMysqlConnection();
			Statement stmt = conn.createStatement();

			StringBuilder SQLStr = new StringBuilder("SELECT * FROM Meal where meal_id = " + meal_id);

			ResultSet res = stmt.executeQuery(SQLStr.toString());

			while (res.next()) {

				meal.setID(res.getInt("meal_id"));
				meal.setName(res.getString("meal_name"));
				meal.setCategories(DataFormat.transformToArrayList(res.getString("categories")));
				meal.setDirections(res.getString("directions"));
				meal.setLastEdit(DataFormat.transformStringToDate(res.getString("lastEdit")));
				meal.setPictureExtension(res.getString("pictureExtension"));
				meal.addMealComponents(this.mealComponentDA.findMealComponentByMeal_id(meal_id));

			}

			stmt.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return meal;

	}

	public void updateMeal(Meal meal) {

		try {

			Connection conn = super.getMysqlConnection();
			Statement sqlStatement = conn.createStatement();

			String sqlString = "UPDATE Meal SET " + "meal_name = '" + meal.getName() + "', pictureExtension = '"
					+ meal.getPictureExtension() + "', categories = '"
					+ DataFormat.transformToString(meal.getCategories()) + "', directions = '" + meal.getDirections()
					+ "', " + "lastEdit = '" + DataFormat.transformDateToString(new Date()) + "' " + "WHERE meal_id = "
					+ meal.getID() + ";";

			sqlStatement.executeUpdate(sqlString);

			this.mealComponentDA.deleteMealComponentByMealId(meal.getID());

			for (int i = 0; i < meal.getMealComponents().size(); i++)
				this.mealComponentDA.saveMealComponent(meal.getMealComponents().get(i), meal.getID());

			sqlStatement.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * findMeal: Search Meal by meal_name
	 *
	 * @param meal_name
	 * @return Meal List
	 */
	public ArrayList<Meal> findMeals(String match) {

		ArrayList<Meal> mealList = new ArrayList<>();

		try {

			Connection conn = super.getMysqlConnection();
			Statement stmt = conn.createStatement();

			StringBuilder SQLStr = new StringBuilder("SELECT * FROM Meal where ");

			match = match.replace("'", "''");

			if ((match != null) && !match.equals(""))
				SQLStr.append("meal_name like '%" + match + "%' OR categories LIKE '%" + match + "%' and ");

			SQLStr.append("1=1");

			ResultSet res = stmt.executeQuery(SQLStr.toString());

			while (res.next()) {

				Meal meal = new Meal();

				meal.setID(res.getInt("meal_id"));
				meal.setName(res.getString("meal_name"));
				meal.setDirections(res.getString("directions"));
				meal.setLastEdit(DataFormat.transformStringToDate(res.getString("lastEdit")));
				meal.setPictureExtension(res.getString("pictureExtension"));
				meal.addMealComponents(this.mealComponentDA.findMealComponentByMeal_id(meal.getID()));

				mealList.add(meal);

			}

			stmt.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return mealList;

	}

	public ArrayList<String> findMealNames(String match) {

		ArrayList<String> mealList = new ArrayList<>();

		try {

			Connection conn = super.getMysqlConnection();
			Statement stmt = conn.createStatement();

			StringBuilder SQLStr = new StringBuilder("SELECT * FROM Meal where ");

			if ((match != null) && !match.equals(""))
				SQLStr.append("meal_name like '%" + match + "%' OR categories LIKE '%" + match + "%' and ");

			SQLStr.append("1=1");

			ResultSet res = stmt.executeQuery(SQLStr.toString());

			while (res.next())
				mealList.add(res.getString("meal_name"));

			stmt.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return mealList;

	}

	/**
	 * deleteMealById: delete meal based on meal_id
	 *
	 * @param meal_id
	 */
	public void deleteMealById(int meal_id) {

		if (meal_id != 0)

			try {

				Connection conn = super.getMysqlConnection();
				Statement stmt = conn.createStatement();

				String SQLStr = "delete from Meal where meal_id = " + meal_id;

				stmt.executeUpdate(SQLStr);

				this.mealComponentDA.deleteMealComponentByMealId(meal_id);

				stmt.close();
				conn.close();

			} catch (Exception e) {

				e.printStackTrace();

			}

	}

}
