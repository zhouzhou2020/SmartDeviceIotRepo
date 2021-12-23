package org.debug.smartdeviceiot.server.DSP.window;



//import com.cb612.dsp.controller.model.window.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WindowType {

    /*------------------------ FIELDS REGION ------------------------*/
    RECTANGULAR_WINDOW("RECT_WND"),
    HAMMING_WINDOW("Hamming Wnd"),
    HANNING_WINDOW("HANNING_Wnd"),
    BLACKMAN_WINDOW("Blackman Wnd");

    private final String name;

    /*------------------------ METHODS REGION ------------------------*/
    WindowType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WindowType fromString(final String text) {
        return Arrays.asList(WindowType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(WindowType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }

    public static Window fromEnum(WindowType windowType, int M) {
        switch (windowType) {
            case RECTANGULAR_WINDOW: {
                return new RectangularWindow();
            }
            case HAMMING_WINDOW: {
                return new HammingWindow(M);
            }
            case HANNING_WINDOW: {
                return new HanningWindow(M);
            }
            case BLACKMAN_WINDOW: {
                return new BlackmanWindow(M);
            }
        }

        return null;
    }
}
