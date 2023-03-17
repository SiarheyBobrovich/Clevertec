package by.bobrovich.market.service;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.api.Order;
import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.dao.api.ProductDao;
import by.bobrovich.market.exceptions.PdfServiceException;
import by.bobrovich.market.factory.ReceiptFactory;
import by.bobrovich.market.service.api.PdfService;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfContentByte;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author Babrovich Siarhey on 17.03.2023
 */
@Service
@Log4j2
public class PdfServiceImpl extends ReceiptServiceImpl implements PdfService {

    @Autowired
    public PdfServiceImpl(ProductDao productDao, DiscountCardDao discountCardDao, ReceiptFactory receiptFactory) {
        super(productDao, discountCardDao, receiptFactory);
    }

    @Override
    public Path getPath(Order order) {
        final Receipt receipt = super.getReceipt(order);
        final Document document = new Document(PageSize.A4);
        final PdfDiv emptyDiv = new PdfDiv();
        final PdfPTable titleTable = getTitleTable(receipt);
        final PdfPTable bodyTable = getBodyTable(receipt);
        final PdfPTable totalTable = getTotalTable(receipt);
        final Path receipt1 = getPathToTempFile();
        final PdfWriter writer = getPdfWriter(document, receipt1.toFile());
        final PdfReader pdfReader = getPdfReader();
        final PdfImportedPage importedPage = writer.getImportedPage(pdfReader, 1);
        document.open();

        final PdfContentByte cb = writer.getDirectContent();
        document.newPage();
        cb.addTemplate(importedPage, 0, 0);
        emptyDiv.setHeight(220f);
        documentAddAll(document, emptyDiv, titleTable, bodyTable, totalTable);

        document.close();
        return receipt1;
    }

    private Path getPathToTempFile() {
        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        try {
            URL resource = getClass().getResource("/temp");
            if (resource == null) {
                throw new PdfServiceException("/resources/temp does not exists");
            }
            return Files.createTempFile(Paths.get(resource.getPath()), "receipt:" + now + ":", ".pdf");
        } catch (IOException e) {
            throw new PdfServiceException(e.getMessage());
        }
    }

    private void documentAddAll(Document document, Element... elements) {
            Arrays.stream(elements).forEachOrdered(e -> {
                try {
                    document.add(e);
                } catch (DocumentException exception) {
                    log.warn(exception);
                    throw new PdfServiceException(exception.getMessage());
                }
            });
    }

    private PdfPTable getTotalTable(Receipt receipt) {
        PdfPTable totalTable = new PdfPTable(2);
        AtomicInteger i = new AtomicInteger(0);
        receipt.getTotal().lines()
                .map(s -> s.split(" {2,}"))
                .flatMap(Arrays::stream)
                .map(s -> new PdfPCell(Phrase.getInstance(s)))
                .peek(c -> c.setHorizontalAlignment(i.getAndIncrement() % 2 == 0 ? Element.ALIGN_LEFT : Element.ALIGN_RIGHT))
                .forEach(totalTable::addCell);
        return totalTable;
    }

    private PdfPTable getTitleTable(Receipt receipt) {
        PdfPTable titleTable = new PdfPTable(1);
        receipt.getTitle().replaceAll(" +", " ").lines()
                .forEach(s -> {
                    PdfPCell cell = new PdfPCell(new Phrase(s));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleTable.addCell(cell);
                });

        return titleTable;
    }

    private PdfPTable getBodyTable(Receipt receipt) {
        Pattern pattern = Pattern.compile("(\\d+)|(([a-zA-Z]+ ?)+)|(\\$\\d+\\.\\d+)");
        PdfPTable bodyTable = new PdfPTable(4);
        AtomicInteger bodyIndex = new AtomicInteger(1);
        receipt.getBody().lines()
                .flatMap(s -> pattern.matcher(s).results())
                .map(MatchResult::group)
                .map(s -> new PdfPCell(Phrase.getInstance(s)))
                .peek(c -> c.setHorizontalAlignment(bodyIndex.getAndIncrement() % 4 == 0 ?
                        Element.ALIGN_RIGHT : Element.ALIGN_CENTER))
                .forEach(bodyTable::addCell);
        return bodyTable;
    }

    private PdfWriter getPdfWriter(Document document, File file) {
        try {
            return PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PdfReader getPdfReader() {
        InputStream clevertecTitle = getClass().getResourceAsStream("/Clevertec_Template.pdf");
        if (clevertecTitle == null) {
            throw new PdfServiceException("/Clevertec_Template.pdf not found");
        }
        try {
            return new PdfReader(clevertecTitle);
        } catch (IOException e) {
            throw new PdfServiceException(e.getMessage());
        }
    }
}
