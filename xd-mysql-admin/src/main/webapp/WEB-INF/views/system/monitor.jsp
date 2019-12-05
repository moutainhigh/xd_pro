<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ include file="/WEB-INF/views/include/codemirror.jsp"%>

<script type="text/javascript" src="${ctx}/static/js/echarts.js"></script>
<script type="text/javascript" src="${ctx}/static/js/codemirror.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/chart/line.js"></script>
<script type="text/javascript" src="${ctx}/static/js/chart/bar.js"></script>
 
<style>

.main {
    overflow:hidden;
    height:250px;
    padding:0px;
    margin:10px;
    border: 1px solid #e3e3e3;
    -webkit-border-radius: 4px;
       -moz-border-radius: 4px;
            border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
       -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
}

.mainChar {
    overflow:hidden;
    padding :0px;
    margin:10px;
    border: 1px solid #e3e3e3;
    -webkit-border-radius: 4px;
       -moz-border-radius: 4px;
            border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
       -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
}

.main3 {
    overflow:hidden;
    height:250px;
    width:48%;
    float:left;
    padding:0px;
    margin:10px;
    border: 1px solid #e3e3e3;
    -webkit-border-radius: 4px;
       -moz-border-radius: 4px;
            border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
       -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
}
</style>
</head>
<body>
 <div id="tb2" style="height:auto">
 <input type="hidden" id="dataSourceId"  value="${dataSourceId}" >
                         <div class="panel-header panel-header-noborder  " 	style="padding: 5px; height: auto">
                         
	                       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-table-gear" plain="true"   onclick="monitorItem()">详细状态参数</a>
	                       <span class="toolbar-item dialog-tool-separator"></span>
                         
	                       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-arrow-refresh" plain="true" onclick="refresh()">刷新</a>
	                       <span class="toolbar-item dialog-tool-separator"></span>
	                       
                            <input type="checkbox" checked id="isAuto" >自动刷新  </input>
	                        <span class="toolbar-item dialog-tool-separator"></span>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain="true" title="刷新间隔10秒"></a>  
                            <span class="toolbar-item dialog-tool-separator" ></span>
                             
                             <a href="javascript:void(0)" class="easyui-linkbutton" plain="true"  >【${databaseType}】${name} ${ip}:${port} </a>
                            <span class="toolbar-item dialog-tool-separator"></span>                                            
                         </div> 
                       
  </div>

   <div>
   <div class ="mainChar" style="text-align:center"> 
     <div  style="margin:5px;padding:2px;height:auto;width:17%;border:1px solid #95B8E7;float:left; text-align:center">
        <span class="l-monitor-title-text">最大连接数 </span> <br>
        <span style="font-size:23px;color:#22BB22;" id="max_used_connections" title="max_used_connections" >0 </span> 
      </div>
      
      
      <div  style="margin:5px;padding:2px;height:auto;width:17%;border:1px solid #95B8E7;float:left; text-align:center">
          <span class="l-monitor-title-text">Uptime(day)</span> <br>
          <span style="font-size:23px;color:#22BB22;" id="Uptime">0 </span> 
     </div>
     
     <div  style="margin:5px;padding:2px;height:auto;width:17%;border:1px solid #95B8E7;float:left; text-align:center">
        <span class="l-monitor-title-text">当前连接数</span> <br> 
        <span style="font-size:23px;color:#22BB22;" id="Threads_connected" title="Threads_connected">0 </span> 
     </div>
     
     <div  style="margin:5px;padding:2px;height:auto;width:17%;border:1px solid #95B8E7;float:left; text-align:center" >
         <span class="l-monitor-title-text">running </span> <br>
         <span style="font-size:23px;color:#22BB22;" id="Threads_running" title="Threads_running">0 </span> 
     </div>
     <div  style="margin:5px;padding:2px;height:auto;width:17%;border:1px solid #95B8E7;float:left; text-align:center">
          <span class="l-monitor-title-text">Open_tables </span> <br>
          <span style="font-size:23px;color:#22BB22;" id="Open_tables">0 </span> 
     </div>
    </div>
  
     <div  id="graphic1" style="height:auto;width:auto;"> 
            <div id="main1" class="main"></div>
     </div> 
      
       <div  id="graphic2" style="height:auto;width:auto;"> 
            <div id="main2" class="main"></div>
      </div> 
          
      <div  id="graphic3" style="height:auto;width:auto;"> 
            <div id="main3" class="main"></div>
      </div>         
           
      <div  id="graphic4" style="height:auto;width:auto;"> 
            <div id="main4" class="main" style="display:none"></div>
            <div id="main5" class="main" style="display:none"></div>
      </div> 
           
    </div>
 
<script type="text/javascript">

var timeTicket
var lastData = 11;
var axisData;
var dataSourceId;
//var questions1=0;
//var questions_qps = 0;

$(function(){ 
     dataSourceId = $("#dataSourceId").val();
     clearInterval(timeTicket);
     timeTicket = setInterval(function (){
        mainAddData();
     }, 10000);
                    	
	  queryInfoItem();
	  
	  $("#isAuto").change(function() {
       if( $(this).is(':checked') ){
    	   
		   timeTicket = setInterval(function (){
              mainAddData();
            }, 10000); 
	   }else{
		    clearInterval(timeTicket); 
	   }
     }); 
});
     
function refresh(){
	  queryInfoItem();
}

function monitorItem(){
	 parent.window.mainpage.mainTabs.addModule( '详细状态参数' ,'${ctx}/system/permission/i/monitorItem/'+dataSourceId ,  'icon-berlin-statistics');
}
    
 //查询状态参数
 function queryInfoItem(){
	  $.ajax({
		type:'get',
		url:"${ctx}/system/permission/i/queryDatabaseStatus/"+ dataSourceId ,
		success: function(data){
			var status = data.status ;
			if(status == 'success' ){
				 $("#max_used_connections").html(data.Max_used_connections );
		         $("#Uptime").html( parseInt( data.Uptime /60/60/24 ) );
		         $("#Threads_connected").html( data.Threads_connected  );
		         $("#Threads_running").html(data.Threads_running  );
		         $("#Open_tables").html( data.Open_tables  ) ;
			}else{
				 parent.$.messager.show({ title : "提示",msg: data.mess , position: "Center" });
			}
		   
		}
      });
 }
 
  var myChart;
  var myChart2 ;
  var myChart3 ;
  
   // 使用  
  require(  
        [  
            'echarts',  
            'echarts/chart/line',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载  
        ],  
        function (ec) {  
            // 基于准备好的dom，初始化echarts图表  
            myChart = ec.init(document.getElementById('main1'));   
            var option = {
             title : {
               text: 'sent or received kbps',
               subtext: ''
             },
             tooltip : {
               trigger: 'axis'
              },
              legend: {
                 data:['Bytes_received','Bytes_sent']
              },
            toolbox: {
               show : true,
               feature : {
                  mark : {show: false},
                  dataView : {show: true, readOnly: true},
                  magicType : {show: true, type: ['line', 'bar']},
                  restore : {show: true},
                  saveAsImage : {show: true}
                }
             }, 
            calculable : true,
            xAxis : [
             {
               type : 'category',
               data :[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
             }
            ],
            yAxis : [
            {
                type : 'value',
                data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
             }
            ],
           series : [
           {
               name:'Bytes_received',
               type:'line',
               data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
            },
           {
               name:'Bytes_sent',
               type:'line',
               data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
            }
           ]
        };
     
        // 为echarts对象加载数据   
            myChart.setOption(option);   
        }  
   );  
   
   
  var begin_old=0;
  var commit_old=0;
  var delete_old=0;
  var insert_old=0;
  var rollback_old=0;
  var select_old=0;
  var update_old=0;
  
  var Bytes_received_old=0;
  var Bytes_sent_old=0;
   
  function mainAddData(){
	var datetime = new Date();
     axisData = datetime.getHours()+":" + datetime.getMinutes() +":" + datetime.getSeconds();
	$.ajax({
	    type : "get",	  
		url:"${ctx}/system/permission/i/queryDatabaseStatus/"+ dataSourceId ,
		dataType : "json",
		success:function(data) {
		  // myChart.hideLoading();
		 
		  
		  if(Bytes_received_old == 0){
			  Bytes_received_old = data.Bytes_received ;
		  }
		  if(Bytes_sent_old == 0){
			  Bytes_sent_old = data.Bytes_sent ;
		  }
		  
		  // 动态数据接口 addData
          myChart.addData([
            [
              0,        // 系列索引
           Math.abs(  parseInt( (data.Bytes_received - Bytes_received_old) /1000 ) ), // 新增数据
              false,     // 新增数据是否从队列头部插入
              false     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
            ],
            [
              1,        // 系列索引
            Math.abs( parseInt((data.Bytes_sent - Bytes_sent_old) /1000 )), // 新增数据
              false,    // 新增数据是否从队列头部插入
              false,    // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
              axisData  // 坐标轴标签, For example 18:10:12
            ]
         ]);
		  
		  
		  if(begin_old == 0){
			  begin_old = data.Com_begin;
		  }
		  if(commit_old == 0){
			  commit_old = data.Com_commit;
		  }
		  if(delete_old == 0){
			  delete_old = data.Com_delete;
		  }
		  if(insert_old == 0){
			  insert_old = data.Com_insert;
		  }
		  if(rollback_old == 0){
			  rollback_old = data.Com_rollback;
		  }
		  if(select_old == 0){
			  select_old = data.Com_select;
		  }
		  if(update_old == 0){
			  update_old = data.Com_update;
		  }
		  
		  myChart2.addData([
            [
              0,        // 系列索引
            Math.abs( (data.Com_begin - begin_old)) /10 , // 新增数据 begin
              false,    // 新增数据是否从队列头部插入
              false     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
            ],
            [
              1,        
              Math.abs( ( data.Com_commit - commit_old )) /10, // 新增数据 commit
              false,     
              false   
            ],
            [
              2,        
              Math.abs( (data.Com_delete - delete_old)) / 10 , // 新增数据delete
              false,     
              false    
            ],
            [
              3,        
              Math.abs((data.Com_insert - insert_old)) / 10  , // 新增数据insert
              false,     
              false    
            ],
            [
              4,        
               Math.abs(( data.Com_rollback - rollback_old)) / 10, // 新增数据rollback
              false,     
              false    
            ],
            [
              5,        
               Math.abs(( data.Com_select - select_old )) / 10 ,  // 新增数据select
              false,     
              false     
            ],
            [
              6,        
               Math.abs( (data.Com_update - update_old)) / 10, // 新增数据update
              false,     
              false,
              axisData  // 坐标轴标签, For example 18:10:12
            ] 
            
          ]);
		  
		 // alert( data.Com_select +" " + select_old );
		  
		  begin_old  = data.Com_begin;
		  commit_old = data.Com_commit;
		  delete_old = data.Com_delete;
	      insert_old = data.Com_insert;
	      rollback_old=data.Com_rollback;
		  select_old = data.Com_select;
		  update_old = data.Com_update ;
		  
		  Bytes_received_old = data.Bytes_received ;
		  Bytes_sent_old = data.Bytes_sent ; ;
		  
	      myChart3.addData([
               [
                0,        // 系列索引
               data.Threads_connected, // 新增数据
                false,    // 新增数据是否从队列头部插入
                false,    // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                axisData  // 坐标轴标签, For example 18:10:12
               ]
           ]);
	      
		  $("#Questions").html( data.Questions );
		  $("#Connections").html(data.Connections  );
		  $("#Threads_connected").html( data.Threads_connected  );
		  $("#Threads_running").html(data.Threads_running  );
		  $("#Open_tables").html( data.Open_tables  ) ;
		   
		},
		error: function(errorMsg) {
		  //  alert("不好意思，图表请求数据失败啦！");
		}
	});
  }

   // 使用  
  require(  
        [  
            'echarts',  
            'echarts/chart/line',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载  
        ],  
      function (ec) {  
       // 基于准备好的dom，初始化echarts图表  
       myChart2 = ec.init(document.getElementById('main2'));   
          
        var option = {  
             title : {  
             text: 'QPS(事务数/秒)',  
             subtext: ''  
      },  
      tooltip : {  
        trigger: 'axis'  
      },  
      legend: {  
        data:['begin','commit','delete','insert','rollback','select','update']  
      },  
      toolbox: {  
        show : true,  
        feature : {  
            mark : {show: false},  
            dataView : {show: true, readOnly: false},  
            magicType: {show: true, type: ['line', 'bar']},  
            restore : {show: true},  
            saveAsImage : {show: true}  
        }  
      },  
      calculable : true,  
      xAxis : [  
        {  
        	type : 'category',  
            data : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        }  
      ],  
      yAxis : [  
        {  
            type : 'value',  
            data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        }  
      ],  
      
      
      series : [  
        {  
               name:'begin',  
               type:'line',  
               data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        },
        {
               name:'commit',
               type:'line',
               data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        },
        {
               name:'delete',
               type:'line',
               data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        },
        {
               name:'insert',
               type:'line',
               data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        },
        {
               name:'rollback',
               type:'line',
               data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        },
        {
               name:'select',
               type:'line',
               data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        },
        {
               name:'update',
               type:'line',
               data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        }
        
       ]  
     };  
          // 为echarts对象加载数据   
           myChart2.setOption(option);   
        }  
   ); 
    
   // 使用  
  require(  
        [  
            'echarts',  
            'echarts/chart/line',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载  
        ],  
        function (ec) {  
            // 基于准备好的dom，初始化echarts图表  
            myChart3 = ec.init(document.getElementById('main3'));   
          
            var option = {  
             title : {  
             text: 'Threads_connected',  
             subtext: ''  
           },  
      tooltip : {  
        trigger: 'axis'  
      },  
      legend: {  
        data:['Threads_connected']  
      },  
      toolbox: {  
        show:true,  
        feature : {  
            mark : {show: false},  
            dataView : {show: true, readOnly: false},  
            magicType: {show: true, type: ['line', 'bar']},  
            restore : {show: true},  
            saveAsImage : {show: true}  
        }  
      },  
      calculable:true,  
      xAxis : [  
        {  
        	type:'category',  
            data :[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        }  
      ],  
      yAxis : [  
        {  
            type:'value',  
            data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0] 
        }  
      ],  
      series : [  
        {  
            name:'Threads_connected',  
            type:'line',  
            data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        }  
       ]  
     };  
            // 为echarts对象加载数据   
            myChart3.setOption(option);   
        }  
   ); 
    
 // 使用  
  require(  
        [  
            'echarts',  
            'echarts/chart/line',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载  
        ],  
        function (ec) {  
            // 基于准备好的dom，初始化echarts图表  
            var myChart = ec.init(document.getElementById('main4'));   
          
     var option = {  
      title : {  
        text: '内存占用率',  
        subtext: ''  
      },  
      tooltip : {  
        trigger: 'axis'  
      },  
      legend: {  
        data:['2015年']  
      },  
      toolbox: {  
        show : true,  
        feature : {  
            mark : {show: false},  
            dataView : {show: true, readOnly: false},  
            magicType: {show: true, type: ['line', 'bar']},  
            restore : {show: true},  
            saveAsImage : {show: true}  
        }  
      },  
      calculable : true,  
      xAxis : [  
        {  
        	type : 'category',  
            data : ['1月','2月','3月','4月','5月','总'] 
        }  
      ],  
      yAxis : [  
        {  
            type : 'value',  
            boundaryGap : [0, 0.01] 
        }  
      ],  
      series : [  
        {  
            name:'2015年',  
            type:'bar',  
            data:[48203, 53489, 119034, 184970, 231744, 630230]  
        }  
       ]  
     };  
  
        // 为echarts对象加载数据   
            myChart.setOption(option);   
        }  
   ); 
 
  // 使用  
  require(  
        [  
            'echarts',  
            'echarts/chart/line',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载  
        ],  
        function (ec) {  
            // 基于准备好的dom，初始化echarts图表  
            var myChart = ec.init(document.getElementById('main5'));   
          
     var option = {  
      title : {  
        text: '内存占用率',  
        subtext: ''  
      },  
      tooltip : {  
        trigger: 'axis'  
      },  
      legend: {  
        data:['2015年']  
      },  
      toolbox: {  
        show : true,  
        feature : {  
            mark : {show: false },  
            dataView : {show: true, readOnly: false},  
            magicType: {show: true, type: ['line', 'bar']},  
            restore : {show: true},  
            saveAsImage : {show: true}  
        }  
      },  
      calculable : true,  
      xAxis : [  
        {  
            type : 'category',  
            data : ['1月','2月','3月','4月','5月','总'] 
        }  
      ],  
      yAxis : [  
        {  
        	type : 'value',  
            boundaryGap : [0, 0.01]  
             
        }  
      ],  
      series : [  
        {  
            name:'2015年',  
            type:'bar',  
            data:[48203, 53489, 119034, 184970, 231744, 630230]  
        }  
       ]  
     };  
  
        // 为echarts对象加载数据   
            myChart.setOption(option);   
        }  
   );              
      
   
</script>
 <%--
   <script type="text/javascript" src="${ctx}/static/js/echartsExample.js"></script>
 --%>
</body>
</html>