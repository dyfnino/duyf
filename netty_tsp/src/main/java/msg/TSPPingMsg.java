package msg;

import enums.MsgType;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-2
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPPingMsg extends BaseMsg{
     public TSPPingMsg(){
        super();
        setType(MsgType.TSPPING);
     }
    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
