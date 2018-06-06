package com.cn.climax.wisdomparking.data.response;

import com.cn.climax.wisdomparking.data.BaseBean;

/**
 * author：leo on 2018/6/6 0006 21:24
 * email： leocheung4ever@gmail.com
 * description: 首页公告栏响应对象
 * what & why is modified:
 */
public class BulletinResponse extends BaseBean<BulletinResponse> {


    /**
     * sorts : null
     * ranges : null
     * id : 3
     * createTime : 1528103715000
     * modifyTime : 1528103991000
     * title : 测试公告1
     * body : 我是测试公告1，哈哈哈
     * status : 0
     */

    private String sorts;
    private String ranges;
    private int id;
    private long createTime;
    private long modifyTime;
    private String title;
    private String body;
    private int status;

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
