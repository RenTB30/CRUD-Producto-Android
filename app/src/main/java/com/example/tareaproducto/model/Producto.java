package com.example.tareaproducto.model;

public class Producto {
    private Integer _id;
    private String nombre;
    private Integer stock;
    private Double precio;

    public Producto() {
    }

    public Producto(Integer _id, String nombre, Integer stock, Double precio) {
        this._id = _id;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
