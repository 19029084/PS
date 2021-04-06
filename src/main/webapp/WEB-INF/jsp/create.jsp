<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="com.synopsys.coverity.CoverityEndpoint"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%
request.setCharacterEncoding("utf-8");
CoverityEndpoint endpoint = (CoverityEndpoint)session.getAttribute("endpoint");
%>

<!DOCTYPE html>
<html>
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
      display: -webkit-box;
      display: -webkit-flex;
      display: -ms-flexbox;
      display: flex;
      -webkit-box-align: center;
      -webkit-align-items: center;
      -ms-flex-align: center;
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
      background-image: url('/static/assets/img/logo_coverity.png');
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

    content {
      padding: 24px;
      position: relative;
      display: block;
      margin: auto;
      width: 800px;
    }

    form {
      display:block;
      margin: auto;
      margin-top: 40px;
      padding: 24px 16px 40px 16px;
      border: 1px solid #f0f0f0;
      border-radius: 4px;
      position: relative;
      box-shadow: 0px 6px 10px 0px rgba(0, 0, 0, 0.1);
    }

    .input {
      margin: 12px 0;
      display: -webkit-box;
      display: -webkit-flex;
      display: -ms-flexbox;
      display: flex;
      -webkit-box-align: center;
      -webkit-align-items: center;
      -ms-flex-align: center;
      align-items: center;
    }

    .input label {
      color: #666666;
      width: 144px;
      text-align: right;
      margin-right: 16px;
    }

    .input input {
      border: 1px solid #f0f0f0;
      border-radius: 4px;
      background: transparent;
      line-height: 28px;
      width: 280px;
      padding: 3px 12px;
      color: #333333;
    }

    .input-selector {
      display: none;
      position: absolute;
      width: 306px;;
      left: 176px;
      top: 73px;
      background: #ffffff;
      border-radius: 4px;
      box-shadow: 0px 6px 10px 0px rgba(0, 0, 0, 0.1);
    }

    .selector2 {
      top: 120px;
    }

    .input-selector .option {
      color: #666666;
      line-height: 28px;
      border-bottom: 1px solid #f0f0f0;
      padding: 0 12px;
      font-size: 14px;
    }

    .input-selector .option:hover {
      background: #80539c;
      color: #ffffff;
      cursor: pointer;
    }

    .action {
      padding-top: 16px;
      padding-left: 365px;
    }

    .action button {
      border: none;
      color: #333333;
      padding: 6px 24px;
      border-radius: 4px;
      cursor: pointer;
    }
    .action button:hover {
      background: #80539c;
      color: #ffffff;
    }
	</style>
</head>

<SCRIPT type="text/javascript">
 var projectArray = new Array();
 var streamArray = new Array();
 
 <%
 if(endpoint!=null)
 {
			  Map<String,List<String> > projectStreams = endpoint.getProjectStreams();
			  for (String key : projectStreams.keySet()) 
			  {
				  List<String> streams = (List<String>)projectStreams.get(key);
				  for(int i=0;i<streams.size();i++)
				  {
					  %>
						streamArray[streamArray.length] = new Array("<%=key%>","<%=streams.get(i)%>");
					  <%
				  }
 %>
				  projectArray[projectArray.length] = new Array("<%=key%>");
 <%
			  }
 }
 %>


	function changeStreams(project,stream){
        var objProject = document.getElementById(project);
        var objStream = document.getElementById(stream);
        var i;
        var itemArray = null;
        if(objProject.value.length > 0){
             itemArray = new Array();
             for(i=0;i<streamArray.length;i++){
                if(streamArray[i][0]==objProject.value){
                    itemArray[itemArray.length]=new Array(streamArray[i][1]);
                }
             }
        }
        for(i = objStream.options.length ; i >= 0 ; i--){
                objStream.options[i] = null;
        }
        objStream.options[0] = new Option("请选择数据流");
        objStream.options[0].value = "";
  
        if(itemArray != null){
                for(i = 0 ; i < itemArray.length; i++){
                        objStream.options[i+1] = new Option(itemArray[i][0]);
                        if(itemArray[i][0] != null){
                           objStream.options[i+1].value = itemArray[i][0];
                        }
                }
        }
    } 

     
      
    </SCRIPT>



<body>
<header class="cx-nav">
  <div class="navbar">
    <div class="nav-brand">
      <a href="">
        <div class="logo"></div>
      </a>
      <div class="logo-name">Coverity V2021.03</div>
    </div>
    <div class="nav-item ">
	<a href="/display?action=list">
      <div>
        <img src="./assets/img/list.png" />
      </div>
	
      <div>扫描项目列表</div>
	  </a>
    </div>
    <div class="nav-item active">
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
  <form action="/createTask" enctype="multipart/form-data" method="post" >
  
     <div class="input">
      <label>工作目录</label>
      <select name="workspace">
	  <option value="">请选择工作目录</option>
	  <%
		List<String> workspaces = (List<String>)request.getAttribute("workspaces");
		if(workspaces != null)
		{
			for(int i=0;i< workspaces.size();i++)
			{
		  
	  %>
			 <option value="<%=workspaces.get(i)%>"> <%=workspaces.get(i)%></option>
	  <%
			}			
		}
	  
	  %>
	  
	  </select>
    </div>
	
    <div class="input">
		<label>项目名称</label>
		<select name="project" id="project" onchange="changeStreams('project','stream');">

		</select>
	</div>
    <div class="input">
		<label>数据流 (Stream)</label>
		<select name="stream" id="stream">
		</select>
    </div>

    <div class="input">
      <label>项目源码</label>
      <input name="source" type="file" />
    </div>
	
	<div class="input">
		<input name="endpoint" value=<%=endpoint==null?"0":endpoint.getId()%> type="hidden"/>
	</div>

    <div class="action">
      <button type="submit">开始扫描</button>
    </div>
	

	
  </form>

</content>

<footer>
  ©2021 Synopsys, Inc. All Rights Reserved
</footer>
</body>
<script src="./assets/lib/jquery.min.js" ></script>
<script>
  $(function (){
	var objProject = document.getElementById('project');
	objProject.options[0] = new Option("请选择项目");
    objProject.options[0].value = "";
	
	var objStream = document.getElementById('stream');
	objStream.options[0] = new Option("请选择数据流");
    objStream.options[0].value = "";
	
	for(i=0;i<projectArray.length;i++)
	{
		objProject.options[i+1]=new Option(projectArray[i]);
		objProject.options[i+1].value= projectArray[i];
	}	  

  })
</script>

</html>