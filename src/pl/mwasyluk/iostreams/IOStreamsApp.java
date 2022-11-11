package pl.mwasyluk.iostreams;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class IOStreamsApp {

	public static void main(String[] args) {
		System.out.println("Elooo eclipse");
		try {
			System.out.println(new IOStreamsApp().getTextFromFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTextFromFile() throws Exception {
		var is = this.getClass().getClassLoader().getResourceAsStream("resources" + java.io.File.separator + "employees.txt");
		
		if (is == null) throw new IOException("Resource stream jest null");
		var dis = new DataInputStream(is);
		var isr = new InputStreamReader(dis, StandardCharsets.UTF_8);
		var br = new BufferedReader(isr);
		
		var fullFileContentBuilder = new StringBuilder();
		br.lines()
		.map(String::trim)
		.filter(line -> !line.isEmpty())
		.filter(this::isLineCorrect)
		.map(line -> line.split(",")[0].trim() + ",")
		.forEach(line -> fullFileContentBuilder.append(line.trim() + "\n"));
		return fullFileContentBuilder.toString();
	}
	
	private boolean isLineCorrect(String line) {
		var lineSplit = line.split(",");
		
		if (lineSplit.length > 1 && !lineSplit[1].isBlank()) {
			return false;
		}
		if (lineSplit[0].isBlank()) {
			return false;
		} 
		if (!line.endsWith(",")) {
			return false;
		}
		if (!Pattern.matches("^([\\p{L}|[0-9]]*\\s{0,1})*[,]{1}", line)) {
			return false;
		}
		
		return true;
	}
}
