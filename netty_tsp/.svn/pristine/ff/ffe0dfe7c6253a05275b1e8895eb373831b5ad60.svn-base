package msg;

import enums.MsgType;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-2
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPFatigueDriving extends BaseMsg {
    public TSPFatigueDriving() {
        super();
        setType(MsgType.TSPDRIVING);
    }

    private String termCode;        //设备ID(String)
    private long timeStam;        //时间(LONG)
    private int drivType;        //驾驶事件类型：疲劳驾驶事件(int)
    private double totLengt;        //	本次行程疲劳驾驶总时长(Double)
    private String uuiduuid;  //uuid改为     //uuid
    private String vinCodee;   //vin改为vinCodee      //vin


    public String getVinCodee() {
        return vinCodee;
    }

    public void setVinCodee(String vinCodee) {
        this.vinCodee = vinCodee;
    }

    public String getUuiduuid() {
        return uuiduuid;
    }

    public void setUuiduuid(String uuiduuid) {
        this.uuiduuid = uuiduuid;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public void setTimeStam(long timeStam) {
        this.timeStam = timeStam;
    }

    public void setDrivType(int drivType) {
        this.drivType = drivType;
    }

    public void setTotLengt(double totLengt) {
        this.totLengt = totLengt;
    }

    public String getTermCode() {

        return termCode;
    }

    public long getTimeStam() {
        return timeStam;
    }

    public int getDrivType() {
        return drivType;
    }

    public double getTotLengt() {
        return totLengt;
    }

    @Override
    public String toString() {
        return "TSPFatigueDriving{" +
                "termCode='" + termCode + '\'' +
                ", timeStam=" + timeStam +
                ", drivType=" + drivType +
                ", totLengt=" + totLengt +
                ", uuiduuid='" + uuiduuid + '\'' +
                ", vinCodee='" + vinCodee + '\'' +
                '}';
    }
}
