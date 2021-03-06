package msg;

import enums.MsgType;


/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-2
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPSharpSlowBown extends BaseMsg {
    public TSPSharpSlowBown() {
        super();
        setType(MsgType.TSPSLOWBOWN);
    }

    private String termCode;        //设备ID(String)
    private long timeStam;        //时间(Long)
    private int drivType;        //驾驶事件类型：急减速事件(int)
    private double sharpAge;        //急减加速度(Double)
    private String uuiduuid;  //uuid改为uuiduuid     //uuid
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

    public void setSharpAge(double sharpAge) {
        this.sharpAge = sharpAge;
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

    public double getSharpAge() {
        return sharpAge;
    }

    @Override
    public String toString() {
        return "TSPSharpSlowBown{" +
                "termCode='" + termCode + '\'' +
                ", timeStam=" + timeStam +
                ", drivType=" + drivType +
                ", sharpAge=" + sharpAge +
                ", uuiduuid='" + uuiduuid + '\'' +
                ", vinCodee='" + vinCodee + '\'' +
                '}';
    }
}
