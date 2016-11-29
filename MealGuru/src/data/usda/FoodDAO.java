package data.usda;

import java.util.ArrayList;
import java.util.SortedSet;

import edible.Food;

/**
 * DAO Design pattern interface
 * 
 * DAO design pattern uses a Data Access Object (DAO) to abstract and
 * encapsulate all access to the data source. The DAO manages the connection
 * with the data source to obtain and store data. The domain layer "domain
 * entities" / "model objects" (POJO) should be completely ignorant of the
 * persistence layer. In other words, in the domain object there is no storage
 * logic. It gets handled by data mappers.
 * 
 * CRUD Operations create read update delete
 *
 */
public interface FoodDAO {

	/**
	 * Searches database by description and returns an Array of Strings
	 * containing a name and id
	 *
	 * 
	 * @param search
	 * @return
	 */
	abstract SortedSet<String> searchFood(String search);

	/**
	 * 
	 * get operation takes arguments from search returns the respective Food
	 * object
	 *
	 * @param foodInfo
	 * @return
	 */
	abstract ArrayList<Food> getFoods(String foodStr);

}
