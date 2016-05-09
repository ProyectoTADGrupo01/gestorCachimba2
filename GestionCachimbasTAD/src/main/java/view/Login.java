/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import model.Dao;

/**
 *
 * @author Mario
 */
@Theme("mytheme")
@Widgetset("com.mycompany.gestioncachimbastad.MyAppWidgetset")
public class Login extends UI {

    @Override
    protected void init(VaadinRequest request) {
        FormLayout layout = new FormLayout();
        TextField dni = new TextField("DNI");
        layout.addComponent(dni);
        PasswordField password = new PasswordField("Password");
        layout.addComponent(password);
        Button enviar = new Button("Enviar");
        layout.addComponent(enviar);
        
        enviar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Integer login = 0;
                Dao dao = new Dao();
                try {
                    login = dao.login(dni.getValue(), password.getValue());
                    if (login != 0) {
                        if (login == 1) {
                            getUI().getPage().setLocation("/AdminPrincipal");
                        } else if (login == 2 ) {
                            getUI().getPage().setLocation("/AdminUsuario");
                        }
                    } else {
                        Notification.show("Login incorrecto");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        setContent(layout);
    }
    
    @WebServlet(urlPatterns = "/Login/*", name = "LoginServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Login.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
