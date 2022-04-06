package sandboxapii;

import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class ComputerJson {
	private JSONObject listing;

	public enum Price {
		StartPrice, ReservePrice, BuyNowPrice

	}

	public ComputerJson(String filePath) {
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(filePath)) {	
			Object obj = jsonParser.parse(reader);
			listing = (JSONObject) obj;	

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public ComputerJson setTitle(String title) {
		listing.put("Title", title);
		return this;
	}

	@SuppressWarnings("unchecked")
	public ComputerJson setDuration(int duration) {
		listing.put("Duration", duration);
		return this;
	}

	@SuppressWarnings("unchecked")
	public ComputerJson setAttribute(String name, String value) {
		JSONArray attributes = (JSONArray) listing.get("Attributes");
		JSONArray attr_list = new JSONArray();

		for (int i = 0; i < attributes.size(); i++) {
			JSONObject jsonObject = (JSONObject) attributes.get(i);

			if (jsonObject.get("DisplayName").equals(name)) {
				jsonObject.put("Value", value);
			}
			attr_list.add(jsonObject);

		}

		listing.put("Attributes", attr_list);

		return this;
	}

	@SuppressWarnings("unchecked")
	public ComputerJson setPrice(Price startprice, double d) {
		if (listing.containsKey(startprice.toString())) {
			listing.put(startprice.toString(), d);
		}

		return this;
	}

	public String getJsonString() {
		return listing.toJSONString();
	}
	
	public JSONObject getJsonObject() {
		return listing;
	}

}
