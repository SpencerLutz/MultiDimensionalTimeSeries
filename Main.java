import java.awt.Toolkit;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application  {
	
	final int W = Toolkit.getDefaultToolkit().getScreenSize().width;
	final int H = Toolkit.getDefaultToolkit().getScreenSize().height;
	final Color BACKGROUND_COLOR = Color.rgb(255,164,27);
	final Color SIDE_COLOR = Color.rgb(0,80,130);
	final Color TEXT_COLOR = Color.rgb(0,8,57);
	final Color BUTTON_COLOR = Color.rgb(0,168,204);
	final Color TRANSPARENT_COLOR = Color.rgb(0,8,57,0.15);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Group homeGroup = new Group();
		Group epg = new Group();
		
		Scene homeScene = new Scene(homeGroup);
		Scene epScene = new Scene(epg);
		
		epScene.setFill(BACKGROUND_COLOR);
		
		Image home = new Image("home.png");
		ImageView homeView = new ImageView(home);
		Button homeButton = new Button("Home",homeView);
		homeButton.setLayoutX(W*0.01);
		homeButton.setLayoutY(H*0.01);
		homeButton.setPrefWidth(150);
		homeButton.setFont(Font.font("Georgia",FontWeight.BOLD,15));
		homeButton.setStyle("-fx-background-color: rgb(0,168,204);");
		homeButton.setTextFill(TEXT_COLOR);
		
		homeButton.setOnAction(e-> {
			primaryStage.setScene(homeScene);
			primaryStage.setFullScreen(true);
		});
		homeButton.setOnMouseEntered(e-> {
			homeButton.setStyle("-fx-background-color: rgb(0,140,204);");
		});
		
		homeButton.setOnMouseExited(e-> {
			homeButton.setStyle("-fx-background-color: rgb(0,168,204);");
		});
		
		Label none = new Label("Your portfolio is empty");
		none.setLayoutX(W*0.17);
		none.setLayoutY(H*0.25);
		none.setFont(Font.font("Georgia",60));
		none.setTextFill(TRANSPARENT_COLOR);;
		
		epg.getChildren().addAll(homeButton,none);
		
		homeScene.setFill(BACKGROUND_COLOR);
		Rectangle menuRectangle = new Rectangle(0,0,W*0.22,H);
		menuRectangle.setFill(SIDE_COLOR);
		
		Image logo = new Image("logo.png");
		ImageView logoImageView = new ImageView(logo);
		logoImageView.setLayoutX(W*0.01);
		logoImageView.setLayoutY(H*0.001);
		
		Button epb = new Button("Edit Portfolio");
		epb.setLayoutX(menuRectangle.getWidth()/4);
		epb.setLayoutY(H*0.3);
		epb.setPrefWidth(menuRectangle.getWidth()/2);
		epb.setPrefHeight(H*0.05);
		epb.setStyle("-fx-background-color: rgb(0,168,204);");
		epb.setFont(Font.font("Georgia",25));
		epb.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		epb.setOnAction(e-> {
			primaryStage.setScene(epScene);
			primaryStage.setFullScreen(true);
		});
		epb.setOnMouseEntered(e-> {
			epb.setStyle("-fx-background-color: rgb(0,140,204);");
		});
		
		epb.setOnMouseExited(e-> {
			epb.setStyle("-fx-background-color: rgb(0,168,204);");
		});
		
		Button help = new Button("Help");
		help.setLayoutX(menuRectangle.getWidth()/4);
		help.setLayoutY(H*0.4);
		help.setPrefWidth(menuRectangle.getWidth()/2);
		help.setPrefHeight(H*0.05);
		help.setStyle("-fx-background-color: rgb(0,168,204);");
		help.setFont(Font.font("Georgia",25));
		help.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		help.setOnMouseEntered(e-> {
			help.setStyle("-fx-background-color: rgb(0,140,204);");
		});
		
		help.setOnMouseExited(e-> {
			help.setStyle("-fx-background-color: rgb(0,168,204);");
		});
		
		Button info = new Button("Info");
		info.setLayoutX(menuRectangle.getWidth()/4);
		info.setLayoutY(H*0.5);
		info.setPrefWidth(menuRectangle.getWidth()/2);
		info.setPrefHeight(H*0.05);
		info.setStyle("-fx-background-color: rgb(0,168,204);");
		info.setFont(Font.font("Georgia",25));
		info.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		info.setOnMouseEntered(e-> {
			info.setStyle("-fx-background-color: rgb(0,140,204);");
		});
		
		info.setOnMouseExited(e-> {
			info.setStyle("-fx-background-color: rgb(0,168,204);");
		});
		
		
		/*NumberAxis xAxis = new NumberAxis(0,100,25);
		xAxis.setLabel("Day");
		NumberAxis yAxis = new NumberAxis(0,500,100);
		yAxis.setLabel("Price");
		 final ScatterChart<Number,Number> sc = new
		            ScatterChart<Number,Number>(xAxis,yAxis);
		 sc.setTitle("AAPL");
		 
		 XYChart.Series series1 = new XYChart.Series();
		 double[][] prices = StockReader.getPrices("AAPL");
		 for(int i = 0;i<prices.length;i++) {
			 series1.getData().add(new XYChart.Data(i+1, prices[i][0]));
		 }
		 sc.getData().add(series1);
		 sc.setLayoutX(W*0.29);
		 sc.setLayoutY(H*0.1);
		 */

		homeGroup.getChildren().addAll(menuRectangle,logoImageView,epb,help,info);
		
		primaryStage.setScene(homeScene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
