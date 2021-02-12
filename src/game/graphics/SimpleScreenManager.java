package game.graphics;

import javax.swing.*;
import java.awt.*;

public class SimpleScreenManager {
    private GraphicsDevice device;

    /**
     Tworzy nowy obiekt SimpleScreenManager.
     */
    public SimpleScreenManager() {
        GraphicsEnvironment environment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
    }

    /**
     Przechodzi w tryb pe�noekranowy i zmienia tryb graficzny.
     */
    public void setFullScreen(DisplayMode displayMode,
                              JFrame window)
    {
        window.setUndecorated(true);
        window.setResizable(false);

        device.setFullScreenWindow(window);
        if (displayMode != null &&
                device.isDisplayChangeSupported())
        {
            try {
                device.setDisplayMode(displayMode);
            }
            catch (IllegalArgumentException ex) {
                // Ignorowanie - niedozwolony tryb dla tego urz�dzenia.
            }
        }
    }


    /**
     Zwraca okno wykorzystywane w trybie pe�noekranowym.
     */
    public Window getFullScreenWindow() {
        return device.getFullScreenWindow();
    }


    /**
     Przywraca pocz�tkowy tryb wy�wietlania.
     */
    public void restoreScreen() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            window.dispose();
        }
        device.setFullScreenWindow(null);
    }
}
