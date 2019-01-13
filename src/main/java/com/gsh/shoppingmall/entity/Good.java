package com.gsh.shoppingmall.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author gsh
 * @since 2019-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("good")
public class Good extends Model<Good> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品图片(多张图,分割)
     */
    private String imgUrl;

    /**
     * 商品库存
     */
    private Integer inventory;

    /**
     * 是否上架 1是0否
     */
    private Integer isShelves;

    /**
     *  0已删除 1存在
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
