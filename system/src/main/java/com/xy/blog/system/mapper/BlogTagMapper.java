package com.xy.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.blog.system.entity.po.BlogTag;
import com.xy.blog.system.vo.AppTagListVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 博客标签 Mapper 接口。
 */
public interface BlogTagMapper extends BaseMapper<BlogTag> {

    /**
     * 统计启用标签数量。
     */
    @Select("""
        <script>
        SELECT COUNT(1)
        FROM blog_tag
        WHERE status = '1'
          AND tag_id IN
          <foreach collection="tagIds" item="tagId" open="(" separator="," close=")">
              #{tagId}
          </foreach>
        </script>
        """)
    long countEnabledTagsByIds(@Param("tagIds") List<Long> tagIds);

    /**
     * 查询前台启用标签列表，并统计每个标签下的已发布文章数量。
     */
    @Select("""
        SELECT t.tag_id AS tagId,
               t.tag_name AS tagName,
               t.sort_num AS sortNum,
               COUNT(DISTINCT a.article_id) AS articleCount
        FROM blog_tag t
        LEFT JOIN blog_article_tag bat ON t.tag_id = bat.tag_id
        LEFT JOIN blog_article a
               ON bat.article_id = a.article_id
              AND a.status = '1'
        WHERE t.status = '1'
        GROUP BY t.tag_id, t.tag_name, t.sort_num
        ORDER BY t.sort_num ASC, t.tag_id ASC
        """)
    List<AppTagListVo> selectPublicTagList();
}