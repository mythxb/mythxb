package com.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一分页方法
 * 
 * @author myth
 *
 */
public class Paging<T> {

	// 分页
	public List<T> getPage(Integer start, Integer end, List<T> list) {
		List<T> result = new ArrayList<T>();
		Integer total = list.size();
		if (start <= total && total <= end) {
			for (int i = start; i <= list.size(); i++) {
				result.add(list.get(i - 1));
			}
		} else if (start <= total && end <= total) {
			for (int i = start; i <= end; i++) {
				result.add(list.get(i - 1));
			}
		}
		return result;
	}

	//分页
	public List<T> getPages(Integer page, Integer pagerSize, List<T> list) {
		List<T> result = new ArrayList<T>();
		Integer start = ((page - 1) * pagerSize)+1;
		Integer end = start + pagerSize - 1;
		Integer total = list.size();
		if (start <= total && total <= end) {
			for (int i = start; i <= list.size(); i++) {
				result.add(list.get(i - 1));
			}
		} else if (start <= total && end <= total) {
			for (int i = start; i <= end; i++) {
				result.add(list.get(i - 1));
			}
		}
		return result;
	}



}
