package com.ibrasoft.storeStackProd.response;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class AgGridResponse {

	private List<Object> content;
	private Pageable pageable; 
    private boolean last;
    private long totalElements;
    private long totalPages;
	private long size;
	private long number; 
    private Sort sort;
    private boolean first;
    private long numberOfElements; 
    private boolean empty;

	public AgGridResponse (){}

	public AgGridResponse(Pageable pageable, boolean last,
			Integer totalElements, Integer totalPages, Integer size, Integer number, Sort sort, boolean first,
			Integer numberOfElements, boolean empty) {
		this.pageable = pageable;
		this.last = last;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.size = size;
		this.number = number;
		this.sort = sort;
		this.first = first;
		this.numberOfElements = numberOfElements;
		this.empty = empty;
	}

	public AgGridResponse(Page<?> page) {
		this.pageable = page.getPageable();
		this.last = page.isLast();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.size = page.getSize();
		this.number = page.getNumber();
		this.sort = page.getSort();
		this.first = page.isFirst();
		this.numberOfElements = page.getNumberOfElements();
		this.empty = page.isEmpty();
	}

	public Pageable getPageable() {
		return pageable;
	}
	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}

	public List<Object> getContent() {
		return content;
	}

	public void setContent(List<Object> content) {
		this.content = content;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public long getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(long numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
}
