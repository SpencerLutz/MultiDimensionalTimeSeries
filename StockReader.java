import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class StockReader {
		private static String stockName;
		private static URL apiURL;
		private static String jsonString = "";
		private static ArrayList<Double> prices = new ArrayList<Double>();
	 
		public StockReader(String stockName) {
			this.stockName = stockName;
		}
		
		private static void setAPILink() throws MalformedURLException {
			apiURL = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + StockReader.stockName + "&interval=5min&outputsize=compact&apikey=3NW99LS85309Q6FH");
		}
		
		private static void setJsonString() throws IOException {
			StockReader.setAPILink();
			URLConnection conn = apiURL.openConnection();
			Scanner apiCallScanner = new Scanner(apiURL.openStream());
			while(apiCallScanner.hasNext()) {
				jsonString += apiCallScanner.nextLine();
			}
		}
		
		private static double getPrice(String date) throws JSONException, IOException {
			StockReader.setJsonString();
			JSONObject prices = new JSONObject(jsonString);
			return prices.getJSONObject("Time Series (Daily)").getJSONObject(date).getDouble("4. close");
		}
		
		private static boolean jsonObjectExists(String date) throws IOException, JSONException {
			StockReader.setJsonString();
			JSONObject currentObject = new JSONObject(jsonString);
			return !currentObject.getJSONObject("Time Series (Daily)").isNull(date);
		}
		
		public static void setPricesForLast(int n) throws JSONException, IOException {
			for(int i = 0;i<n;i++) {
				String date = LocalDate.now().plusDays(-i-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				if(StockReader.jsonObjectExists(date)) {
					prices.add(StockReader.getPrice(date));
				}
			}					
		}
		
		public static String getURL() {
			return StockReader.apiURL.toString();
		}
		
		public static String getJsonString() {
			return StockReader.jsonString;
		}
		
		public static ArrayList<Double> getPrices() {
			return StockReader.prices;
		}
		public static void main(String[] args) throws JSONException, IOException {
			StockReader AppleStockReader = new StockReader("AAPL");
			AppleStockReader.setPricesForLast(150);
			ArrayList<Double> prices = AppleStockReader.getPrices();
			for(int i = 0;i<prices.size();i++) {
				System.out.println(prices.get(i));
			}
		}
	}
