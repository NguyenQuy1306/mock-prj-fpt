insert into users(user_id,user_role,email,first_name,last_name,name,password,phone_number,activated)
values (1,'S','abc@gmail.com','abc','def','ggg','12345','1234', true);
insert into users(user_id,user_role,email,first_name,last_name,name,password,phone_number,activated)
values (2,'S','nnnn@gmail.com','nnnn','nnnn','nnnn','nnnn','nnnn232', true);
insert into users(user_id,user_role,email,first_name,last_name,name,password,phone_number,activated)
values (3,'I','vvvv@gmail.com','vvvv','vvv','vv','vv','vvvv', true);
insert into users(user_id,user_role,email,first_name,last_name,name,password,phone_number,activated)
values (4,'S','ffff@gmail.com','vvv1v','ada','ada','ad','123', true);
insert into users(user_id,user_role,email,first_name,last_name,name,password,phone_number,activated)
values (5,'I','fffgggf@gmail.com','vvg1v1v','agda','gada','agd','12gg3', true);
insert into users(user_id,user_role,email,first_name,last_name,name,password,phone_number,activated)
values (6,'A','admin@gmail.com','123','123123','asdasd','aaaagd','12aagg3', true);
insert into students(user_id)
values(1);
insert into students(user_id)
values(2);
insert into instructors(user_id)
values(3);
insert into instructors(user_id)
values(5);
insert into students(user_id)
values(4);
insert into admins(user_id)
values(6);


insert into categories(category_id,category_name)
values (1,'PPL');
insert into categories(category_id,category_name)
values (2,'DSA');
insert into categories(category_id,category_name)
values (3,'network');

insert into courses(course_id,category_id,instructor_id,price,description,title, course_thumbnail,created_at,status)
values (1,1,3,10000000,'day la khoa ppl','PPL- programming','dmdev',now(),'PENDING_APPROVAL');
insert into courses(course_id,category_id,instructor_id,price,description,title, course_thumbnail,created_at,status)
values (2,2,3,20000,'day la khoa dsa','dsa- data structure','dev',now(),'PENDING_APPROVAL');
insert into courses(course_id,category_id,instructor_id,price,description,title, course_thumbnail,created_at)
values (3,2,3,80000,'day la khoa abc','software','dev',now());
insert into carts(cart_id,student_id)
values (1,1);
insert into carts(cart_id,student_id)
values (2,2);
insert into carts(cart_id,student_id)
values (3,4);
insert into cart_items(cart_id,course_id)
values (1,1);
insert into cart_items(cart_id,course_id)
values (1,2);
insert into cart_items(cart_id,course_id)
values (2,1);
insert into cart_items(cart_id,course_id)
values (2,2);
insert into discounts(discount_id,description,value,start_date,end_date,code)
values(1,'giam gia',20000,now(),now(),'nnngyuyeneh');
insert into discounts(discount_id,description,value,start_date,end_date,code)
values(2,'giam gia123',40000,now(),now(),'nnngyu23232232yeneh');
insert into student_discounts(is_used,discount_id,student_id)
values(false,1,1);
insert into student_discounts(is_used,discount_id,student_id)
values(false,2,1);

insert into enrollments(is_complete,course_id,enrollment_date,enrollment_id,student_id)
values(true,1,now(),1,1);
insert into enrollments(is_complete,course_id,enrollment_date,enrollment_id,student_id)
values(false,2,now(),2,1);

SELECT * FROM public.users
ORDER BY user_id ASC ;
SELECT * FROM public.students;
SELECT * FROM public.instructors;
SELECT * FROM public.courses;
select * from courses;
select * from categories;
select * from carts;
select * from cart_items;
-- select * from student_discounts;
 -- select * from discounts;
select * from order_items;
select * from enrollments;
select * from ratings;

-- COPY users(activated,user_id,user_role,email,first_name,last_name,name,password,phone_number)
-- FROM 'C:\Users\cnyegun\Downloads\users_rows.csv'
-- DELIMITER ','
-- CSV HEADER;

-- COPY courses(avg_rating, category_id, course_id, created_at,instructor_id,price,total_rating,course_thumbnail,description,title)
-- FROM 'C:\Users\cnyegun\Downloads\courses_rows.csv'
-- DELIMITER ','
-- CSV HEADER;
