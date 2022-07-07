package com.jt.test.controller;
import com.jt.test.PushControllerUtils.GetOpenIdAct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RequestMapping("/WX")
@RestController
@Api(tags = "网上找到一套用于群发的测试")
public class PushControl {
    @PostMapping("/TEST")
    @ApiOperation("发消息")
    public void sendWxInfo(String json) {
        // 获取模板Id
        String templateId= "VJ9B-VjzxbJ9C-PSxH-GYDhFwt2H-APhuA8tiUStLGE";
        // 需要跳转的url----点击消息推送时需要跳转的地址
        String url = "baidu.com";
        //获取关注该公众号的所有openid
        List<String> oplist=new GetOpenIdAct().getWeixinUser();
        //获取关注该公众号的所有openid
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId("wxf9fc4558c1f2812e");
        wxStorage.setSecret("bbb816109367824cf2f96bae0e791100");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        // 发送模板消息接口
        WxMpTemplateMessage templateMessage=new WxMpTemplateMessage();
        templateMessage.setUrl(url);
        templateMessage.setTemplateId(templateId);
        //
        String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 添加模板数据
        templateMessage.addData(new WxMpTemplateData("first","尊敬的客户，您的设备出发报警"))
                .addData(new WxMpTemplateData("user_name","jt"))
                .addData(new WxMpTemplateData("sex","某某公司"))
                .addData(new WxMpTemplateData("phone","监测到挖掘机工作"));

        String msgId = null;
        try {
            // 发送模板消息
            for (String uids:oplist){
                templateMessage.setToUser(uids);
                msgId = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            }

        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}