CREATE TABLE public.users (
	user_id int4 NOT NULL,
	"name" varchar(100) NOT NULL,
	email varchar(50) NOT NULL,
	mobile_number varchar(20) NOT NULL,
	pwd varchar(200) NOT NULL,
	"role" varchar(20) NOT NULL,
	status varchar(20) NOT NULL,
	created_at timestamp NOT NULL,
	created_by varchar(50) NOT NULL,
	updated_at timestamp NULL,
	updated_by varchar(50) NULL,
	CONSTRAINT user_pk PRIMARY KEY (user_id)
);


CREATE TABLE public.reviews (
	review_id int4 NOT NULL,
	"name" varchar(50) NOT NULL,
	review_text varchar(2000) NOT NULL,
	"show" bool NOT NULL,
	created_at timestamp NOT NULL,
	created_by varchar(50) NOT NULL,
	updated_at timestamp NULL,
	updated_by varchar(50) NULL,
	CONSTRAINT reviews_pk PRIMARY KEY (review_id)
);


CREATE TABLE public.tours (
	tour_id numeric NOT NULL, -- Tour Id
	"name" varchar(64) NOT NULL, -- Tour Name
	begin_date date NOT NULL, -- Tour Begin Date
	end_date date NOT NULL, -- Tour End Date
	fees varchar NOT NULL,
	"show" bool NOT NULL,
	image_path varchar(200) NULL,
	"type" varchar(20) NOT NULL,
    created_at date NOT NULL, -- Record created time
    created_by varchar(50) NOT NULL, -- User created the record
    updated_at date NULL, -- Last update time
    updated_by varchar(50) NULL, -- User who updated record
	CONSTRAINT tours_pk PRIMARY KEY (tour_id)
);
COMMENT ON TABLE public.tours IS 'List of tours';

CREATE TABLE public.contact_msg (
	contact_id numeric NOT NULL,
	"name" varchar(100) NOT NULL,
	mobile_num varchar(10) NOT NULL,
	email varchar(100) NOT NULL,
	subject varchar(100) NOT NULL,
	message varchar(500) NOT NULL,
	status varchar(10) NOT NULL,
	type varchar(500) NOT NULL,
    tour_id numeric(10) NULL,
	created_at date NOT NULL,
	created_by varchar(50) NOT NULL,
	updated_at date NULL,
	updated_by varchar(50) NULL,
	CONSTRAINT contact_msg_pk PRIMARY KEY (contact_id)
);

ALTER TABLE public.contact_msg ADD CONSTRAINT contact_msg_fk FOREIGN KEY (tour_id) REFERENCES public.tours(tour_id);

INSERT INTO public.users (user_id,"name",email,mobile_number,pwd,"role",status,created_at,created_by,updated_at,updated_by) VALUES
	 (1,'admin','admin@gmail.com','9992345678','$2a$12$ksqu0CLAqzRLvyv3Y/5.eeCl3Gf5Geq19W3ef03kNmDobJyULuJ/C','ADMIN','Active','2024-11-23 22:33:39.531','Admin','2024-11-23 22:33:39.531','Admin');

INSERT INTO public.users (user_id,"name",email,mobile_number,pwd,"role",status,created_at,created_by,updated_at,updated_by) VALUES
	 (2,'anonymous','anonymous@gmail.com','9992345678','$2a$12$ksqu0CLAqzRLvyv3Y/5.eeCl3Gf5Geq19W3ef03kNmDobJyULuJ/C','ANONYMOUS','Active','2024-11-23 22:33:39.531','Admin','2024-11-23 22:33:39.531','Admin');


