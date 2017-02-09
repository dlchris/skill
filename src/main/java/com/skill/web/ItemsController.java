package com.skill.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.skill.entity.ItemsCustom;
import com.skill.entity.ItemsQueryVo;
import com.skill.service.ItemsService;
import com.skill.web.validation.ValidGroup1;

/**
 * Created by codingBoy on 16/11/15.
 */
@Controller
//����url�ĸ�·��������ʱ��·��+��������url
@RequestMapping("/items")
public class ItemsController {

    //ע��service
    @Autowired
    private ItemsService itemsService;

    //��������Ʒ���͵ķ�����ȡ����������������ֵ��䵽request����ҳ����ʾ
    @ModelAttribute("itemsType")
    public Map<String,String> getItemsType() throws Exception{
        HashMap<String,String> itemsType=new HashMap<>();
        itemsType.put("001","data type");
        itemsType.put("002","clothes");
        return itemsType;
    }

    @RequestMapping("/queryItems")
    @RequiresPermissions("item:query")
    public ModelAndView queryItems() throws Exception {
        //����servie����ѯ��Ʒ�б�
        List<ItemsCustom> itemsList=itemsService.findItemsList(null);

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("itemsList",itemsList);
        //ָ���߼���ͼ��itemsList.jsp
        modelAndView.setViewName("itemsList");

        return modelAndView;
    }

    //�����޸���Ʒ��ѯ
    @RequestMapping("/editItemsList")
    public ModelAndView editItemsList() throws Exception {
        //����servie����ѯ��Ʒ�б�
        List<ItemsCustom> itemsList=itemsService.findItemsList(null);

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("itemsList",itemsList);
        //ָ���߼���ͼ��itemsList.jsp
        modelAndView.setViewName("editItemsList");

        return modelAndView;
    }

    //�����޸���Ʒ���ύ

    @RequestMapping("/editItemsListSubmit")
    public String editItemsListSubmit(ItemsQueryVo itemsQueryVo) throws Exception{
        return "success";
    }


    //��Ʒ�޸�ҳ����ʾ
    //ʹ��method = RequestMethod.GET������ʹ��get����
//    @RequestMapping(value = "/editItems",method = RequestMethod.GET)
//    public ModelAndView editItems() throws Exception
//    {
//        ModelAndView modelAndView=new ModelAndView();
//
//        //����service��ѯ��Ʒ����Ϣ
//        ItemsCustom itemsCustom=itemsService.findItemsById(1);
//        //��ģ�����ݴ���jsp
//        modelAndView.addObject("item",itemsCustom);
//        //ָ���߼���ͼ��
//        modelAndView.setViewName("editItem");
//
//        return modelAndView;
//    }

    //���������ַ������ַ��������߼���ͼ����Model����ʱ��������䵽request����ҳ����ʾ
    @RequestMapping(value = "/editItems",method = RequestMethod.GET)
    @RequiresPermissions("item:update")//ִ�д˷�����Ҫitem:updateȨ��
    public String editItems(Model model, Integer id) throws Exception
    {

        //��id����ҳ��
        model.addAttribute("id",id);

        //����service��ѯ��Ʒ����Ϣ
        ItemsCustom itemsCustom=itemsService.findItemsById(id);



        model.addAttribute("itemsCustom",itemsCustom);

        return "editItem";
    }

    //������Ʒid�鿴��Ʒ��Ϣrest�ӿ�
    //@requestMapping��ָ��restful��ʽ��url�еĲ�����������Ҫ��{}������
    //@PathVariable��url�еĲ������βν��а�
    @RequestMapping("/viewItems/{id}")
    public @ResponseBody ItemsCustom viewItems(@PathVariable("id") Integer id) throws Exception
    {
        //����service��ѯ��Ʒ����Ϣ
        ItemsCustom itemsCustom=itemsService.findItemsById(id);


        return itemsCustom;
    }


//    @RequestMapping(value = "/editItems",method = RequestMethod.GET)
//    public void editItems(HttpServletRequest request, HttpServletResponse response,
////                          @RequestParam(value = "item_id",required = false,defaultValue = "1")
//                                  Integer id) throws Exception
//    {
//
//        //����service��ѯ��Ʒ����Ϣ
//        ItemsCustom itemsCustom=itemsService.findItemsById(id);
//
//        request.setAttribute("item",itemsCustom);
//
//        //zhuyi���ʹ��requestת��ҳ�棬������Ҫָ��ҳ�������·��
//        request.getRequestDispatcher("/WEB-INF/jsp/editItem.jsp").forward(request,response);
//    }

    //��Ʒ�ύҳ��
    //itemsQueryVo�ǰ�װ���͵�pojo
    //��@Validated�ж���ʹ��ValidGroup1���±ߵ�У��

    @RequestMapping("/editItemSubmit")
    @RequiresPermissions("item:update")//ִ�д˷�����Ҫitem:updateȨ��
    public String editItemSubmit(Model model,Integer id,
                                 @Validated(value = {ValidGroup1.class}) @ModelAttribute(value = "itemsCustom") ItemsCustom itemsCustom,
                                 BindingResult bindingResult,
                                 //�ϴ�ͼƬ
                                 MultipartFile pictureFile
                                 ) throws Exception
    {
        //���У�������Ϣ
        //���������ʱ����
        if (bindingResult.hasErrors())
        {
            //��ȡ����
            List<ObjectError> errors=bindingResult.getAllErrors();

            model.addAttribute("errors",errors);

            for (ObjectError error:errors)
            {
                //���������Ϣ
                System.out.println(error.getDefaultMessage());
            }

            //���У�������Ȼ�ص���Ʒ�޸�ҳ��
            return "editItem";

        }


        //�������ݻ���
        model.addAttribute("id",id);
//        model.addAttribute("item",itemsCustom);

        //����ͼƬ���ϴ�
        if (pictureFile!=null&&pictureFile.getOriginalFilename()!=null&&pictureFile.getOriginalFilename().length()>0)
        {
            //ͼƬ�ϴ��ɹ��󣬽�ͼƬ�ĵ�ַд�����ݿ�
            String filePath="/Users/codingBoy/Pictures/";
            String originalFilename=pictureFile.getOriginalFilename();

            String newFileName= UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));

            //���ļ�
            File file=new File(filePath+newFileName);

            //���ڴ��е��ļ�д�����
            pictureFile.transferTo(file);

            //ͼƬ�ϴ��ɹ�
            itemsCustom.setPic(newFileName);
        }


        itemsService.updateItems(id,itemsCustom);
        //����ת��
//        return "forward:queryItems.action";


//        return "editItem";
        //�ض���
        return "redirect:queryItems.action";
    }
//
//    //�Զ������Ա༭��
//    @InitBinder
//    public void initBinder(WebDataBinder binder) throws  Exception{
//
//        //Date.class��������controller�����β�pojo����һ�µ�date���ͣ�������java.util.Date
//        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"),true));
//
//    }

    //ɾ����Ʒ
    @RequestMapping("/deleteItems")
    public String deleteItems(Integer[] delete_id) throws Exception
    {
        //����serive����ɾ����Ʒ

        return "success";
    }
}
