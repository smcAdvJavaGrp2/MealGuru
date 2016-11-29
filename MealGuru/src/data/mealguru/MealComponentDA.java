package data.mealguru;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import edible.Food;
import edible.MealComponent;
import utility.Amount;

public class MealComponentDA extends JDBC {

	private FoodDA foodDA = new FoodDA();

	/**
	 * addMealComponent: add MealComponent
	 *
	 * @param a
	 *            MealComponent object
	 * @return new MealComponent_id
	 */
	public int saveMealComponent(MealComponent mealComponent, int mealID) {

		int mealComponentID = 0;

		try {

			Connection databaseConnection = super.getMysqlConnection();
			Statement sqlStatement = databaseConnection.createStatement();

			String SQLStr = "INSERT INTO MealComponent (meal_id, food_id, ingredientAmount) VALUES (" + mealID + ","
					+ mealComponent.getFood().getID() + ",'" + mealComponent.getAmount() + "')";

			sqlStatement.executeUpdate(SQLStr);

			ResultSet res = sqlStatement.executeQuery("select last_insert_rowid() newid;");

			if (res.next())
				mealComponentID = res.getInt(1);

			sqlStatement.close();
			databaseConnection.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return mealComponentID;

	}

	public void updateMealComponent(MealComponent mealComponent) {

		try {

			Connection databaseConnection = super.getMysqlConnection();
			Statement sqlStatement = databaseConnection.createStatement();

			String sqlString = "UPDATE MealComponent SET " + "food_id = '" + mealComponent.getFood().getID() + "', "
					+ "ingredientAmount = '" + mealComponent.getAmount() + "' WHERE id = " + mealComponent.getID()
					+ ";";

			sqlStatement.executeUpdate(sqlString);

			sqlStatement.close();
			databaseConnection.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * findMealComponentByMeal_id: Get a list of mealComponent based on meal_id
	 *
	 * @param meal_id
	 * @return List of MealComponent
	 */
	public ArrayList<MealComponent> findMealComponentByMeal_id(int meal_id) {

		if (meal_id == 0)
			return null;

		ArrayList<MealComponent> mealComponentList = new ArrayList<>();
		MealComponent mealComponent;

		try {

			Connection databaseConnection = super.getMysqlConnection();
			Statement sqlStatement = databaseConnection.createStatement();

			StringBuilder SQLStr = new StringBuilder("SELECT * FROM MealComponent WHERE meal_id = " + meal_id);

			ResultSet res = sqlStatement.executeQuery(SQLStr.toString());

			while (res.next()) {

				mealComponent = new MealComponent();

				mealComponent.setAmount(new Amount(res.getString("ingredientAmount")));
				mealComponent.setFood(this.foodDA.findFoodById(res.getInt("food_id")));

				mealComponentList.add(mealComponent);

			}

			sqlStatement.close();
			databaseConnection.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return mealComponentList;

	}

	/**
	 * findMealComponentById: Get mealComponent based on mealComponent_id
	 *
	 * @param mealComponent_id
	 * @return MealComponent class
	 */
	public MealComponent findMealComponentById(int mealComponent_id) {
		MealComponent mealComponent = new MealComponent();
		try {

			Connection databaseConnection = super.getMysqlConnection();
			Statement sqlStatement = databaseConnection.createStatement();

			if (mealComponent_id == 0)
				return null;

			StringBuilder SQLStr = new StringBuilder("SELECT * FROM MealComponent where id = " + mealComponent_id);

			ResultSet res = sqlStatement.executeQuery(SQLStr.toString());

			while (res.next()) {

				mealComponent.setID(res.getInt("id"));
				mealComponent.setAmount(new Amount(res.getString("ingredientAmount")));
				Food food = this.foodDA.findFoodById(mealComponent.getFood().getID());
				mealComponent.setFood(food);

			}

			sqlStatement.close();
			databaseConnection.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return mealComponent;

	}

	/**
	 * deleteMealComponentById: delete mealComponent based on mealComponent_id
	 *
	 * @param mealComponent_id
	 */
	public void deleteMealComponentById(int mealComponent_id) {

		if (mealComponent_id != 0)

			try {

				Connection databaseConnection = super.getMysqlConnection();
				Statement sqlStatement = databaseConnection.createStatement();

				String SQLStr = "delete from MealComponent where id = " + mealComponent_id;

				sqlStatement.executeUpdate(SQLStr);

				sqlStatement.close();
				databaseConnection.close();

			} catch (Exception e) {

				e.printStackTrace();

			}

	}

	/**
	 * deleteMealComponentByMealId: delete mealComponent based on meal_id
	 *
	 * @param meal_id
	 */
	public void deleteMealComponentByMealId(int meal_id) {
		if (meal_id != 0)

			try {

				Connection databaseConnection = super.getMysqlConnection();
				Statement sqlStatement = databaseConnection.createStatement();

				String SQLStr = "delete from MealComponent where meal_id = " + meal_id;

				if (BasicData.SQL_OUT_PRINT)
					System.out.println(
							"MealComponentDA class, the method is deleteMealComponentByMealId, SQL: " + SQLStr);

				sqlStatement.executeUpdate(SQLStr);
				sqlStatement.close();

				databaseConnection.close();

			} catch (Exception e) {

				e.printStackTrace();

			}

	}

}
