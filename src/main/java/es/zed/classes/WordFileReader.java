package es.zed.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class WordFileReader {

	public static Set<String> readWordsFromFile(String filename) {
		Set<String> words = new HashSet<>();
		Resource resource = new ClassPathResource(filename);

		try (InputStream inputStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] wordArray = line.split("\\s+");
				for (String word : wordArray) {
					if (!word.isEmpty()) {
						words.add(word);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading file: " + filename, e);
		}

		return words;
	}
}
