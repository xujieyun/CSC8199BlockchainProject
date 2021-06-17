
// 获取弹窗元素

// 获取按钮元素
var modalBtn = document.getElementById("modalBtn");

// 获取关闭弹窗按钮元素
var closeBtn = document.getElementsByClassName("closeBtn")[0];

// 监听打开弹窗事件
modalBtn.addEventListener("click",openModal);

// 监听关闭弹窗事件
closeBtn.addEventListener("click",closeModal);

// 监听window关闭弹窗事件
window.addEventListener("click",outsideClick);

var loginUserName=GetQueryString("userName");
var loginrole=GetQueryString("role");
// $("#PleaseSelect").text()=loginrole;
//$("#WelcomeUser").attr("text",loginrole);

var UserId;

// 弹窗事件
function openModal () {
	var modal = document.getElementById("simpleModal");
	modal.style.display = "";


}

// 关闭弹框事件
function closeModal () {
	modal.style.display = "none";
}

// outsideClick
function outsideClick (e) {
	if(e.target == modal){
		modal.style.display = "none";
	}
}


function showTable() {
	var loginUserName=GetQueryString("userName");
	var obj = document.getElementById("WelcomeUser");
	obj.innerText= loginUserName;
	$.ajax({
		url:'/api/records/record',
		type:'GET',//GET 或POST
		async:true,//false是否异步 contentType: 'application/json',
		//dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
		data: JSON.stringify({}),
		contentType: 'application/json',
		success:function(data){
			var ui = document.getElementById("RepairTitle");//表格標題顯示/隱藏
			ui.style.display="";
			var loginrole=GetQueryString("role");
			var htmlstr="<body>";
			htmlstr+="<table border='1' id='tab3AlertSummary' class='table table-bordered table-hover compact display' role='grid' data-show-columns='true' width='100%'>";
			if(loginrole=="doctor"){
				htmlstr+="<tr>";
				htmlstr+="<td align='center'> Patient</td>";
				htmlstr+="<td align='center'> FirstName</td>";
				htmlstr+="<td align='center'> LastName</td>";
				htmlstr+="<td align='center'> Symptom</td>";
				htmlstr+="<td align='center'> Date</td>";
				htmlstr+="<td align='center'> Status</td>";
				htmlstr+="<td align='center'> Edit</td>";
				htmlstr+="</tr>";
				for(var i=0;i<data.length;i++){
					htmlstr+="<tr>";
					htmlstr+="<td align='center'>" + "<a href='basic_table.html#'	>" + data[i].userName +"</td>";
					htmlstr+="<td align='center'>" + data[i].firstName +"</td>";
					htmlstr+="<td align='center'>" + data[i].lastName +"</td>";
					htmlstr+="<td align='center'>" + data[i].symptom +"</td>";
					htmlstr+="<td align='center'>" + data[i].date +"</td>";
					htmlstr+="<td align='center'>" + "<span class='label label-success label-mini'>" + data[i].status + "</span>" +"</td>";
					htmlstr+="<td align='center'>" +
						"<button id='btncheck' class='btn btn-primary btn-xs'><i class='fa fa-check'></i></button>"+ " "+
						"<button id='open_btn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i></button>"+ " "+
						"<button class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i></button>"+ " "+"</td>";

					htmlstr+="</tr>";
				};
			}else{
				$("#open_btn").attr("style","display:none;")
				htmlstr+="<tr>";
				htmlstr+="<td align='center'> Patient</td>";
				htmlstr+="<td align='center'> FirstName</td>";
				htmlstr+="<td align='center'> LastName</td>";
				htmlstr+="<td align='center'> Symptom</td>";
				htmlstr+="<td align='center'> Date</td>";
				htmlstr+="<td align='center'> Status</td>";
				htmlstr+="</tr>";
				for(var i=0;i<data.length;i++){
					if(data[i].userName==loginUserName){
						htmlstr+="<tr>";
						htmlstr+="<td align='center'>" + "<a href='basic_table.html#'	>" + data[i].userName +"</td>";
						htmlstr+="<td align='center'>" + data[i].firstName +"</td>";
						htmlstr+="<td align='center'>" + data[i].lastName +"</td>";
						htmlstr+="<td align='center'>" + data[i].symptom +"</td>";
						htmlstr+="<td align='center'>" + data[i].date +"</td>";
						htmlstr+="<td align='center'>" + "<span class='label label-success label-mini'>" + data[i].status + "</span>" +"</td>";
						htmlstr+="</tr>";
					}
				};
			}



			htmlstr+="</table>";
			htmlstr+="</body>";
			$("#tablesEquipmentDetail").html(htmlstr);
		},
		error:function(data){
			alert("Sign up fail"+data);
		}


});

}
function changeDetail(){
	// var selectedTr = null;
	// var str = selectedTr.cells[0].childNodes[0].value;
	// document.getElementById("lab").innerHTML = str;
	var te0="1";
	td0=$(this).parents("tr").find("td").eq(0).text();
	alert(td0);
}
function savePatientData() {
	//注册
	var Patient = $("#Patient").val();
	var Symptom = $("#Symptom").val();
	var Date = $("#Date").val();
	var Status = $("#Status").val();
	var userfirstName = $("#UserfirstName").val();
	var userlastName = $("#UserlastName").val();
	$.ajax({
		url:'/api/records',
		type:'POST',//GET 或POST
		async:true,//false是否异步
		data: JSON.stringify({'firstName':userfirstName,'lastName':userlastName,'userName':Patient,'symptom':Symptom,'date':Date,'status':Status}),
		contentType: 'application/json',
		/*   processData: false,
        contentType: false,*/
		//dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
		success:function(data){
			alert("Save succuss");
			showTable();
		},
		error:function(data){
			alert("Sign up fail"+data);
		}
	});

}


function getUserId() {
	//注册
	var UserfirstName = $("#UserfirstName").val();
	var UserlastName = $("#UserlastName").val();
	$.ajax({
		url:'/api/records',
		type:'GET',//GET 或POST
		async:true,//false是否异步
		data: 'firstname='+UserfirstName+'&lastname='+UserlastName,
		contentType: 'application/json',
		/*   processData: false,
        contentType: false,*/
		//dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
		success:function(data){

			UserId=data[0].id;
			// alert("get ID succuss"+UserId);
		},
		error:function(data){
			alert("get ID fail"+data);
		}
	});
}

function DeleteUserId() {
	//注册
	var UserfirstName = $("#UserfirstName").val();
	var UserlastName = $("#UserlastName").val();
	$.ajax({
		url:'/api/records',
		type:'GET',//GET 或POST
		async:true,//false是否异步
		data: 'firstname='+UserfirstName+'&lastname='+UserlastName,
		contentType: 'application/json',
		/*   processData: false,
        contentType: false,*/
		//dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
		success:function(data){

			UserId=data[0].id;
			// alert("get ID succuss"+UserId);
			deletePatientData();
		},
		error:function(data){
			alert("get ID fail"+data);
		}
	});
}

function editPatientData() {
	getUserId();
	//注册
	var Patient = $("#Patient").val();
	var Symptom = $("#Symptom").val();
	var Date = $("#Date").val();
	var Status = $("#Status").val();
	var UserfirstName = $("#UserfirstName").val();
	var UserlastName = $("#UserlastName").val();
	$.ajax({
		url:'/api/records/'+UserId,
		type:'PUT',//GET 或POST
		async:true,//false是否异步
		data: JSON.stringify({'id':UserId,'firstName':UserfirstName,'lastName':UserlastName,'userName':Patient,'symptom':Symptom,'date':Date,'status':Status}),
		contentType: 'application/json',
		/*   processData: false,
        contentType: false,*/
		//dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
		success:function(data){
			alert("update succuss");
			showTable();
		},
		error:function(data){
			alert("Sign up fail"+data);
		}
	});
}

function deletePatientData() {
	$.ajax({
		url:'/api/records/'+UserId,
		type:'DELETE',//GET 或POST
		async:true,//false是否异步
		// data: JSON.stringify({'id':UserId,'firstName':UserfirstName,'lastName':UserlastName,'userName':Patient,'symptom':Symptom,'date':Date,'status':Status}),
		contentType: 'application/json',
		/*   processData: false,
        contentType: false,*/
		//dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
		success:function(data){
			alert("DELETE succuss");
			showTable();
		},
		error:function(data){
			alert("DELETE fail"+data);
		}
	});
}

function load(){
	document.getElementById("open_btn").onclick();
}
window.onload=function(){
	document.getElementById("open_btn").click();
}

function GetQueryString(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
	if(r!=null)return  unescape(r[2]); return null;
}


