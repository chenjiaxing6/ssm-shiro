package cn.edu.neusoft.entity;

/**
 * @author Chen
 * @create 2019-05-01 14:10
 * 分页条件
 */
public class PageBean {
    private int page;//页数
    private int limit;//每页显示的条数
    private int offset;//开始页

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return (page-1)*limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
