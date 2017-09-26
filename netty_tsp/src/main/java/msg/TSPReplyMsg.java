package msg;

import enums.MsgType;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-2
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class TSPReplyMsg extends BaseMsg{
    public TSPReplyMsg() {
        super();
        setType(MsgType.TSPREPLY);
    }
    private String reply;
    private MsgType type;
    private long id;//返回当前接受到数据集合的id

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public MsgType getType() {
        return type;
    }

    @Override
    public void setType(MsgType type) {
        this.type = type;
    }
}
