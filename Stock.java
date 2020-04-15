

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONException;

public class Stock {
	
	public static int params = 10;
	
	private static double[][] getLastPricesFor(String stockName) throws IOException {
		return StockReader.getPrices(stockName);
	}
	
	public static double[] getNextPrices(int n, String stockName) throws IOException {
		double[][] lastPrices = getLastPricesFor(stockName);
		double[] result = new double[n];
		HypothesisFunction StockFunction = new HypothesisFunction(lastPrices,params);
		StockFunction.setParameterVector();
		StockFunction.setAlternateTimeSeries(StockFunction.inputMatrix.length + n);
		for(int i = 100;i<100+n;i++) {
			result[i-100] = StockFunction.HoX(i);
		}
		return result;
	}
	public static double getMaxPrice(int n,String stockName) throws IOException {
		double[] prices = getNextPrices(n,stockName);
		double max = prices[0];
		for(int i = 0;i<prices.length;i++) {
			if(prices[i] > max) {
				max = prices[i];
			}
		}
		return max;
	}
	
	public static void main(String[] args) throws IOException {
		double[] AAPL_PRICES = Stock.getNextPrices(100,"AAPL");
		for(int i = 0;i<AAPL_PRICES.length;i++) {
			System.out.println(AAPL_PRICES[i]);
		}
	}
}
 
