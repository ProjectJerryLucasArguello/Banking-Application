package com.argjerryl.the_banking_application.service;

import com.argjerryl.the_banking_application.enitity.Transaction;
import com.argjerryl.the_banking_application.repository.TransactionRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
//import org.springframework.cglib.core.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Component
@AllArgsConstructor
public class BankStatement {

    private static final Logger log = LoggerFactory.getLogger(BankStatement.class);
    private TransactionRepository transactionRepository;
    private static final String FILE = "C:\\Users\\argje\\OneDrive\\Documents\\MyStatment.pdf";

    /**
     * Create a list to store the tranactions with the data which they had occured
     * Should list the account number
     * SHould generate the pdf file of the information above
     * send via email
     */

    //Checking the accountNumber and it's action between a certain time frame
    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
        //parse() takes a string and manipulates it into the type of variable the creator wants
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);


        //Design the size of account statement
        //Provide the file

        Rectangle statementSize = new Rectangle(PageSize.A4); // Unreachable statement
        Document document = new Document(statementSize);
        log.info("Creating PDF document for the bank statement");
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        //Design the layout of the bank statement pdf
        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("Banking Application SpringBoot"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.GREEN);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("123, Spring with we, Please hire me"));
        bankAddress.setBorder(0);
        bankInfoTable.addCell(bankAddress);

        //=========================================//

        PdfPTable statementInfo = new PdfPTable(2);
        PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
        customerInfo.setBorder(0);
        PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
        statement.setBorder(0);
        PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));

        return transactionRepository.findAll().stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().isEqual(start))
                .filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();
    }
}
