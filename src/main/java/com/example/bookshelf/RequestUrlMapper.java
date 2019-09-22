package com.example.bookshelf;


import com.example.bookshelf.controller.BookController;
import com.example.bookshelf.controller.CustomerController;
import com.example.bookshelf.controller.OrderController;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

import static fi.iki.elonen.NanoHTTPD.Method.GET;
import static fi.iki.elonen.NanoHTTPD.Method.POST;
import static fi.iki.elonen.NanoHTTPD.Response.Status.NOT_FOUND;

public class RequestUrlMapper {
    private final static String ADD_BOOK_URL = "/book/add";
    private final static String GET_BOOK_URL = "/book/get";
    private final static String GET_ALL_BOOK_URL = "/book/getAll";
    private final static String ADD_CUSTOMER_URL = "/customer/add";
    private final static String ADD_ORDER_URL = "/order/add";
    private final static String GET_ORDER_URL = "/order/get";

    private BookController bookController = new BookController();
    private CustomerController customerController = new CustomerController();
    private OrderController orderController = new OrderController();


    public BookController getBookController() {
        return bookController;
    }

    public Response delegateRequest(IHTTPSession session) {
        if (GET.equals(session.getMethod()) && GET_BOOK_URL.equals(session.getUri())){
            return bookController.serveGetBookRequest(session);
        }

        else if (GET.equals(session.getMethod()) && GET_ALL_BOOK_URL.equals(session.getUri())){

                return  bookController.serveGetBooksRequest(session);

            }


        else if (POST.equals(session.getMethod()) && ADD_BOOK_URL.equals(session.getUri())){
            return bookController.serveAddBookRequest(session);
        }
        else if (POST.equals(session.getMethod()) && ADD_CUSTOMER_URL.equals(session.getUri())){
            return customerController.serveAddCustomerRequest(session);
        }

        else if (POST.equals(session.getMethod()) && ADD_ORDER_URL.equals(session.getUri())){
            return orderController.serveAddOrderRequest(session);
        }


        return NanoHTTPD.newFixedLengthResponse(NOT_FOUND, "text/plain" , "Not found");


    }



}
