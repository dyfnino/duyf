package msg;

import enums.MsgType;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-2
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPContinuousDriving extends BaseMsg {
    public TSPContinuousDriving() {
        super();
        setType(MsgType.TSPCONDRIVING);
    }

    private String uuiduuid;  //uuid改为uuiduuid     //uuid
    private String vinCodee;   //vin改为vinCodee      //vin
    private String termCode;        //终端ID(String)
    private long timeStam;        //时间(LONG)
    private int alarmTyp;        //报警标识：长时间连续驾驶报警(Int)
    private String longtude;            //经度(String)
    private String latitude;            //纬度(String)


    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public void setTimeStam(long timeStam) {
        this.timeStam = timeStam;
    }

    public void setAlarmTyp(int alarmTyp) {
        this.alarmTyp = alarmTyp;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTermCode() {

        return termCode;
    }

    public long getTimeStam() {
        return timeStam;
    }

    public int getAlarmTyp() {
        return alarmTyp;
    }

    public String getLongtude() {
        return longtude;
    }

    public String getLatitude() {
        return latitude;
    }

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

    @Override
    public String toString() {
        return "TSPContinuousDriving{" +
                "uuiduuid='" + uuiduuid + '\'' +
                ", vinCodee='" + vinCodee + '\'' +
                ", termCode='" + termCode + '\'' +
                ", timeStam=" + timeStam +
                ", alarmTyp=" + alarmTyp +
                ", longtude='" + longtude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
