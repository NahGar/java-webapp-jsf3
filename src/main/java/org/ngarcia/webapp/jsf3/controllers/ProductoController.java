package org.ngarcia.webapp.jsf3.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.*;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.ngarcia.webapp.jsf3.entities.Producto;
import org.ngarcia.webapp.jsf3.services.ProductoService;

import java.util.Arrays;
import java.util.List;

//@Named
//@RequestScoped
@Model //Model equivale a Named + RequestScoped
public class ProductoController {

   @Inject
   private ProductoService service;

   @Produces
   @Model
   public String titulo() {
      return "Hola mundo JavaServer Face 3.0 desde controller";
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

}
