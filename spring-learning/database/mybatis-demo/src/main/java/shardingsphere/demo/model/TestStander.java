package shardingsphere.demo.model;

import lombok.Data;

@Data
//@TableName("test_standard_1")
public class TestStander {
//    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
}
