package com.example.bookshelf.controller;

import com.example.bookshelf.Customer;
import com.example.bookshelf.Order;
import com.example.bookshelf.OrderItems;
import com.example.bookshelf.storage.CustomersList;
import com.example.bookshelf.storage.OrderList;
import com.example.bookshelf.storage.impl.PostgresCustomerListImpl;
import com.example.bookshelf.storage.impl.PostgresOrderListImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class OrderController {

    public OrderList getOrderList() {
        return orderList;
    }

    private final static String ORDER_ID_PARAM_NAME = "orderId";

    //private BookStorage bookStorage = new StaticListBookStorageImpl();
    private OrderList orderList = new PostgresOrderListImpl();

    public NanoHTTPD.Response serveAddOrderRequest (NanoHTTPD.IHTTPSession session){

        ObjectMapper objectMapper = new ObjectMapper();
        long randomOrderId = System.currentTimeMillis();

        String lenghtHeader = session.getHeaders().get("content-lenght");
        int contentLenght = 800;//Integer.parseInt(lenghtHeader);
        byte[] buffer = new byte[contentLenght];

        try{
            session.getInputStream().read(buffer,0,contentLenght);
            String requestBody = new String(buffer).trim();
            OrderItems requestOrder = objectMapper.readValue(requestBody, OrderItems.class);
            requestOrder.setOrgerItemID(randomOrderId);
            orderList.addOrder(requestOrder);

        } catch (Exception e) {
            System.err.println ("Error during process request: \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error book hasn't been added" );
        }
        return newFixedLengthResponse(OK, "text/plain", "Book has been successfully added, id=" + randomOrderId);
    }
}
