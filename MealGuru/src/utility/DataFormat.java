package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataFormat {

	public static Date transformStringToDate(String dataStr) {

		if ((dataStr == null) || dataStr.equals("null"))
			return null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;

		try {
			date = format.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;

	}

	public static String transformDateToString(Date date) {

		if (date == null)
			date = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		return format.format(date);

	}

	public static ArrayList<String> transformToArrayList(String array) {

		String[] categories = array.split("#");

		for (String categorie : categories) {
		}

		ArrayList<String> arrayList = new ArrayList<>();

		for (String categorie : categories)
			arrayList.add(categorie);

		return arrayList;

	}

	public static String transformToString(ArrayList<String> arrayList) {

		StringBuffer str = new StringBuffer();

		if (arrayList == null)
			return "";

		for (int i = 0; i < arrayList.size(); i++) {
			str.append(arrayList.get(i));
			if (i != (arrayList.size() - 1))
				str.append("#");
		}

		return str.toString();
	}

}
