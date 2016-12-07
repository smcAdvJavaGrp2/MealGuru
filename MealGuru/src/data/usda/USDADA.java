package data.usda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import edible.Food;
import utility.Amount;
import utility.DataFormat;

/**
 * Data Access Object concrete class - This class implements FoodDAO interface.
 * This class is responsible for getting data from a data source which is a
 * database
 */
public class USDADA {

	// TO DO: ENTITY MANAGER, ADD THE OTHER CRUD METHODS
	// DATABASE USES INCREMENTAL INTEGER SUROGATE KEYS,
	public SortedSet<String> searchFood(String search, String category) {
		SortedSet<String> foods = new TreeSet<>();
		// SELECT (column, ... ) FROM (table) WHERE (column) Like (wildcard)
		// http://dev.mysql.com/doc/refman/5.7/en/index-btree-hash.html
		// LIKE is an interesting relational search pattern (Don't forget the
		// %s)

		String sqlQuery = "";

		if ((category != null) && !category.equals(""))
			sqlQuery = "SELECT food.id, food.long_desc, food.food_group_id, food_group.name, food_group.id AS group_id FROM food INNER JOIN food_group "
					+ " ON food.food_group_id = food_group.id WHERE long_desc LIKE ? AND food_group.name LIKE '%"
					+ category + "%';";
		else
			sqlQuery = "SELECT food.long_desc FROM food " + "WHERE long_desc LIKE ?;";

		// Spring Framework JDBC MAKES Statements and ResultSets UNECESSARY,
		// might be worth learning

		// https://www.tutorialspoint.com/jdbc/jdbc-statements.htm
		// Statement is vulnerable to SQL Injection
		PreparedStatement preparedStatement = null;
		// https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
		// Used to retrieve and modify
		ResultSet rs = null;

		try {
			Class.forName("org.sqlite.JDBC");
			// Connection conn =
			// DriverManager.getConnection("jdbc:sqlite::resource:db/usda.sql3");
			Connection conn = DriverManager.getConnection("jdbc:sqlite::resource:usda.sql3");
			preparedStatement = conn.prepareStatement(sqlQuery);
			// databases usually use 1-based indexing for bound parameters
			// instead of 0

			preparedStatement.setString(1, "%" + search + "%");
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String longDesc = rs.getString("long_desc");
				foods.add(longDesc);

			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return foods;
	}

	public ArrayList<Food> getFoods(String foodStr, String category) {

		ArrayList<Food> foodsToReturn = new ArrayList<>();

		try {

			Connection conn = DriverManager.getConnection("jdbc:sqlite::resource:usda.sql3");
			Class.forName("org.sqlite.JDBC");

			foodStr = foodStr.replace("'", "''");

			String sqlSelect = "";

			if ((category != null) && !category.equals(""))
				sqlSelect = "SELECT food.id, food.long_desc, food_group.id AS group_id, name FROM food "
						+ "INNER JOIN food_group ON food.food_group_id = group_id WHERE (food.long_desc LIKE '%"
						+ foodStr + "%' OR food_group.name LIKE '%" + foodStr + "%') AND (name LIKE '%" + category
						+ "%');";
			else
				sqlSelect = "SELECT food.id, food.long_desc, food_group.id AS group_id, name FROM food "
						+ "INNER JOIN food_group ON food.food_group_id = group_id WHERE (food.long_desc LIKE '%"
						+ foodStr + "%' OR food_group.name LIKE '%" + foodStr + "%');";

			PreparedStatement preparedStatement1 = conn.prepareStatement(sqlSelect);
			ResultSet resultSetOfFood = preparedStatement1.executeQuery();

			Food food;

			while (resultSetOfFood.next()) {

				food = new Food();
				food.setName(resultSetOfFood.getString("long_desc"));

				food.setServingSize(new Amount("100 g"));

				food.setCategories(DataFormat.transformToArrayList(resultSetOfFood.getString("name")));

				sqlSelect = "SELECT nutrient.name, nutrition.amount, nutrient.units FROM nutrition "
						+ "INNER JOIN nutrient ON nutrition.food_id = " + resultSetOfFood.getInt("id")
						+ " AND nutrition.nutrient_id = nutrient.id;";

				PreparedStatement preparedStatement2 = conn.prepareStatement(sqlSelect);
				ResultSet resultSetOfNutrients = preparedStatement2.executeQuery();

				while (resultSetOfNutrients.next()) {

					String name = resultSetOfNutrients.getString("name");
					double amount = resultSetOfNutrients.getDouble("amount");
					String units = resultSetOfNutrients.getString("units");

					if (name.equalsIgnoreCase("ENERGY") && units.equalsIgnoreCase("kcal"))
						food.setCalories(amount);
					else if (name.equalsIgnoreCase("Total lipid (fat)"))
						food.setTotalFat(new Amount(amount + " " + units));
					else if (name.equalsIgnoreCase("Fatty acids, total saturated"))
						food.setSaturatedFat(new Amount(amount + " " + units));
					else if (name.equalsIgnoreCase("Fatty acids, total trans"))
						food.setTransFat(new Amount(amount + " " + units));
					else if (name.equalsIgnoreCase("Cholesterol"))
						food.setCholesterol(new Amount(amount + " " + units));
					else if (name.equalsIgnoreCase("Sodium"))
						food.setSodium(new Amount(amount + " " + units));
					else if (name.equalsIgnoreCase("Carbohydrate, by difference"))
						food.setCarbohydrates(new Amount(amount + " " + units));
					else if (name.equalsIgnoreCase("Fiber, total dietary"))
						food.setDietaryFiber(new Amount(amount + " " + units));
					else if (name.equalsIgnoreCase(""))
						food.setSugar(new Amount(amount + " " + units));
					else if (name.equalsIgnoreCase("Protein"))
						food.setProtein(new Amount(amount + " " + units));
					else if (name.equalsIgnoreCase("Vitamin A, RAE")) {
						if (units.equalsIgnoreCase("mcg"))
							food.setVitaminA((amount / 800) * 100);
						System.out.println(food.getVitaminA());
					} else if (name.equalsIgnoreCase("Vitamin C, total ascorbic acid"))
						food.setVitaminC((amount / 85) * 100);
					else if (name.equalsIgnoreCase("Calcium, Ca"))
						food.setCalcium((amount / 1000) * 100);
					else if (name.equalsIgnoreCase("Iron, Fe"))
						food.setIron((amount / 19) * 100);

				}

				resultSetOfNutrients.close();

				foodsToReturn.add(food);
				if (foodsToReturn.size() > 15)
					return foodsToReturn;

			}

			resultSetOfFood.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return foodsToReturn;

	}

}