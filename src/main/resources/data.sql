INSERT INTO users (login_id,name,password,email,created_at,updated_at)
VALUES
    ('login1','홍길동1','1111','111@gmail.com',NOW(),NOW()),
    ('login2','홍길동2','2222','222@naver.com',NOW(),NOW());


INSERT INTO posts (title,content,user_id,created_at,updated_at)
VALUES
    ('제목','내용',1,NOW(),NOW());