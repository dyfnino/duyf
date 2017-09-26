package msg;

import enums.MsgType;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-2
 * 说明:
 *
 */
public class TSPWaitWakeUp extends BaseMsg {
    public TSPWaitWakeUp() {
        super();
        setType(MsgType.TSPWAKEUP);
    }
    private String uuiduuid;                //uuid
    private String vinCodee;                 //vin
    private String termCode;		    //设备ID(String)
    private long   rruntime;		    //时间(LONG)
    private String  longtude;			//经度(String)
    private String  latitude;			//纬度(String)
    private double flameout;			//熄火时长(Double)*/
    private double voltagee;			//电瓶电压(DOUBLE) 0.0

    public double getVoltagee() {
        return voltagee;
    }

    public void setVoltagee(double voltagee) {
        this.voltagee = voltagee;
    }

    public String getUuiduuid() {
        return uuiduuid;
    }

    public void setUuiduuid(String uuiduuid) {
        this.uuiduuid = uuiduuid;
    }

    public String getVinCodee() {
        return vinCodee;
    }

    public void setVinCodee(String vinCodee) {
        this.vinCodee = vinCodee;
    }

    public long getRruntime() {
        return rruntime;
    }

    public void setRruntime(long rruntime) {
        this.rruntime = rruntime;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }



    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setFlameout(double flameout) {
        this.flameout = flameout;
    }



    public String getTermCode() {
        return termCode;
    }

    public String getLongtude() {
        return longtude;
    }

    public String getLatitude() {
        return latitude;
    }

    public double getFlameout() {
        return flameout;
    }

    @Override
    public String toString() {
        return "TSPWaitWakeUp{" +
                "uuiduuid='" + uuiduuid + '\'' +
                ", vinCodee='" + vinCodee + '\'' +
                ", termCode='" + termCode + '\'' +
                ", rruntime=" + rruntime +
                ", longtude='" + longtude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", flameout=" + flameout +
                ", voltagee=" + voltagee +
                '}';
    }
}
