package com.honorfly.schoolsys.utils.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 翻页对象
 */
public class Page
{
    /**
     * 默认的记录数
     */
    public final static int DEFAULT_PAGE_SIZE = 15;

    /**
     * 默认显示的页数数�?
     */
    public final static int DEFAULT_SHOW_PAGE_NUMBER_COUNT = 11;

    /*
     * 当前页码
     */
    private int pageNo = 1;

    /*
     * 页面记录�?
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /*
     * 记录总数
     */
    private int totalCount;

    /*
     * 记录总数
     */
    private int total;
    /*
     * 记录对象集合
     */
    private List data;

    private int totalPage;
    /*
     * 显示的页码集合范�?
     */
    private int[] showPageNumbers;

    private Map otherMap = new HashMap();

    public Map getOtherMap() {
		return otherMap;
	}
	public void setOtherMap(Map otherMap) {
		this.otherMap = otherMap;
	}
	public Page()
    {

    }
    public Page(int pageNo, int pageSize, int totalCount, List data) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;
        this.total = totalCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount()
    {
    	if(totalCount==-1)
    	{
    		totalCount = 0;
    	}
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }

    public int getPageNo()
    {
        if (this.pageNo < 0)
        {
            this.pageNo = 1;
        }
        if (this.pageNo > this.getTotalPage())
        {
            this.pageNo = this.getTotalPage();
        }
        return this.pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    /**
     * 获得总页�?
     *
     * @return
     */
    public int getTotalPage()
    {
        return (totalCount % pageSize == 0) ? totalCount / pageSize
                : totalCount / pageSize + 1;
    }

    /**
     * 获得数据库中记录集的索引位置
     *
     * @return
     */
    public int getStartIndex()
    {
        return (getPageNo() - 1) * getPageSize();
    }

    /**
     * 是否有上�?�?
     *
     * @return
     */
    public boolean hasLastPage()
    {
        if (this.getPageNo() > 1) { return true; }
        return false;
    }

    /**
     * 是否有下�?�?
     *
     * @return
     */
    public boolean hasNextPage()
    {
        if (this.getPageNo() < this.getTotalPage()) { return true; }
        return false;
    }

    /**
     * 默认显示的页码范�?
     *
     * @return
     */
    public int[] getShowPageNumbers()
    {
        return getShowPageNumbers(DEFAULT_SHOW_PAGE_NUMBER_COUNT);
    }

    /**
     * 自定义的显示页码范围
     *
     * @param showCount
     *            页码范围�?
     * @return
     */
    public int[] getShowPageNumbers(int showCount)
    {
        if (showPageNumbers == null)
        {
            int totalPageCount = getTotalPage();
            showPageNumbers = new int[totalPageCount > showCount ? showCount
                    : totalPageCount];
            if (totalPageCount > showCount)
            {
                int firstShowPage = pageNo - (showCount + 1) / 2;
                int endShowPage = pageNo + showCount / 2;
                if (firstShowPage > 0 && endShowPage < totalPageCount)
                {
                    for (int i = 0, max = showPageNumbers.length; i < max; i++)
                    {
                        showPageNumbers[i] = firstShowPage + i + 1;
                    }
                }
                else if (firstShowPage > 0)
                {
                    for (int i = 0, max = showPageNumbers.length; i < max; i++)
                    {
                        showPageNumbers[i] = totalPageCount - showCount + i;
                    }
                }
                else
                {
                    for (int i = 0, max = showPageNumbers.length; i < max; i++)
                    {
                        showPageNumbers[i] = i;
                    }
                }
            }
            else
            {
                for (int i = 1, max = showPageNumbers.length; i <= max; i++)
                {
                    showPageNumbers[i - 1] = i;
                }
            }
        }
        return showPageNumbers;
    }
}
