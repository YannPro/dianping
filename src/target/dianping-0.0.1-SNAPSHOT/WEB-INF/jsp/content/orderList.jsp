<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
		<title></title>
		<link rel="stylesheet" type="text/css" href="/css/all.css">
		<link rel="stylesheet" type="text/css" href="/css/pop.css">
		<link rel="stylesheet" type="text/css" href="/css/main.css">
		<script type="text/javascript" src="/js/common/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/js/content/orderList.js"></script>
	</head>
	<body style="background: #e1e9eb;">
		<form action="/comment/ad/search" id="mainForm" method="post">
			<input id="id" name="id" type="hidden">
			<input id="basePath" value="
			/comment" type="hidden">
			<input name="page.currentPage" id="currentPage" value="1" type="hidden">
			<div class="right">
				<div class="current">当前位置：<a href="#">内容管理</a> &gt; 订单查询</div>
				<div class="rightCont">
					<p class="g_title fix">订单列表</p>
					<table class="tab1">
						<tbody>
							<tr>
								<td width="80" align="right">手机号：</td>
								<td>
									<input name="title" id="title" value="" class="allInput" type="text">
								</td>
	                            <td style="text-align: right;" width="150">
	                            	<input class="tabSub" value="查询" onclick="search('1');" type="button">&nbsp;&nbsp;&nbsp;&nbsp;
	                            </td>
	       					</tr>
						</tbody>
					</table>
					<div class="zixun fix">
						<table class="tab2" width="100%">
							<tbody>
								<tr>
								    <th>序号</th>
								    <th>手机号</th>
								    <th>订单号</th>
								    <th>金额(元)</th>
								</tr>

								<c:forEach items="${list}" var="item" varStatus="s">
									<tr>
										<td>${s.index + 1}</td>
										<td>${item.title}</td>
										<td>${item.subtitle}</td>
										<td>${item.cityDic.name}</td>
										<td>${item.categoryDic.name}</td>
										<td>
											<a href="javascript:void(0);" onclick="modifyInit('${item.id}')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
												<%--
                                                                                            <a href="javascript:void(0);" onclick="remove('${item.id}')">删除</a>
                                                --%>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<!-- 分页 -->

						<t:page jsMethodName="search" page="${searchParam.page}"></t:page>



</body>
</html>