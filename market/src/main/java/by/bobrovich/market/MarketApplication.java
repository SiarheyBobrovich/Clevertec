package by.bobrovich.market;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.api.ProductService;
import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.api.Order;
import by.bobrovich.market.converter.api.OrderConverter;
import by.bobrovich.market.dao.FileDiscountCardDao;
import by.bobrovich.market.dao.FileProductDao;
import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import by.bobrovich.market.converter.ArgsOrderConverter;
import by.bobrovich.market.exceptions.ConvertionException;
import by.bobrovich.market.exceptions.DiscountCardNotFoundException;
import by.bobrovich.market.exceptions.ProductNotFoundException;
import by.bobrovich.market.factory.ReceiptFactory;
import by.bobrovich.market.service.MarketService;
import by.bobrovich.market.exceptions.ServiceNotAvailableNow;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class MarketApplication {

	public static void main(String[] args) {
		ProductService productService;
		Receipt receipt;

		if (args.length >= 2) {
			Map.Entry<String, String> products = splitArg(args[0]);
			Map.Entry<String, String> cards = splitArg(args[1]);

			if ("products".equals(products.getKey()) && "cards".equals(cards.getKey())) {
				productService = initFromFiles(products.getValue(), cards.getValue());
				args = Arrays.copyOfRange(args, 2, args.length);
			}else {
				productService = initFromMemory();
			}
		}else {
			productService = initFromMemory();
		}

		try {
			receipt = productService.getReceipt(args);
		}catch (ConvertionException | DiscountCardNotFoundException |
				ProductNotFoundException | ServiceNotAvailableNow e) {
			System.out.println(e.getMessage());
			return;
		}

		try {
			new UserTalker(new Scanner(System.in)).talkAboutReceipt(receipt);
		} catch (IOException e) {
			System.out.println("Something went wrong.");
		}
	}

	private static Map.Entry<String, String> splitArg(String arg) {
		String[] strings = arg.split("-");
		return Map.entry(strings[0], strings[1]);
	}
	private static ProductService initFromMemory() {
		return new MarketService(
				getOrderFactory(),
				getProductDao(null),
				getDiscountCardDao(null),
				getReceiptFactory()
		);
	}
	private static ProductService initFromFiles(String productsPath, String cardsPath) {
		return new MarketService(
			getOrderFactory(),
				getProductDao(productsPath),
				getDiscountCardDao(cardsPath),
				getReceiptFactory()
		);
	}

	/**
	 * If you use postgresql, you will uncomment block
	 * Returns Product card db
	 * @param path file path [please see the resources/in_memory_products.csv]
	 * @return product db
	 */
	public static ProductDao getProductDao(String path) {
		if (path == null) {
//			return new JdbcProductDao();
			return new InMemoryProductDao();
		}else {
			try {
				return new FileProductDao(path);
			}catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		return null;
	}

	/**
	 * If you use postgresql, you will uncomment block
	 * Returns Discount card db
	 * @param path file path [please see the resources/in_memory_discount_cards.csv]
	 * @return discount card db
	 */
	public static DiscountCardDao getDiscountCardDao(String path) {
		if (path == null) {
//			return new JdbcDiscountCardDao();
			return new InMemoryDiscountCardDao();
		}else {
			try {
				return new FileDiscountCardDao(path);
			}catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		return null;
	}
	public static OrderConverter<String[], Order> getOrderFactory() {
		return new ArgsOrderConverter();
	}
	public static ReceiptFactory getReceiptFactory() {
		return new ReceiptFactory();
	}
}