package com.saick.base.dao.entiy;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

/**
 * 对User对象的校验,是通过注解完成的
 * 
 * Date类型数据如何进行校验

 * @author Liubao
 * @Version 1.0
 */
/**
 * JsonIgnoreProperties，作用是json序列化时,将java bean中的一些属性忽略掉，序列化和反序列化都受影响
 */
//@JsonIgnoreProperties 
/**
 * JsonSerialize只序列化不为null的属性值
 */
//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable {
    private static final long serialVersionUID = -3379175634334411627L;
    private Long id;
    private String username;
    private String password;
    private String nickname="";
    /**
     * 此注解用于属性或者方法上（最好是属性上），作用和上面的@JsonIgnoreProperties一样。  
     */
    //@JsonIgnore
    private String email;
    private String coutry;
    private String number;

    private Book book;
    
    /**
     * 下面两种类方式都可以,可以使用自定义的，也可以使用json包自带的
     */
//    @JsonSerialize(using=JsonDateSerializer.class)
    /**
     * 下面的无用
     */
    @JsonSerialize(using= DateSerializer.class)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date birthday;

    /**
     * JsonFormat用于属性或者方法上（最好是属性上），可以方便的把Date类型直接转化为我们想要的模式，
     */
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getBirthday() {
        return birthday;

    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // 必须提供无参的构造方法
    public User() {
    }

    public User(String username, String password, String nickname, String email) {
        super();
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public User(Long id, String username, String password, String nickname,
            String email) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    @NotEmpty(message = "用户名不能为空!")
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Size(max = 10, min = 3, message = "密码长度需要在3-10位之间")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "昵称不能为空!")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Email(message = "邮箱地址并不正确")
    @NotEmpty(message = "邮箱地址不能为空!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCoutry() {
        return coutry;
    }

    public void setCoutry(String coutry) {
        this.coutry = coutry;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password="
                + password + ", nickname=" + nickname + ", email=" + email
                + ", coutry=" + coutry + ", number=" + number + ", birthday="
                + birthday + "]";
    }

}
