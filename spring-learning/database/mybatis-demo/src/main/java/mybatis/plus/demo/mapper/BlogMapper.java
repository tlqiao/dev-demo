package mybatis.plus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mybatis.plus.demo.model.Blog;
import mybatis.plus.demo.model.BlogUserVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogMapper extends BaseMapper<Blog> {
    //多表查询
    @Select("SELECT t_blog.*,t_user.`name` FROM t_user,t_blog WHERE t_blog.user_id=t_user.id")
    List<BlogUserVo> getAllUserBlogs();
    //多表分页查询
    @Select("SELECT t_blog.*,t_user.`name` FROM t_user,t_blog WHERE t_blog.user_id=t_user.id")
    List<BlogUserVo> getAllUserBlogsByPage(Page<BlogUserVo> page);
}
