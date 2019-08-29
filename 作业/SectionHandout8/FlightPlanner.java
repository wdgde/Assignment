import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import acm.program.ConsoleProgram;

public class FlightPlanner extends ConsoleProgram {
	// 定义并初始化一个HashMap，String为起点城市，ArrayList为可达终点城市数组
	private HashMap<String, ArrayList> cityMap = new HashMap<String, ArrayList>();
	// 第一个起点城市
	private String firstCity;
	// 某一个结束城市
	private String endCity;
	// 保存路径的一个数组
	private ArrayList<String> routeCityList = new ArrayList<String>();
	// 是否第一个输入
	private boolean isFirstInput = true;

	public void run() {
		try {
			// 读文件
			readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		println("Welcome to Flight Planner!");
		println("Here's a list of the cities in our database:");
		// 将所有可以作为出发点的城市列出
		printAllCity();
		// 选择城市
		chooseCity();
		// 输出路径
		printRoute();
	}

	private void readFile() throws IOException {
		String str;
		BufferedReader reader = new BufferedReader(new FileReader("flights.txt"));
		while ((str = reader.readLine()) != null) {
			String startCity = str.substring(0, str.indexOf("-") - 1);
			String endCity = str.substring(str.indexOf(">") + 2);
			saveCity(startCity, endCity);
		}
	}

	// 将文件中的城市信息保存
	private void saveCity(String startCity, String endCity) {
		if (cityMap.get(startCity) != null) {
			// 添加非第一个城市信息
			ArrayList cityList = cityMap.get(startCity);
			cityList.add(endCity);
			cityMap.put(startCity, cityList);
		} else {
			// 添加第一个城市信息
			ArrayList<String> newCityList = new ArrayList<String>();
			newCityList.add(endCity);
			cityMap.put(startCity, newCityList);
		}
	}

	// 输出城市
	private void printAllCity() {
		Iterator<String> hashmapIter = cityMap.keySet().iterator();
		while (hashmapIter.hasNext()) {
			String keyCity = hashmapIter.next();
			println(keyCity);
		}
		println("Let's plan a round-trip route!");
	}

	private void chooseCity() {
		while (true) {
			// 保存输入的城市
			String startCity = "";
			// 第一次输入
			if (firstCity == null) {
				startCity = readLine("Enter the starting city: ");
				if (startCity.equals("")) {
					continue;
				}
				if (cityMap.get(startCity) == null) {
					println("There isn't that starting city.");
					continue;
				}
				firstCity = startCity;
			} else {
				// 非第一次输入
				startCity = readLine("Where do you want to go from " + endCity + " ? ");
				isFirstInput = false;
			}
			// 如果结束城市等于出发城市且不是第一次输入
			if (firstCity.equals(startCity) && !isFirstInput) {
				break;
			}
			// 输入的城市在文件中不存在
			if (cityMap.get(startCity) == null) {
				println("You can't get to that city by a direct flight.");
				continue;
			}
			endCity = startCity;
			routeCityList.add(startCity);
			// 输出startCity可以到达的城市
			println("From " + startCity + " you can fly directly to:");
			for (int i = 0; i < cityMap.get(startCity).size(); i++) {
				println("-----" + cityMap.get(startCity).get(i));
			}
		}
	}

	// 输出路径
	private void printRoute() {
		println("The route you've chosen is:");
		for (int j = 0; j < routeCityList.size(); j++) {
			print(routeCityList.get(j));
			if (j != routeCityList.size() - 1) {
				print(" -> ");
			}
		}
	}

}
