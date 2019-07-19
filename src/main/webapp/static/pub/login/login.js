
$(function(){
	    $('#signup_code').change(function(){
	    	$("#userCodeMsg").hide();
	    })
	    $('#signup_password').change(function(){
	    	$(".tip").hide();
	    })
	    $('#signup_verifyCode').change(function(){
	    	$("#verifyCodeMsg").hide();
	    })

	});

	function _loginKeyPress(event) {
		if (event.keyCode == 13) {
			login();
		}
	}

	//验证吗
	function reloadImage() {
		document.getElementById('imageId').src = _path+"/pub/verify?t=" + (new Date()).toTimeString();
	}
	var isRequest=false;
	
	function login(){
		var userCode=$("#signup_code").val();
		var password=$("#signup_password").val();
		var verifyCode=$("#signup_verifyCode").val();
		if (userCode == '') {
			$("#showMsg").html("用户名不能为空");
			$("#userCodeMsg").show();
			return;
		}
		if (password == '') {
			$("#showMsg").html("用户密码不能为空");
			$("#passwordMsg").show();
			return;
		}
		if(vc!="")isRequest=true;
		if (isRequest && verifyCode == '') {
			$("#showMsg").html("验证码不能为空");
			$("#verifyCodeMsg").show();
			$("#show_verifyCode").show();
			return;
		}

		//加密处理
		bodyRSA();
	    password = encryptedString(key, encodeURIComponent(password));
		$('#signup_password').val(password);
		$('#signup_form').submit();
	}
	
	//加密key
	var key ;
	function bodyRSA(){
	    setMaxDigits(130);
	    key = new RSAKeyPair("10001","","dff46645b6337855b0c1f9812a1a943904f2abd5f2f339f0f3b7f81cdb169eab00da0321a0075ef1d9e12d2af4d168b16d0f3ded064f8bcb97ca2af891eb73a0b55a2990b62fffc0cee0e61efcf5ec6247c8eb4a1f4df6d2ac42d930407c52c6e8cd07f6babf109c50428c3d8f1a64a66950178197136ee19b04b2bdf6dcb3df");
	}