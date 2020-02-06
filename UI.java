import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UI extends Application {
	
	@Override public void start(Stage stage) throws IOException, IllegalArgumentException {
			
		stage.setTitle("Apple Stock Price Prediction");
		
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Price");
        
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Apple stock predictions");
        lineChart.setPrefWidth(800);
        lineChart.setPrefHeight(600);
        lineChart.setLayoutX(400);
        lineChart.setLayoutY(0);
	
        XYChart.Series actualSeries = new XYChart.Series();
        XYChart.Series predictedSeries = new XYChart.Series();
        actualSeries.setName("Actual");
        predictedSeries.setName("Predicted");
        
        Group root = new Group();
        Scene scene = new Scene(root,1600,1200);
        scene.setFill(Color.HONEYDEW);
        stage.setScene(scene);
        
        Button setNumberOfParameters = new Button();
        setNumberOfParameters.setText("Set # of Params");
        setNumberOfParameters.setFont(new Font(25));
        setNumberOfParameters.setPrefWidth(250);
        setNumberOfParameters.setPrefHeight(50);
        setNumberOfParameters.setLayoutX(50);
        setNumberOfParameters.setLayoutY(50);    
        
        TextField enp = new TextField("");
        enp.setPrefWidth(250);
        enp.setPrefHeight(50);
        enp.setLayoutX(50);
        enp.setLayoutY(100);
        enp.setFont(new Font(25)); 
       
          setNumberOfParameters.setOnAction(e-> {
        	  
        	  
        	try {
                
        		actualSeries.getData().clear();
          	  	predictedSeries.getData().clear();
        		HypothesisFunction Apple = new HypothesisFunction("C:/Users/student/Desktop/data.xlsx",Integer.parseInt(enp.getText()));
				Apple.getDataFromExcelFile();
				Apple.setParameterVector();
		        Apple.setPredictedData();
		        double avgError = Apple.getAverageError();
		        
		        for(int i = 0;i<Apple.inputMatrix.length;i++) {
		        	actualSeries.getData().add(new XYChart.Data(Apple.inputMatrix[i][0],Apple.outputMatrix[i][0]));
		        	predictedSeries.getData().add(new XYChart.Data(Apple.inputMatrix[i][0],Apple.predictedData[i]));
		        }
			} catch (IOException e1) {
				System.out.print("");
			} catch(IllegalArgumentException e1) {
				System.out.print("");
			}
        	
        	   lineChart.getData().add(actualSeries);
               lineChart.getData().add(predictedSeries);
               root.getChildren().add(lineChart);
        });
       
        root.getChildren().addAll(setNumberOfParameters,enp);
 
        stage.show();
    }
	public static void main(String[] args) throws IOException, IllegalArgumentException {
		launch(args); 
	}
}
