package com.bool.jutils.express.entity;

import java.util.List;

/**
 * 快递结果
 * @author 18365
 *
 */
public class ExpressResult {

	private String status;
	private String msg;
	private ProcessResult result;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ProcessResult getResult() {
		return result;
	}

	public void setResult(ProcessResult result) {
		this.result = result;
	}

	
	/**
	 * 快递根节点数据
	 * @author 18365
	 *
	 */
	public static class ProcessResult{
		
		private String number;
		private String type;
		private List<CatchList> list;
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public List<CatchList> getList() {
			return list;
		}
		public void setList(List<CatchList> list) {
			this.list = list;
		}

	}
	
	/**
	 * 快递追踪信息
	 * @author 18365
	 *
	 */
	public static class CatchList{
		
		private String time;
		private String status;
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
		
	}
}
