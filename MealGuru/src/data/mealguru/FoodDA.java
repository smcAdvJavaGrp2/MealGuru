package data.mealguru;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import edible.Food;
import utility.Amount;
import utility.DataFormat;

public class FoodDA extends data.mealguru.JDBC {

	/**
	 * findFood: Search food by food_name
	 *
	 * @param match
	 * @return food List
	 */
	public ArrayList<edible.Food> findFood(String match) {

		ArrayList<Food> foodList = new ArrayList<>();

		try {

			Connection databaseConnection = super.getMysqlConnection();
			Statement sqlStatement = databaseConnection.createStatement();

			StringBuilder sqlString = new StringBuilder("SELECT * FROM Food WHERE ");

			if ((match != null) && !match.equals(""))
				sqlString.append("food_name LIKE '%" + match + "%' OR categories LIKE '%" + match + "%' AND ");

			sqlString.append("1=1");

			ResultSet res = sqlStatement.executeQuery(sqlString.toString());

			while (res.next()) {

				Food food = new Food();

				food.setFoodID(res.getInt("food_id"));
				food.setName(res.getString("food_name"));
				food.setPictureExtension(res.getString("pictureExtension"));

				food.setCategories(DataFormat.transformToArrayList(res.getString("categories")));
				food.setLastEdit(DataFormat.transformStringToDate(res.getString("lastEdit")));

				food.setServingSize(new Amount(res.getString("unitsPerServingSize")));
				food.setServingSize(new Amount(res.getString("weightPerServingSize")));
				food.setServingSize(new Amount(res.getString("liquidVolumePerServingSize")));
				food.setServingSize(new Amount(res.getString("servingOfThis")));

				food.setCalories(res.getDouble("calories"));
				food.setTotalFat(new Amount(res.getString("totalFat")));
				food.setSaturatedFat(new Amount(res.getString("saturatedFat")));
				food.setTransFat(new Amount(res.getString("transFat")));
				food.setCholesterol(new Amount(res.getString("cholesterol")));
				food.setSodium(new Amount(res.getString("sodium")));
				food.setCarbohydrates(new Amount(res.getString("carbohydrates")));
				food.setDietaryFiber(new Amount(res.getString("dietaryFiber")));
				food.setSugar(new Amount(res.getString("sugar")));
				food.setProtein(new Amount(res.getString("protein")));
				food.setVitaminA(res.getDouble("vitaminA"));
				food.setVitaminC(res.getDouble("vitaminC"));
				food.setCalcium(res.getDouble("calcium"));
				food.setIron(res.getDouble("iron"));

				foodList.add(food);

			}

			sqlStatement.close();
			databaseConnection.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return foodList;

	}

	public ArrayList<String> findFoodNames(String match) {

		ArrayList<String> foodNameList = new ArrayList<>();

		try {

			Connection databaseConnection = super.getMysqlConnection();
			Statement sqlStatement = databaseConnection.createStatement();

			StringBuilder sqlString = new StringBuilder("SELECT food_name FROM Food WHERE ");

			if ((match != null) && !match.equals(""))
				sqlString.append("food_name LIKE '%" + match + "%' OR categories LIKE '%" + match + "%' AND ");

			sqlString.append("1=1");

			ResultSet res = sqlStatement.executeQuery(sqlString.toString());

			while (res.next())
				foodNameList.add(res.getString("food_name"));

			sqlStatement.close();
			databaseConnection.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		System.out.println(foodNameList);
		return foodNameList;
	}

	/**
	 * findFood: Get food based on food_id
	 *
	 * @param food_id
	 * @return Food class
	 */
	public Food findFoodById(int food_id) {

		Food food = new Food();

		try {

			Connection databaseConnection = super.getMysqlConnection();
			Statement sqlStatement = databaseConnection.createStatement();

			if (food_id == 0)
				return null;

			StringBuilder sqlString = new StringBuilder("SELECT * FROM Food where food_id = " + food_id);

			ResultSet res = sqlStatement.executeQuery(sqlString.toString());

			while (res.next()) {

				food = new Food();

				food.setFoodID(res.getInt("food_id"));
				food.setName(res.getString("food_name"));
				food.setPictureExtension(res.getString("pictureExtension"));

				food.setCategories(DataFormat.transformToArrayList(res.getString("categories")));
				food.setLastEdit(DataFormat.transformStringToDate(res.getString("lastEdit")));

				food.setServingSize(new Amount(res.getString("unitsPerServingSize")));
				food.setServingSize(new Amount(res.getString("weightPerServingSize")));
				food.setServingSize(new Amount(res.getString("liquidVolumePerServingSize")));
				food.setServingSize(new Amount(res.getString("servingsOfThis")));
				food.setCalories(res.getDouble("calories"));
				food.setTotalFat(new Amount(res.getString("totalFat")));
				food.setSaturatedFat(new Amount(res.getString("saturatedFat")));
				food.setTransFat(new Amount(res.getString("transFat")));
				food.setCholesterol(new Amount(res.getString("cholesterol")));
				food.setSodium(new Amount(res.getString("sodium")));
				food.setCarbohydrates(new Amount(res.getString("carbohydrates")));
				food.setDietaryFiber(new Amount(res.getString("dietaryFiber")));
				food.setSugar(new Amount(res.getString("sugar")));
				food.setProtein(new Amount(res.getString("protein")));
				food.setVitaminA(res.getDouble("vitaminA"));
				food.setVitaminC(res.getDouble("vitaminC"));
				food.setCalcium(res.getDouble("calcium"));
				food.setIron(res.getDouble("iron"));

			}

			sqlStatement.close();
			databaseConnection.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return food;

	}

	/**
	 * addFood: add food
	 *
	 * @param
	 * @return food_id
	 */
	public int saveFood(Food food) {

		int food_id = 0;

		try {

			Connection conn = super.getMysqlConnection();
			Statement sqlStatement = conn.createStatement();

			String sqlString = "INSERT INTO Food (" + "food_name, pictureExtension, " + "categories, lastEdit, "
					+ "unitsPerServingSize, weightPerServingSize, " + "liquidVolumePerServingSize, servingsOfThis, calories, "
					+ "totalFat, saturatedFat, " + "transFat, cholesterol, " + "sodium, carbohydrates, "
					+ "dietaryFiber, sugar, " + "protein, vitaminA, " + "vitaminC, calcium, iron) " + "VALUES ('"
					+ food.getName().replaceAll("'", "''") + "','" + food.getPictureExtension() + "','"
					+ DataFormat.transformToString(food.getCategories()) + "','"
					+ DataFormat.transformDateToString(food.getLastEdit()) + "','" + food.getUnitsPerServingSize()
					+ "','" + food.getWeightPerServingSize() + "','" + food.getLiquidVolumePerServingSize() + "','" + food.getServingSize() + "', "
					+ food.getCalories() + ",'" + food.getTotalFat() + "','" + food.getSaturatedFat() + "','"
					+ food.getTransFat() + "','" + food.getCholesterol() + "','" + food.getSodium() + "','"
					+ food.getCarbohydrates() + "','" + food.getDietaryFiber() + "','" + food.getSugar() + "','"
					+ food.getProtein() + "'," + food.getVitaminA() + "," + food.getVitaminC() + "," + food.getCalcium()
					+ "," + food.getIron() + ")";

			sqlStatement.executeUpdate(sqlString);
			ResultSet res = sqlStatement.executeQuery("SELECT last_insert_rowid() newid;");

			if (res.next())
				food_id = res.getInt(1);

			sqlStatement.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return food_id;

	}

	public void updateFood(Food food) {

		try {

			Connection conn = super.getMysqlConnection();
			Statement sqlStatement = conn.createStatement();
			
			String sqlString = "UPDATE Food SET " + "food_name = '" + food.getName().replaceAll("'", "''") + "', " + "pictureExtension = '"
					+ food.getPictureExtension() + "', " + "categories = '"
					+ DataFormat.transformToString(food.getCategories()) + "', " + "lastEdit = '"
					+ DataFormat.transformDateToString(food.getLastEdit()) + "', " + "unitsPerServingSize = '"
					+ food.getUnitsPerServingSize() + "', " + "weightPerServingSize = '"
					+ food.getWeightPerServingSize() + "', " + "liquidVolumePerServingSize = '"
					+ food.getLiquidVolumePerServingSize() + "', servingsOfThis = '"+food.getServingSize()+"', " + "calories = '" + food.getCalories() + "', "
					+ "totalFat = '" + food.getTotalFat() + "', " + "saturatedFat = '" + food.getSaturatedFat() + "', "
					+ "transFat = '" + food.getTransFat() + "', " + "cholesterol = '" + food.getCholesterol() + "', "
					+ "sodium = '" + food.getSodium() + "', " + "carbohydrates = '" + food.getCarbohydrates() + "', "
					+ "dietaryFiber = '" + food.getDietaryFiber() + "', " + "sugar = '" + food.getSugar() + "', "
					+ "protein = '" + food.getProtein() + "', " + "vitaminA = " + food.getVitaminA() + ", "
					+ "vitaminC = " + food.getVitaminC() + ", " + "calcium = " + food.getCalcium() + ", " + "iron = "
					+ food.getIron() + " " + "WHERE food_id = " + food.getID() + ";";

			sqlStatement.executeUpdate(sqlString);

			sqlStatement.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * deleteFood: delete food
	 *
	 * @param food_id
	 */
	public void deleteFood(int food_id) {

		if (food_id != 0)

			try {

				Connection databaseConnection = super.getMysqlConnection();
				Statement sqlStatement = databaseConnection.createStatement();

				String sqlString = "delete from food where food_id = " + food_id;

				if (BasicData.SQL_OUT_PRINT)
					System.out.println("FoodDA class, the method is deleteFood, SQL: " + sqlString);

				sqlStatement.executeUpdate(sqlString);

				sqlStatement.close();
				databaseConnection.close();

			} catch (Exception e) {

				e.printStackTrace();

			}

	}

}
