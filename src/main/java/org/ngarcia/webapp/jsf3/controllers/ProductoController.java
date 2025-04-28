package org.ngarcia.webapp.jsf3.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.*;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.ngarcia.webapp.jsf3.entities.Categoria;
import org.ngarcia.webapp.jsf3.entities.Producto;
import org.ngarcia.webapp.jsf3.services.ProductoService;

import java.util.List;

//@Named
//@RequestScoped
@Model //Model equivale a Named + RequestScoped
public class ProductoController {

   private Producto producto;

   private Long id;

   @Inject
   private ProductoService service;

   @Inject
   private FacesContext facesContext;

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
   @RequestScoped
   @Named("listadoCategorias")
   public List<Categoria> findAllCategorias() {
      return service.listarCategorias();
   }


   @Produces
   @Model
   public String guardar() {

      //guarda id porque service.guardar() carga uno en insert
      Long id = this.producto.getId();

      System.out.println("GUARDAR: "+this.producto);
      service.guardar(this.producto);

      if(id != null && id > 0) {
         facesContext.addMessage(null,new FacesMessage("Producto " + producto.getNombre() + " actualizado"));
      }
      else {
         facesContext.addMessage(null,new FacesMessage("Producto " + producto.getNombre() + " creado"));
      }

      //el redirect es importante para evitar que se ejecute más de una vez (creo)
      return "index.xhtml?faces-redirect=true";
   }

   public String editar(Long id) {
      System.out.println(this.producto);
      this.id = id;
      return "productoForm.xhtml";
   }

   public String eliminar(Producto producto) {

      service.eliminar(producto.getId());

      facesContext.addMessage(null,new FacesMessage("Producto " + producto.getNombre() + " eliminado"));

      return "index.xhtml?faces-redirect=true";
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }
}
