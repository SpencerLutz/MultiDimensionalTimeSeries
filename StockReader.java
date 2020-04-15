
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StockReader {
	
		public static String getURLString(String stockName) {
			return "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stockName + "&interval=5min&outputsize=compact&apikey=3NW99LS85309Q6FH";
		}
		
		public static URL getAPIUrl(String stockName) throws MalformedURLException {
			return new URL(getURLString(stockName));
		}
		
		public static String getJsonString(String stockName) throws IOException {
			String jsonString = "";
			URL apiURL = getAPIUrl(stockName);
			URLConnection conn = apiURL.openConnection();
			Scanner apiCallScanner = new Scanner(apiURL.openStream());
			while(apiCallScanner.hasNext()) {
				jsonString += apiCallScanner.nextLine();
			}
			return jsonString;
		}
					
		public static String[] getAvailableDates(String stockName) throws IOException {
			String jsonString = getJsonString(stockName);
			JSONObject apiCallResult = new JSONObject(jsonString);
			JSONObject timeSeriesDaily = apiCallResult.getJSONObject("Time Series (Daily)");
			JSONArray dates = timeSeriesDaily.names();
			String[] result = new String[dates.length()];
			for(int i = 0;i<dates.length();i++) {
				result[i] = dates.getString(i);
			}
			return result;
		}
		
		public static double[][] getPrices(String stockName) throws IOException {
			String jsonString = getJsonString(stockName);
			JSONObject timeSeriesDaily = new JSONObject(jsonString);
			String[] dates = getAvailableDates(stockName);
			double[][] prices = new double[dates.length][1];
			for(int i = 0;i<prices.length;i++) {
				prices[i][0] = timeSeriesDaily.getJSONObject("Time Series (Daily)").getJSONObject(dates[i]).getDouble("4. close");
			}
			return prices;
		}
		
		public static double getMaxPrice(String stockName) throws IOException {
			double[][] prices = getPrices(stockName);
			double max = prices[0][0];
			for(int i = 0;i<prices.length;i++) {
				if(prices[i][0] > max) {
					max = prices[i][0];
				}
			}
			return max;
		}
}
