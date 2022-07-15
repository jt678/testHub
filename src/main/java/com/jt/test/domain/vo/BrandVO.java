package com.jt.test.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jt.test.domain.entity.Brand;
import lombok.Data;

/**
 * BrandVO
 *
 * @author jt
 * @date 2022/5/18
 **/
@Data
public class BrandVO {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String name;

    /**
     * 首字母
     */
    private String firstLetter;

    /**
     *
     */
    private Integer sort;

    /**
     * 是否为品牌制造商：0->不是；1->是
     */
    private Integer factoryStatus;

    /**
     *
     */
    private Integer showStatus;

    /**
     * 产品数量
     */
    private Integer productCount;

    /**
     * 产品评论数量
     */
    private Integer productCommentCount;

    /**
     * 品牌logo
     */
    private String logo;

    /**
     * 专区大图
     */
    private String bigPic;

    /**
     * 品牌故事
     */
    private String brandStory;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Brand other = (Brand) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getFirstLetter() == null ? other.getFirstLetter() == null : this.getFirstLetter().equals(other.getFirstLetter()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
                && (this.getFactoryStatus() == null ? other.getFactoryStatus() == null : this.getFactoryStatus().equals(other.getFactoryStatus()))
                && (this.getShowStatus() == null ? other.getShowStatus() == null : this.getShowStatus().equals(other.getShowStatus()))
                && (this.getProductCount() == null ? other.getProductCount() == null : this.getProductCount().equals(other.getProductCount()))
                && (this.getProductCommentCount() == null ? other.getProductCommentCount() == null : this.getProductCommentCount().equals(other.getProductCommentCount()))
                && (this.getLogo() == null ? other.getLogo() == null : this.getLogo().equals(other.getLogo()))
                && (this.getBigPic() == null ? other.getBigPic() == null : this.getBigPic().equals(other.getBigPic()))
                && (this.getBrandStory() == null ? other.getBrandStory() == null : this.getBrandStory().equals(other.getBrandStory()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFirstLetter() == null) ? 0 : getFirstLetter().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getFactoryStatus() == null) ? 0 : getFactoryStatus().hashCode());
        result = prime * result + ((getShowStatus() == null) ? 0 : getShowStatus().hashCode());
        result = prime * result + ((getProductCount() == null) ? 0 : getProductCount().hashCode());
        result = prime * result + ((getProductCommentCount() == null) ? 0 : getProductCommentCount().hashCode());
        result = prime * result + ((getLogo() == null) ? 0 : getLogo().hashCode());
        result = prime * result + ((getBigPic() == null) ? 0 : getBigPic().hashCode());
        result = prime * result + ((getBrandStory() == null) ? 0 : getBrandStory().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", firstLetter=").append(firstLetter);
        sb.append(", sort=").append(sort);
        sb.append(", factoryStatus=").append(factoryStatus);
        sb.append(", showStatus=").append(showStatus);
        sb.append(", productCount=").append(productCount);
        sb.append(", productCommentCount=").append(productCommentCount);
        sb.append(", logo=").append(logo);
        sb.append(", bigPic=").append(bigPic);
        sb.append(", brandStory=").append(brandStory);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
