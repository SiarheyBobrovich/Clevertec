package by.bobrovich.market;

import by.bobrovich.market.api.ProductService;
import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.builder.ApplicationBuilder;
import by.bobrovich.market.exceptions.ConvertionException;
import by.bobrovich.market.exceptions.DiscountCardNotFoundException;
import by.bobrovich.market.exceptions.ProductNotFoundException;
import by.bobrovich.market.exceptions.ServiceNotAvailableNow;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MarketApplication {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Args must be not empty");
		}

		final Receipt receipt;
		final ProductService productService;

		final int argsLength = args.length;
		final boolean isProductsFilename = argsLength > 0 && args[0].startsWith("products");
		final boolean isCardsFilename = argsLength > 1 && args[1].startsWith("cards");
		final ApplicationBuilder applicationBuilder = new ApplicationBuilder();

		if ((isProductsFilename && !isCardsFilename) ||
			(!isProductsFilename && isCardsFilename)) {
			System.out.println("Products and cards files must be set");
			return;
		}

		if (isProductsFilename) {
			try {
				applicationBuilder.productDao(args[0])
						.discountCardDao(args[1]);
			}catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				return;
			}
		}

		productService = applicationBuilder.build();

		final String[] productsArgs = isProductsFilename ?
				Arrays.copyOfRange(args, 2, argsLength) :
				args;

		try {
			receipt = productService.getReceipt(productsArgs);
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
}