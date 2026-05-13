INSERT INTO users (login_id,name,password,email,created_at,updated_at,role)
VALUES
    ('login1','홍길동1','$2a$12$5JA9Dwb7Hut2vJZcS8ZdYOy5T/EGWhV6m8/Srn2t4moNSrtwwdjq6','111@gmail.com',NOW(),NOW(),'ROLE_USER'),
    ('login2','홍길동2','2222','222@naver.com',NOW(),NOW(),'ROLE_ADMIN');


INSERT INTO posts (title,content,user_id,created_at,updated_at)
VALUES
    ('제목','내용',1,NOW(),NOW());