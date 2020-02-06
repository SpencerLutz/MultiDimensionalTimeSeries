import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ejml.simple.SimpleMatrix;

// The purpose of this is just for the evaluation of historic stock data
public class HypothesisFunction {
	
	public static int numberOfParameters;
	public static double[][] inputMatrix;
	public static double[][] outputMatrix;
	public static double[][] designMatrix;
	public static SimpleMatrix convertedDesignMatrix;
	public static SimpleMatrix convertedOuputMatrix;
	public static SimpleMatrix parameterVector;
	public static String excelFile;
	public static double[] predictedData;
	
	public HypothesisFunction(String excelFile, int numberOfParameters) {
		this.excelFile = excelFile;
		this.numberOfParameters = numberOfParameters;;
	}
	
	public static void getDataFromExcelFile() throws IOException {
		FileInputStream inputStream = new FileInputStream(excelFile);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		
		inputMatrix = new double[252][1];
		outputMatrix = new double[252][1];

		int i = 0;
		while(iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			while(cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				double y = cell.getNumericCellValue();
				outputMatrix[i][0] = y; 
				inputMatrix[i][0] = i+1;
			}
			
			// Cell cell = cellIterator.next();
			//double x = cell.getNumericCellValue();
			//Cell cell = cellIterator.next();
			//double y =cell.getNumericCellValue();
			//inputMatrix[i][0] = x
			//outputMatrix[i][0] = y
			i++;
		}
		workbook.close();
		inputStream.close();
	}

	
	public static void setParameterVector() throws IOException {
		getDataFromExcelFile();
		HypothesisFunction.setDesignMatrix();
		convertedDesignMatrix = new SimpleMatrix(designMatrix);
		convertedOuputMatrix = new SimpleMatrix(outputMatrix);
		SimpleMatrix x = convertedDesignMatrix;
		SimpleMatrix y = convertedOuputMatrix;
		parameterVector = x.transpose().mult(x).pseudoInverse().mult(x.transpose()).mult(y);
	}

	public static void displayParameters() {
		for(int i = 0;i<parameterVector.numRows();i++) {
			for(int j = 0;j<parameterVector.numCols();j++) {
				System.out.println(parameterVector.get(i,j));
			}
		}
	}
	public static void addOneToBeginning() {
		double[][] result = new double[designMatrix.length][designMatrix[0].length+1];
		for(int i = 0;i<result.length;i++) {
			for(int j = 1;j<result[i].length;j++) {
				result[i][j] = designMatrix[i][j-1];
			}
			result[i][0] = 1;
		}
		designMatrix = result;
	}
	
	
	public static void setDesignMatrix() {
		int numberOfRows = inputMatrix.length;
		int numberOfColumns = (numberOfParameters * inputMatrix[0].length);
		designMatrix = new double[numberOfRows][numberOfColumns];
		double[] elements = new double[numberOfRows * numberOfColumns];
		int curIndex = 0;
		
		for(int i = 0;i<inputMatrix.length;i++) {
			double[] curVec = inputMatrix[i];
			for(int j = 0;j<curVec.length;j++) {
				for(int k = 1;k<=numberOfParameters;k++) {
					elements[curIndex] = Math.cos(curVec[j] * k);
					curIndex++;
					}
				}
			}
		
			int assigningIndex = 0;
			for(int i = 0;i<designMatrix.length;i++) {
				for(int j = 0;j<designMatrix[i].length;j++) {
					designMatrix[i][j] = elements[assigningIndex];
					assigningIndex++;
				}
			}
			HypothesisFunction.addOneToBeginning();
		}
	
	public static void displayDesignMatrix() {
		for(int i = 0;i<designMatrix.length;i++) {
			HypothesisFunction.displayElementsOfVector(designMatrix[i]);
			System.out.println();
		}
	}
	
	public static void displayElementsOfVector(double[] vec) {
		for(int i = 0;i<vec.length;i++) {
			System.out.print(vec[i] +  " ");
		}
	}
	
	public static void displayMatrix(double[][] mat) {
		for(int i = 0;i<mat.length;i++) {
			displayElementsOfVector(mat[i]);
			System.out.println();
		}
	}
	
	public static void setPredictedData() {
		predictedData = new double[252];
		for(int i = 0;i<predictedData.length;i++) {
			double currentSum = parameterVector.get(0);
			for(int k = 1;k<numberOfParameters;k++) {
				currentSum += parameterVector.get(k) * Math.cos(k * inputMatrix[i][0]);
			}
			predictedData[i] = currentSum;
		}
	}
	
	public static double getAverageError() {
		setPredictedData();
		double sum = 0;
		for(int i = 0;i<predictedData.length;i++) {
			sum += (Math.abs(predictedData[i]-outputMatrix[i][0]));
		}
		return sum/252;
	}
	
	public static void main(String[] args) throws IOException {
		HypothesisFunction Apple = new HypothesisFunction("C:/Users/student/Desktop/data.xlsx",50);
		Apple.setParameterVector();
		for(int i = 0;i<Apple.parameterVector.numRows();i++) {
			for(int j =0;j<Apple.parameterVector.numCols();j++) {
				System.out.print(Apple.parameterVector.get(i,j) + "   ");
			}
		}
	}
}