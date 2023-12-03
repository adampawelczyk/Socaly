package com.socaly.comment;

import com.socaly.post.Post;
import com.socaly.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String text;

    private Integer points = 0;
    private Instant creationDate;
    private Instant editDate;

    @Nullable
    private Long parentCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public static String getCommentPointsText(int commentPoints) {
        return (commentPoints == 1 || commentPoints == -1) ? "1 point" : commentPoints + " points";
    }

    public static String getCommentReplyCountText(int commentReplyCount) {
        return (commentReplyCount == 1) ? "1 reply" : commentReplyCount + " replies";
    }
}
