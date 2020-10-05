package it.cannizzaro.receiptprinter;

import it.cannizzaro.receiptprinter.entities.business.Receipt;
import it.cannizzaro.receiptprinter.service.business.ReceiptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EndToEndTest
{

        @Autowired
        private ReceiptService receiptService;

        @Test
        @Transactional
        void demo() throws IOException
        {

                ClassLoader classLoader = getClass().getClassLoader();
                File inputFileOne = new File(classLoader.getResource("receipts/in/receipt_1.txt").getFile());
                File inputFileTwo = new File(classLoader.getResource("receipts/in/receipt_2.txt").getFile());
                File inputFileThree = new File(classLoader.getResource("receipts/in/receipt_3.txt").getFile());

                Receipt receiptOne = receiptService.parse(inputFileOne);
                Receipt receiptTwo = receiptService.parse(inputFileTwo);
                Receipt receiptThree = receiptService.parse(inputFileThree);

                receiptService.process(receiptOne);
                receiptService.process(receiptTwo);
                receiptService.process(receiptThree);

                receiptService.save(receiptOne);
                receiptService.save(receiptTwo);
                receiptService.save(receiptThree);

                File outputFileOne = new File(classLoader.getResource("receipts/out").getFile() + "/receipt_1.txt");
                File outputFileTwo = new File(classLoader.getResource("receipts/out").getFile() + "/receipt_2.txt");
                File outputFileThree = new File(classLoader.getResource("receipts/out").getFile() + "/receipt_3.txt");

                receiptService.print(receiptService.findById(receiptOne.getReceiptId()), outputFileOne);
                receiptService.print(receiptService.findById(receiptTwo.getReceiptId()), outputFileTwo);
                receiptService.print(receiptService.findById(receiptThree.getReceiptId()), outputFileThree);

        }

}
