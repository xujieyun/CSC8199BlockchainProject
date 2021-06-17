const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');


signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});


signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});
	var privateKey1 = 'null';
	var privateKey2 = 'null';
	var privateKey3 = 'null';
	var privateKey4 = 'null';
	var publicKey = 'null';
	var messageEn1 = 'null';
	var messageEn2 = 'null';
	var role = 'null';
function selectDoctor() {
	role ='doctor'
}
function selectPatient() {
	role ='patient'
}

function sighUpCheck() {
	if(role==null){
		alert("please select DOCTOR or PATIENT");
	}else{
		CreateSignature();
		alert("CreateSignature success");
		sighUp();
	}
}

function sighUp() {
	 //注册
	var userName = $("#name").val();
	var email = $("#email").val();
	var password = $("#password").val();
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
$.ajax({
   url:'/api/customers',
   type:'POST',//GET 或POST
   async:true,//false是否异步
   data: JSON.stringify({'firstName':'*','lastName':'*','password':password,'userName':userName,'email':email,'role':'*','privateKey1':privateKey1,'privateKey2':privateKey2,'privateKey3':privateKey3,'privateKey4':privateKey4,'publicKey':publicKey,'messageEn1':messageEn1,'messageEn2':messageEn2}),
   contentType: 'application/json',
   /*   processData: false, 
   contentType: false,*/
   //dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
   success:function(data){
       alert("Sign up succuss");
	   window.location.href="login.html";
   },
   error:function(data){
       alert("Sign up fail"+data);
   }
}); 

}

function signIn() {
	 //登录 
	var userName = $("#signInName").val();
	var password = $("#signInPassword").val();

$.ajax({
		url:'/api/customers/customer?username='+userName+'&password='+password,
	   type:'GET',//GET 或POST
	   async:true,//false是否异步 contentType: 'application/json', 
    //dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
	   data: JSON.stringify({}),    
	   contentType: 'application/json',
	   success:function(data){
		   if(data.length==0){
			   alert("Sign up fail"+data);
		   }else{
			   alert("sign in success");
			   privateKey1=data[0].privateKey1;
			   privateKey2=data[0].privateKey2;
			   privateKey3=data[0].privateKey3;
			   privateKey4=data[0].privateKey4;
			   messageEn2=data[0].messageEn2;
			   DeCreateSig();
			   alert("DeCreateSig success");
			   window.location.href="basic_table.html?userName="+userName+"&role="+role;
		   }
    },
    error:function(data){
    	alert("Sign up fail"+data);
    }
}); 

}
function DeCreateSig() {
	var userName = $("#name").val();
	var email = $("#email").val();
	var password = $("#password").val();
	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	//登出
//	var sessionId = "699b599a-6f23-4eb0-b81b-acb46cc2375a";
//	formData.append("sessionId", sessionId);
// 	document.getElementById("sessionID").value=setSessionId;
	//登出
//	var sessionId = "699b599a-6f23-4eb0-b81b-acb46cc2375a";
//	formData.append("sessionId", sessionId);
	$.ajax({
		url:'/DeCreateSig',
		dataType:'TEXT',
		type:'POST',
		data: {'privateKey1':privateKey1,'privateKey2':privateKey2,'privateKey3':privateKey3,'privateKey4':privateKey4,'messageEn1':messageEn1,'messageEn2':messageEn2},
		/*   processData: false,
        contentType: false,*/
		//dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
		success:function(data){
			role=data.split("#")[4];
		},
		error:function(data){
			alert("failed"+data);
		}
	});
}

function CreateSignature() {
	var userName = $("#name").val();
	var email = $("#email").val();
	var password = $("#password").val();
	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	//登出
//	var sessionId = "699b599a-6f23-4eb0-b81b-acb46cc2375a";
//	formData.append("sessionId", sessionId);
// 	document.getElementById("sessionID").value=setSessionId;
	//登出
//	var sessionId = "699b599a-6f23-4eb0-b81b-acb46cc2375a";
//	formData.append("sessionId", sessionId);
	$.ajax({
		url:'/CreateSignature',
		dataType:'TEXT',
		type:'POST',
		data: {'firstName':firstName,'lastName':lastName,'password':password,'userName':userName,'email':email,'role':role},
		/*   processData: false,
        contentType: false,*/
		//dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
		success:function(data){
			privateKey1=data.split("###")[0];
			privateKey2=data.split("###")[1];
			privateKey3=data.split("###")[2];
			privateKey4=data.split("###")[3];
			publicKey=data.split("###")[4];
			messageEn1=data.split("###")[5];
			messageEn2=data.split("###")[6];
		},
		error:function(data){
			alert("failed"+data);
		}
	});
}



