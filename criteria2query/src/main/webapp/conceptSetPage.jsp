<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>

<head>

<meta charset="utf-8">
<title>Criteria2Query</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="keywords" content="Clinical trial,">
<meta name="author" content="Chi Yuan, Chunhua Weng">
<meta name="robots" content="index,follow">

<!-- Site CSS -->
<!--  <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"> -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link
	href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="https://static.bootcss.com/www/assets/css/site.min.css?v5"
	rel="stylesheet">
<style>
.job-hot {
	position: absolute;
	color: #d9534f;
	right: 0;
	top: 15px;
}
.col-center-block {  
    float: none;  
    display: block;  
    margin-left: auto;  
    margin-right: auto;  
}  
.eliback {
	color: white;
	background-color: #1E90FF;
	position: relative;
}

.navback {
	background-color: #428bca;
	color: white;
}

.conceptsetbody {
	top: 25px;
}
</style>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<!-- Custom styles for this template -->
<link href="../css/dashboard.css" rel="stylesheet">
<link href="../css/ner.css" rel="stylesheet">
<link
	href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="https://static.bootcss.com/www/assets/css/site.min.css?v5"
	rel="stylesheet">
<link rel="stylesheet" href="../css/bootstrap-table.min.css">
<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="../js/ie-emulation-modes-warning.js"></script>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../js/ie10-viewport-bug-workaround.js"></script>
<script src="../js/bootstrap-table.min.js"></script>
<script type="text/javascript"
	src="http://malsup.github.io/min/jquery.blockUI.min.js"></script>
</head>
<body>

	<div class="navbar navbar-inverse navbar-fixed-top navback">
		<div class="container">
			<div class="navbar-header">
				<button class="navbar-toggle collapsed" type="button"
					data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand hidden-sm" href="<%=basePath%>ohdsi/ft2query"
					style="color: white"
					onclick="_hmt.push(['_trackEvent', 'navbar', 'click', 'navbar-mainpage'])">Criteria2Query</a>
			</div>
			<div class="navbar-collapse collapse" role="navigation">
				<ul class="nav navbar-nav">
				</ul>
			</div>
		</div>
	</div>
	<div class="container projects">
		<div class="page-header projects-header">
			<h4>ConceptSet Recommendation</h4>
		</div>
		<div class="row">


			<div id="candidates" class="col-sm-12 col-md-12 col-lg-12">
				<!-- <table id="conceptsettable"></table> -->
			</div>
			 <div class="projects-header" >
			<button type="button"  style="margin-top: 15px" id="generate" class="btn btn-primary glyphicon glyphicon-pencil ">GenerateJSON</button>
		</div>
		</div>
	</div>

	<footer class="footer ">
	<div class="container">
		<div class="row footer-top">
			<div class="col-sm-6 col-lg-6">

				<p>This website was developed by ChiYuan, Chunhua Weng</p>
				
			</div>

		</div>


	</div>
	</footer>
	<!-- Modal -->
		<div class="modal fade" id="resultModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Create New ConceptSet</h4> 	
					</div>
					<div class="modal-body">
					
						<div class="form-group">
        					<label  class="col-sm-3 control-label" style="margin-top: 5px">New ConceptSet Name</label>
									<div class="col-sm-4" >	
										<input type="text" class="form-control col-sm-4" id="newconceptname" name="newconceptname" placeholder="" >
									</div>
									<button type="button" id="searchnewkey" class="btn btn-primary pull-right">Search</button>
									<div class="col-sm-2 col-offset-1  pull-right" >
									<input type="text" class="form-control col-sm-2 " id="newkeyword" name="newkeyword" placeholder="Input Concept" >
									</div>	
									
									</div>
                         
                          	
					<table id="vocaburesult" class="table table-bordered" >
					</table>
					<center><label id="loadingtext">Data Searching...</label></center>
					</div>
					<div class="modal-footer">
						
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" id="createconceptset" class="btn btn-primary"  >Create</button>
					</div>
					</form>
				</div>
			</div>
		</div>
		<!--modal end-->
	<script type="text/javascript">
  var basePath = "<%=basePath%>";
  var concepturl="http://api.ohdsi.org/WebAPI/conceptset/";
  var count;
  var conceptsetname;
  var wholeindex;
  $(function() {
	  mapConceptSet();
	  $("#searchVB").click(function() {
		  searchvacobulary();
		});
	  $("#generate").click(function() {
		  generateJson();
	});
	  $('#searchnewkey').click(function() {
		  searchnewkey();
		});
	  $("#createconceptset").click(function() {
		  vdata= $("#vocaburesult").bootstrapTable('getAllSelections');
		  conceptsetname=$('#newconceptname').val();
		  createconceptset(vdata,conceptsetname);
	  });
	  })
	  function searchnewkey(){
		  var keyword=$('#newkeyword').val();
		  var keydomain=$("#conceptdomainindex"+wholeindex+"").html().trim();
		  $("#loadingtext").html('Data Searching...');
		  $("#loadingtext").show();
		  updateconcepttable(keyword,keydomain);
		  
	  }
	 function generateJson(){
	  var allconceptsets = [];
	  var tcount=0;
		for (var i = 0; i < count; i++) {
			tcount=0;
				zdata = $("#conceptsettable" + i + "").bootstrapTable('getAllSelections');
				entity=$("#conceptsetindex"+i+"").html().trim();
				for ( var o in zdata) {
					var row={}
					tcount=tcount+1;
					row.entity=entity;
					row.conceptsetid=zdata[o]['id'];	
					allconceptsets.push(row);	
				}
				if(tcount>1){
					alert('You only can choose one conceptset for '+entity);
					return;
				}
				else if(tcount==0){
					alert('Please choose at least one conceptset for '+entity);
					return;
				}
		}
		//alert(JSON.stringify(allconceptsets));
		$.ajax({
			type : 'POST',
			url : basePath + "nlpmethod/allconceptsets",
			data :{
				conceptsets:JSON.stringify(allconceptsets)},
			dataType : "json",
			success : function(data) {
				//alert('success!');
				window.location.href=basePath + "nlpmethod/jsonpage";
			}
		});
  	}
	function createconceptset(vdata,name){
	  var jsondata = [];
	  for ( var o in vdata) {
		  var row={}
		  row.conceptId=vdata[o]['CONCEPT_ID'];
		  if($("#exclude"+vdata[o]['CONCEPT_ID']).is(':checked'))
			{
			  row.isExcluded=1		  
			  }else{
				  row.isExcluded=0
			  }
			  if($("#descendants"+vdata[o]['CONCEPT_ID']).is(':checked')){
				  row.includeDescendants=1
			  }else{
				  row.includeDescendants=0
			  }
			  if($("#mapped"+vdata[o]['CONCEPT_ID']).is(':checked')){
				  row.includeMapped=1;
			  }else{
				  row.includeMapped=0;
			  }
		  
		  jsondata.push(row);
		  JSON.stringify(jsondata);
	  }
	  $.ajax({
			type : 'GET',
			url : concepturl+0+"/"+name+"/exists",
			data : {
			},
			dataType : "json",
			success : function(data) {
				if(data.length>0){
					alert('This conceptset name has been used, please try another one!');
					return
				}else{
					insert(name,jsondata);
				}
			}
		});
	  
	  }
	function refreshconceptset(){
		window.location.reload();
	}
	  function insert(conceptsetname,jsondata){
		  var senddata = {};
		  senddata.name=conceptsetname;
		  senddata.id=123213;
		  $.ajax({
				type : 'POST',
				url : concepturl,
				contentType: "application/json",
				data :JSON.stringify(senddata),
				dataType : "json",
				success : function(data) {
					 $.ajax({
							type : 'PUT',
							url : concepturl+data['id']+"/items",
							contentType: "application/json",
							data :JSON.stringify(jsondata),
							dataType : "json",
							success : function(data) {
								window.location.reload();
							}
						});
				}
			});
	  }
	  function toAtlas(index){
		  word=$("#conceptsetindex"+index+"").html().trim();
		  openNewWin("http://www.ohdsi.org/web/atlas/#/search/"+escape(word));
	  }
		function searchvacobulary() {
			var conceptname = $("#concept_name").val();
			alert(conceptname);
			$.ajax({
				type : 'POST',
				url : basePath + "nlpmethod/searchconcept",
				data : {
					'word' : conceptname
				},
				dataType : "json",
				success : function(data) {
					$('#vocaburesult').bootstrapTable({
						data : data["conceptlist"],
					});
				}
			});
		}
		function mapConceptSet() {
			$.blockUI({
				message : '<h3><img src="../img/gears.gif" /> Mapping...</h3>',
				css : {
					border : '1px solid khaki'
				}
			});
			$.ajax({
						type : 'GET',
						url : basePath + "nlpmethod/searchconceptsets",
						data : {},
						dataType : "json",
						success : function(data) {
							$(document).ajaxStop($.unblockUI);

							var nav1 = '';
							count = getJsonObjLength(data) / 3;
							conceptsetname = new Array(count);
							conceptdomain = new Array(count);
							var conceptsetcand = new Array(count);
							for ( var o in data) {
								if (o.indexOf('cstname') != -1) {
									a1 = o.substring(7);
									conceptsetname[a1] = data[o];

								} else if (o.indexOf('conceptset') != -1) {
									a2 = o.substring(10);
									conceptsetcand[a2] = data[o];
								} else if (o.indexOf('domain') != -1) {
									a3 = o.substring(6);
									conceptdomain[a3] = data[o];
								}
							}
							for (var i = 0; i < conceptsetname.length; i++) {
								var x=conceptsetname[i].replace(/\'/g, '&#39;');
								//alert(x); 
								nav1 += "<div class=\"page-header\"><h4> <label id=\"conceptsetindex"
										+i
										+"\">"
										+ conceptsetname[i]
										+ "</label><label id=\"conceptdomainindex"
										+i
										+"\" style=\"display: none;\" >"
										+conceptdomain[i]
										+"</label>"
										+"<button id=\"conceptsetadd"
										+ i
										+ "\"class=\"pull-right btn btn-success\" onclick=\"toAtlas('"
										+i
										+ "')\">Create New ConceptSet</button><button class=\"pull-right btn btn-primary \" onclick=\"refreshconceptset()\">Refresh</button></h4></div><table id=\"conceptsettable"+i+"\"></table> ";
							}

							$("#candidates").html(nav1);
							//document.getElementById("candidates").innerHTML=nav1.replace(/\'/g, '&#39;');
							for (var i = 0; i < conceptsetcand.length; i++) {
								$('#conceptsettable' + i).bootstrapTable({
									clickToSelect : true,
									data : conceptsetcand[i],
									columns : [ {
										checkbox : true
									}, {
										field : 'id',
										width : '10%',
										title : 'id'
									}, {
										field : 'name',
										width : '90%',
										title : 'title',
										formatter: 
									    function (value, row, index) {
											return "<a href=\"#\" onclick=openNewWin(\"http://www.ohdsi.org/web/atlas/#/conceptset/"+row['id']+"/details\")>"+value+"</a>"; 
													
									    }
									} ]

								});
							}

						}
					});
		}
		function openNewWin(url, title)  
		{  
		    window.open(url);  
		}  
		function getJsonObjLength(jsonObj) {
			var Length = 0;
			for ( var item in jsonObj) {
				Length++;
			}
			return Length;
		}
		function editInfo(index) {
			word=$("#conceptsetindex"+index+"").html().trim();
			domain=$("#conceptdomainindex"+index+"").html().trim();
			wholeindex=index;
			var searchword=$("#newconceptname").val(word);
			//initTable(word);
			$("#loadingtext").html('Data Searching...');
			$("#loadingtext").show();
			$("#newconceptname").val(word);
			updateconcepttable(word,domain);
		}
		function updateconcepttable(word,domain){
			$('#vocaburesult').bootstrapTable('destroy');
			$.ajax({
				type : 'POST',
				url : basePath + "nlpmethod/searchconcept",
				data : {
					'word' : word,
					'domain' : domain
				},
				dataType : "json",
				success : function(data) {
					$("#loadingtext").hide();
					$('#vocaburesult').bootstrapTable({
						striped : true, //表格显示条纹  
						pagination : true, //启动分页  
						pageSize : 5, //每页显示的记录数  
						pageNumber : 1, //当前第几页  
						pageList : [ 5, 10, 15, 20, 25 ], //记录数可选列表  
						data : data["conceptlist"],
						sidePagination : "client", //表示服务端请求  
						columns : [ {
							checkbox : true,
							align:"center",
							valign:"middle"
						}, {
							field : 'CONCEPT_ID',
							title : 'CONCEPT_ID',
							align:"center",
							valign:"middle"
						}, {
							field : 'CONCEPT_NAME',
							title : 'CONCEPT_NAME',
							align:"center",
							valign:"middle"
						}, {
							field : 'DOMAIN_ID',
							title : 'DOMAIN_ID',
							align:"center",
							valign:"middle"
						},
						{
							title:'DRC',
							field : 'DRC',
							sortable:"true",
							align:"center",
							valign:"middle",
							formatter: 
						            function (value, row, index) {
										if(value==-1){
							              return 'none'; 
										}else{
											return value;
										}
						             }
						},
						{
							field:"EXCLUDE",
							class:"tablebody",
							title:"Exclude",
							align:"center",
							valign:"middle",
							formatter: 
				            function (value, row, index) {
								if(index>=0){
					              return "<input id=exclude"+row['CONCEPT_ID']+" type=\"checkbox\">"; 
								}
				             }
						},
				        {
							field:"DESCENDANTS",
				           	class:"tablebody",
				           	title:"Descendants",
				           	align:"center",
				           	valign:"middle",
				           	formatter: 
						            function (value, row, index) {
										if(index>=0){
							              return "<input  id=descendants"+row['CONCEPT_ID']+" type=\"checkbox\">"; 
										}
						             }
						},
				        {
						    field:"MAPPED",
						    class:"tablebody",
						    title:"Mapped",
						    align:"center",
						    valign:"middle",
						    formatter: 
						            function (value, row, index) {
										if(index>=0){
											return "<input id=mapped"+row['CONCEPT_ID']+" type=\"checkbox\">"; 
										}
						             }
						}
						]

					});
				},
				error: function(e) { 
					$("#loadingtext").html('no match result! You can try other key words');
				} 
			});
		}
		function html_encode(str)   
		{   
		  var s = "";   
		  if (str.length == 0) return "";   
		  s = str.replace(/&/g, "&gt;");   
		  s = s.replace(/</g, "&lt;");   
		  s = s.replace(/>/g, "&gt;");   
		  s = s.replace(/ /g, "&nbsp;");   
		  s = s.replace(/\'/g, "&#39;");   
		  s = s.replace(/\"/g, "&quot;");   
		  s = s.replace(/\n/g, "<br>");   
		  return s;   
		}   

		
	</script>
</body>


</html>
