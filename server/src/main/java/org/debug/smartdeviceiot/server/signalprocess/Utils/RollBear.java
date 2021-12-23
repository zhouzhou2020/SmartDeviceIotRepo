package org.debug.smartdeviceiot.server.signalprocess.Utils;

public class RollBear {

    private int ID;  //轴承ID
    private String type; //轴承型号
    private String company; //轴承型号
    private int ballNum; //滚珠数量
    private double ballDiamt; //滚动体直径,mm
    private double pitchDiamt; //轴承节径,mm
    private double alpha; //轴承接触角

    private double rotating;//转速,rpm

    private double BPFI; //内圈故障特征频率
    private double BPFO; //外圈故障特征频率
    private double BSF;  //滚动体故障特征频率
    private double FTF;  //


    public RollBear(int ballNum, double ballDiamt, double pitchDiamt, double alpha, double rotating) {
        this.ballNum = ballNum;
        this.ballDiamt = ballDiamt;
        this.pitchDiamt = pitchDiamt;
        this.alpha = alpha;
        this.rotating = rotating;
        updateFreqs(rotating);
    }

    public RollBear(String company,String pattern, int ballNum, double ballDiamt, double pitchDiamt, double alpha, double rotating) {
        this.company=company;
        this.type=pattern;
        this.ballNum = ballNum;
        this.BPFI = ballDiamt;
        this.BPFO = pitchDiamt;
        this.BSF = alpha;
        this.FTF = rotating;
    }

    public void setRotating(double rotating) {
        this.rotating = rotating;
    }

    public void setBPFI(double rotating) {
        this.BPFI = rotating/60.0*0.5*this.ballNum*
                (1.0+this.ballDiamt/this.pitchDiamt*Math.cos(this.alpha));
    }

    public void setBPFO(double rotating) {
        this.BPFO = rotating/60.0*0.5*this.ballNum*
                (1.0-this.ballDiamt/this.pitchDiamt*Math.cos(this.alpha));
    }

    public void setBSF(double rotating) {
        this.BSF = rotating/60.0*0.5*this.pitchDiamt/this.ballDiamt*
                (1.0-Math.pow(this.ballDiamt/pitchDiamt,2.0)*Math.pow(Math.cos(this.alpha),2));
    }

    public void setFTF(double rotating) {
        this.FTF = rotating/60.0*0.5* (1.0-this.ballDiamt/this.pitchDiamt*Math.cos(this.alpha));
    }

    public boolean updateFreqs(double rotating){
        setRotating(rotating);
        setBPFI(rotating);
        setBPFO(rotating);
        setFTF(rotating);
        setBSF(rotating);
        return true;
    }

    //下面四个故障特征频率经验公式
    public void estmtBPFI()
    {
        this.BPFI = 0.6 * this.ballNum * this.rotating;
    }
    public void estmtBPFO()
    {
        this.BPFO = 0.6 * this.ballNum * this.rotating;
    }

    public void estmtFTF(){
        this.FTF=(0.381+0.4)/2*this.rotating;
    }
    public void estmtBSF(){
        this.FTF=0.0;
    }
    public int getBallNum() {
        return ballNum;
    }

    public double getBallDiamt() {
        return ballDiamt;
    }

    public double getPitchDiamt() {
        return pitchDiamt;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getRotating() {
        return rotating;
    }

    public double getBPFI() {
        return BPFI;
    }

    public double getBPFO() {
        return BPFO;
    }

    public double getBSF() {
        return BSF;
    }

    public double getFTF() {
        return FTF;
    }

    public static void main(String[] args) {

        RollBear rollBear = new RollBear(10, 12.7, 70.0, 0.0/180.0*Math.PI, 900.0);
        System.out.println("rollBear.BPFI:\t"+rollBear.BPFI);
        System.out.println("rollBear.BPFO:\t"+rollBear.BPFO);
        System.out.println("rollBear.BSF:\t"+rollBear.BSF);
        System.out.println("rollBear.FTF:\t"+rollBear.FTF);
//        rollBear.updateFreqs(1797.0/2);
    }

    @Override
    public String toString() {
        return "RollBear{" +
                "type='" + type + '\'' +
                ", company='" + company + '\'' +
                ", ballNum=" + ballNum +
                ", BPFI=" + BPFI +
                ", BPFO=" + BPFO +
                ", BSF=" + BSF +
                ", FTF=" + FTF +
                '}';
    }
}
