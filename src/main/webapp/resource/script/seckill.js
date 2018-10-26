//存放主要交互逻辑
var seckill = {
	//秒杀相关的ajax的URL
	URL:{
		now:function(){
			return '/seckill/time/now';
		},
		exposer:function(seckillId){
			return '/seckill/' + seckillId + '/exposer';
		},
		execution:function(seckillId,md5){
			return '/seckill/' + seckillId + '/' + md5 + '/execution';
		}
	},
	//验证手机号
	validataPhone:function(phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	//处理秒杀逻辑
	handleSeckill:function(seckillId,node){
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开启秒杀</button>').show();
		$.post(seckill.URL.exposer(seckillId),{},function(result){
			//在回调函数中,执行交互流程
			if(result && result['success']){
				var exposer = result['data'];
				if(exposer['exposed']){
					//开始秒杀
					var md5 = exposer['md5'];
					var killURL = seckill.URL.execution(seckillId,md5);
					//绑定一次点击事件
					$('#killBtn').one('click',function(){
						 $(this).addClass('disabled');
						//发送秒杀请求
						$.post(killURL,{},function(result){
							if(result && result['success']){
								var killSuccess = result['data'];
								var state = killSuccess['state'];
								var stateInfo = killSuccess['stateInfo'];
								//现实秒杀结果
								node.html('<span class="label label-success">'+ stateInfo +'</span>').show();
							}
						});
					});
					node.show();
				}else{
					var nowTime = exposer['now'];
					var startTime = exposer['start'];
					var endTime = exposer['end'];
					//重新计算
					seckill.countdown(seckillId,nowTime,startTime,endTime);
				}
					
			}else{
				console.log("result:" + result);
			}
		});
	},
	//时间判断
	countdown:function(seckillId,nowTime,startTime,endTime){
		var seckillBox = $("#seckill-box");
		if(nowTime > endTime){
			//秒杀结束
			seckillBox.html("秒杀结束");
		}else if(nowTime < startTime){
			//秒杀未开始 开始倒计时
			var killTime = new Date(startTime + 1000);
			seckillBox.countdown(killTime,function(event){
				var format = event.strftime("秒杀倒计时：%D天 %H时 %M分 %S秒");
				seckillBox.html(format);
			}).on('finish.countdown',function(){
				//获取秒杀地址，控制现实逻辑 执行秒杀
				seckill.handleSeckill(seckillId,seckillBox);
			});
		}else{
			//秒杀开始
			seckill.handleSeckill(seckillId,seckillBox);
		}	
	},
	//详情页秒杀逻辑
	detail:{
		//详情页初始化
		init:function(params){
			//手机号验证和登录，计时交互
			//在Cookie中查找手机号
			var killPhone = $.cookie("killPhone");
			var seckillId = params["seckillId"];
			var startTime = params["startTime"];
			var endTime = params["endTime"];
			//验证手机号
			if(!seckill.validataPhone(killPhone)){
				//绑定phone
				var killPhoneModal = $('#killPhoneModal');
				killPhoneModal.modal({
					show:true,
					backdrop:'static', //禁止位置关闭
					Keyboard:false  //关闭键盘事件
				});
				$("#killPhoneBtn").click(function(){
					var inputPhone = $('#killPhoneKey').val();
					if(seckill.validataPhone(inputPhone)){
						//phone 写入cookie
						$.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'})
						//刷新页面
						window.location.reload();
					}else{
						$("#killPhoneMessage").hide().html('<label label-danger>手机号错误</label>').show(300);
					}		
				});
			}
			var startTime = params["startTime"];
			var endTime = params["endTime"];
			var seckillId = params["seckillId"];
			$.get(seckill.URL.now(),{},function(result){
				console.log(result);
				if(result && result['success']){
					var nowTime = result['data'];
					//时间判断
					seckill.countdown(seckillId,nowTime,startTime,endTime);
				}
			});	
		}
	}
		
}