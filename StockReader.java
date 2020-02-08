import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class StockReader {
		public static String stockName;
		public static URL apiURL;
		public static String jsonString = "";
	 
		public StockReader(String stockName) {
			this.stockName = stockName;
		}
		
		public static void setAPILink() throws MalformedURLException {
			apiURL = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stockName + "&interval=5min&outputsize=compact&apikey=3NW99LS85309Q6FH");
		}
		
		public static void setJsonString() throws IOException {
			StockReader.setAPILink();
			URLConnection conn = apiURL.openConnection();
			Scanner apiCallScanner = new Scanner(apiURL.openStream());
			while(apiCallScanner.hasNext()) {
				jsonString += apiCallScanner.nextLine();
			}
		}
		
		public static double getPrice(String date) throws JSONException, IOException {
			StockReader.setJsonString();
			JSONObject prices = new JSONObject(jsonString);
			return prices.getJSONObject("Time Series (Daily)").getJSONObject(date).getDouble("4. close");
		}
			
		public static void showJsonString() throws IOException {
			StockReader.setJsonString();
			System.out.println(jsonString);
		}
		
		public static boolean jsonObjectExists(String date) throws IOException, JSONException {
			StockReader.setJsonString();
			JSONObject currentObject = new JSONObject(jsonString);
			return !currentObject.getJSONObject("Time Series (Daily)").isNull(date);
		}
		
		public static void getPriceForLastNDays(int n) throws JSONException, IOException {
			for(int i = 0;i<n;i++) {
				String date = LocalDate.now().plusDays(-i-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				if(StockReader.jsonObjectExists(date)) {
					System.out.println(LocalDate.now().plusDays(-i-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " : " + StockReader.getPrice(date)); 
				}
			}
		}
		public static void main(String[] args) throws JSONException, IOException {
			StockReader AppleStockReader = new StockReader("AAPL");
			StockReader.getPriceForLastNDays(365);
		}
	}