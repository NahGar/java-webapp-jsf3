package org.ngarcia.webapp.jsf3.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.*;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.ngarcia.webapp.jsf3.entities.Producto;
import org.ngarcia.webapp.jsf3.services.ProductoService;

import java.util.List;

//@Named
//@RequestScoped
@Model //Model equivale a Named + RequestScoped
public class ProductoController {

   private Producto producto;

   private Long id;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Inject
   private ProductoService service;

   @Produces
   @Model
   public String titulo() {
      return "Hola mundo JavaServer Faces 3.0 desde controller";
   }

   @Produces
   @RequestScoped
   @Named("listadoProductos")
   public List<Producto> findAll() {
      //return Arrays.asList(new Producto("peras"),
      //        new Producto("manzanas"),
      //        new Producto("mandarinas"));
      return service.listar();
   }

   @Produces
   @Model
   public Producto producto() {
      this.producto = new Producto();
      if(this.id != null && this.id > 0) {
         service.porId(id).ifPresent(p -> this.producto = p);
      }
      return this.producto;
   }

   @Produces
   @Model
   public String guardar() {
      System.out.println(this.producto);
      service.guardar(this.producto);
      //el redirect es importante para evitar que se ejecute más de una vez (creo)
      return "index.xhtml?faces-redirect=true";
   }

   public String editar(Long id) {
      System.out.println(this.producto);
      this.id = id;
      return "productoForm.xhtml";
   }
}
