package com.xy.blog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xy.blog.system.dto.BlogArticleAppQueryDto;
import com.xy.blog.system.dto.BlogArticleQueryDto;
import com.xy.blog.system.dto.BlogUserArticleQueryDto;
import com.xy.blog.system.entity.po.BlogArticle;
import com.xy.blog.system.vo.AppArticleArchiveItemVo;
import com.xy.blog.system.vo.AppArticleDetailVo;
import com.xy.blog.system.vo.AppArticleListVo;
import com.xy.blog.system.vo.AppArticleTagVo;
import com.xy.blog.system.vo.BlogArticleDetailVo;
import com.xy.blog.system.vo.BlogArticleListVo;
import com.xy.blog.system.vo.BlogUserArticleDetailVo;
import com.xy.blog.system.vo.BlogUserArticleListVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 博客文章 Mapper 接口。
 */
public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    @Select("""
        <script>
        SELECT a.article_id AS articleId,
               a.title AS title,
               a.summary AS summary,
               a.cover_image AS coverImage,
               a.category_id AS categoryId,
               c.category_name AS categoryName,
               a.user_id AS userId,
               u.user_name AS userName,
               u.nick_name AS nickName,
               a.status AS status,
               a.is_top AS isTop,
               a.view_count AS viewCount,
               a.like_count AS likeCount,
               a.publish_time AS publishTime,
               a.update_time AS updateTime
        FROM blog_article a
        LEFT JOIN blog_article_category c ON a.category_id = c.category_id
        LEFT JOIN blog_user u ON a.user_id = u.user_id
        WHERE 1 = 1
        <if test="query.title != null and query.title != ''">
            AND a.title LIKE CONCAT('%', #{query.title}, '%')
        </if>
        <if test="query.userName != null and query.userName != ''">
            AND u.user_name LIKE CONCAT('%', #{query.userName}, '%')
        </if>
        <if test="query.categoryId != null">
            AND a.category_id = #{query.categoryId}
        </if>
        <if test="query.status != null and query.status != ''">
            AND a.status = #{query.status}
        </if>
        <if test="query.isTop != null and query.isTop != ''">
            AND a.is_top = #{query.isTop}
        </if>
        ORDER BY a.is_top DESC, a.update_time DESC, a.article_id DESC
        </script>
        """)
    IPage<BlogArticleListVo> selectAdminArticlePage(Page<BlogArticleListVo> page, @Param("query") BlogArticleQueryDto query);

    @Select("""
        SELECT a.article_id AS articleId,
               a.title AS title,
               a.summary AS summary,
               a.cover_image AS coverImage,
               a.content_md AS contentMd,
               a.category_id AS categoryId,
               c.category_name AS categoryName,
               a.user_id AS userId,
               u.user_name AS userName,
               u.nick_name AS nickName,
               a.status AS status,
               a.is_top AS isTop,
               a.is_original AS isOriginal,
               a.original_url AS originalUrl,
               a.allow_comment AS allowComment,
               a.view_count AS viewCount,
               a.like_count AS likeCount,
               a.publish_time AS publishTime,
               a.update_time AS updateTime
        FROM blog_article a
        LEFT JOIN blog_article_category c ON a.category_id = c.category_id
        LEFT JOIN blog_user u ON a.user_id = u.user_id
        WHERE a.article_id = #{articleId}
        LIMIT 1
        """)
    BlogArticleDetailVo selectAdminArticleDetail(@Param("articleId") Long articleId);

    @Select("""
        <script>
        SELECT a.article_id AS articleId,
               a.title AS title,
               a.summary AS summary,
               a.cover_image AS coverImage,
               a.category_id AS categoryId,
               c.category_name AS categoryName,
               COALESCE(NULLIF(u.nick_name, ''), u.user_name) AS authorName,
               a.publish_time AS publishTime,
               a.view_count AS viewCount,
               a.is_top AS isTop
        FROM blog_article a
        LEFT JOIN blog_article_category c ON a.category_id = c.category_id
        LEFT JOIN blog_user u ON a.user_id = u.user_id
        WHERE a.status = '1'
        <if test="query.categoryId != null">
            AND a.category_id = #{query.categoryId}
        </if>
        <if test="query.keyword != null and query.keyword != ''">
            AND a.title LIKE CONCAT('%', #{query.keyword}, '%')
        </if>
        ORDER BY a.is_top DESC, a.publish_time DESC, a.article_id DESC
        </script>
        """)
    IPage<AppArticleListVo> selectPublicArticlePage(Page<AppArticleListVo> page, @Param("query") BlogArticleAppQueryDto query);

    @Select("""
        SELECT a.article_id AS articleId,
               a.title AS title,
               a.summary AS summary,
               a.cover_image AS coverImage,
               a.content_md AS contentMd,
               a.category_id AS categoryId,
               c.category_name AS categoryName,
               a.user_id AS authorId,
               COALESCE(NULLIF(u.nick_name, ''), u.user_name) AS authorName,
               a.publish_time AS publishTime,
               a.view_count AS viewCount,
               a.like_count AS likeCount,
               a.is_original AS isOriginal,
               a.original_url AS originalUrl,
               a.allow_comment AS allowComment
        FROM blog_article a
        LEFT JOIN blog_article_category c ON a.category_id = c.category_id
        LEFT JOIN blog_user u ON a.user_id = u.user_id
        WHERE a.article_id = #{articleId}
          AND a.status = '1'
        LIMIT 1
        """)
    AppArticleDetailVo selectPublicArticleDetail(@Param("articleId") Long articleId);

    @Select("""
        SELECT t.tag_id AS tagId,
               t.tag_name AS tagName
        FROM blog_article_tag bat
        INNER JOIN blog_tag t ON bat.tag_id = t.tag_id
        WHERE bat.article_id = #{articleId}
          AND t.status = '1'
        ORDER BY t.sort_num ASC, t.tag_id ASC
        """)
    List<AppArticleTagVo> selectTagListByArticleId(@Param("articleId") Long articleId);

    @Update("""
        UPDATE blog_article
        SET view_count = view_count + 1
        WHERE article_id = #{articleId}
        """)
    int increaseViewCount(@Param("articleId") Long articleId);

    @Select("""
        SELECT a.article_id AS articleId,
               a.title AS title,
               a.summary AS summary,
               a.cover_image AS coverImage,
               a.category_id AS categoryId,
               c.category_name AS categoryName,
               COALESCE(NULLIF(u.nick_name, ''), u.user_name) AS authorName,
               a.publish_time AS publishTime,
               a.view_count AS viewCount,
               a.is_top AS isTop
        FROM blog_article a
        LEFT JOIN blog_article_category c ON a.category_id = c.category_id
        LEFT JOIN blog_user u ON a.user_id = u.user_id
        WHERE a.status = '1'
        ORDER BY a.view_count DESC, a.publish_time DESC, a.article_id DESC
        LIMIT #{limit}
        """)
    List<AppArticleListVo> selectPublicHotArticleList(@Param("limit") int limit);

    @Select("""
        SELECT a.article_id AS articleId,
               a.title AS title,
               a.summary AS summary,
               a.cover_image AS coverImage,
               a.category_id AS categoryId,
               c.category_name AS categoryName,
               COALESCE(NULLIF(u.nick_name, ''), u.user_name) AS authorName,
               a.publish_time AS publishTime,
               a.view_count AS viewCount,
               a.is_top AS isTop
        FROM blog_article a
        LEFT JOIN blog_article_category c ON a.category_id = c.category_id
        LEFT JOIN blog_user u ON a.user_id = u.user_id
        WHERE a.status = '1'
        ORDER BY a.publish_time DESC, a.article_id DESC
        LIMIT #{limit}
        """)
    List<AppArticleListVo> selectPublicLatestArticleList(@Param("limit") int limit);

    @Select("""
        SELECT DATE_FORMAT(a.publish_time, '%Y-%m') AS yearMonth,
               a.article_id AS articleId,
               a.title AS title,
               a.publish_time AS publishTime
        FROM blog_article a
        WHERE a.status = '1'
          AND a.publish_time IS NOT NULL
        ORDER BY a.publish_time DESC, a.article_id DESC
        """)
    List<AppArticleArchiveItemVo> selectPublicArchiveItemList();

    @Select("""
        <script>
        SELECT a.article_id AS articleId,
               a.title AS title,
               a.summary AS summary,
               a.cover_image AS coverImage,
               a.category_id AS categoryId,
               c.category_name AS categoryName,
               a.status AS status,
               a.is_top AS isTop,
               a.publish_time AS publishTime,
               a.view_count AS viewCount,
               a.update_time AS updateTime
        FROM blog_article a
        LEFT JOIN blog_article_category c ON a.category_id = c.category_id
        WHERE a.user_id = #{userId}
        <if test="query.title != null and query.title != ''">
            AND a.title LIKE CONCAT('%', #{query.title}, '%')
        </if>
        <if test="query.status != null and query.status != ''">
            AND a.status = #{query.status}
        </if>
        <if test="query.categoryId != null">
            AND a.category_id = #{query.categoryId}
        </if>
        ORDER BY a.update_time DESC, a.article_id DESC
        </script>
        """)
    IPage<BlogUserArticleListVo> selectCurrentUserArticlePage(Page<BlogUserArticleListVo> page,
                                                              @Param("query") BlogUserArticleQueryDto query,
                                                              @Param("userId") Long userId);

    @Select("""
        SELECT a.article_id AS articleId,
               a.title AS title,
               a.summary AS summary,
               a.cover_image AS coverImage,
               a.content_md AS contentMd,
               a.category_id AS categoryId,
               c.category_name AS categoryName,
               a.status AS status,
               a.is_top AS isTop,
               a.is_original AS isOriginal,
               a.original_url AS originalUrl,
               a.allow_comment AS allowComment,
               a.view_count AS viewCount,
               a.like_count AS likeCount,
               a.publish_time AS publishTime,
               a.update_time AS updateTime
        FROM blog_article a
        LEFT JOIN blog_article_category c ON a.category_id = c.category_id
        WHERE a.article_id = #{articleId}
          AND a.user_id = #{userId}
        LIMIT 1
        """)
    BlogUserArticleDetailVo selectCurrentUserArticleDetail(@Param("articleId") Long articleId,
                                                           @Param("userId") Long userId);
}