import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

public class Stock {
	public static String stockName;
	public static double[][] actualPrices;
	public static double[] predictedPrices;
	
	public static final int LAST_N = 150;
	public static final int NUMBER_OF_PARAMETERS = 300;
	
	public Stock(String stockName) {
		this.stockName = stockName;
	}
	
	private static void setPrices() throws JSONException, IOException {
		StockReader stockReader = new StockReader(Stock.stockName);
		stockReader.setPricesForLast(LAST_N);
		ArrayList<Double> prices = stockReader.getPrices();
		Stock.actualPrices = new double[prices.size()][1];
		for(int i = 0;i<stockReader.getPrices().size();i++) {
			Stock.actualPrices[i][0] = prices.get(i);
		}
	}
	
	public static void setPredictedPrices() throws IOException, JSONException {
		Stock.setPrices();
		HypothesisFunction currentTimeSeries = new HypothesisFunction(Stock.actualPrices,NUMBER_OF_PARAMETERS);
		currentTimeSeries.setPredictedData();
		Stock.predictedPrices = currentTimeSeries.getPredictedData();
	}
	
	public static double getAverageError() {
		double sum = 0;
		for(int i = 0;i<predictedPrices.length;i++) {
			sum+= Math.abs(Stock.actualPrices[i][0] - Stock.predictedPrices[i]);
		}
		return sum/predictedPrices.length;
	}

	public static String getStockName() {
		return Stock.stockName;
	}
	
	public static double[][] getActualPrices() {
		return Stock.actualPrices;
	}
	
	public static double[] getPredictedPrices() {
		return Stock.predictedPrices;
	}
	
	public static void main(String[] args) throws JSONException, IOException  {
		Stock Apple = new Stock("AAPL");
		Apple.setPredictedPrices();
		System.out.print(Apple.getAverageError());
	}
}
 