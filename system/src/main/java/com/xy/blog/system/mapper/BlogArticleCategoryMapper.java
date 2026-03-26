package com.xy.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.blog.system.dto.BlogArticleCategoryQueryDto;
import com.xy.blog.system.entity.po.BlogArticleCategory;
import com.xy.blog.system.vo.AppCategoryListVo;
import com.xy.blog.system.vo.BlogArticleCategoryListVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 分页查询后台分类列表，并统计每个分类下的文章数量。
     */
    @Select("""
        <script>
        SELECT c.category_id AS categoryId,
               c.category_name AS categoryName,
               c.parent_id AS parentId,
               c.sort_num AS sortNum,
               c.status AS status,
               c.remark AS remark,
               c.create_time AS createTime,
               COUNT(a.article_id) AS articleCount
        FROM blog_article_category c
        LEFT JOIN blog_article a ON c.category_id = a.category_id
        WHERE 1 = 1
        <if test="query.categoryName != null and query.categoryName != ''">
            AND c.category_name LIKE CONCAT('%', #{query.categoryName}, '%')
        </if>
        <if test="query.status != null and query.status != ''">
            AND c.status = #{query.status}
        </if>
        GROUP BY c.category_id, c.category_name, c.parent_id, c.sort_num, c.status, c.remark, c.create_time
        ORDER BY c.sort_num ASC, c.category_id ASC
        </script>
        """)
    IPage<BlogArticleCategoryListVo> selectAdminCategoryPage(Page<BlogArticleCategoryListVo> page,
                                                             @Param("query") BlogArticleCategoryQueryDto query);

    /**
     * 统计分类下的文章数量。
     */
    @Select("""
        SELECT COUNT(1)
        FROM blog_article
        WHERE category_id = #{categoryId}
        """)
    long selectCountArticlesByCategoryId(@Param("categoryId") Long categoryId);
}
