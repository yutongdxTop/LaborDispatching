package com.yutongdxTop.LaborDispatching.controller;

import com.yutongdxTop.LaborDispatching.domain.vo.ClientVo;
import com.yutongdxTop.LaborDispatching.service.ClientVoService;
import com.yutongdxTop.LaborDispatching.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/LaborDispatching/clientVo")
public class ClientVoController {

    @Autowired
    private ClientVoService clientVoService;

    @RequestMapping(value = "/getAllClientVos")
    @ResponseBody
    public JSONResult getAllCustomerVos() {
        List<ClientVo> clientVos;
        clientVos = clientVoService.getAllClientVos();
        if (clientVos.isEmpty()) {
            return JSONResult.errorMsg("还没有客户信息");
        } else {
            int count = clientVos.size();
            return JSONResult.ok(count, clientVos);
        }
    }

    @RequestMapping("/getClientVoByUserName")
    @ResponseBody
    public JSONResult getClientVoByUserName(@RequestParam(value = "userName", required = true, defaultValue = "-1") String userName) {
        ClientVo clientVo = clientVoService.getClientVoByUserName(userName);
        System.out.println("getClientVo:" + userName);
        if (clientVo == null) {
            return JSONResult.errorMsg("找不到这个用户");
        } else {
            return JSONResult.ok(clientVo);
        }
    }

    @RequestMapping("/updateClientVo")
    @ResponseBody
    public JSONResult updateClientVo(@ModelAttribute ClientVo clientVo) {
        System.out.println(clientVo.getName());
        String msg = clientVoService.updateClientVo(clientVo);
        if (msg!=null){
            return JSONResult.ok(msg);
        }else{
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

}
