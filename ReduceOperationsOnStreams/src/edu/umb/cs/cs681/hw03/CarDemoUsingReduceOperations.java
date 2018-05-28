package edu.umb.cs.cs681.hw03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarDemoUsingReduceOperations {

	public static void main(String[] args) {

		List<Car> carsList = new ArrayList<>();
		carsList.add(new Car(25000, 2016, 100.56f, "BMW"));
		carsList.add(new Car(15000, 2015, 55.89f, "Toyota"));
		carsList.add(new Car(10000, 2014, 85.45f, "Mazda6"));

		carsList.forEach(car -> System.out.println("Price: " + car.getPrice() + " , Year: " + car.getYear() + " , Mileage: "
				+ car.getMileage() + " , MakerName: " + car.getMakerName()));

		/**
		 * reduce() to find the min() price among the given cars list.
		 */
		Integer minPrice = carsList.stream().map((Car car) -> car.getPrice()).reduce(0, (result, carPrice) -> {
			if (result == 0)
				return carPrice;
			else if (carPrice < result)
				return carPrice;
			else
				return result;
		});

		System.out.println("The min. price of car using reduce() is : $ " + minPrice);

		/**
		 * reduce() to find the max() price among the given cars list.
		 */
		Integer maxPrice = carsList.stream().map((Car car) -> car.getPrice()).reduce(0, (result, carPrice) -> {
			if (result == 0)
				return carPrice;
			else if (carPrice > result)
				return carPrice;
			else
				return result;
		});

		System.out.println("The max. price of car using reduce() is : $ " + maxPrice);

		/**
		 * reduce() to find the count() of cars from the given list.
		 */
		Integer carCount = carsList.stream().map((Car car) -> car.getMakerName()).reduce(0,
				(result, carMaker) -> ++result, (result, intermediateResult) -> result);

		System.out.println("The count() of cars using reduce() is : " + carCount);

		/**
		 * reduce() to find the average() of all the prices of the given car
		 * list.
		 */
		int average = carsList.stream().map((Car car) -> car.getPrice()).reduce(new int[3], (arr, price) -> {
			for (int i = 0; i < arr.length; i++) {
				arr[i] += price / arr.length;
			}
			return arr;
		} , (arr, intermediateArr) -> {
			return arr;
		})[2];

		System.out.println("The average() of car prices using reduce() is : " + average);

	}

}
