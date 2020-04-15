import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StockPortfolio {

	public static ArrayList<String> symbols = new ArrayList<String>();
	
	public static void addStock(String symbol) {
		symbols.add(symbol);
	}
	
	public static void removeStock(String symbol) {
		for(int i = 0;i<symbols.size();i++) {
			if(symbols.get(i).equals(symbol)) {
				symbols.remove(symbol);
			}
		}
	}
	
	public static void save() {
		
		File symbolsFile = new File("bin/symbols.txt");
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(symbolsFile); 
            bw = new BufferedWriter(fw); 

            for (String symbol: symbols) {
                bw.write(symbol);        
                bw.newLine();          
            }
 
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
