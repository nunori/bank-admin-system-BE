package com.im.branchlayout.entity;

import com.im.branchlayout.enums.ElementType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "element_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ElementInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "element_id")
    private Integer elementId;

    @Column(name = "dept_id", nullable = false)
    private Integer deptId;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Column(name = "element_name", nullable = false)
    private String elementName;

    @Enumerated(EnumType.STRING)
    @Column(name = "element_type", nullable = false)
    private ElementType elementType;

    @Column(name = "element_type_description", nullable = false)
    private String elementTypeDescription;

    @Column(name = "element_grid_x")
    private Integer elementGridX;

    @Column(name = "element_grid_y")
    private Integer elementGridY;

    @Column(name = "element_width", nullable = false)
    private Integer elementWidth;

    @Column(name = "element_height", nullable = false)
    private Integer elementHeight;

    @Column(name = "element_color")
    private String elementColor;

    @PrePersist
    @PreUpdate
    private void setElementTypeDescription() {
        if (this.elementType != null) {
            switch (this.elementType) {
                case window:
                    this.elementTypeDescription = "창구";
                    break;
                case wall:
                    this.elementTypeDescription = "벽";
                    break;
                case entrance:
                    this.elementTypeDescription = "입구";
                    break;
                default:
                    this.elementTypeDescription = "기타";
                    break;
            }
        }
    }

}
