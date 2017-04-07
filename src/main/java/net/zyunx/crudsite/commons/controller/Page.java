package net.zyunx.crudsite.commons.controller;

import javax.servlet.http.HttpServletRequest;

import net.zyunx.crudsite.commons.dao.Range;

public class Page {
	/* start from 1 */
	private Integer pageIndex = 1;
	private Integer pageRows = 100;
	private Integer totalRows;
	
	private Page() {}
	
	private boolean hasRows() {
		return totalRows - (pageIndex - 1) * pageRows > 0;
	}
	
	public static Page valueOf(HttpServletRequest request, int totalRows) {
		Integer pageIndex = null;
		Integer pageRows = null;
		try {
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		} catch (Exception e) {};
		try {
			pageRows = Integer.valueOf(request.getParameter("pageRows"));
		} catch (Exception e) {};
		
		Page page = new Page();
		page.pageIndex = (null != pageIndex && pageIndex >= 1 ? pageIndex : 1);
		page.pageRows = (null != pageRows && pageRows >= 1 && pageRows <= 100 ? pageRows : 100);
		page.totalRows = totalRows;
		
		return page.hasRows() ? page : getDefaultPage(totalRows);
	}

	
	public static Page getDefaultPage(int totalRows) {
		Page page = new Page();
		page.pageIndex = 1;
		page.pageRows = 100;
		page.totalRows = totalRows;
		return page;
	}
	
	public Integer getPageIndex() {
		return pageIndex;
	}

	public Integer getPageRows() {
		return pageRows;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public int getCurrentPageRows() {
		return Math.min(this.getTotalRows() - (this.getPageIndex() - 1) * this.getPageRows(), 
				this.getPageRows());
	}
	public int getTotalPages() {
		if (this.totalRows == 0) {
			return 1;
		}
		return (this.totalRows - 1) / this.pageRows + 1;
	}
	
	public Range toRange() {
		Range range = new Range();
		range.setStart((this.getPageIndex() - 1) * this.getPageRows());
		range.setCount(this.getCurrentPageRows());
		return range;
	}
}
