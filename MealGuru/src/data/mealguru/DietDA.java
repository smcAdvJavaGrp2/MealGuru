package data.mealguru;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gui.PrimaryWindow;
import user.Diet;
import user.User;
import utility.Amount;

public class DietDA extends JDBC {

	public int saveDiet(Diet diet) {

		int ret_id = 0;

		try {

			Connection conn = super.getMysqlConnection();
			Statement stmt = conn.createStatement();

			String SQLStr = "INSERT INTO Diet (user_id, caloric_goal, total_fat, saturated_fat, trans_fat, cholesterol, sodium, carbohydrates"
					+ ", dietary_fiber, sugar, protein, vitamin_a_goal, vitamin_c_goal,iron_goal,calcium_goal) VALUES ("
					+ PrimaryWindow.getActiveUser().getID() + "," + diet.getCaloriesLimit() + ",'"
					+ diet.getTotalFatLimit() + "','" + diet.getSaturatedFatLimit() + "','" + diet.getTransFatLimit()
					+ "','" + diet.getCholesterolLimit() + "','" + diet.getSodiumLimit() + "','"
					+ diet.getCarbohydratesLimit() + "','" + diet.getDietaryFiberLimit() + "','" + diet.getSugarLimit() + "', '" + diet.getProteinLimit()
					+ "','" + diet.getVitaminALimit() + "','" + diet.getVitaminCLimit() + "','" + diet.getIronLimit()
					+ "','" + diet.getCalciumLimit() + "');";

			stmt.executeUpdate(SQLStr);

			ResultSet res = stmt.executeQuery("SELECT last_insert_rowid() newid;");

			if (res.next())
				ret_id = res.getInt(1);

			stmt.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return ret_id;

	}
	
	public Diet getDiet() {

		Diet diet = null;
		
		try {
			
			Connection databaseConnection = super.getMysqlConnection();
			Statement sqlStatement = databaseConnection.createStatement();
			
			Statement statement = databaseConnection.createStatement();
			StringBuilder SQLStr = new StringBuilder("SELECT * FROM Diet WHERE user_id = "+PrimaryWindow.getActiveUser().getID()+";");

			ResultSet res = statement.executeQuery(SQLStr.toString());

			while (res.next()) {

				diet = new Diet();

				diet.setCaloriesLimit(res.getInt("caloric_goal"));
				diet.setTotalFatLimit(new Amount(res.getString("total_fat")));
				diet.setSaturatedFatLimit(new Amount(res.getString("saturated_fat")));
				diet.setTransFatLimit(new Amount(res.getString("trans_fat")));
				diet.setCholesterolLimit(new Amount(res.getString("cholesterol")));
				diet.setSodiumLimit(new Amount(res.getString("sodium")));
				diet.setCarbohydratesLimit(new Amount(res.getString("carbohydrates")));
				diet.setDietaryFiberLimit(new Amount(res.getString("dietary_fiber")));
				diet.setSugarLimit(new Amount(res.getString("sugar")));
				diet.setProteinLimit(new Amount(res.getString("protein")));
				diet.setVitaminALimit(res.getInt("vitamin_a_goal"));
				diet.setVitaminCLimit(res.getInt("vitamin_c_goal"));
				diet.setIronLimit(res.getInt("iron_goal"));
				diet.setCalciumLimit(res.getInt("calcium_goal"));

			}

			statement.close();
			databaseConnection.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return diet;

	}
	
	public void updateDiet(Diet diet) {

		try {

			Connection conn = super.getMysqlConnection();
			Statement stmt = conn.createStatement();
			
			String SQLStr = "UPDATE Diet SET " + 
					"caloric_goal = " + diet.getCaloriesLimit() + ", "+
					"total_fat = '" + diet.getTotalFatLimit() + "', "+
					"saturated_fat = '" + diet.getSaturatedFatLimit() + "', "+
					"trans_fat = '" + diet.getTransFatLimit() + "', "+
					"cholesterol = '" + diet.getCholesterolLimit() + "', "+
					"sodium = '" + diet.getSodiumLimit() + "', "+
					"carbohydrates = '" + diet.getCarbohydratesLimit() + "', "+
					"dietary_fiber = '" + diet.getDietaryFiberLimit() + "', "+
					"sugar = '" + diet.getSugarLimit() + "', "+
					"protein = '" + diet.getProteinLimit() + "', "+
					"vitamin_a_goal = " + diet.getVitaminALimit() + ", "+
					"vitamin_c_goal = " + diet.getVitaminCLimit() + ", "+
					"iron_goal = " + diet.getIronLimit() + ", "+
					"calcium_goal = " + diet.getCalciumLimit() +					
					" WHERE user_id = " + PrimaryWindow.getActiveUser().getID() + ";";

			stmt.executeUpdate(SQLStr);

			stmt.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
