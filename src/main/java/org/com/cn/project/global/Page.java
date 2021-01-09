package org.com.cn.project.global;

import java.io.Serializable;

public class Page implements Serializable {
    //当前页
    private Integer page = 1;
    //页大小
    private Integer rows = 10;
    // 总记录 数
    private Integer totalRecord;
    //总页数
    private Integer totalPage;
    //关键字类型
    private String keyType;
    //查询关键字
    private String keyWord;
    //开始记录位置
    private Integer start;
    //用户id
    private String userid;
    //其他用户id
    private String otherid;
    private String nickname;
    private String email;
    private String globalValue;
    private String globalValue1;
    private String globalValue2;

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getOtherid() {
        return otherid;
    }

    public void setOtherid(String otherid) {
        this.otherid = otherid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Integer getTotalPage() {
        totalPage = (totalRecord - 1) / rows + 1;
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {

        this.totalPage = totalPage;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getStart() {
        start = (page - 1) * rows;
        return start;
    }

    public void setStart(Integer start) {

        this.start = start;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGlobalValue() {
        return globalValue;
    }

    public void setGlobalValue(String globalValue) {
        this.globalValue = globalValue;
    }

    public String getGlobalValue1() {
        return globalValue1;
    }

    public void setGlobalValue1(String globalValue1) {
        this.globalValue1 = globalValue1;
    }

    public String getGlobalValue2() {
        return globalValue2;
    }

    public void setGlobalValue2(String globalValue2) {
        this.globalValue2 = globalValue2;
    }

    public Page() {
    }

    public Page(Integer page, Integer rows, Integer totalRecord, Integer totalPage, String keyType, String keyWord, Integer start, String userid, String otherid, String nickname, String email, String globalValue, String globalValue1, String globalValue2) {
        this.page = page;
        this.rows = rows;
        this.totalRecord = totalRecord;
        this.totalPage = totalPage;
        this.keyType = keyType;
        this.keyWord = keyWord;
        this.start = start;
        this.userid = userid;
        this.otherid = otherid;
        this.nickname = nickname;
        this.email = email;
        this.globalValue = globalValue;
        this.globalValue1 = globalValue1;
        this.globalValue2 = globalValue2;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", rows=" + rows +
                ", totalRecord=" + totalRecord +
                ", totalPage=" + totalPage +
                ", keyType='" + keyType + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", start=" + start +
                ", userid='" + userid + '\'' +
                ", otherid='" + otherid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", globalValue='" + globalValue + '\'' +
                ", globalValue1='" + globalValue1 + '\'' +
                ", globalValue2='" + globalValue2 + '\'' +
                '}';
    }

}
