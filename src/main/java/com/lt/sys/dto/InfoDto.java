package com.lt.sys.dto;

import com.lt.sys.entity.Note;
import lombok.Data;
import org.w3c.dom.ls.LSException;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class InfoDto {

    //注册人
    @NotNull(message = "电话号码不能为空")
    private String registration;

    //经度
    private String longitude;

    //邀请码
    @NotNull(message = "邀请码不能为空号")
    private String referralCode;

    //纬度
    private String latitude;

    //短信
    private List<NoteDto> noteDtos;

    //地址
    private String address;

    //通讯录
    private List<ContactsDto> contactsDtos;

    @Override
    public String toString() {
        return "InfoDto{" +
                "registration='" + registration + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", noteDtos=" + noteDtos.toString() +
                ", contactsDtos=" + contactsDtos.toString() +
                '}';
    }
}
