import org.junit.jupiter.api.Test;
import pl.radomiej.javity.JEngine;
import pl.radomiej.javity.hardware.JGraphics;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrameworkTest {

    @Test
    void myFirstTest() {
        JEngine.Instance.init(new JGraphics());
    }

}
