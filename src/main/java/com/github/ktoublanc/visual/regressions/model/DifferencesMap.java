package com.github.ktoublanc.visual.regressions.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ktoublanc on 11/11/2016.
 */
public class DifferencesMap {

	private final double[][] differences;
	private final int height;
	private final int width;
	private long differenceCount;

	public DifferencesMap(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.differences = new double[this.width][this.height];
	}


	public void addDifference(final int x, final int y, final double difference) {
		if (difference > 0) {
			differences[x][y] = difference;
			differenceCount++;
		}
	}

	public Double getDifferencePercentage() {
		return (differenceCount / (double) (width * height)) * 100.0;
	}

	public Stream<Difference> differenceStream() {
		return IntStream.range(0, width)
				.mapToObj(x -> IntStream.range(0, height)
						.filter(y -> this.differences[x][y] > 0)
						.mapToObj(y -> new Difference(x, y, this.differences[x][y]))
						.collect(Collectors.toList()))
				.flatMap(List::stream);
	}

	public class Difference {
		private final int x;
		private final int y;
		private final double percentage;

		public Difference(final int x, final int y, final double percentage) {
			this.x = x;
			this.y = y;
			this.percentage = percentage;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public double getPercentage() {
			return percentage;
		}
	}
}
