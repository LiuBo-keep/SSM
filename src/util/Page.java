package util;

/**
 *分页类
 *
 */
public class Page {

    private int page;//当前页
    private int rows;//每页数量
    private int offset;//开始行

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int row) {
        this.rows = row;
    }

    public int getOffset() {
        return (page-1)*rows;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
