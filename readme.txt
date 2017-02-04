һ��Restful�����淶����URL����ƹ淶��
ʲô��Restful
1.������Rails
2.һ�����ŵ�URI������ʽ
3.��Դ��״̬��״̬ת��

Restful�淶
GET->��ѯ����
POST->��ӡ��޸Ĳ���
PUT->�޸Ĳ�������HTTP�У�PUT������Ϊidempotent���ݵȡ������ķ�����POST���ǣ�����һ������Ҫ���������һ�������ظ�ִ�ж�Σ�������Ч����һ���ģ��Ǿ���idempotent��
DELETE->ɾ������

URL���
/ģ��/��Դ/{��ʾ}/����1/...

/user/{uid}/friends->�����б�
/user/{uid}/followers->��ע���б�

��ɱAPI��URL���
GET  /seckill/list ��ɱ�б�
GET /seckill/{id}/detail ����ҳ
GET /seckill/time/now ϵͳʱ��
POST /seckill/{id}/exposer ��¶��ɱ
POST /seckill/{id}/{md5}/execution ִ����ɱ

����ע��ӳ�似��
@RequestMappingע�⣺
��1��֧�ֱ�׼��URL
��2��Ant���URL��������*��**���ַ���
��3����{xxx}ռλ����URL��

�������󷽷�ϸ�ڴ���
1�����������
2������ʽ����
3������ת�����ض���
4������ģ�͸�ֵ
5������json����
6��cookie����

����1��
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

����2��
����json����
@RequestMapping(
	value="/{seckillid}/{md5}/execution",
	method=RequestMethod.POST,
	produces={"application/json;charset=UTF-8"} // ����HTTP��response��HeaderΪapplication/json��ͬʱ�����˱���Ϊutf-8
)
/**
@ResponseBody ע�����ڽ�Controller�ķ������صĶ���ͨ���ʵ���HttpMessageConverterת��Ϊָ����ʽ��д�뵽Response�����body��������
���ص����ݲ���html��ǩ��ҳ�棬��������ĳ�ָ�ʽ������ʱ����json��xml�ȣ�ʹ�ã�
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

����3��
Cookie����
@RequestMapping(
	value="/{seckillid}/{md5}/execution",
	method=RequestMethod.POST)
public SeckillResult<Exposer> execute(@PathVariable("seckillId") long seckillId,
									@PathVariable("md5") String md5,
									// requiredΪfalse��ǿ�Ʊ�����cookieValue��Ĭ��Ϊtrue
									@CookieValue(value="killPhone", required=false) Long phone
)
{
	if(phone = null){
		return new SeckillResult<SeckillExecution> (false,"Ϊע��绰");
	}
	
	SeckillExecution execution = seckillService.executeSeckillByProcedure(seckillId,phone,md5);
	SeckillResult<SeckillExecution> result = new SeckillResult<SeckillExecution> (true,execution);
	return result;
}