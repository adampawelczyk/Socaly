package com.socaly.post;

import com.socaly.community.Community;
import com.socaly.user.User;
import com.socaly.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Post title cannot be empty or null")
    private String postTitle;

    @Nullable
    @Lob
    private String description;

    private Integer voteCount = 0;
    private Instant creationDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Community community;

    public static String getPostPointsText(int postPoints) {
        String postPointsText;

        if (postPoints == 1 || postPoints == -1) {
            postPointsText = "1 point";
        } else {
            postPointsText = postPoints + " points";
        }

        return postPointsText;
    }

    public static String getPostCommentCountText(int postCommentCount) {
        String postCommentCountText;

        if (postCommentCount == 1) {
            postCommentCountText = "1 comment";
        } else {
            postCommentCountText = postCommentCount + " comments";
        }

        return postCommentCountText;
    }
}
