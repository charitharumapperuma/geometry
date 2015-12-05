package com.ffx.geometry.triangle.classifier;

import java.util.Scanner;

public class TriangleHandler {
	public static final int MAX_ARRAY_SIZE = 500;
	public static final String[] TRIANGLE_TYPES = {
		"Undefined", 
		"Perpendicular", 
		"Obtuse",
		"Acute", 
		"Equilateral", 
		"Right Angle Isoscele", 
		"Obtuse Isoscele",
		"Acute Angle Isoscele" 
	};
	public static final int[][] INITIAL_TRIANGLES = {
		{ 2,  2,  4},
		{ 3,  4,  5},
		{ 5,  5,  5},
		{ 8,  6, 10},
		{10, 10,  2},
		{ 2,  4,  9},
		{12, 36,  4},
		{ 5,  6,  7},
		{ 7,  9, 12}
	};

	private Scanner scanner;
	private TriangleStatistics stats;
	
	private int[][] triangles; // Holds all triangles
	private int counter = 0; // Number of triangles stored in the array
	private String[] triangleTypes; // Holds type of each triangle. index is same as triangles 2d array
	private int[] triangleStats; // Holds triangle types distribution statistics. index is same as TRIANGLE_TYPES array

	public TriangleHandler() {
		triangles = new int[MAX_ARRAY_SIZE][3];
		scanner = new Scanner(System.in);
		
		stats = new TriangleStatistics();
	}

	public void loadInitialValues() {
		for(int[] t:INITIAL_TRIANGLES) {
			if (t[0] == 0 || t[1] == 0 || t[2] == 0) {
				// do nothing
			} else {
				// No 0s present
				// Sort and add new triangle to the main triangle list
				triangles[counter] = this.sort(t);
				counter++; // increment triangle counter
			}
		}
		System.out.println("Default triangles loaded succesfully...");
	}
	
	public void readInputs() {
		boolean exit = false; // holds whether user had entered exit code or not
		int[] triangle; // holds current triangle user has entered

		// Loop to read user inputs until user enters exit code (0)
		do {
			triangle = new int[3];
			
			System.out.print("Triangle " + (counter + 1) + ": ");
			
			// Checking for 3 user inputs only
			triangle[0] = scanner.nextInt();
			triangle[1] = scanner.nextInt();
			triangle[2] = scanner.nextInt();

			if (triangle[0] == 0 || triangle[1] == 0 || triangle[2] == 0) {
				// user has entered 0 in his input
				exit = true;
			} else {
				// No 0s present in the current input
				// Sort and add new triangle to the main triangle list
				triangles[counter] = this.sort(triangle);
				counter++; // increment triangle counter
			}
		} while (!exit);
		
		scanner.close(); // Close scanner since user inputs are over
	}

	// Sort (Bubble sort)
	public int[] sort(int[] a) {
		int tmp;
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j < 3; j++) {
				if (a[i] > a[j]) {
					tmp = a[i];
					a[i] = a[j];
					a[j] = tmp;
				}
			}
		}
		return a;
	}
	
	public void detectTriangles() {
		// Create triangle type holder and triangle statistics holder
		triangleTypes = new String[counter];
		triangleStats = new int[counter];
		
		// Check type of each triangle
		for(int i = 0; i < counter; i++) {
			triangleTypes[i] = checkTriangle(triangles[i]);
		}
	}
	
	public String checkTriangle(int[] t) {
		int a = t[0];
		int b = t[1];
		int c = t[2];

		int lhs = a * a + b * b;
		int rhs = c * c;

		// check triangle condition (a + b > c)
		if (a + b <= c) {
			// not a triangle
			// "Undefined"
			return TriangleHandler.TRIANGLE_TYPES[0];
		} else {
			// checking a^2 + b^2 with c^2
			if (lhs == rhs) {
				// checking length of sides
				if (a == b || b == c || c == a) {
					// "Right Angle Isoscele"
					return TriangleHandler.TRIANGLE_TYPES[5];
				} else {
					// "Perpendicular"
					return TriangleHandler.TRIANGLE_TYPES[1];
				}
			} else if (lhs < rhs) {
				// checking length of sides
				if (a == b || b == c || c == a) {
					// "Obtuse Isoscele"
					return TriangleHandler.TRIANGLE_TYPES[6];
				} else {
					// "Obtuse"
					return TriangleHandler.TRIANGLE_TYPES[2];
				}
			} else {
				// checking length of sides
				if (a == b && b == c) {
					// "Equilateral"
					return TriangleHandler.TRIANGLE_TYPES[4];
				} else if (a == b || b == c || c == a) {
					// "Acute Angle Isoscele"
					return TriangleHandler.TRIANGLE_TYPES[7];
				} else {
					// "Acute"
					return TriangleHandler.TRIANGLE_TYPES[3];
				}
			}
		}
	}
	
	public void getStatistics() {
		triangleStats = stats.generate(triangleTypes);
	}
	
	public void printTriangles() {
		System.out.println("\n//////////////// Triangles Types ////////////////\n");
		
		for (int i = 0; i < counter; i++) {
			for (int j : triangles[i]) {
				System.out.print(j + " ");
			}
			System.out.println("- " + triangleTypes[i]);
		}
	}
	
	public void printFormattedTriangles() {
		System.out.println();
		System.out.println("-------- Triangles Types --------");
		System.out.println("---------------------------------");
		
		for (int i = 0; i < counter; i++) {
			for (int j : triangles[i]) {
				System.out.printf("%2s  ", j);
			}
			System.out.println("- " + triangleTypes[i]);
		}
	}
	
	private void printStatistics() {
		System.out.println("\n//////////////// Statistics ////////////////");
		System.out.println("Category Number");
		
		for (int i = 0; i< TriangleHandler.TRIANGLE_TYPES.length; i++) {
			System.out.println(TriangleHandler.TRIANGLE_TYPES[i] + " - " + triangleStats[i]);
		}
	}
	
	private void printFormattedStatistics() {
		System.out.println();
		System.out.println("------- Statistics --------");
		System.out.printf("%-20s Number\n", "Category");
		System.out.println("---------------------------");
		
		for (int i = 0; i< TriangleHandler.TRIANGLE_TYPES.length; i++) {
			//System.out.println(TriangleHandler.TRIANGLE_TYPES[i] + " - " + triangleStats[i]);
			System.out.printf("%-20s %6s%n",TriangleHandler.TRIANGLE_TYPES[i], triangleStats[i]);
		}
	}
	
	public static void main(String[] args) {
		TriangleHandler tc = new TriangleHandler();
		
		tc.loadInitialValues();
		tc.readInputs();

		tc.detectTriangles();
		tc.getStatistics();
		
		// tc.printTriangles();
		tc.printFormattedTriangles();
		// tc.printStatistics();
		tc.printFormattedStatistics();
	}
	
}
