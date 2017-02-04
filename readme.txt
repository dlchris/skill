一、Restful用来规范我们URL的设计规范。
什么是Restful
1.兴起于Rails
2.一种优雅的URI表述方式
3.资源的状态和状态转移

Restful规范
GET->查询操作
POST->添加、修改操作
PUT->修改操作（在HTTP中，PUT被定义为idempotent‘幂等’操作的方法，POST则不是，这是一个很重要的区别）如果一个方法重复执行多次，产生的效果是一样的，那就是idempotent的
DELETE->删除操作

URL设计
/模块/资源/{标示}/集合1/...

/user/{uid}/friends->好友列表
/user/{uid}/followers->关注者列表

秒杀API的URL设计
GET  /seckill/list 秒杀列表
GET /seckill/{id}/detail 详情页
GET /seckill/time/now 系统时间
POST /seckill/{id}/exposer 暴露秒杀
POST /seckill/{id}/{md5}/execution 执行秒杀

二、注解映射技巧
@RequestMapping注解：
（1）支持标准的URL
（2）Ant风格URL（即？和*和**等字符）
（3）带{xxx}占位符的URL。

三、请求方法细节处理
1、请求参数绑定
2、请求方式限制
3、请求转发和重定向
4、数据模型赋值
5、返回json数据
6、cookie访问

例子1：
@RequestMapping(value="/{seckillid}/detail",method=RequestMethod.GET)
public String detail(@PathVariable("seckillid") Long seckillid,Model model){
	if(seckillid==null){
		return "redirect:/seckill/list";
	}
	Seckill seckill = seckillService.getById(seckillid);
	if(seckill == null){
		return "forward:/seckill/list";
	}
	model.addAttribute("seckill",seckill);
	return "detail";
}

例子2：
返回json数据
@RequestMapping(
	value="/{seckillid}/{md5}/execution",
	method=RequestMethod.POST,
	produces={"application/json;charset=UTF-8"} // 返回HTTP的response的Header为application/json，同时限制了编码为utf-8
)
/**
@ResponseBody 注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
*/
@ResponseBody 
public SeckillResult<Exposer> exportSeckillURL(@PathVariable("id") long id){
	SeckillResult<Exposer> result;
	try{
		Exposer exposer = ...
	} catch(Exception e){
		logger.error(e.getMessage(),e);
		result= new SeckillResult<Exposer>(false,e.getMessage());
	}
	return result;
}

例子3：
Cookie访问
@RequestMapping(
	value="/{seckillid}/{md5}/execution",
	method=RequestMethod.POST)
public SeckillResult<Exposer> execute(@PathVariable("seckillId") long seckillId,
									@PathVariable("md5") String md5,
									// required为false不强制必须有cookieValue，默认为true
									@CookieValue(value="killPhone", required=false) Long phone
)
{
	if(phone = null){
		return new SeckillResult<SeckillExecution> (false,"为注册电话");
	}
	
	SeckillExecution execution = seckillService.executeSeckillByProcedure(seckillId,phone,md5);
	SeckillResult<SeckillExecution> result = new SeckillResult<SeckillExecution> (true,execution);
	return result;
}