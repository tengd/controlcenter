package com.evisible.os.controlcenter.model;

/**
 * <p>封装</p>
 * @author TengDong
 *@Date 20160401
 */
public class PageUI {
		private int value;
		private int secondValue;
		private int pageNo;
		private int totalNum;//总条数
		private int totalPage;//总页数
		
		/**
		 * @param page 页号
		 * @param rows 条数
		 */
		public PageUI(int page, int rows){
			this.pageNo = page;
			this.value=(page-1)*rows;
			this.secondValue=rows;
		}
		
		public int getTotalNum() {
			return totalNum;
		}
		
		public void setTotalNum(int totalNum) {
			this.totalNum = totalNum;
			if(secondValue == 0){
				secondValue = 10;
			}
			if(totalNum%secondValue==0){
				totalPage=totalNum/secondValue;
			}else{
				totalPage=totalNum/secondValue+1;
			}
			if (value > totalPage)
			{
				value = totalPage;
			}
		}
		
		
		public int getTotalPage() {
			return totalPage;
		}

		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}

		/**
		 * @return the value
		 */
		public int getValue() {
			return value;
		}
	
		/**
		 * @return the secondValue
		 */
		public int getSecondValue() {
			return secondValue;
		}

		public int getPageNo() {
			return pageNo;
		}
		public void setPageNo(int pageNo) {
			this.pageNo = pageNo;
		}
}
