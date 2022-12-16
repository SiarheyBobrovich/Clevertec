package by.bobrovich.market;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.api.ProductService;
import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.api.Order;
import by.bobrovich.market.converter.api.OrderConverter;
import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import by.bobrovich.market.converter.ArgsOrderConverter;
import by.bobrovich.market.exceptions.ConvertionException;
import by.bobrovich.market.exceptions.DiscountCardNotFoundException;
import by.bobrovich.market.exceptions.ProductNotFoundException;
import by.bobrovich.market.factory.ReceiptFactory;
import by.bobrovich.market.service.MarketService;

public class MarketApplication {

	public static void main(String[] args) {
		ProductService productService =
				new MarketService(
						getOrderFactory(),
						getProductDao(),
						getDiscountCardDao(),
						getBillFactory()
				);

		Receipt receipt;

		try {
			receipt = productService.getBill(args);
		}catch (ConvertionException | DiscountCardNotFoundException | ProductNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}

		receipt.print(System.out);
	}

	public static ProductDao getProductDao() {
		return new InMemoryProductDao();
	}
	public static DiscountCardDao getDiscountCardDao() {
		return new InMemoryDiscountCardDao();
	}
	public static OrderConverter<String[], Order> getOrderFactory() {
		return new ArgsOrderConverter();
	}
	public static ReceiptFactory getBillFactory() {
		return new ReceiptFactory();
	}
}