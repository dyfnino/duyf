package msg;

import enums.MsgType;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-2
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPCallPolice extends BaseMsg{
    public TSPCallPolice() {
        super();
        setType(MsgType.TSPSPEEDPOLICE);
    }
  private String termCode;		//终端ID(String)
    private long timeStam;		//时间(LONG)
    private  int alarmTyp;		//报警标识：超速报警(Int)
    private  String  longtude;		//	经度(String)
    private  String latitude;		//纬度(String)
    private double speedsss;			//车速(Double)
    private String uuiduuid;
    private String vinCodee;



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



    public void setSpeedsss(double speedsss) {
        this.speedsss = speedsss;
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

    public String getVinCodee() {
        return vinCodee;
    }

    public void setVinCodee(String vinCodee) {
        this.vinCodee = vinCodee;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUuiduuid() {
        return uuiduuid;
    }

    public void setUuiduuid(String uuiduuid) {
        this.uuiduuid = uuiduuid;
    }

    public double getSpeedsss() {
        return speedsss;
    }
    @Override
    public String toString() {
        return "TSPCallPolice{" +
                "termCode='" + termCode + '\'' +
                ", timeStam=" + timeStam +
                ", alarmTyp=" + alarmTyp +
                ", longtude='" + longtude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", speedsss=" + speedsss +
                ", uuiduuid='" + uuiduuid + '\'' +
                ", vinCodee='" + vinCodee + '\'' +
                '}';
    }
}
