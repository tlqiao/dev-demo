package geektime.spring.springbucks.jpademo.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@NoRepositoryBean
//每个实体类有需要实现的相同的方法，就可以单独抽取出来，放在一个公共的接口BaseRepository中，且这个类继承了jpa的相关Repository接口或类
//添加上面的注解后Spring就不会尝试创建实例
public interface BaseRepository<T, Long> extends PagingAndSortingRepository<T, Long> {
    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
