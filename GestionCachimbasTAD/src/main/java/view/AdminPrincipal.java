package view;

import com.mycompany.gestioncachimbastad.MyUI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import java.sql.SQLException;
import java.util.ArrayList;
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
        HorizontalLayout h1v1 = new HorizontalLayout();
        h1v1.setMargin(true);
        HorizontalLayout h1v2 = new HorizontalLayout();
        h1v2.setMargin(true);

        final HorizontalSplitPanel h1 = new HorizontalSplitPanel();
        h1.addComponent(h1v1);
        h1.addComponent(h1v2);
        h1.setSplitPosition(50, Sizeable.Unit.PERCENTAGE);
        h1.setLocked(true);

        final HorizontalLayout h2 = new HorizontalLayout();
        h2.setMargin(true);

        VerticalLayout v1 = new VerticalLayout();
        v1.setMargin(true);

        VerticalSplitPanel v2 = new VerticalSplitPanel();
        v2.addComponent(h1);
        v2.addComponent(h2);
        v2.setSplitPosition(20, Sizeable.Unit.PERCENTAGE);

        HorizontalSplitPanel layout = new HorizontalSplitPanel();
        layout.addComponent(v1);
        layout.addComponent(v2);
        layout.setSplitPosition(17, Sizeable.Unit.PERCENTAGE);

        setContent(layout);

        List<Usuario> listaUsuarios = new ArrayList();

        final Dao dao = new Dao();

        try {
            dao.conexion();
        } catch (InstantiationException ex) {
            Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            listaUsuarios = dao.getListaUsuarios();
        } catch (SQLException ex) {
            Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dao.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Table table = new Table();

        table.addContainerProperty("ID", Integer.class, null);
        table.addContainerProperty("Nombre", String.class, null);
        table.addContainerProperty("Apellidos", String.class, null);
        table.addContainerProperty("DNI", String.class, null);
        table.addContainerProperty("Edad", Integer.class, null);
        table.addContainerProperty("Rol", String.class, null);

        table.setPageLength(table.size());
        table.setSelectable(true);
        if (!listaUsuarios.isEmpty()) {
            for (Usuario usuario : listaUsuarios) {
                table.addItem(new Object[]{usuario.getId(), usuario.getNombre(), usuario.getApellidos(),
                    usuario.getDni(), usuario.getEdad(), usuario.getRol()}, usuario.getId());
            }
        } else {
            //Escribir mensaje NO DATA en tabla
        }

        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Usuario usuario = null;
                try {
                    usuario = dao.getUsuario((Integer) event.getProperty().getValue());
                } catch (SQLException ex) {
                    Logger.getLogger(AdminPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                ;

                final Window window = new Window("Información detallada:");
                window.setWidth(700.0f, Sizeable.Unit.PIXELS);
                final FormLayout content = new FormLayout();
                Label datos = new Label(
                        "<b>ID:</b> " + usuario.getId() + "<br>"
                        + "<b>Nombre:</b> " + usuario.getNombre() + "<br>"
                        + "<b>Apellidos:</b> " + usuario.getApellidos() + "<br>"
                        + "<b>DNI orginal:</b> " + usuario.getDni() + "<br>"
                        + "<b>Edad:</b> " + usuario.getEdad() + "<br>"
                        + "<b>Rol:</b> " + usuario.getRol() + "<br> ");
                datos.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
                content.addComponent(datos);

                content.setMargin(true);
                window.setContent(content);
                window.center();
                window.setModal(true);
                window.setResizable(false);
                window.setClosable(true);
                UI.getCurrent().addWindow(window);
            }
        });

        h2.addComponent(table);
        
    }
    
    @WebServlet(urlPatterns = "/AdminPrincipal/*", name = "AdminPrincipalServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AdminPrincipal.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
