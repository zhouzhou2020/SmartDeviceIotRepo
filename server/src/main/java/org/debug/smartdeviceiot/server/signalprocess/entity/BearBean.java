package org.debug.smartdeviceiot.server.signalprocess.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BearBean {

    private SimpleStringProperty company;
    private SimpleStringProperty type;
    private SimpleIntegerProperty ballNum;
    private SimpleDoubleProperty BPFI;
    private SimpleDoubleProperty BPFO;
    private SimpleDoubleProperty BSF;
    private SimpleDoubleProperty FTF;

    public BearBean(SimpleStringProperty company, SimpleStringProperty type, SimpleIntegerProperty ballNum, SimpleDoubleProperty BPFI, SimpleDoubleProperty BPFO, SimpleDoubleProperty BSF, SimpleDoubleProperty FTF) {
        this.company = company;
        this.type = type;
        this.ballNum = ballNum;
        this.BPFI = BPFI;
        this.BPFO = BPFO;
        this.BSF = BSF;
        this.FTF = FTF;
    }

    @Override
    public String toString() {
        return "BearBean{" +
                "company=" + company +
                ", type=" + type +
                ", ballNum=" + ballNum +
                ", BPFI=" + BPFI +
                ", BPFO=" + BPFO +
                ", BSF=" + BSF +
                ", FTF=" + FTF +
                '}';
    }
}
