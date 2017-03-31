/**
 * @Title Page.java
 * @Package com.chuangyuan.util.pageinterceptor
 * @Description
 * @author 庄佳彬
 * @Date 2016年3月3日 
 * @Version v1.0
 */
package com.will.utils.pageinterceptor;

import com.will.framework.entity.IEntity;

import java.util.List;


/**
 * 
 * <li>ClassName: Page</li>
 * <li>Description:</li>
 * <li>Change:</li>
 * <li>Date: 2016年3月3日 下午1:39:18</li>
 * @author 庄佳彬
 * @param <E>
 */
public class Page<E extends IEntity> {
	
	private int pageNo;
	private int pageSize;
	private int startRow;
	private int endRow;
	private long total;
	private int pages;
	private List<E> result;
	
	public Page(int pageNo,int pageSize){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.startRow = pageNo > 0 ?(pageNo - 1) * pageSize : 0;
		this.endRow = pageNo * pageSize;
	}
	
	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
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
		this.pageSize = pageSize;
	}
	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return startRow;
	}
	/**
	 * @param startRow the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	/**
	 * @return the endRow
	 */
	public int getEndRow() {
		return endRow;
	}
	/**
	 * @param endRow the endRow to set
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}
	/**
	 * @return the pages
	 */
	public int getPages() {
		return pages;
	}
	/**
	 * @param pages the pages to set
	 */
	public void setPages(int pages) {
		this.pages = pages;
	}
	/**
	 * @return the result
	 */
	public List<E> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(List<E> result) {
		this.result = result;
	}

}
