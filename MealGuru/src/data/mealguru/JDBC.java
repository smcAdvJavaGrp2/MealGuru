package data.mealguru;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {

	Connection getMysqlConnection() throws Exception {

		Class.forName(BasicData.JDBC_NAME).newInstance();
		return DriverManager.getConnection(BasicData.JDBC_CONNECTION);

	}

}
