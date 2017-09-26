package msg;

import java.util.ArrayList;

import enums.MsgType;

/**
 * Created by flytome on 2017/9/2.
 */

public class TSPGpsDate extends BaseMsg {
    public ArrayList<TSPGpsBasicInfoVo> listGps;

    public TSPGpsDate() {
        super();
        setType(MsgType.TSPLOGIN);

    }

    public ArrayList<TSPGpsBasicInfoVo> getListGps() {
        return listGps;
    }

    public void setListGps(ArrayList<TSPGpsBasicInfoVo> listGps) {
        this.listGps = listGps;
    }
}
