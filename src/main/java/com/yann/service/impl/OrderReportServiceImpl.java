package com.yann.service.impl;

import com.yann.dao.ReportDao;
import com.yann.dto.echarts.Option;
import com.yann.dto.echarts.Serie;
import com.yann.service.OrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OrderReportServiceImpl implements OrderReportService {

	@Autowired
	private ReportDao reportDao;
	
	public Option count() {
		Option option = new Option();
		List<Map<String, String>> list = reportDao.countOrder();
		// 类别
		Set<String> categoryNameSet = new TreeSet<String>();
		// 类别+时间为KEY，数量为VALUE
		Map<String,Long> countMap = new HashMap<String,Long>();
		for(Map<String, String> map : list) {
			categoryNameSet.add(map.get("categoryName"));
			countMap.put(map.get("categoryName") + map.get("hour"), Long.valueOf(map.get("count")));
		}
		// 设置参数中线条的分类
		option.getLegend().setData(new ArrayList<String>(categoryNameSet));
		// 设置参数的X轴坐标
		List<String> hours = new ArrayList<String>();
		for(int i = 0; i <= 23; i++) {
			hours.add(String.format("%02d", i));
		}
		option.getxAxis().setData(hours);
		// 设置线条的名称和数值
		for(String categoryName : option.getLegend().getData()) {
			Serie serie = new Serie();
			option.getSeries().add(serie);
			serie.setName(categoryName);
			serie.setType("line");
			for(String hour : hours) {
				serie.getData().add(countMap.get(categoryName + hour) == null ? 0 : countMap.get(categoryName + hour));
			}
		}
		return option;
	}
}