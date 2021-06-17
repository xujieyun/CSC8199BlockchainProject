

const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');




signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});


signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

function sighUp() {
	 //注册
	var userName = $("#name").val();
	var email = $("#email").val();
	var password = $("#password").val();
$.ajax({
   url:'/api/customers',
   type:'POST',//GET 或POST
   async:true,//false是否异步
   data: JSON.stringify({'firstName':'default','lastName':'default','password':password,'userName':userName,'email':email}),    
   contentType: 'application/json', 
   /*   processData: false, 
   contentType: false,*/
   //dataType:'text',//返回的数据格式类型json/xml/html/script/jsonp/text    （返回的值很关键，如果不是text类型，页面可能会被重写）
   success:function(data){
       alert("Sign up succuss");
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
		   }
    },
    error:function(data){
    	alert("Sign up fail"+data);
    }
}); 

}

