package com.skill.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skill.entity.ItemsCustom;

@Controller
public class JsonTest
{
    //�����json��Ӧjson��������Ʒ��Ϣ����Ʒ��Ϣ��json��ʽ�����Ʒ��Ϣ
    @RequestMapping("/requestJson")
    public @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom) throws Exception{
        return itemsCustom;
    }

    //����key/value��Ӧjson
    @RequestMapping("/responseJson")
    public @ResponseBody ItemsCustom responseJson(ItemsCustom itemsCustom) throws Exception{

        return itemsCustom;
    }

}
