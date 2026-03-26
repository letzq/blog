package com.xy.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.blog.system.entity.po.BlogArticleCategory;
import com.xy.blog.system.vo.AppCategoryListVo;
import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * 博客文章分类 Mapper 接口。
 */
public interface BlogArticleCategoryMapper extends BaseMapper<BlogArticleCategory> {

    /**
     * 查询前台启用分类列表，并统计每个分类下的已发布文章数量。
     */
    @Select("""
        SELECT c.category_id AS categoryId,
               c.category_name AS categoryName,
               c.sort_num AS sortNum,
               COUNT(a.article_id) AS articleCount
        FROM blog_article_category c
        LEFT JOIN blog_article a
               ON c.category_id = a.category_id
              AND a.status = '1'
        WHERE c.status = '1'
        GROUP BY c.category_id, c.category_name, c.sort_num
        ORDER BY c.sort_num ASC, c.category_id ASC
        """)
    List<AppCategoryListVo> selectPublicCategoryList();
}
