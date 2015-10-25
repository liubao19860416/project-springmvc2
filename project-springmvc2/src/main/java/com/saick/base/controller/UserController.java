package com.saick.base.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meidusa.fastjson.JSON;
import com.meidusa.fastjson.JSONObject;
import com.meidusa.fastmark.feature.SerializerFeature;
import com.saick.base.ApplicationContextHelper;
import com.saick.base.controller.config.JsonStringUnicodeSerializer;
import com.saick.base.controller.util.MyStringUtils;
import com.saick.base.dao.entiy.Book;
import com.saick.base.dao.entiy.User;

/**
 * 备注:在Controller内部转发使用return "forward:action路径"实现
 * 
 * @author Liubao
 * @Version 1.0
 * 
 */
@Controller
@RequestMapping("/user")
//@Scope(value="prototype")
public class UserController {
    
    /**
     * 默认只被初始化1次,即单例的;可以添加上面的注解为多例的Controller；@Scope(value="prototype")
     * 在struts2中的Action，默认是多例的；
     */
    public UserController() {
        super();
        System.out.println("构造方法执行了。。。"+this.getClass().toString());
    }

    @Autowired
    private ApplicationContextHelper applicationContext;
    
    @SuppressWarnings("static-access")
    @RequestMapping("/userIndex")
    public String userIndex(Model model, HttpServletRequest request,HttpServletResponse response)throws Exception {
        //方式1：返回页面值,顺序为较高；
        model.addAttribute("username", "LIUBAO1");
        User user = applicationContext.getBean("user");
        System.out.println("配置信息为：" + user);
        //方式2：返回页面值；
        ModelAndView model2=new ModelAndView("user/userIndex");
        model2.addObject("username", "LIUBAO2");
        //方式3：返回页面值；
        //request.setAttribute("username", "LIUBAO3");
        //方式4：返回页面值；
        request.getSession().setAttribute("username", "LIUBAO4");
        //方式5：返回页面值；
        //request.getSession().getServletContext().setAttribute("username", "LIUBAO5");
        return "user/userIndex";
    }
    
    /**
     * 测试自动补全功能,两种方式测试使用,produces = {"application/json;charset=UTF-8"}
     */
    
    @RequestMapping(value="/userIndex3",method={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object userIndex3(HttpServletRequest request,HttpServletResponse response)throws Exception {
        List<Map> usernameList = new ArrayList<Map>();
        List<String> list=new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            Map map=new HashMap();
            if(i%2==0){
                map.put("name", "王丽"+i);
                list.add( "王丽"+i);
            }else{
                map.put("name", "刘保"+i);
                list.add( "刘保"+i);
            }
            usernameList.add(map);
        }
        list.add(null);
        String stringValue = MyStringUtils.getStringValue(request.getParameter("username"));
        ObjectMapper objectMapper = new ObjectMapper(); 
        configObjectMapper(objectMapper);
        String json=objectMapper.writeValueAsString(list);
        
        ObjectMappingCustomer2 o=new ObjectMappingCustomer2();
        json=o.writeValueAsString(list);
        Map map=new HashMap();
        map.put("time", new Date());
        map.put("name",null);
        usernameList.add(map);
        return usernameList;
    }
    /**
     * 注意使用ObjectMapper的时候，设置对应的编码格式，否则会出现乱码
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = "/userIndex2",produces = "application/json; charset=utf-8",method = {RequestMethod.POST,RequestMethod.GET}) 
    //@RequestMapping(value="/userIndex2" ,produces = {"application/json;charset=UTF-8"},method={RequestMethod.POST},consumes = "application/json")
    @ResponseBody
    public Object userIndex2(@RequestParam(required=true,value="username")String username ,HttpServletRequest request,HttpServletResponse response)throws Exception {
        response.setContentType("contentType=application/json;charset=utf-8");
        User user = applicationContext.getBean("user");
        //user.setBirthday(null);
        //user.setNickname("");
        username=new String(username.getBytes("ISO-8859-1"), "utf-8");
        System.out.println(username+"配置信息为：" + user);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(user);
        
        /**
         * 获取非null值的json数据的2种简便方法
         */
        JSONObject.toJSONString(user, SerializerFeature.WriteNullStringAsEmpty);
        String jsonString = JSON.toJSONString(user, SerializerFeature.WriteNullStringAsEmpty);
        Gson g = new Gson();
        String msgObj = g.toJson(user);
        
        /**
         * 方式3：终极方案,只对VO起作用,对List和Map不起作用？需要进行进一步的覆写，添加类型判断即可；
         * Jackson's org.codehaus.jackson.map.ObjectMapper "just works" for mapping 
         * JSON data into plain old Java objects ("POJOs").
         *  For example, given JSON data
         */
        ObjectMappingCustomer o=new ObjectMappingCustomer();
        Book book=new Book();
        //book.setName("名称书");
        book.setPrive(100F);
        user.setBook(book);
        String str1=o.writeValueAsString(user);
        
        Map map =new HashMap();
        map.put("user", user);
        map.put("name", null);
        map.put("age", 22);
        map.put("bir", "HAO");
        map.put("NICK", "好");
        String str2=o.writeValueAsString(map);
        
        
        ObjectMapper objectMapper = new ObjectMapper(); 
        configObjectMapper(objectMapper);
        User user2 = objectMapper.readValue(str1, User.class);
        String str3=objectMapper.writeValueAsString(user);
        //return JSONObject.toJSONString(str);
        //return JSON.toJSONString(str);
        return str2;
    }
    
    private void configObjectMapper(ObjectMapper mapper) {
        //当找不到对应的序列化器时 忽略此字段
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //使Jackson JSON支持Unicode编码非ASCII字符
        CustomSerializerFactory serializerFactory= new CustomSerializerFactory();
        serializerFactory.addSpecificMapping(String.class, new JsonStringUnicodeSerializer());
        mapper.setSerializerFactory(serializerFactory);
        
        // 允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 字段和值都加引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 数字也加引号
        mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        mapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        //this.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        // 空值处理为空串
        mapper.getSerializerProvider().setNullValueSerializer(
                new JsonSerializer<Object>() {
                    @Override
                    public void serialize(Object value, JsonGenerator jg,
                            SerializerProvider sp) throws IOException,
                            JsonProcessingException {
                        jg.writeString("");
                    }
                });
        

        
    }

    @RequestMapping("/userDetail")
    public String userDetail(@RequestParam(defaultValue = "2", value = "userId", required = true) String userId)
            throws Exception {
        System.out.println(userId);
        return "user/userDetail";
    }

    /**
     * 用户修改页面：添加了校验 @PathVariable，可以使用它在请求参数路径中，类是Restful风格
     */
    @RequestMapping("/useredit/{userid:\\d+}")
    public String useredit(Model model, @PathVariable String userid)
            throws Exception {
        System.err.println("userid=" + userid);
        return "user/useredit";
    }
}
