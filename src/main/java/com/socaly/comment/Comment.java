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
        String commentPointsText;

        if (commentPoints == 1 || commentPoints == -1) {
            commentPointsText = "1 point";
        } else {
            commentPointsText = commentPoints + " points";
        }

        return commentPointsText;
    }

    public static String getCommentReplyCountText(int commentReplyCount) {
        String commentReplyCountText;

        if (commentReplyCount == 1) {
            commentReplyCountText = "1 reply";
        } else {
            commentReplyCountText = commentReplyCount + " replies";
        }

        return commentReplyCountText;
    }
}
