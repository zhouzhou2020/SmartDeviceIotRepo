//package org.debug.smartdeviceiot.server.DSP.signal;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public enum SignalType {
//
//    /*------------------------ FIELDS REGION ------------------------*/
//    UNIFORM_NOISE("UNIFORM_NOISE"),
//    GAUSSIAN_NOISE("GAUSSIAN_NOISE"),
//    SINUSOIDAL_SIGNAL("SINUSOIDAL_SIGNAL"),
//    SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL("SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL"), //正弦整流半正弦信号
//    SINUSOIDAL_RECTIFIED_IN_TWO_HALVES("SINUSOIDAL_RECTIFIED_IN_TWO_HALVES"),   //正弦波整流
//    RECTANGULAR_SIGNAL("RECTANGULAR_SIGNAL"),
//    SYMMETRICAL_RECTANGULAR_SIGNAL("SYMMETRICAL_RECTANGULAR_SIGNAL"),  //对称矩形信号
//    TRIANGULAR_SIGNAL("TRIANGULAR_SIGNAL"),
//    UNIT_JUMP("UNIT_JUMP"),
//    UNIT_IMPULSE("UNIT_IMPULSE"),
//    IMPULSE_NOISE("IMPULSE_NOISE"),
//    LOW_PASS_FILTER("LOW_PASS_FILTER"),
//    BAND_PASS_FILTER("BAND_PASS_FILTER"),
//    HIGH_PASS_FILTER("HIGH_PASS_FILTER");
//
//    private final String name;
//
//    /*------------------------ METHODS REGION ------------------------*/
//    SignalType(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public static SignalType fromString(final String text) {
//        return Arrays.asList(SignalType.values())
//                .stream()
//                .filter((it) -> it.getName().equals(text))
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new);
//    }
//
//    public static List<String> getNamesList() {
//        return Arrays.asList(SignalType.values())
//                .stream()
//                .map((it) -> it.getName())
//                .collect(Collectors.toList());
//    }
//}
