package enums;

/**
 * Created by xiaoyongbao on 17/6/24.
 */
public enum MsgType {
    TSPPING,      //心跳
    TSPREPLY,     //回应
    TSPLOGIN,     //gps
    TSPWAKEUP,    //待机自换醒
    TSPSHARPBEND,  //急转弯事件
    TSPSLOWBOWN, //  急减速事件
    TSPSPEEDUP,       //急加速事件
    TSPDRIVING,      //疲劳驾驶
    TSPSPEEDPOLICE,  //超速报警
    TSPLDLE, //怠速报警
    TSPCONDRIVING,//连续驾驶
    TSPFENCE ,//电子围栏
    TSPCONFIGURE,   //配置信息
    TSPPUSHMSG


}
