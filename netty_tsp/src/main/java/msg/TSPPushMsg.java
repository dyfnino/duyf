package msg;

import enums.MsgType;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-20
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPPushMsg extends BaseMsg{
    public TSPPushMsg(){
        super();
        setType(MsgType.TSPPUSHMSG);
    }

    private String on_off;


    public String getOn_off() {
        return on_off;
    }

    public void setOn_off(String on_off) {
        this.on_off = on_off;
    }

    private String vincode;

    public String getVincode() {
        return vincode;
    }

    public void setVincode(String vincode) {
        this.vincode = vincode;
    }


}
