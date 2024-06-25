package geektime.spring.springbucks.jpademo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "T_ORDER")
@Data
@ToString(callSuper = true)
//使用了@Data注解, 调用toString时只会打印子类本身的属性值, 如果想要打印父类的属性，
//子类加上@Data和@ToString(callSuper = true)两个注解, 父类也使用注解@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//通过@Builder注解，lombok还可以方便的实现建造者模式
public class CoffeeOrder extends BaseEntity implements Serializable {
    private String customer;
    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    @OrderBy("id")
    private List<Coffee> items;
    @Enumerated
    //表示映射到枚举字段
    @Column(nullable = false)
    //@Column常用参数包括unique,nullable,updatable,insertable
    private OrderState state;
}
