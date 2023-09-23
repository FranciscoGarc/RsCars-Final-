package Aplicacion;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import java.awt.Component;
import java.awt.ComponentOrientation;
import Forms.pnlRecuperacionEmail;
import Forms.MainMeca;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import Forms.LoginForm;
import Forms.MainCont;
import Forms.MainForm;
import Forms.MainRecep;
import Forms.RegistroForm;
import raven.toast.Notifications;

/**
 *
 * @author Raven
 */
public class Application extends javax.swing.JFrame {

    private static Application app;
    private final MainForm mainForm;
    private final MainMeca mainMeca;
    private final MainRecep mainRecep;
    private final MainCont mainCont;
    private final LoginForm loginForm;
    private final pnlRecuperacionEmail recup;
    private final RegistroForm registroForm;

    public Application() {
        initComponents();
        setSize(new Dimension(1200, 768));
        setLocationRelativeTo(null);
        mainForm = new MainForm();
        loginForm = new LoginForm();
        mainMeca = new MainMeca();
        mainRecep = new MainRecep();
        mainCont = new MainCont();
        registroForm = new RegistroForm();
        recup = new pnlRecuperacionEmail();
        setContentPane(loginForm);
        Notifications.getInstance().setJFrame(this);
    }

    public static void showForm(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainForm.showForm(component);
    }

    public static void showFormMeca(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainMeca.showForm(component);
    }

    public static void showFormCont(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainCont.showForm(component);
    }

    public static void showFormRecep(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainRecep.showForm(component);
    }

    public static void login(int idUsers, int idTipoUser) {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainForm);
        app.mainForm.setIdUsuario(idUsers);
        app.mainForm.setIdTipoUser(idTipoUser);
        app.mainForm.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenu(0, 0);
        app.mainForm.hideMenu();
        SwingUtilities.updateComponentTreeUI(app.mainForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void loginMeca(int idUsers, int idTipoUser) {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainMeca);
        app.mainMeca.setIdUsuario(idUsers);
        app.mainMeca.setIdTipoUser(idTipoUser);
        app.mainMeca.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenu(0, 0);
        app.mainMeca.hideMenu();
        SwingUtilities.updateComponentTreeUI(app.mainMeca);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void loginRecep(int idUsers, int idTipoUser) {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainRecep);
        app.mainRecep.setIdUsuario(idUsers);
        app.mainRecep.setIdTipoUser(idTipoUser);
        app.mainRecep.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenu(0, 0);
        app.mainRecep.hideMenu();
        SwingUtilities.updateComponentTreeUI(app.mainRecep);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void loginCont(int idUsers, int idTipoUser) {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainCont);
        app.mainCont.setIdUsuario(idUsers);
        app.mainCont.setIdTipoUser(idTipoUser);
        app.mainCont.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenu(0, 0);
        app.mainCont.hideMenu();
        SwingUtilities.updateComponentTreeUI(app.mainCont);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void logout() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.loginForm);
        app.loginForm.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.loginForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void registro() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.registroForm);
        app.registroForm.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.registroForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void Actualizar() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.recup);
        app.recup.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.recup);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void setSelectedMenu(int index, int subIndex) {
        app.mainForm.setSelectedMenu(index, subIndex);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frame0"); // NOI18N
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatLaf.registerCustomDefaultsSource("Tema");
        FlatDarculaLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            app = new Application();
            //  app.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            app.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
