package com.ffx.geometry.triangle.classifier;

public class TriangleStatistics {

	public int[] generate(String[] triangleTypes) {
		int[] stats = new int[TriangleHandler.TRIANGLE_TYPES.length];

		for (String t : triangleTypes) {
			for (int i = 0; i < TriangleHandler.TRIANGLE_TYPES.length; i++) {
				if (t.equals(TriangleHandler.TRIANGLE_TYPES[i]))
					stats[i]++;
			}
		}

		return stats;
	}

}
