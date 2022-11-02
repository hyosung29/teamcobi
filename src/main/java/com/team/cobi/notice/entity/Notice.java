package com.team.cobi.notice.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.notice.dto.NoticeCreateRequest;
import com.team.cobi.notice.dto.NoticeUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity implements Serializable {

    // DTO = 내가 화면에서 입력한 값을 주고 받는 작은 바구니
    // Entity = DB 에 저장된 값과 DTO 를 비교할 때 사용
    // 자세한 내용은 디스코드 참고하세요

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    private String title;

    private String content;

    public Notice(NoticeCreateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void update(NoticeUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void delete() {
        this.deleteFlag = true;
    }
}
