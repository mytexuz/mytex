insert into public.users (first_name, last_name, phone_number, photo, password,
                          username, email, status, lang, created_by, created_at)
values ('Admin', 'Admin', '+998998652321', 'image.jpg',
        '$2a$10$EoohCOxXLbxvudBb4T20z.8aCt3tiICdsF1HYxaIkD/CH7M05FTky', 'admin', 'admin@gmail.com', 'ACTIVE', 'EN', 0,
        current_timestamp);
--  password: Ab%1l2cc

insert into public.devices(device_type, mac_address, ip_address, user_agent, device_id, status, user_id, created_at)
values ('DESKTOP', 'ASDASFDF-DSFDF-SDFSDF', '192.167.23.1', 'ChromeApp', '381b74b9-085f-46d4-a013-00180a4ccba7',
        'ACTIVE', 1, current_timestamp);

insert into public.users ( first_name, last_name, phone_number, photo, password,
                           username, email, status, created_by, created_at)
values ( 'Doston', 'Matyakubov', '+9989986521231', 'image.jpg',
         '$2a$10$EoohCOxXLbxvudBb4T20z.8aCt3tiICdsF1HYxaIkD/CH7M05FTky', 'sdasd', 'adm43in@gmail.com', 'ACTIVE', 0,
         current_timestamp);
insert into public.users ( first_name, last_name, phone_number, photo, password,
                           username, email, status, created_by, created_at)
values ( 'Shohjahon', 'Rahmataliyev', '+998998652321', 'image.jpg',
         '$2a$10$EoohCOxXLbxvudBb4T20z.8aCt3tiICdsF1HYxaIkD/CH7M05FTky', 'afafdfdmin', 'admdfdsfin@gmail.com', 'ACTIVE', 0,
         current_timestamp);
insert into public.users ( first_name, last_name, phone_number, photo, password,
                           username, email, status, created_by, created_at)
values ( 'Nozim', 'Jurayev', '+998998652321', 'image.jpg',
         '$2a$10$EoohCOxXLbxvudBb4T20z.8aCt3tiICdsF1HYxaIkD/CH7M05FTky', 'admigfgfgfn', ',kjhjllk@gmail.com', 'ACTIVE', 0,
         current_timestamp);
insert into public.users ( first_name, last_name, phone_number, photo, password,
                           username, email, status, created_by, created_at)
values ( 'Davron', 'Mamarayimov', '+998998652321', 'image.jpg',
         '$2a$10$EoohCOxXLbxvudBb4T20z.8aCt3tiICdsF1HYxaIkD/CH7M05FTky', 'afadfaddmin', 'sdasfdsafad@gmail.com', 'ACTIVE', 0,
         current_timestamp);
