package com.shanghai.common.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.github.pagehelper.Page;

/**
* @author: YeJR 
* @version: 2018年4月28日 上午10:07:51
* 对Page<E>结果进行包装
*/
public class PageInfo<T> implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    /**
     * 当前页码
     */
    private int pageNo;
    
    /**
     * 一页显示多少条
     */
    private int pageSize;
    
    /**
     * 总记录数, 一共多少条记录
     */
    private long total;
    
    /**
     * 总页数, 一共XX页
     */
    private int pages;
    
    /**
     * 结果集
     */
    private List<T> list;
    
    /**
     * 是否为第一页
     */
    private boolean isFirstPage = false;
    
    /**
     * 是否为最后一页
     */
    private boolean isLastPage = false;
    
    
    /**
     * 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
     */
    private String funcName = "page";
	
    /**
     * 函数的附加参数，第三个参数值。
     */
    private String funcParam = ""; 
    
    /**
     * 首页索引
     */
    private int first;
    
    /**
     * 尾页索引
     */
    private int last;
    
    /**
     * 上一页索引
     */
    private int prev;
    
    /**
     * 下一页索引
     */
    private int next;
    
    /**
     * 显示页面长度
     */
    private int length = 5;
    
    /**
     * 前后显示页面长度
     */
    private int slider = 1;


    public PageInfo() {
    }

    /**
     * 包装Page对象
     *
     * @param list
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public PageInfo(List<T> list) {
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNo = page.getPageNum();
            this.pageSize = page.getPageSize();

            this.pages = page.getPages();
            this.list = page;
            this.total = page.getTotal();
        } else if (list instanceof Collection) {
            this.pageNo = 1;
            this.pageSize = list.size();

            this.pages = 1;
            this.list = list;
            this.total = list.size();
        }
        if (list instanceof Collection) {
            //判断页面边界
            judgePageBoudary();
        }
        //初始化必要参数
        initialize();
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNo == 1;
        isLastPage = pageNo == pages;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }
    
    /**
	 * 初始化参数
	 */
	public void initialize(){
				
		this.first = 1;
		this.last = pages;
		if (this.last == 0) {
			this.last++;
		}
		if (this.last < this.first) {
			this.last = this.first;
		}
		
		if (this.pageNo <= 1) {
			this.pageNo = this.first;
			this.isFirstPage=true;
		}

		if (this.pageNo >= this.last) {
			this.pageNo = this.last;
			this.isLastPage = true;
		}

		if (this.pageNo < this.last - 1) {
			this.next = this.pageNo + 1;
		} else {
			this.next = this.last;
		}

		if (this.pageNo > 1) {
			this.prev = this.pageNo - 1;
		} else {
			this.prev = this.first;
		}
		
		// 如果当前页小于首页
		if (this.pageNo < this.first) {
			this.pageNo = this.first;
		}
		// 如果当前页大于尾页
		if (this.pageNo > this.last) {
			this.pageNo = this.last;
		}
		
	}
	
    
    /**
	 * 获取分页HTML代码
	 * @return
	 */
	public String getHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"fixed-table-pagination\" style=\"display: block;\">");
		long startIndex = total == 0 ? 0 : (pageNo-1) * pageSize + 1;
		long endIndex = pageNo * pageSize <=total ? pageNo * pageSize : total;
		sb.append("<div class=\"pull-left pagination-detail\">");
		sb.append("<span class=\"pagination-info\">显示第 "+startIndex+" 到第 "+ endIndex +" 条记录，总共 "+total+" 条记录</span>");
		sb.append("<span class=\"page-list\">每页显示 <span class=\"btn-group dropup\">");
		sb.append("<button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle page-btn\" data-toggle=\"dropdown\" aria-expanded=\"false\">");
		sb.append("<span class=\"page-size\">"+pageSize+"</span> <span class=\"caret\"></span>");
		sb.append("</button>");
		sb.append("<ul class=\"dropdown-menu page-ul\" role=\"menu\">");
		sb.append("<li onclick=\""+funcName+"("+pageNo+",10,'"+funcParam+"');\" class=\""+getSelected(pageSize,10)+ " page-li\"><a href=\"javascript:;\">10</a></li>");
		sb.append("<li onclick=\""+funcName+"("+pageNo+",25,'"+funcParam+"');\" class=\""+getSelected(pageSize,25)+ " page-li\"><a href=\"javascript:;\">25</a></li>");
		sb.append("<li onclick=\""+funcName+"("+pageNo+",50,'"+funcParam+"');\" class=\""+getSelected(pageSize,50)+ " page-li\"><a href=\"javascript:;\">50</a></li>");
		sb.append("<li onclick=\""+funcName+"("+pageNo+",100,'"+funcParam+"');\" class=\""+getSelected(pageSize,100)+ " page-li\"><a href=\"javascript:;\">100</a></li>");
		sb.append("</ul>");
		sb.append("</span> 条记录</span>");
		sb.append("</div>");
		
		sb.append("<div class=\"pull-right pagination-roll\">");
		sb.append("<ul class=\"pagination pagination-outline\">");
		// 如果是首页
		if (pageNo == first) {
			sb.append("<li class=\"paginate_button previous  \"><a class=\"aPage-left aPage\" style=\"cursor: not-allowed;\" href=\"javascript:\"><i class=\"iconfont icon-zuizuo\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button previous  \"><a class=\"aPage-middle aPage\" style=\"cursor: not-allowed;\" href=\"javascript:\"><i class=\"iconfont icon-xiangzuo\"></i></a></li>\n");
		} else {
			sb.append("<li class=\"paginate_button previous\"><a class=\"aPage-left aPage\" href=\"javascript:\" onclick=\""+funcName+"("+first+","+pageSize+",'"+funcParam+"');\"><i class=\"iconfont icon-zuizuo\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button previous\"><a class=\"aPage-middle aPage\" href=\"javascript:\" onclick=\""+funcName+"("+prev+","+pageSize+",'"+funcParam+"');\"><i class=\"iconfont icon-xiangzuo\"></i></a></li>\n");
		}

		int begin = pageNo - (length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}

		if (begin > first) {
			int i = 0;
			for (i = first; i < first + slider && i < begin; i++) {
				sb.append("<li class=\"paginate_button \"><a class=\"aPage-middle\" href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
			if (i < begin) {
				sb.append("<li class=\"paginate_button disabled\"><a class=\"aPage-middle\" href=\"javascript:\">...</a></li>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				sb.append("<li class=\"paginate_button page-right-active\"><a class=\"aPage-middle\" href=\"javascript:\">" + (i + 1 - first)
						+ "</a></li>\n");
			} else {
				sb.append("<li class=\"paginate_button \"><a class=\"aPage-middle\" href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
		}

		if (last - end > slider) {
			sb.append("<li class=\"paginate_button disabled\"><a class=\"aPage-middle\" href=\"javascript:\">...</a></li>\n");
			end = last - slider;
		}

		for (int i = end + 1; i <= last; i++) {
			sb.append("<li class=\"paginate_button \"><a class=\"aPage-middle\" href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
					+ (i + 1 - first) + "</a></li>\n");
		}

		if (pageNo == last) {
			sb.append("<li class=\"paginate_button next  \"><a class=\"aPage-middle aPage\" style=\"cursor: not-allowed;\" href=\"javascript:\"><i class=\"iconfont icon-xiangyou\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button next  \"><a class=\"aPage-right  aPage\" style=\"cursor: not-allowed;\" href=\"javascript:\"><i class=\"iconfont icon-zuiyou\"></i></a></li>\n");
		} else {
			sb.append("<li class=\"paginate_button next\"><a class=\"aPage-middle aPage\" href=\"javascript:\" onclick=\""+funcName+"("+next+","+pageSize+",'"+funcParam+"');\">"
					+ "<i class=\"iconfont icon-xiangyou\"></i></a></li>\n");
			sb.append("<li class=\"paginate_button next\"><a class=\"aPage-right aPage\" href=\"javascript:\" onclick=\""+funcName+"("+last+","+pageSize+",'"+funcParam+"');\">"
					+ "<i class=\"iconfont icon-zuiyou\"></i></a></li>\n");
		}
		
        sb.append("</ul></div></div>");
		
		return sb.toString();
	}
	
	/**
	 * 是否是选中状态
	 * @param pageNo
	 * @param selectedPageNo
	 * @return
	 */
	protected String getSelected(int pageNo, int selectedPageNo){
		if(pageNo == selectedPageNo){
			return "page-a-active";
		}else{
			return "";
		}
	}
	
	@Override
	public String toString() {
		return "PageInfo [pageNo=" + pageNo + ", pageSize=" + pageSize + ", total=" + total + ", pages=" + pages
				+ ", list=" + list + ", isFirstPage=" + isFirstPage + ", isLastPage=" + isLastPage + "]";
	}

}
