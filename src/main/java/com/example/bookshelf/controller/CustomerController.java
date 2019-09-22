package com.example.bookshelf.controller;

import com.example.bookshelf.Book;
import com.example.bookshelf.Customer;
import com.example.bookshelf.storage.BookStorage;
import com.example.bookshelf.storage.CustomersList;
import com.example.bookshelf.storage.impl.PostgresBookStorageImpl;
import com.example.bookshelf.storage.impl.PostgresCustomerListImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class CustomerController {
    public CustomersList getCustomerList() {
        return customersList;
    }

    private final static String CUSTOMER_ID_PARAM_NAME = "customerId";

    //private BookStorage bookStorage = new StaticListBookStorageImpl();
    private CustomersList customersList = new PostgresCustomerListImpl();

    public NanoHTTPD.Response serveAddCustomerRequest (NanoHTTPD.IHTTPSession session){

        ObjectMapper objectMapper = new ObjectMapper();
        long randomCustomerId = System.currentTimeMillis();

        String lenghtHeader = session.getHeaders().get("content-lenght");
        int contentLenght = 800;//Integer.parseInt(lenghtHeader);
        byte[] buffer = new byte[contentLenght];

        try{
            session.getInputStream().read(buffer,0,contentLenght);
            String requestBody = new String(buffer).trim();
            Customer requestCustomer = objectMapper.readValue(requestBody, Customer.class);
            requestCustomer.setCustomerID(randomCustomerId);

            customersList.addCustomer(requestCustomer);
        } catch (Exception e) {
            System.err.println ("Error during process request: \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error customer hasn't been added" );
        }
        return newFixedLengthResponse(OK, "text/plain", "Book has been successfully added, id=" + randomCustomerId);
    }
}
