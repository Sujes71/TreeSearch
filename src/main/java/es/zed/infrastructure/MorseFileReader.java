package es.zed.infrastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class MorseFileReader {

	public static Map<String, String> loadMorseMap(String filename) {
		Map<String, String> morseMap = new HashMap<>();
		Resource resource = new ClassPathResource(filename);

		try (InputStream inputStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(":");
				if (parts.length == 2) {
					String key = parts[0].trim().toLowerCase();
					String value = parts[1].trim().toLowerCase();
					morseMap.put(key, value);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading file: " + filename, e);
		}

		return morseMap;
	}

	public static String getMorseKey(Map<String, String> map, String value) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}
}