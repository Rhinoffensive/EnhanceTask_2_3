package sandboxapii;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

	public String getProperty(String key) {
		try (InputStream input = new FileInputStream("ConfigFiles/trademe.properties")) {

			Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			return prop.get(key).toString();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

	}

}
