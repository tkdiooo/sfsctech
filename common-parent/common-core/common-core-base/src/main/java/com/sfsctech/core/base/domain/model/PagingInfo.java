package com.sfsctech.core.base.domain.model;

import com.sfsctech.core.base.domain.dto.BaseDto;
import com.sfsctech.core.base.constants.LabelConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Class PageInfo
 *
 * @author 张麒 2017/5/31.
 * @version Description:
 */
public class PagingInfo<T> extends BaseDto {

    private static final long serialVersionUID = 1882965792578587198L;

    /**
     * datatables 参数 起始行数
     */
    private int start;

    /**
     * datatables 参数 自动获取后赋值到pageSize
     */
    private int length = 10;

    /**
     * datatables 参数 重绘表格属性
     **/
    private String draw;

    /**
     * datatables 参数 数据总数
     */
    private long recordsTotal;

    /**
     * datatables 参数 数据集合
     */
    private List<T> data;

    /**
     * datatables 参数 查询参数
     */
    private T condition;

    /**
     * com.github.pagehelper.Pageinfo 参数 每页显示多少条
     */
    private int pageSize = 10;

    /**
     * com.github.pagehelper.Pageinfo 参数 当前页数
     */
    private int currentPage = 1;

    /**
     * 展示的列
     */
    private List<Column> columns;

    /**
     * 排序
     */
    private List<Order> order;

    public PagingInfo() {
    }

    public PagingInfo(PagingInfo<?> paging, T condition) {
        this.columns = paging.getColumns();
        this.condition = condition;
        this.currentPage = paging.getCurrentPage();
        this.draw = paging.getDraw();
        this.length = paging.getLength();
        this.order = paging.getOrder();
        this.pageSize = paging.getPageSize();
        this.recordsTotal = paging.getRecordsTotal();
        this.start = paging.getStart();
    }

    public PagingInfo(PagingInfo<?> paging, List<T> data) {
        this.columns = paging.getColumns();
        this.data = data;
        this.currentPage = paging.getCurrentPage();
        this.draw = paging.getDraw();
        this.length = paging.getLength();
        this.order = paging.getOrder();
        this.pageSize = paging.getPageSize();
        this.recordsTotal = paging.getRecordsTotal();
        this.start = paging.getStart();
    }

    /**
     * 获取length
     *
     * @return 页大小
     */
    public int getLength() {
        return length;
    }

    /**
     * 设置页大小
     */
    public void setLength(int length) {
        this.length = length;
        this.pageSize = length;
    }

    /**
     * @return the draw
     */
    public String getDraw() {
        return draw;
    }

    /**
     * @param draw the draw to set
     */
    public void setDraw(String draw) {
        this.draw = draw;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
        this.currentPage = start / this.pageSize + 1;
    }

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        if (currentPage <= 0) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
    }

    /**
     * @return the recordsTotal
     */
    public long getRecordsTotal() {
        return this.recordsTotal;
    }

    /**
     * @param recordsTotal the recordsTotal to set
     */
    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        if (null == data) {
            data = new ArrayList<>();
        }
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 获取条数
     *
     * @return 条数
     */
    public long getRecordsFiltered() {
        return this.recordsTotal;
    }

    /**
     * 获取页数
     *
     * @return 页数
     */
    public long getPages() {
        return this.recordsTotal / this.pageSize + (this.recordsTotal % this.pageSize == 0 ? 0 : 1);
    }

    /**
     * 获取页码
     *
     * @return 页码
     */
    public int getPageNum() {
        return start / this.pageSize + 1;
    }

    /**
     * 获取起始下标
     *
     * @return 起始下标
     */
    public int getStartRow() {
        return (this.currentPage - 1) * this.pageSize;
    }

    /**
     * 获取结束下标
     *
     * @return 结束下标
     */
    public int getEndRow() {
        return this.currentPage * this.pageSize - 1;
    }

    /**
     * 获取起始下标
     *
     * @return 起始下标
     */
    public int getStartIndex() {
        return (this.currentPage - 1) * this.pageSize;
    }

    public T getCondition() {
        return this.condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public String getOrderByClause() {
        StringBuilder builder = new StringBuilder();
        if (null != this.order) {
            this.order.forEach(order -> {
                if (builder.length() > 0) {
                    builder.append(LabelConstants.COMMA);
                }
                builder.append(this.columns.get(order.getColumn()).getData()).append(LabelConstants.SPACE).append(order.getDir());
            });
        }
        return builder.toString();
    }
}
