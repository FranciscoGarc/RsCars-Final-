package Menu.Recep;

import Menu.Meca.*;
import Menu.Admin.*;
import Menu.*;

/**
 *
 * @author Raven
 */
public interface MenuEvent {

    public void menuSelected(int index, int subIndex, MenuAction action);
}
