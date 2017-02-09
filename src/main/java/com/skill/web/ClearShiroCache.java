package com.skill.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skill.shiro.CustomRealm;

/**
 * Created by codingBoy on 16/11/23.
 */
@Controller
public class ClearShiroCache
{
    @Autowired
    private CustomRealm customRealm;

    @RequestMapping("/clearShiroCache")
    public String clearShiroCache()
    {

        //�������,��������Ҫ��service����
        customRealm.clearCached();
        return "success";
    }
}
