package msg;

import enums.MsgType;

/**
 * Created with IntelliJ IDEA.
 * 浣滆�: duyingfeng
 * 鏃ユ湡: 17-9-2
 * 璇存槑:
 * To change this template use File | Settings | File Templates.
 */
public class TSPFence extends BaseMsg {
    public TSPFence() {
        super();
        setType(MsgType.TSPFENCE);
    }
    private String uuiduuid;  //uuid鏀逛负uuiduuid     //uuid
    private  String vinCodee;   //vin鏀逛负vinCodee      //vin
    private   String termCode;		//缁堢ID(String)
    private   long timeStam;		//鏃堕棿(LONG)
    private  int alarmTyp;		//鎶ヨ鏍囪瘑锛氱數瀛愬洿鏍�Int)
    private  String longtude;		//	缁忓害(String)

    private  String latitude;   //纬度

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public long getTimeStam() {
        return timeStam;
    }

    public void setTimeStam(long timeStam) {
        this.timeStam = timeStam;
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

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public void setAlarmTyp(int alarmTyp) {
        this.alarmTyp = alarmTyp;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }



    public String getTermCode() {
        return termCode;
    }



    public int getAlarmTyp() {
        return alarmTyp;
    }

    public String getLongtude() {
        return longtude;
    }


    @Override
    public String toString() {
        return "TSPFence{" +
                "uuiduuid='" + uuiduuid + '\'' +
                ", vinCodee='" + vinCodee + '\'' +
                ", termCode='" + termCode + '\'' +
                ", timeStam=" + timeStam +
                ", alarmTyp=" + alarmTyp +
                ", longtude='" + longtude + '\'' +
                '}';
    }
}
