package com.skill.web.converter;



import org.springframework.core.convert.converter.Converter;

/**
 * Created by codingBoy on 16/11/16.
 */
public class StringTrimConverter implements Converter<String,String> {

    @Override
    public String convert(String source) {

        try{
        	//ȥ���ַ������ߵĿո����ȥ����Ϊ���򷵻�null
            if (source!=null)
            {
                source=source.trim();
                if (source.equals(""))
                    return null;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return source;
    }
}
