package com.xy.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.blog.system.entity.po.BlogArticleTag;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 博客文章标签关联 Mapper 接口。
 */
public interface BlogArticleTagMapper extends BaseMapper<BlogArticleTag> {

    /**
     * 根据文章ID查询标签ID列表。
     */
    @Select("""
        SELECT tag_id
        FROM blog_article_tag
        WHERE article_id = #{articleId}
        ORDER BY id ASC
        """)
    List<Long> selectTagIdsByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据文章ID删除标签关联。
     */
    @Delete("""
        DELETE FROM blog_article_tag
        WHERE article_id = #{articleId}
        """)
    int deleteByArticleId(@Param("articleId") Long articleId);

    /**
     * 批量新增文章标签关联。
     */
    @Insert("""
        <script>
        INSERT INTO blog_article_tag (article_id, tag_id)
        VALUES
        <foreach collection="tagIds" item="tagId" separator=",">
            (#{articleId}, #{tagId})
        </foreach>
        </script>
        """)
    int insertBatch(@Param("articleId") Long articleId, @Param("tagIds") List<Long> tagIds);
}
