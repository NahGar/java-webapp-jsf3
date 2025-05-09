package org.ngarcia.webapp.jsf3.controllers;

import jakarta.enterprise.context.*;
import jakarta.enterprise.inject.*;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.ngarcia.webapp.jsf3.entities.Categoria;
import org.ngarcia.webapp.jsf3.entities.Producto;
import org.ngarcia.webapp.jsf3.services.ProductoService;

import java.util.List;
import java.util.ResourceBundle;

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

   @Inject
   private ResourceBundle bundle;

   @Produces
   @Model
   public String titulo() {
      //return "Hola mundo JavaServer Faces 3.0 desde controller";
      return bundle.getString("producto.index.titulo");
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
      //this.producto = new Producto();
      if (this.producto == null) {
         this.producto = new Producto();
         if (this.id != null && this.id > 0) {
            service.porId(id).ifPresent(p -> this.producto = p);
         }
      }
      System.out.println("PRODUCTO " + producto);
      return this.producto;
   }

   @Produces
   @RequestScoped
   @Named("listadoCategorias")
   public List<Categoria> findAllCategorias() {
      return service.listarCategorias();
   }

   public String guardar() {

      System.out.println("GUARDAR: "+this.producto);
      
      if(producto.getId() != null && producto.getId() > 0) {
         //facesContext.addMessage(null,new FacesMessage("Producto " + producto.getNombre() + " actualizado"));
         facesContext.addMessage(null,new FacesMessage(
                 String.format(bundle.getString("producto.mensaje.editar"),producto.getNombre())));
      }
      else {
         //facesContext.addMessage(null,new FacesMessage("Producto " + producto.getNombre() + " creado"));
         facesContext.addMessage(null,new FacesMessage(
                 String.format(bundle.getString("producto.mensaje.crear"),producto.getNombre())));
      }
      
      service.guardar(this.producto);
      //el redirect es importante para evitar que se ejecute más de una vez (creo)
      return "index.xhtml?faces-redirect=true";
   }

   public String editar(Long id) {
      this.id = id;
      // Quita el faces-redirect para mantener el ID durante validaciones
      return "productoForm.xhtml";
   }

   public String eliminar(Producto producto) {

      service.eliminar(producto.getId());

      //facesContext.addMessage(null,new FacesMessage("Producto " + producto.getNombre() + " eliminado"));
      facesContext.addMessage(null,new FacesMessage(
              bundle.getString("producto.mensaje.eliminar"),producto.getNombre()));

      return "index.xhtml?faces-redirect=true";
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }
}
