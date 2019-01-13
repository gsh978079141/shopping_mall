package com.gsh.shoppingmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gsh.shoppingmall.entity.ApiResult;
import com.gsh.shoppingmall.entity.Good;
import com.gsh.shoppingmall.service.GoodService;
import com.gsh.shoppingmall.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/good")
public class GoodController {

    @Value("${web.upload-file-path}")
    String uploadFilePath;
    @Value("${web.host_name}")
    String hostName;

    @Autowired
    GoodService goodService;

    /**
     * 商品列表
     * @param good
     * @return
     */
    @RequestMapping("/list")
    public ApiResult goodList(Good good){
        return ResultUtil.success(goodService.list(new QueryWrapper<>(good)));
    }

    /**
     * 修改或者保存商品信息
     * @param good
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public ApiResult saveOrUpdate(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,Good good)  {
        goodService.saveOrUpdate(good);
        String absolutePath = new File("").getAbsolutePath();
        //上传的文件名
        String fileName = file.getOriginalFilename();
        // 前缀名
        String prefixName = "upload_good_img_"+good.getId();
        // 后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //文件服务器存储路径
        String newFileName = uploadFilePath+ prefixName+suffixName;
        //保存文件
        File dest = new File(newFileName);
        //设置数据库图片路径
        good.setImgUrl(hostName + prefixName+suffixName);
        goodService.saveOrUpdate(good);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.success("");
    }

    /**
     * 批量删除商品
     * @param goodIds
     * @return
     */
    @RequestMapping("/delete")
    public ApiResult delete(@RequestParam(required = true, value = "goodIds[]")List<Integer> goodIds){
        return ResultUtil.success(goodService.removeByIds(goodIds));
    }



}

