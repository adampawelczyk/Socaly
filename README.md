# Project Name
> Socaly-Backend

## Table of contents
* [General info](#general-info)
* [Installation](#installation)
* [API Endpoints](#api-endpoints)
    * [Auth Controller](#auth-controller)
        * [Sign Up](#sign-up)
        * [Log In](#log-in)
        * [Log Out](#log-out)
        * [Refresh Token](#refresh-token)
        * [Verify Account](#verify-account)
    * [Comment Controller](#comment-controller)
        * [Create Comment](#create-comment)
        * [Edit Comment](#edit-comment)
        * [Get Comment](#get-comment)
        * [Get All Comments By Post](#get-all-comments-by-post)
        * [Get All Comments By User](#get-all-comments-by-user)
        * [Get Sub Comments](#get-sub-comments)
    * [Comment Vote Controller](#comment-vote-controller)
        * [Vote](#comment-vote)
    * [Community Controller](#community-controller)
        * [Create Community](#create-community)
        * [Get Community](#get-community)
        * [Get All Communities](#get-all-communities)
        * [Get All Communities By User](#get-all-communities-by-user)
        * [Join Community](#join-community)
        * [Leave Community](#leave-community)
    * [Post Controller](#post-controller)
        * [Create Post](#create-post)
        * [Get Post](#get-post)
        * [Get All Posts](#get-all-posts)
        * [Get All Posts By Community](#get-all-posts-by-community)
        * [Get All Posts By User](#get-all-posts-by-user)
        * [Edit Post](#edit-post)
    * [Post Vote Controller](#post-vote-controller)
        * [Vote](#post-vote)
    * [User Community Settings Controller](#user-community-settings-controller)
        * [Get User Community Settings](#get-user-community-settings)
        * [Update Community Content Sorting](#update-community-content-sorting)
        * [Update Community Show Theme](#update-community-show-theme)
    * [User Controller](#user-controller)
        * [Get Current User](#get-current-user)
        * [Get User](#get-user)
        * [Get Email](#get-email)
        * [Get Profile Image](#get-profile-image)
        * [Is Email Verified](#is-email-verified)
        * [Change Email](#change-email)
        * [Change Password](#change-password)
        * [Change Description](#change-description)
        * [Change Profile Image](#change-profile-image)
        * [Change Profile Banner](#change-profile-banner)
        * [Delete Account](#delete-account)
    * [User Settings Controller](#user-settings-controller)
        * [Get User Settings](#get-user-settings)
        * [Update Post Comment Emails](#update-post-comment-emails)
        * [Update Post Up Vote Emails](#update-post-up-vote-emails)
        * [Update Comment Reply Emails](#update-comment-reply-emails)
        * [Update Comment Up Vote Emails](#update-comment-up-vote-emails)
        * [Update Default Community Content Sorting](#update-default-community-content-sorting)
        * [Update Open Posts In New Tab](#update-open-posts-in-new-tab)
* [Acknowledgments](#acknowledgments)

## General info
Socaly-Backend is the backend application for Socaly, a Reddit-inspired social media platform. It's developed using Java and Spring Boot.

## Installation
To set up the project locally, follow these steps:
1. Clone the Repository:
```bash
git clone https://github.com/adampawelczyk/Socaly-Backend.git
```
2. Configuration
* Database: Set up database credentials in the application.properties file.
```properties
spring.datasource.url=your-database-url
```
* Generate the Java KeyStore (JKS) file. You can use the following command:
```bash
keytool -genkey -alias your-alias-name -storetype JKS -keyalg RSA -keysize 2048 -keystore your-keystore-name.jks
```
* Configure the JKS file inside the JwtProvider class:

Replace JKS-file-path, your-keystore-password, and your-keystore-alias with your actual values.
```java
@PostConstruct
public void init() {
    try {
        keyStore = KeyStore.getInstance("JKS");
        InputStream resourceAsStream = getClass().getResourceAsStream("JKS-file-path");
        keyStore.load(resourceAsStream, "your-keystore-password".toCharArray());
    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
        throw new SecurityException("Exception occurred while loading keystore");
    }
}
```
```java
private PrivateKey getPrivateKey() {
    try {
        return (PrivateKey) keyStore.getKey("your-keystore-alias", "your-keystore-password".toCharArray());
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
        throw new SecurityException("Exception occurred while retrieving public key from keystore");
    }
}
```
```java
private PublicKey getPublicKey() {
    try {
        return keyStore.getCertificate("your-keystore-alias").getPublicKey();
    } catch (KeyStoreException e) {
        throw new SecurityException("Exception occurred while retrieving public key from keystore");
    }
}
```
* SMTP Configuration: 

For email services, set up the spring.mail.username and spring.mail.password properties in the application.properties file.
```properties
spring.mail.username=your-email-username
spring.mail.password=your-email-password
```
## API Endpoints
### Auth Controller

<details>
<summary><code><span id="sign-up" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/auth/sign-up</code></summary>

### Sign Up
#### Description:
* Endpoint used to sign up a new user

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "email": "string",
        "password": "string",
        "username": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** string

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="log-in" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/auth/log-in</code></summary>

### Log In
#### Description:
* Endpoint used to log in a user

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "username": "string",
        "password": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    {
        "authenticationToken": "string",
        "expiresAt": "2023-12-02T22:26:50.267Z",
        "refreshToken": "string",
        "username": "string"
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="log-out" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/auth/log-out</code></summary>

### Log out
#### Description:
* Endpoint used to log out a user

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "refreshToken": "string",
        "username": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** string

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="refresh-token" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/auth/refresh-token</code></summary>

### Refresh Token
#### Description:
* Endpoint used to refresh an authentication token

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "refreshToken": "string",
        "username": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    {
        "authenticationToken": "string",
        "expiresAt": "2023-12-03T00:08:27.285Z",
        "refreshToken": "string",
        "username": "string"
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="verify-account" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/auth/verify-account/{token}</code></summary>

### Verify Account
#### Description:
* Endpoint used to verify an account

#### Path Parameters:
* ```token``` - verification token

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** string

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

### Comment Controller

<details>
<summary><code><span id="create-comment" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/comment/create</code></summary>

### Create a Comment
#### Description:
* Endpoint used to create a new comment

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "parentCommentId": 0,
        "postId": 0,
        "text": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

<br/>

<details>
<summary><code><span id="edit-comment" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/comment/edit/{id}</code></summary>

### Edit a Comment
#### Description:
* Endpoint used to edit a comment

#### Path Parameters:
* ```id``` - id of a comment to edit

#### Request Body:
* **Content-Type:** application/json

    ```string``` - comment text to edit

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-comment" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/comment/get/{id}</code></summary>

### Get a Comment
#### Description:
* Endpoint used to get a comment by its id

#### Path Parameters:
* ```id``` - id of a comment you want to get

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    {
        "downVote": false,
        "id": 0,
        "parentCommentId": 0,
        "points": 0,
        "postId": 0,
        "text": "string",
        "timeSinceCreation": "string",
        "timeSinceEdit": "string",
        "upVote": true,
        "username": "string"
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-all-comments-by-post" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/comment/get/all/by-post/{id}</code></summary>

### Get All Comments By Post
#### Description:
* Endpoint used to get all of the comments that belong to a post with a specified id

#### Path Parameters:
* ```id``` - id of a post from which you want to get all comments

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    [
        {
            "downVote": false,
            "id": 0,
            "parentCommentId": 0,
            "points": 0,
            "postId": 0,
            "text": "string",
            "timeSinceCreation": "string",
            "timeSinceEdit": "string",
            "upVote": true,
            "username": "string"
        }
    ]
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-all-comments-by-user" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/comment/get/all/by-user/{name}</code></summary>

### Get All Comments By User
#### Description:
* Endpoint used to get all of the comments that belong to a specified user

#### Path Parameters:
* ```name``` - name of a user from which you want to get all comments

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    [
        {
            "downVote": false,
            "id": 0,
            "parentCommentId": 0,
            "points": 0,
            "postId": 0,
            "text": "string",
            "timeSinceCreation": "string",
            "timeSinceEdit": "string",
            "upVote": true,
            "username": "string"
        }
    ]
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-sub-comments" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/comment/get/sub-comments/{id}</code></summary>

### Get Sub Comments
#### Description:
* Endpoint used to get the sub comments for a specified comment

#### Path Parameters:
* ```id``` - id of the parent comment from which you want to get the sub comments

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** 

    ```json
    [
        {
            "downVote": false,
            "id": 0,
            "parentCommentId": 0,
            "points": 0,
            "postId": 0,
            "text": "string",
            "timeSinceCreation": "string",
            "timeSinceEdit": "string",
            "upVote": true,
            "username": "string"
        }
    ]
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

### Comment Vote Controller

<details>
<summary><code><span id="comment-vote" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/comment/vote</code></summary>

### Vote
#### Description:
* Endpoint used to vote for a comment

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json
* **Available values:** UPVOTE, DOWNVOTE

    ```json
    {
        "commentId": 0,
        "voteType": "DOWNVOTE"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

### Community Controller

<details>
<summary><code><span id="create-community" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/community/create</code></summary>

### Create New Community
#### Description:
* Endpoint used to create a new community

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "description": "string",
        "name": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** 

    ```json
    {
        "createdDate": "2023-12-03T18:23:51.987Z",
        "description": "string",
        "id": 0,
        "name": "string",
        "numberOfUsers": 0
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-community" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/community/get/{name}</code></summary>

### Get Community
#### Description:
* Endpoint used to get a specified community

#### Path Parameters:
* ```name``` - name of the community you want to get

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    {
        "createdDate": "2023-12-03T18:25:14.893Z",
        "description": "string",
        "id": 0,
        "name": "string",
        "numberOfUsers": 0
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-all-communities" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/community/get/all</code></summary>

### Get All Communities
#### Description:
* Endpoint used to get all communities

#### Path Parameters:
* none

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    [
        {
            "createdDate": "2023-12-03T18:28:05.569Z",
            "description": "string",
            "id": 0,
            "name": "string",
            "numberOfUsers": 0
        }
    ]
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-all-communities-by-user" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/community/get/all/by-user/{name}</code></summary>

### Get All Communities By User
#### Description:
* Endpoint used to get all communities to which the specified user belongs to

#### Path Parameters:
* ```name``` - name of the user you want to get the communities it belongs to

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    [
        {
            "createdDate": "2023-12-03T18:28:05.569Z",
            "description": "string",
            "id": 0,
            "name": "string",
            "numberOfUsers": 0
        }
    ]
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="join-community" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/community/join/{name}</code></summary>

### Join Community
#### Description:
* Endpoint used to join the specified community

#### Path Parameters:
* ```name``` - name of the community you want to join

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="leave-community" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/community/leave/{name}</code></summary>

### Leave Community
#### Description:
* Endpoint used to leave the specified community

#### Path Parameters:
* ```name``` - name of the community you want to leave

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

### Post Controller

<details>
<summary><code><span id="create-post" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/post/create</code></summary>

### Create Post
#### Description:
* Endpoint used to create a new post

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "communityName": "string",
        "description": "string",
        "images": [
            "string"
        ],
        "title": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** ```integer``` - id of a newly created post

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

<br/>

<details>
<summary><code><span id="get-post" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/post/get/{id}</code></summary>

### Get Post
#### Description:
* Endpoint used to get a post with the specified id

#### Path Parameters:
* ```id``` - id of the post you want ot get

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    {
        "commentCount": 0,
        "communityName": "string",
        "description": "string",
        "downVote": false,
        "id": 0,
        "images": [
            "string"
        ],
        "points": 0,
        "timeSinceCreation": "string",
        "timeSinceEdit": "string",
        "title": "string",
        "upVote": true,
        "username": "string"
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-all-posts" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/post/get/all</code></summary>

### Get All Posts
#### Description:
* Endpoint used to get all posts

#### Path Parameters:
* none

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    [
        {
            "commentCount": 0,
            "communityName": "string",
            "description": "string",
            "downVote": false,
            "id": 0,
            "images": [
                "string"
            ],
            "points": 0,
            "timeSinceCreation": "string",
            "timeSinceEdit": "string",
            "title": "string",
            "upVote": true,
            "username": "string"
        }
    ]
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-all-posts-by-community" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/post/get/all/by-community/{name}</code></summary>

### Get All Posts By Community
#### Description:
* Endpoint used to get all posts that belong to a specified community

#### Path Parameters:
* ```name``` - name of the community you want to get all posts

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    [
        {
            "commentCount": 0,
            "communityName": "string",
            "description": "string",
            "downVote": false,
            "id": 0,
            "images": [
                "string"
            ],
            "points": 0,
            "timeSinceCreation": "string",
            "timeSinceEdit": "string",
            "title": "string",
            "upVote": true,
            "username": "string"
        }
    ]
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-all-posts-by-user" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/post/get/all/by-user/{name}</code></summary>

### Get All Posts By User
#### Description:
* Endpoint used to get all posts that belongs to a specified user

#### Path Parameters:
* ```name``` - name of the user you want to get all posts

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    [
        {
            "commentCount": 0,
            "communityName": "string",
            "description": "string",
            "downVote": false,
            "id": 0,
            "images": [
                "string"
            ],
            "points": 0,
            "timeSinceCreation": "string",
            "timeSinceEdit": "string",
            "title": "string",
            "upVote": true,
            "username": "string"
        }
    ]
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="edit-post" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/post/edit/{id}</code></summary>

### Edit Post
#### Description:
* Endpoint used to edit a post with the specified id

#### Path Parameters:
* ```id``` - id of the post you want to edit

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "communityName": "string",
        "description": "string",
        "images": [
            "string"
        ],
        "title": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    [
        {
            "commentCount": 0,
            "communityName": "string",
            "description": "string",
            "downVote": false,
            "id": 0,
            "images": [
                "string"
            ],
            "points": 0,
            "timeSinceCreation": "string",
            "timeSinceEdit": "string",
            "title": "string",
            "upVote": true,
            "username": "string"
        }
    ]
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

### Post Vote Controller

<details>
<summary><code><span id="post-vote" style="color:rgb(73, 204, 144)">POST</span></code> <code>/api/post/vote</code></summary>

### Vote
#### Description:
* Endpoint used to vote for a post

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json
* **Available values:** UPVOTE, DOWNVOTE
    ```json
    {
        "postId": 0,
        "voteType": "DOWNVOTE"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

<br/>

### User Community Settings Controller

<details>
<summary><code><span id="get-user-community-settings" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/user/community/settings/get/{id}</code></summary>

### Get User Community Settings
#### Description:
* Endpoint used to get the user community setting for a specified community

#### Path Parameters:
* ```id``` - id of the community you want to get the user community settings

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    {
        "communityContentSort": "HOT",
        "showTheme": true
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="update-community-content-sorting" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/community/settings/update/community-content-sorting/{id}</code></summary>

### Update Community Content Sorting
#### Description:
* Endpoint used to update the content sorting for a specified community

#### Path Parameters:
* ```id``` - id of the community you want to update the content sorting

#### Request Body:
* **Content-Type:** application/json
* **Available values:** HOT, NEW, TOP_ALL_TIME, TOP_MONTH, TOP_TODAY, TOP_WEEK, TOP_YEAR

    ```
    string
    ```


#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="update-community-show-theme" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/community/settings/update/show-theme/{id}</code></summary>

### Update Community Show Theme
#### Description:
* Endpoint used to update the community show theme setting

#### Path Parameters:
* ```id``` - id of the community you want to update the show theme setting

#### Request Body:
* **Content-Type:** application/json

    ```
    boolean
    ```


#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

### User Controller

<details>
<summary><code><span id="get-current-user" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/user/get</code></summary>

### Get Current User
#### Description:
* Endpoint used to get the current user

#### Path Parameters:
* none

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** 

    ```json
    {
        "creationDate": "string",
        "description": "string",
        "profileBanner": "string",
        "profileImage": "string"
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-user" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/user/get/{name}</code></summary>

### Get User
#### Description:
* Endpoint used to get the user with the specified name

#### Path Parameters:
* ```name``` - name of the user you want to get

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** 

    ```json
    {
        "creationDate": "string",
        "description": "string",
        "profileBanner": "string",
        "profileImage": "string"
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-email" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/user/get/email</code></summary>

### Get Current User Email
#### Description:
* Endpoint used to get the current user email

#### Path Parameters:
* none

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** ```string``` - current user email

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="get-profile-image" style="color:rgb(97, 175, 254)">GET</span></code> <code>​/api​/user​/get​/profile​/image​/{name}</code></summary>

### Get User Profile Image
#### Description:
* Endpoint used to get the profile image of a specified user

#### Path Parameters:
* ```name``` - name of the user you want to get the profile image

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** ```string``` - image url

- **201**
  - **Content-Type:** text-plain
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="is-email-verified" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/user/is-email-verified</code></summary>

### Is Email Verified
#### Description:
* Endpoint used to check if the current user email has been verified

#### Path Parameters:
* none

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** ```boolean```

- **201**
  - **Content-Type:** text-plain
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="is-email-verified" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/user/is/deleted/{name}</code></summary>

### Is Email Verified
#### Description:
* Endpoint used to check if the specified user has deleted their account

#### Path Parameters:
* ```name``` - name of the user you want to check if they deleted their account

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:** ```boolean```

- **201**
  - **Content-Type:** text-plain
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="change-email" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/change/email</code></summary>

### Change Email
#### Description:
* Endpoint used to change the email of the current user

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "email": "string",
        "password": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="change-password" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/change/password</code></summary>

### Change Password
#### Description:
* Endpoint used to change the password of the current user

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "currentPassword": "string",
        "newPassword": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="change-description" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/change/description</code></summary>

### Change Description
#### Description:
* Endpoint used to change the description of the current user

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```string``` - text of the description to change

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="change-profile-image" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/change/profile/image</code></summary>

### Change Profile Image
#### Description:
* Endpoint used to change the profile image of the current user

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```string``` - image url

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="change-profile-banner" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/change/profile/banner</code></summary>

### Change Profile Banner
#### Description:
* Endpoint used to change the profile banner of the current user

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```string``` - image url

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="delete-account" style="color:rgb(249, 62, 62)">DELETE</span></code> <code>/api/user/delete</code></summary>

### Delete Account
#### Description:
* Endpoint used to delete the current user account

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```json
    {
        "password": "string",
        "username": "string"
    }
    ```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

### User Settings Controller

<details>
<summary><code><span id="get-user-settings" style="color:rgb(97, 175, 254)">GET</span></code> <code>/api/user/settings/get</code></summary>

### Get User Settings
#### Description:
* Endpoint used to get the current user settings

#### Path Parameters:
* none

#### Request Body:
* none

#### Responses:
- **200**
  - **Content-Type:** application/json
  - **Response:**

    ```json
    {
        "commentReplyEmails": true,
        "commentUpVoteEmails": true,
        "communityContentSort": "HOT",
        "openPostsInNewTab": true,
        "postCommentEmails": true,
        "postUpVoteEmails": true,
        "rememberLastCommunityContentSort": true,
        "useCustomCommunityThemes": true
    }
    ```

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="update-post-comment-emails" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/settings/update/post-comment-emails</code></summary>

### Update Post Comment Emails
#### Description:
* Endpoint used to update settings to receive emails when someone comments on your post

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```boolean```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="update-post-up-vote-emails" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/settings/update/post-up-vote-emails</code></summary>

### Update Post Up Vote Emails
#### Description:
* Endpoint used to update settings to receive emails when someone up votes your post

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```boolean```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="update-comment-reply-emails" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/settings/update/comment-reply-emails</code></summary>

### Update Comment Reply Emails
#### Description:
* Endpoint used to update settings to receive emails when someone replies on your comment

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```boolean```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="update-comment-up-vote-emails" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/settings/update/comment-up-vote-emails</code></summary>

### Update Comment Up Vote Emails
#### Description:
* Endpoint used to update settings to receive emails when someone up votes your comment

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```boolean```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="update-default-community-content-sorting" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/settings/update/community-content-sort</code></summary>

### Update Default Community Content Sorting
#### Description:
* Endpoint used to update the default community content sorting

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json
* **Available values:** HOT, NEW, TOP_ALL_TIME, TOP_MONTH, TOP_TODAY, TOP_WEEK, TOP_YEAR

    ```string```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

<details>
<summary><code><span id="update-open-posts-in-new-tab" style="color:rgb(80, 227, 194)">PATCH</span></code> <code>/api/user/settings/update/open-posts-in-new-tab</code></summary>

### Update Open Posts In New Tab
#### Description:
* Endpoint used to update the setting of opening the posts in a new tab

#### Path Parameters:
* none

#### Request Body:
* **Content-Type:** application/json

    ```boolean```

#### Responses:
- **200**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **201**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **401**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **403**
  - **Content-Type:** not explicitly set
  - **Response:** none

- **404**
  - **Content-Type:** not explicitly set
  - **Response:** none
    
</details>

</br>

## Acknowledgments
- **[Timeago Library](https://github.com/marlonlom/timeago)**

## License
This project is licensed under the [MIT License](https://github.com/adampawelczyk/Socaly-Backend/blob/master/LICENSE) - see the [LICENSE](https://github.com/adampawelczyk/Socaly-Backend/blob/master/LICENSE) file for details.
