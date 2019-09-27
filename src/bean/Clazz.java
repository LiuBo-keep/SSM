package bean;


/**
 * 班级实体
 *
 */
public class Clazz {
    private Long id;//年级id
    private String name;//年级名称
    private Long gradeId;//年级id
    private String remark;//备注

    public Clazz() {
    }

    public Clazz(Long id, Long gradeId, String name, String remark) {
        this.id = id;
        this.gradeId = gradeId;
        this.name = name;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", gradeId=" + gradeId +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
