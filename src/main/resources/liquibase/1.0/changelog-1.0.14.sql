insert into public.localizations (id, key, lang, message)
values (1, 'invalid.token', 'EN', 'Invalid token'),
       (2, 'invalid.token', 'UZ', 'Noto’g’ri kalit'),
       (3, 'invalid.token', 'RU', 'Не верный ключ');
insert into public.localizations (id, key, lang, message)
values (4, 'user.does.not.exists', 'EN', 'User does not exists'),
       (5, 'user.does.not.exists', 'UZ', 'Foydalanuvchi mavjud emas'),
       (6, 'user.does.not.exists', 'RU', 'Пользователь не существует');
insert into public.localizations (id, key, lang, message)
values (7, 'username.exists.in.the.system', 'EN', 'Username exists in the system'),
       (8, 'username.exists.in.the.system', 'UZ', 'Foydalanuvchi nomi tizimda mavjud.'),
       (9, 'username.exists.in.the.system', 'RU', 'Имя пользователя существует в системе');
insert into public.localizations (id, key, lang, message)
values (10, 'email.exists.in.the.system', 'EN', 'Email exists in the system'),
       (11, 'email.exists.in.the.system', 'UZ', 'Elektron pochta tizimda mavjudt'),
       (12, 'email.exists.in.the.system', 'RU', 'Электронная почта существует в системе');
insert into public.localizations (id, key, lang, message)
values (13, 'incorrect.password', 'EN', 'Incorrect password'),
       (14, 'incorrect.password', 'UZ', 'Parol noto’g’ri kiritilgan'),
       (15, 'incorrect.password', 'RU', 'Неправильный пароль');
insert into public.localizations (id, key, lang, message)
values (16, 'data.not.found', 'EN', 'Data not found'),
       (17, 'data.not.found', 'UZ', 'Ma’lumot topilmadi'),
       (18, 'data.not.found', 'RU', 'Данные не найдены');
insert into public.localizations (id, key, lang, message)
values (19, 'internal.server.error', 'EN', 'Internal server error'),
       (20, 'internal.server.error', 'UZ', 'Serverning ichki xatoligi'),
       (21, 'internal.server.error', 'RU', 'Внутренняя ошибка сервера');
insert into public.localizations (id, key, lang, message)
values (22, 'invalid.data', 'EN', 'Invalid data'),
       (23, 'invalid.data', 'UZ', 'Ma’lumotlar noto’g’ri kiritilgan'),
       (24, 'invalid.data', 'RU', 'Неправильные данные');
insert into public.localizations (id, key, lang, message)
values (25, 'successWithObject', 'EN', 'Successfully'),
       (26, 'successWithObject', 'UZ', 'Muvafaqqiyatli'),
       (27, 'successWithObject', 'RU', 'Успешно');
insert into public.localizations (id, key, lang, message)
values (28, 'user.firstname.not.blank', 'EN', 'First name must not be empty'),
       (29, 'user.firstname.not.blank', 'UZ', 'Foydalanuvchi ismini kiriting'),
       (30, 'user.firstname.not.blank', 'RU', 'Введите имя пользователя');
insert into public.localizations (id, key, lang, message)
values (31, 'user.lastname.not.blank', 'EN', 'Last name must not be empty'),
       (32, 'user.lastname.not.blank', 'UZ', 'Foydalanuvchining familiyasini kiriting'),
       (33, 'user.lastname.not.blank', 'RU', 'Введите фамилию пользователя');
insert into public.localizations (id, key, lang, message)
values (34, 'user.phone.not.valid', 'EN', 'Phone number is not valid'),
       (35, 'user.phone.not.valid', 'UZ', 'Telefon raqami noto’g’ri'),
       (36, 'user.phone.not.valid', 'RU', 'Номер телефона недействителен');
insert into public.localizations (id, key, lang, message)
values (37, 'user.photo.not.valid', 'EN', 'Please upload valid photo. Photo must be jpg or jpeg formats.'),
       (38, 'user.photo.not.valid', 'UZ',
        'Iltimos, to’g’ri formatdagi rasm yuklang. Rasm jpg yoki jpeg formatlaridan birida bo’lishi kerak.'),
       (39, 'user.photo.not.valid', 'RU',
        'Пожалуйста, загрузите действительное фото. Фото должно быть в формате jpg или jpeg.');
insert into public.localizations (id, key, lang, message)
values (40, 'user.password.not.valid', 'EN', 'Enter valid password. Password must be have following characters:

* minimum 8 characters

* at least one upper case letter

* at least one lower case letter

* at least one digit

* at least one special character'),
       (41, 'user.password.not.valid', 'UZ', 'Iltimos, quyidagi qoidalarga javob beruvchi parol kiriting:

* kamida 8 ta belgidan iborat bo’lishi

* kamida bitta katta harf

* kamida bitta kichik harf

* kamida bitta raqam

* va kamida bitta maxsus belgidan iborat bo’lishi kerak'),
       (42, 'user.password.not.valid', 'RU', 'Введите действительный пароль. Пароль должен содержать следующие символы:

* минимум 8 символов

* минимум одна заглавная буква

* минимум одна строчная буква

* минимум одна цифра

* минимум один специальный символ');
insert into public.localizations (id, key, lang, message)
values (43, 'user.username.not.valid', 'EN', 'Username must be valid and unique'),
       (44, 'user.username.not.valid', 'UZ', 'Foydalanuvchi nomi to’g’ri va tizimda mavjud bo’lmasligi kerak.'),
       (45, 'user.username.not.valid', 'RU', 'Имя пользователя должно быть действительным и уникальным');
insert into public.localizations (id, key, lang, message)
values (46, 'auth.usernameoremail.not.valid', 'EN', 'Username or email is not valid'),
       (47, 'auth.usernameoremail.not.valid', 'UZ', 'Foydalanuvchi nomi yoki elektron pochta noto’g’ri kiritilgan'),
       (48, 'auth.usernameoremail.not.valid', 'RU', 'Имя пользователя или электронная почта недействительны');
insert into public.localizations (id, key, lang, message)
values (49, 'auth.password.not.valid', 'EN', 'Password is not valid'),
       (50, 'auth.password.not.valid', 'UZ', 'Parol noto’g’ri kiritilgan'),
       (51, 'auth.password.not.valid', 'RU', 'Пароль недействителен');
insert into public.localizations (id, key, lang, message)
values (52, 'user.email.not.valid', 'EN', 'Email is not valid'),
       (53, 'user.email.not.valid', 'UZ', 'Elektron pochta noto’g’ri kiritilgan'),
       (54, 'user.email.not.valid', 'RU', 'Электронная почта не является допустимым');
insert into public.localizations (id, key, lang, message)
values (55, 'auth.account.pending', 'EN', 'You cannot use the system. Wait for the system administrator to accept'),
       (56, 'auth.account.pending', 'UZ', 'Siz tizimdan foydalana olmaysiz. Tizim ma''muri qabul qilishini kuting'),
       (57, 'auth.account.pending', 'RU',
        'Вы не можете использовать систему. Подождите, пока системный администратор примет');
insert into public.localizations (id, key, lang, message)
values (58, 'auth.account.blocked', 'EN', 'You cannot log in. Your account has been blocked'),
       (59, 'auth.account.blocked', 'UZ', 'Siz tizimga kira olmaysiz. Hisobingiz bloklangan'),
       (60, 'auth.account.blocked', 'RU', 'Вы не можете войти. Ваш аккаунт заблокирован.');
insert into public.localizations (id, key, lang, message)
values (61, 'forbidden', 'EN',
        'You do not have enough privileges to access this resource. Contact with your adiministrator.'),
       (62, 'forbidden', 'UZ',
        'Ushbu resursdan foydalanish uchun sizda ruxsat mavjud emas. Iltmos, administrator bilan bog’laning.'),
       (63, 'forbidden', 'RU',
        'У вас недостаточно прав для доступа к этому ресурсу. Свяжитесь с вашим администратором');
insert into public.localizations (id, key, lang, message)
values (64, 'max.size.file', 'EN',
        'Larger than specified file size. Please upload a smaller file.'),
       (65, 'max.size.file', 'UZ',
        'Belgilangan fayl miqdoridan katta. Iltimos kichikroq fayl yuklang.'),
       (66, 'max.size.file', 'RU',
        'Размер файла больше указанного. Загрузите файл меньшего размера.');
