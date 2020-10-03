package it.cannizzaro.receiptprinter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReceiptController
{

        @RequestMapping("/")
        public String index()
        {
                return "Greetings from Spring Boot!";
        }

}
