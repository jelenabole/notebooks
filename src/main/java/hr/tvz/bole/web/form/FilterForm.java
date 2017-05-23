package hr.tvz.bole.web.form;

import java.util.List;

public class FilterForm {

	List<String> orderByList;
	String object;

	String orderBy;
	String orderDirection;
	String searchBy;

	public FilterForm() {
	}

	public FilterForm(List<String> orderByList, String objectName) {
		this.orderByList = orderByList;
		this.object = objectName;
		this.orderDirection = "asc";
	}

	public List<String> getOrderByList() {
		return orderByList;
	}

	public void setOrderByList(List<String> orderByList) {
		this.orderByList = orderByList;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

}