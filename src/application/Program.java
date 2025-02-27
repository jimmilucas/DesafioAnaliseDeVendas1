package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Sale> sales = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				sales.add(new Sale(Integer.parseInt(fields[0]),
									Integer.parseInt(fields[1]),
									fields[2],
									Integer.parseInt(fields[3]),
									Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			
			List<Sale> topSales = sales.stream()
								.filter(sl -> sl.getYear() == 2016)
								.sorted((sl1, sl2) -> sl2.averagePrice().compareTo(sl1.averagePrice()))
								.limit(5)
								.collect(Collectors.toList());
								
			topSales.forEach(System.out::println);
			
			double sumSalesLogan = sales.stream()
								.filter(sl -> sl.getMonth() == 1 || sl.getMonth() == 7 )
								.filter(sl -> sl.getSeller().equals("Logan"))
								.map(sl -> sl.getTotal())
								.reduce(0.0, (x,y) -> x + y);
			
			System.out.println();
			System.out.print("Valor vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", sumSalesLogan));
			
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		sc.close();
	}

}
