/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components.assets;

import java.awt.*;

public class BootstrapAssets {
    public final static BootstrapColors colors = new BootstrapColors();

    public static class BootstrapColors {
        public final Color TEXT_PRIMARY = new Color(0, 123, 255);
        public final Color TEXT_SECONDARY = new Color(108, 117, 125);
        public final Color TEXT_SUCCESS = new Color(40, 167, 69);
        public final Color TEXT_DANGER = new Color(220, 53, 69);
        public final Color TEXT_WARNING = new Color(255, 193, 7);
        public final Color TEXT_INFO = new Color(23, 162, 184);
        public final Color TEXT_LIGHT = new Color(248, 249, 250);
        public final Color TEXT_DARK = new Color(52, 58, 64);
        public final Color TEXT_MUTED = new Color(108, 117, 125);
        public final Color TEXT_WHITE = new Color(255, 255, 255);

        public final Color BACKGROUND_PRIMARY = new Color(0, 123, 255);
        public final Color BACKGROUND_SECONDARY = new Color(108, 117, 125);
        public final Color BACKGROUND_SUCCESS = new Color(40, 167, 69);
        public final Color BACKGROUND_DANGER = new Color(220, 53, 69);
        public final Color BACKGROUND_WARNING = new Color(255, 193, 7);
        public final Color BACKGROUND_INFO = new Color(23, 162, 184);
        public final Color BACKGROUND_LIGHT = new Color(248, 249, 250);
        public final Color BACKGROUND_DARK = new Color(52, 58, 64);
        public final Color BACKGROUND_MUTED = new Color(108, 117, 125);
        public final Color BACKGROUND_WHITE = new Color(255, 255, 255);
        public final Color BACKGROUND_TRANSPARENT = new Color(255, 255, 255, 0);

        public final Color ALERT_PRIMARY = new Color(204, 229, 255);
        public final Color ALERT_SECONDARY = new Color(226, 227, 229);
        public final Color ALERT_SUCCESS = new Color(212, 237, 218);
        public final Color ALERT_DANGER = new Color(248, 215, 218);
        public final Color ALERT_WARNING = new Color(255, 243, 205);
        public final Color ALERT_INFO = new Color(209, 236, 241);
        public final Color ALERT_LIGHT = new Color(254, 254, 254);
        public final Color ALERT_DARK = new Color(214, 216, 217);

        public final Color ALERT_BORDER_PRIMARY = new Color(184, 218, 255);
        public final Color ALERT_BORDER_SECONDARY = new Color(214, 216, 219);
        public final Color ALERT_BORDER_SUCCESS = new Color(195, 230, 203);
        public final Color ALERT_BORDER_DANGER = new Color(245, 198, 203);
        public final Color ALERT_BORDER_WARNING = new Color(255, 238, 186);
        public final Color ALERT_BORDER_INFO = new Color(190, 229, 235);
        public final Color ALERT_BORDER_LIGHT = new Color(253, 253, 254);
        public final Color ALERT_BORDER_DARK = new Color(198, 200, 202);

        private Color[] alphaWhiteColors;

        public Color getAlphaWhite(int i) {
            if (alphaWhiteColors == null) createAlphaWhiteColors();
            if(i < 0) return alphaWhiteColors[0];
            else if(i > 255) return alphaWhiteColors[255];

            return alphaWhiteColors[i];
        }

        private void createAlphaWhiteColors() {
            alphaWhiteColors = new Color[256];
            for (int alpha = 0; alpha <= 255; alpha++) {
                alphaWhiteColors[alpha] = new Color(255, 255, 255, alpha);
            }
        }
    }
}
