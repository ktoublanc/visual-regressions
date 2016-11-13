package com.github.ktoublanc.visual.regressions.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Difference Map class.
 * Created by ktoublanc on 11/11/2016.
 */
public class DifferencesMap {

	/**
	 * The difference percent table
	 */
	private final double[][] differences;

	/**
	 * The difference map height
	 */
	private final int height;

	/**
	 * The difference map width
	 */
	private final int width;

	/**
	 * The difference count
	 */
	private long differenceCount;

	/**
	 * Build {@link DifferencesMap} instance
	 *
	 * @param width  the image width
	 * @param height the image height
	 */
	public DifferencesMap(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.differences = new double[this.width][this.height];
	}

	/**
	 * Add difference to map.
	 * <p>
	 * If difference <= 0 nothing is added to map.
	 * <p>
	 *
	 * @param x          the x position
	 * @param y          the y position
	 * @param difference the difference percent
	 * @throws ArrayIndexOutOfBoundsException when #x parameter is greater to {@link #width} or less than zero
	 * @throws ArrayIndexOutOfBoundsException when #y parameter is greater to {@link #height} or less than zero
	 */
	public void addDifference(final int x, final int y, final double difference) {
		if (difference > 0) {
			differences[x][y] = difference;
			differenceCount++;
		}
	}

	/**
	 * Get the difference percentage
	 *
	 * @return an initialized double value
	 */
	public Double getDifferencePercentage() {
		double averagePercent = differenceStream().mapToDouble(d -> d.percentage).average().orElse(0d);
		double differencePercent = differenceCount / (double) (width * height);
		return differencePercent * averagePercent;
	}

	/**
	 * Get differences as {@link Stream}
	 *
	 * @return an initialized stream of {@link Difference}
	 */
	public Stream<Difference> differenceStream() {
		return IntStream.range(0, width)
				.mapToObj(x -> IntStream.range(0, height)
						.filter(y -> hasDifference(x, y))
						.mapToObj(y -> new Difference(x, y, this.differences[x][y]))
						.collect(Collectors.toList()))
				.flatMap(List::stream);
	}

	/**
	 * is there a difference in map ?
	 *
	 * @param x The x position
	 * @param y The y position
	 * @return true if difference is greater than 0
	 */
	private boolean hasDifference(final int x, final int y) {
		return this.differences[x][y] > 0;
	}

	/**
	 * Inner POJO class for differences
	 */
	public class Difference {

		/**
		 * The x position
		 */
		private final int x;

		/**
		 * The y position
		 */
		private final int y;

		/**
		 * The percentage of difference
		 */
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
