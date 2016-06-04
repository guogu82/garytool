package com.gary.garytool.business.baseframe.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gary on 2016/6/4/004.
 */
public class OrderDTO {
    private String total;
    private List<Order> rows;

    public List<Order> getRows() {
        return rows;
    }

    public void setRows(List<Order> rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }



    public OrderDTO() {
    }

    public OrderDTO(String total, List<Order> rows) {
        this.total = total;
        this.rows = rows;
    }
}
