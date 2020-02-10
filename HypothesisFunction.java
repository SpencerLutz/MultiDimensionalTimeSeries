import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.ejml.simple.SimpleMatrix;

public class HypothesisFunction {
	
	private static double[][] designMatrix;
	private static double[] predictedData;
	private static double[][] outputMatrix;
	private static double[][] inputMatrix;
	
	private static SimpleMatrix parameterVector;
	private static int numberOfParameters;
	
	public HypothesisFunction(double[][] outputMatrix,int numberOfParameters) {
		this.outputMatrix = outputMatrix;
		this.numberOfParameters = numberOfParameters;
	}
	
	private static void setTimeSeries() {
		inputMatrix = new double[outputMatrix.length][1];
		for(int i = 0;i<outputMatrix.length;i++) {
			inputMatrix[i][0] = i+1;
		}
	}
	
	private static void setDesignMatrix() {
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
	
	private static void setParameterVector() throws IOException {
		HypothesisFunction.setTimeSeries();
		HypothesisFunction.setDesignMatrix();
		SimpleMatrix x = new SimpleMatrix(designMatrix);
		SimpleMatrix y = new SimpleMatrix(outputMatrix);
		parameterVector = x.transpose().mult(x).pseudoInverse().mult(x.transpose()).mult(y);
	}
	
	public static void setPredictedData() throws IOException {
		HypothesisFunction.setParameterVector();
		predictedData = new double[inputMatrix.length];
		for(int i = 0;i<predictedData.length;i++) {
			double currentSum = parameterVector.get(0);
			for(int k = 1;k<numberOfParameters;k++) {
				currentSum += parameterVector.get(k) * Math.cos(k * inputMatrix[i][0]);
			}
			predictedData[i] = currentSum;
		}
	}
	
	public static double[] getPredictedData() {
		return HypothesisFunction.predictedData;
	}
	
	public static double[][] getInputMatrix() {
		return HypothesisFunction.inputMatrix;
	}
	
	public static SimpleMatrix getParameterVector() {
		return HypothesisFunction.parameterVector;
	}
	
	public static double[][] getOutputMatrix() {
		return HypothesisFunction.outputMatrix;
	}
	
	public static double[][] getDesignMatrix() {
		return HypothesisFunction.designMatrix;
	}
	
	public static int getNumberOfCosines() {
		return HypothesisFunction.numberOfParameters;
	}
	
	private static void addOneToBeginning() {
		double[][] result = new double[designMatrix.length][designMatrix[0].length+1];
		for(int i = 0;i<result.length;i++) {
			for(int j = 1;j<result[i].length;j++) {
				result[i][j] = designMatrix[i][j-1];
			}
			result[i][0] = 1;
		}
		designMatrix = result;
	}
	
}
