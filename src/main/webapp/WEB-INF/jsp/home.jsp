<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="com.synopsys.entity.PSTask"%>
<%@ page import="com.synopsys.util.PSUtil"%>
<%@ page import="com.github.pagehelper.PageInfo"%>
<%@ page import="java.util.List"%>




<%
request.setCharacterEncoding("utf-8");

PageInfo<PSTask> pageInfo = (PageInfo<PSTask>) request.getAttribute("pageInfo");
%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Synopsys</title>
  <link rel="icon" href="https://www.synopsys.com/etc/designs/synopsys/favicon.ico">
  <style>
    body {
      margin: 0;
      min-height: 520px;
    }

    .navbar {
      padding: 0 12px;
      border-bottom: 1px solid #f0f0f0;
      background: #f0f0f4;
      display: flex;
      align-items: center;
    }

    .nav-brand,
    .nav-item {
      display: inline-block;
      color: #666666;
      padding: 12px 0;
      width: 140px;
      text-align: center;
    }

    .nav-item {
      border-left: 1px solid #999999;
      cursor: pointer;
    }

    .nav-item:hover,
    .nav-item.active {
      background: #80539c;
      color: #ffffff;
    }

    .nav-item img {
      width: 24px;
      height: 24px;
    }

    .nav-item:last-of-type {
      border-right: 1px solid #999999;
    }

    .logo {
      background-image:url('/static/assets/img/logo_coverity.png');
      background-position: 50% 50%;
      background-size: cover;
      background-repeat: no-repeat;
      height: 25px;
      width: 115px;
      margin-left: 15px;
    }

    .logo-name {
      font-size: 13px;
      color: #80539c;
    }

    content {
      padding: 24px;
      margin-top: 24px;

      position: relative;
      display: block;
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    table tr {
      cursor: pointer;
    }

    table tr:hover {
      background: #80539c;
    }

    table th,
    table td {
      border: .5px solid #cccccc;
      
      text-align: center;
      padding: 10px;
	  height: 10px;
    }

    table th {
      background: #80539c;
      color: #ffffff;
    }

    table tr:hover td {
      color: #ffffff;
    }
    table td {
      color: #333333;
    }

    footer {
      text-align: center;
      width: 100%;
      position: fixed;
      bottom: 0;

      height: 48px;
      color: #999999;
    }

    @media (max-height: 520px) {
      footer {
        position: relative;
        margin-top: 40px;
      }
    }

    .pagination {
      display: -webkit-box;
      display: -webkit-flex;
      display: -ms-flexbox;
      display: flex;
      -ms-flex-pack: center;
      justify-content: center;
      margin-top: 24px
    }

    .pagination a {
      text-align: center;
      height: 30px;
      width: 30px;
      line-height: 30px;
      color: #333;
      cursor: pointer;
      display: block
    }

    .pagination a, .pagination a:hover {
      text-decoration: none
    }

    .pagination .next-a, .pagination .prive-a {
      width: 45px
    }

    .pagination .first:hover, .pagination .last:hover, .pagination .next:hover, .pagination .prive:hover {
      border: 1px solid #80539c;
      color: #80539c
    }

    .pagination .first:hover a, .pagination .last:hover a, .pagination .next:hover a, .pagination .prive:hover a {
      color: #80539c
    }

    .pagination .first a, .pagination .last a {
      width: auto
    }

    .pagination .first, .pagination .last, .pagination .next, .pagination .prive {
      font-size: 14px;
      border: 1px solid #ededed;
      text-align: center;
      height: 30px;
      width: 45px;
      line-height: 30px;
      cursor: pointer;
      float: left
    }

    .pagination .pagemath {
      color: #80539c;
      font-size: 14px
    }

    .pagination .maxpage {
      font-size: 14px
    }

    .pagination .first {
      margin-right: 10px
    }

    .pagination .last {
      margin-left: 10px
    }

    .pagination .center {
      background: #80539c;
      color: #fff;
      border: 1px solid #80539c
    }

    .pagination .center a {
      color: #fff !important
    }

    .pagination .href {
      display: flex;
      padding: 0 10px;
      float: left
    }

    .pagination .href .page1, .pagination .href .page2, .pagination .href .page3, .pagination .href .page4, .pagination .href .page5 {
      border: 1px solid #ededed;
      margin-left: 5px
    }

    .pagination .href .href-1, .pagination .href .href-2, .pagination .href .href-3, .pagination .href .href-4, .pagination .href .href-5 {
      border: 1px solid #ededed;
      margin-left: 2px
    }

    .pagination .href .href-1:hover, .pagination .href .href-2:hover, .pagination .href .href-3:hover, .pagination .href .href-4:hover, .pagination .href .href-5:hover {
      border: 1px solid #80539c
    }

    .pagination .href .href-1:hover a, .pagination .href .href-2:hover a, .pagination .href .href-3:hover a, .pagination .href .href-4:hover a, .pagination .href .href-5:hover a {
      color: #80539c
    }

  </style>
</head>
<body>
<header class="cx-nav">
  <div class="navbar">
    <div class="nav-brand">
      <a href="">
        <div class="logo"></div>
      </a>
      <div class="logo-name">Coverity V2021.03</div>
    </div>
    <div class="nav-item active">
	<a href="/display?action=list">
      <div>
        <img src="./assets/img/list.png" />
      </div>
	
      <div>扫描项目列表</div>
	  </a>
    </div>
    <div class="nav-item">
      <a href="/display?action=create">
	  <div>
        <img src="./assets/img/scan.png" />
      </div>
	  
      <div>新建扫描项目</div>
	  </a>
    </div>
  </div>
</header>

<content>
  <table cellpadding="0" cellspacing="0">
    <tr>
      <th>序号</th>
      <th>工作目录</th>
      <th>项目名称</th>
	  <th>数据流</th>
      <th>项目状态</th>
      <th>详细信息</th>
	  <th></th>
    </tr>

<%
	List<PSTask> tasks = pageInfo.getList();
	
	for(int i=0;i<tasks.size();i++)
	{
		
		PSTask tsk = tasks.get(i);
		
%>
		 <tr>
			<td><%=tsk.getId()%> </td>
			<td><%=tsk.getWorkspace()%></td>
			<td><%=tsk.getProject()%></td>
			<td><%=tsk.getStream()%></td>
			<td><%=tsk.getStatus()%></td>
			<%if("Done".equalsIgnoreCase(tsk.getStatus())){%>
				<td>				
						<a href="/download/<%=tsk.getWorkspace()%>/<%=PSUtil.logFile(tsk.getUniqueKey())%>" >details</a>
				</td>		
				<td>
					<a href="/task/<%=tsk.getId()%>?pageNo=<%=pageInfo.getPageNum()%>" >
						<img src="/static/assets/img/trash.png" />
					</a>
				</td>		
			<%}else{%>
				<td>-</td>
				<td>-</td>
			<%}%>
			
		</tr>
<%
	}
		
	for(int i=tasks.size();i<pageInfo.getPageSize();i++)
	{
%>
			 <tr>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>
				<td> </td>	
			</tr>
<%
	}
%>
    </table>
    
  <div class='pagination'>
    <a href="display?action=list&&pageNo=1" class="first">首页</a>
    <div> 
		<a href="display?action=list&&pageNo=<%=pageInfo.getPrePage()==0?1:pageInfo.getPrePage()%>" class="prive">上一页</a>
	</div>
    <div class="href">
		<%
			int [] pageNums = pageInfo.getNavigatepageNums();
			
			for(int i=0;i<pageNums.length;i++)
			{
		%>
			<a href="display?action=list&&pageNo=<%=pageNums[i]%>"
			   class="href-1 <%=pageNums[i]==pageInfo.getPageNum()?"center":""%>"> <%=pageNums[i]%></a>
		<%
			}
		%>
    </div>
   
    <div>
		<a href="display?action=list&&pageNo=<%=pageInfo.getNextPage()==0?pageInfo.getPages():pageInfo.getNextPage()%>" class="next">下一页 </a> 
	</div>
    
    <a href="display?action=list&&pageNo=<%=pageInfo.getPages()%>" class="last">末页</a>
  </div>

</content>

<footer>
  ©2021 Synopsys, Inc. All Rights Reserved
</footer>
</body>

</html>