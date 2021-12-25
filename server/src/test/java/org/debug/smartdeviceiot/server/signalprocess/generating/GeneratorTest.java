package org.debug.smartdeviceiot.server.signalprocess.generating;

import org.debug.smartdeviceiot.server.BaseTest;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.Signal;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GeneratorTest extends BaseTest {

    /**
     * When you run these test,please change its param to take place in text like "startTimeText"
     * you need to write proper digits in order to compiler successfully
     *
     * */
    @Test
    public void generatorSignal(){
        try {
            Generator generator = getSelectedGenerator();
            if (generator == null) {
                throw new NullPointerException("Generator should not be null");
            }
            double startTime = Double.parseDouble("startTimeText");
            double endTime = Double.parseDouble("endTimeText");
            double amplitude = Double.parseDouble("amplitudeText");
            double samplingFrequency = Double.parseDouble("samplingFrequencyText");
            Signal signal = generator.generate(startTime, endTime, amplitude, samplingFrequency);
            System.out.println("Signal generated");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public Generator getSelectedGenerator() {
        Scanner sc = new Scanner(System.in);
        String name = (String) sc.nextLine().trim();
        Generator generator = null;
        switch (name) {
            case "Delta":
                generator = new Delta(Integer.valueOf("stepSampleNumberText"));
                break;
            case "FullWaveRectifiedSine":
                generator = new FullWaveRectifiedSine(Double.valueOf("periodText"));
                break;
            case "GaussianNoise":
                generator = new GaussianNoise(Double.valueOf("averageText"), Double.valueOf("standardDeviationText"));
                break;
            case "HalfWaveRectifiedSine":
                generator = new HalfWaveRectifiedSine(Double.valueOf("periodText"));
                break;
            case "ImpulseNoise":
                generator = new ImpulsiveNoise(Double.valueOf("probabilityText"));
                break;
            case "PulseWave":
                generator = new PulseWave(Double.valueOf("dutyCycleText"), Double.valueOf("periodText"));
                break;
            case "SineWave":
                generator = new SineWave(Double.valueOf("periodText"));
                break;
            case "SymmetricalPulseWave":
                generator = new SymmetricalPulseWave(Double.valueOf("dutyCycleText"), Double.valueOf("periodText"));
                break;
            case "TriangleWave":
                generator = new TriangleWave(Double.valueOf("dutyCycleText"), Double.valueOf("periodText"));
                break;
            case "UniformNoise":
                generator = new UniformNoise();
                break;
            case "UnitStep":
                generator = new UnitStep(Double.valueOf("stepTimeNumberText"));
                break;
        }
        return generator;
    }

    @Test
    public void test3() {
        List<String> generatorsList = Arrays.asList("Delta", "FullWaveRectifiedSine"
                , "GaussianNoise", "HalfWaveRectifiedSine", "ImpulseNoise", "PulseWave", "SineWave"
                , "SymmetricalPulseWave", "TriangleWave", "UniformNoise", "UnitStep");

    }
}
