package es.rest.controller.json;

public class ListParams {

	private String orderBy;
	private String orderByType;
	private Integer limit;
	private Integer page;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(final String orderByType) {
		this.orderByType = orderByType;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(final Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page == null ? 1 : page;
	}

	public void setPage(final Integer page) {
		this.page = page;
	}
}