package com.example.bookshelf;


import java.sql.Date;

public class Order {
    private long id;
    private Date date;
    private long customerID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return date;
    }

    public void setdate(Date orderDate) {
        this.date = orderDate;
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }
}
