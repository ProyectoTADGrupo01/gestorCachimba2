//MIERCOLES 11, 00:04

package view;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.client.ui.VAbsoluteLayout;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import model.Dao;
import model.Usuario;

/**
 *
 * @author Mario
 */
@Theme("mytheme")
@Widgetset("com.mycompany.gestioncachimbastad.MyAppWidgetset")
public class AdminPrincipal extends UI {

    @Override
    protected void init(VaadinRequest request) {

        try {
            HorizontalLayout v2h2 = new HorizontalLayout();
            v2h2.setMargin(true);
            v2h2.setStyleName(ValoTheme.THEME_NAME);

            final VerticalLayout v2h1 = new VerticalLayout();
            v2h1.setMargin(true);

            final VerticalSplitPanel v2 = new VerticalSplitPanel();
            v2.addComponent(v2h2);
            v2.addComponent(v2h1);
            v2.setSplitPosition(10, Unit.PERCENTAGE);
            v2.setLocked(true);

            VerticalLayout v1 = new VerticalLayout();
            v1.setMargin(true);

            HorizontalSplitPanel layout = new HorizontalSplitPanel();
            layout.addComponent(v1);
            layout.addComponent(v2);
            layout.setSplitPosition(30, Unit.PERCENTAGE);
            layout.setStyleName(ValoTheme.THEME_NAME);
            setContent(layout);

            List<String> listaRoles = new ArrayList<>();
            listaRoles.add("admin");
            listaRoles.add("usuario");
            final BeanItemContainer<String> roles = new BeanItemContainer(String.class, listaRoles);

//            Link estadisticas = new Link("Estadísticas", new ExternalResource("/Estadisticas"));
//            v2h2.addComponent(estadisticas);
            Button crearUsuario = new Button("Nuevo Usuario");
            crearUsuario.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    v2h1.removeAllComponents();
                    final TextField nombre = new TextField("Nombre");
                    nombre.setColumns(25);
                    v2h1.addComponent(nombre);

                    final TextField apellidos = new TextField("Apellidos");
                    v2h1.addComponent(apellidos);

                    final TextField dni = new TextField("DNI");
                    v2h1.addComponent(dni);

                    final PasswordField password = new PasswordField("Password");
                    v2h1.addComponent(password);

                    final TextField edad = new TextField("Edad");
                    v2h1.addComponent(edad);

                    final ComboBox rol = new ComboBox("Rol", listaRoles);
                    rol.setValue("admin");
                    rol.setNullSelectionAllowed(false);
                    v2h1.addComponent(rol);

                    Button insertarUsuario = new Button("Crear usuario");
                    insertarUsuario.addClickListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            Usuario usuario = new Usuario(0,
                                    nombre.getValue(),
                                    apellidos.getValue(),
                                    dni.getValue(),
                                    password.getValue(),
                                    Integer.parseInt(edad.getValue()),
                                    rol.getValue().toString());
                            Dao dao = new Dao();
                            try {
                                dao.conexion();
                                dao.crearUsuario(usuario);
                            } catch (InstantiationException ex) {
                                Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                    v2h1.addComponent(insertarUsuario);
                }
            });

            v2h2.addComponent(crearUsuario);

            Dao dao = new Dao();
            dao.conexion();

            List<Usuario> listaUsuarios = dao.getListaUsuarios();

            Tree tree1 = new Tree("Panel Administracion");
            String usuariosParent = "Usuarios";
            tree1.addItem(usuariosParent);
            for (Usuario usuario : listaUsuarios) {
                tree1.addItem(usuario);
                tree1.setParent(usuario, usuariosParent);
                tree1.setItemCaption(usuario, usuario.getNombre() + " " + usuario.getDni());
                tree1.setChildrenAllowed(usuario, false);
            }

            tree1.setSelectable(true);
            tree1.addValueChangeListener(new Property.ValueChangeListener() {

                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    v2h1.removeAllComponents();

                    final Usuario u = (Usuario) event.getProperty().getValue();

                    final TextField nombre = new TextField("Nombre", u.getNombre());
                    nombre.setColumns(25);
                    v2h1.addComponent(nombre);

                    final TextField apellidos = new TextField("Apellidos", u.getApellidos());
                    v2h1.addComponent(apellidos);

                    final TextField dni = new TextField("DNI", u.getDni());
                    v2h1.addComponent(dni);

                    final PasswordField password = new PasswordField("Password", u.getPassword());
                    v2h1.addComponent(password);

                    final TextField edad = new TextField("Edad", String.valueOf(u.getEdad()));
                    v2h1.addComponent(edad);

                    final ComboBox rol = new ComboBox("Rol", listaRoles);
                    rol.setValue("admin");
                    rol.setNullSelectionAllowed(false);
                    v2h1.addComponent(rol);

                    Button actualizarButton = new Button("Actualizar usuario");
                    actualizarButton.addClickListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            Usuario nuevoUsuario = new Usuario(u.getId(),
                                    nombre.getValue(),
                                    apellidos.getValue(),
                                    dni.getValue(),
                                    password.getValue(),
                                    Integer.parseInt(edad.getValue()),
                                    rol.getValue().toString());

                            Dao dao = new Dao();
                            try {
                                dao.conexion();
                                dao.editarUsuario(nuevoUsuario);
                                v2h1.removeAllComponents();
                                v2h2.removeAllComponents();
                                Notification.show("Usuario modificado con éxito",
                                        Notification.Type.HUMANIZED_MESSAGE);
                            } catch (InstantiationException ex) {
                                Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                   
                    Button eliminarButton = new Button("Eliminar usuario");
                    eliminarButton.addClickListener(new Button.ClickListener() {

                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            Dao dao = new Dao();
                            try {
                                dao.conexion();
                                dao.eliminarUsuario(u.getId());
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InstantiationException ex) {
                                Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                    v2h1.addComponent(actualizarButton);
                    v2h1.addComponent(eliminarButton);
                }
            });

            v1.addComponent(tree1);

            dao.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        //--------------------------------------------------
//        HorizontalLayout h1v1 = new HorizontalLayout();
//        h1v1.setMargin(true);
//        HorizontalLayout h1v2 = new HorizontalLayout();
//        h1v2.setMargin(true);
//
//        final HorizontalSplitPanel h1 = new HorizontalSplitPanel();
//        h1.addComponent(h1v1);
//        h1.addComponent(h1v2);
//        h1.setSplitPosition(50, Sizeable.Unit.PERCENTAGE);
//        h1.setLocked(true);
//
//        final HorizontalLayout h2 = new HorizontalLayout();
//        h2.setMargin(true);
//
//        VerticalLayout v1 = new VerticalLayout();
//        v1.setMargin(true);
//
//        VerticalSplitPanel v2 = new VerticalSplitPanel();
//        v2.addComponent(h1);
//        v2.addComponent(h2);
//        v2.setSplitPosition(20, Sizeable.Unit.PERCENTAGE);
//
//        HorizontalSplitPanel layout = new HorizontalSplitPanel();
//        layout.addComponent(v1);
//        layout.addComponent(v2);
//        layout.setSplitPosition(17, Sizeable.Unit.PERCENTAGE);
//
//        setContent(layout);
//
//        List<Usuario> listaUsuarios = new ArrayList();
//
//        final Dao dao = new Dao();
//
//        try {
//            dao.conexion();
//        } catch (InstantiationException ex) {
//            Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            listaUsuarios = dao.getListaUsuarios();
//        } catch (SQLException ex) {
//            Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                dao.cerrarConexion();
//            } catch (SQLException ex) {
//                Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        Table table = new Table();
//
//        table.addContainerProperty("ID", Integer.class, null);
//        table.addContainerProperty("Nombre", String.class, null);
//        table.addContainerProperty("Apellidos", String.class, null);
//        table.addContainerProperty("DNI", String.class, null);
//        table.addContainerProperty("Edad", Integer.class, null);
//        table.addContainerProperty("Rol", String.class, null);
//
//        table.setPageLength(table.size());
//        table.setSelectable(true);
//        if (!listaUsuarios.isEmpty()) {
//            for (Usuario usuario : listaUsuarios) {
//                table.addItem(new Object[]{usuario.getId(), usuario.getNombre(), usuario.getApellidos(),
//                    usuario.getDni(), usuario.getEdad(), usuario.getRol()}, usuario.getId());
//            }
//        } else {
//            //Escribir mensaje NO DATA en tabla
//        }
//
//        table.addValueChangeListener(new Property.ValueChangeListener() {
//            @Override
//            public void valueChange(Property.ValueChangeEvent event) {
//                Usuario usuario = null;
//                try {
//                    usuario = dao.getUsuario((Integer) event.getProperty().getValue());
//                } catch (SQLException ex) {
//                    Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                ;
//
//                final Window window = new Window("Información detallada:");
//                window.setWidth(700.0f, Sizeable.Unit.PIXELS);
//                final FormLayout content = new FormLayout();
//                Label datos = new Label(
//                        "<b>ID:</b> " + usuario.getId() + "<br>"
//                        + "<b>Nombre:</b> " + usuario.getNombre() + "<br>"
//                        + "<b>Apellidos:</b> " + usuario.getApellidos() + "<br>"
//                        + "<b>DNI orginal:</b> " + usuario.getDni() + "<br>"
//                        + "<b>Edad:</b> " + usuario.getEdad() + "<br>"
//                        + "<b>Rol:</b> " + usuario.getRol() + "<br> ");
//                datos.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
//                content.addComponent(datos);
//
//                content.setMargin(true);
//                window.setContent(content);
//                window.center();
//                window.setModal(true);
//                window.setResizable(false);
//                window.setClosable(true);
//                UI.getCurrent().addWindow(window);
//            }
//        });
//
//        h2.addComponent(table);
    }

    @WebServlet(urlPatterns = "/AdminPrincipal/*", name = "AdminPrincipalServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AdminPrincipal.class, productionMode = false)
    public static class AdminPrincipalServlet extends VaadinServlet {
    }
}
