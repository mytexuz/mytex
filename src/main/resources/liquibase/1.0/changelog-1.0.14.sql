insert into public.localizations (key, lang, message)
values ('invalid.token', 'EN', 'Invalid token'),
       ('invalid.token', 'UZ', 'Noto’g’ri kalit'),
       ('invalid.token', 'RU', 'Не верный ключ');
insert into public.localizations (key, lang, message)
values ('user.does.not.exists', 'EN', 'User does not exists'),
       ('user.does.not.exists', 'UZ', 'Foydalanuvchi mavjud emas'),
       ('user.does.not.exists', 'RU', 'Пользователь не существует');
insert into public.localizations (key, lang, message)
values ('username.exists.in.the.system', 'EN', 'Username exists in the system'),
       ('username.exists.in.the.system', 'UZ', 'Foydalanuvchi nomi tizimda mavjud.'),
       ('username.exists.in.the.system', 'RU', 'Имя пользователя существует в системе');
insert into public.localizations (key, lang, message)
values ('email.exists.in.the.system', 'EN', 'Email exists in the system'),
       ('email.exists.in.the.system', 'UZ', 'Elektron pochta tizimda mavjudt'),
       ('email.exists.in.the.system', 'RU', 'Электронная почта существует в системе');
insert into public.localizations (key, lang, message)
values ('incorrect.password', 'EN', 'Incorrect password'),
       ('incorrect.password', 'UZ', 'Parol noto’g’ri kiritilgan'),
       ('incorrect.password', 'RU', 'Неправильный пароль');
insert into public.localizations (key, lang, message)
values ('data.not.found', 'EN', 'Data not found'),
       ('data.not.found', 'UZ', 'Ma’lumot topilmadi'),
       ('data.not.found', 'RU', 'Данные не найдены');
insert into public.localizations (key, lang, message)
values ('internal.server.error', 'EN', 'Internal server error'),
       ('internal.server.error', 'UZ', 'Serverning ichki xatoligi'),
       ('internal.server.error', 'RU', 'Внутренняя ошибка сервера');
insert into public.localizations (key, lang, message)
values ('invalid.data', 'EN', 'Invalid data'),
       ('invalid.data', 'UZ', 'Ma’lumotlar noto’g’ri kiritilgan'),
       ('invalid.data', 'RU', 'Неправильные данные');
insert into public.localizations (key, lang, message)
values ('success', 'EN', 'Successfully'),
       ('success', 'UZ', 'Muvafaqqiyatli'),
       ('success', 'RU', 'Успешно');
insert into public.localizations (key, lang, message)
values ('user.firstname.not.blank', 'EN', 'First name must not be empty'),
       ('user.firstname.not.blank', 'UZ', 'Foydalanuvchi ismini kiriting'),
       ('user.firstname.not.blank', 'RU', 'Введите имя пользователя');
insert into public.localizations (key, lang, message)
values ('user.lastname.not.blank', 'EN', 'Last name must not be empty'),
       ('user.lastname.not.blank', 'UZ', 'Foydalanuvchining familiyasini kiriting'),
       ('user.lastname.not.blank', 'RU', 'Введите фамилию пользователя');
insert into public.localizations (key, lang, message)
values ('user.phone.not.valid', 'EN', 'Phone number is not valid'),
       ('user.phone.not.valid', 'UZ', 'Telefon raqami noto’g’ri'),
       ('user.phone.not.valid', 'RU', 'Номер телефона недействителен');
insert into public.localizations (key, lang, message)
values ('user.photo.not.valid', 'EN', 'Please upload valid photo. Photo must be jpg or jpeg formats.'),
       ('user.photo.not.valid', 'UZ',
        'Iltimos, to’g’ri formatdagi rasm yuklang. Rasm jpg yoki jpeg formatlaridan birida bo’lishi kerak.'),
       ('user.photo.not.valid', 'RU',
        'Пожалуйста, загрузите действительное фото. Фото должно быть в формате jpg или jpeg.');
insert into public.localizations (key, lang, message)
values ('user.password.not.valid', 'EN', 'Enter valid password. Password must be have following characters:

* minimum 8 characters

* at least one upper case letter

* at least one lower case letter

* at least one digit

* at least one special character'),
       ('user.password.not.valid', 'UZ', 'Iltimos, quyidagi qoidalarga javob beruvchi parol kiriting:

* kamida 8 ta belgidan iborat bo’lishi

* kamida bitta katta harf

* kamida bitta kichik harf

* kamida bitta raqam

* va kamida bitta maxsus belgidan iborat bo’lishi kerak'),
       ('user.password.not.valid', 'RU', 'Введите действительный пароль. Пароль должен содержать следующие символы:

* минимум 8 символов

* минимум одна заглавная буква

* минимум одна строчная буква

* минимум одна цифра

* минимум один специальный символ');
insert into public.localizations (key, lang, message)
values ('user.username.not.valid', 'EN', 'Username must be valid and unique'),
       ('user.username.not.valid', 'UZ', 'Foydalanuvchi nomi to’g’ri va tizimda mavjud bo’lmasligi kerak.'),
       ('user.username.not.valid', 'RU', 'Имя пользователя должно быть действительным и уникальным');
insert into public.localizations (key, lang, message)
values ('auth.usernameoremail.not.valid', 'EN', 'Username or email is not valid'),
       ('auth.usernameoremail.not.valid', 'UZ', 'Foydalanuvchi nomi yoki elektron pochta noto’g’ri kiritilgan'),
       ('auth.usernameoremail.not.valid', 'RU', 'Имя пользователя или электронная почта недействительны');
insert into public.localizations (key, lang, message)
values ('auth.password.not.valid', 'EN', 'Password is not valid'),
       ('auth.password.not.valid', 'UZ', 'Parol noto’g’ri kiritilgan'),
       ('auth.password.not.valid', 'RU', 'Пароль недействителен');
insert into public.localizations (key, lang, message)
values ('user.email.not.valid', 'EN', 'Email is not valid'),
       ('user.email.not.valid', 'UZ', 'Elektron pochta noto’g’ri kiritilgan'),
       ('user.email.not.valid', 'RU', 'Электронная почта не является допустимым');
insert into public.localizations (key, lang, message)
values ('auth.account.pending', 'EN', 'You cannot use the system. Wait for the system administrator to accept'),
       ('auth.account.pending', 'UZ', 'Siz tizimdan foydalana olmaysiz. Tizim ma''muri qabul qilishini kuting'),
       ('auth.account.pending', 'RU',
        'Вы не можете использовать систему. Подождите, пока системный администратор примет');
insert into public.localizations (key, lang, message)
values ('auth.account.blocked', 'EN', 'You cannot log in. Your account has been blocked'),
       ('auth.account.blocked', 'UZ', 'Siz tizimga kira olmaysiz. Hisobingiz bloklangan'),
       ('auth.account.blocked', 'RU', 'Вы не можете войти. Ваш аккаунт заблокирован.');
insert into public.localizations (key, lang, message)
values ('forbidden', 'EN',
        'You do not have enough privileges to access this resource. Contact with your adiministrator.'),
       ('forbidden', 'UZ',
        'Ushbu resursdan foydalanish uchun sizda ruxsat mavjud emas. Iltmos, administrator bilan bog’laning.'),
       ('forbidden', 'RU',
        'У вас недостаточно прав для доступа к этому ресурсу. Свяжитесь с вашим администратором');
insert into public.localizations (key, lang, message)
values ('max.size.file', 'EN',
        'Larger than specified file size. Please upload a smaller file.'),
       ('max.size.file', 'UZ',
        'Belgilangan fayl miqdoridan katta. Iltimos kichikroq fayl yuklang.'),
       ('max.size.file', 'RU',
        'Размер файла больше указанного. Загрузите файл меньшего размера.');
insert into public.localizations (key, lang, message)
values ('session.disabled', 'EN',
        'Your session has been disabled. Please log in again'),
       ('session.disabled', 'UZ',
        'Seansingiz o‘chirildi. Iltimos, qayta kiring.'),
       ('session.disabled', 'RU',
        'Ваша учетная запись была временно приостановлена');
insert into public.localizations (key, lang, message)
values ('blocked.device.id.not.null', 'EN',
        'Device id cannot be empty'),
       ('blocked.device.id.not.null', 'UZ',
        'Qurilma identifikatori boʻsh boʻlishi mumkin emas'),
       ('blocked.device.id.not.null', 'RU',
        'Идентификатор устройства не может быть пустым');
insert into public.localizations (key, lang, message)
values ('device.already.blocked', 'EN',
        'Device is already blocked by - %s'),
       ('device.already.blocked', 'UZ',
        'Qurilma allaqachon - %s - tomonidan bloklangan'),
       ('device.already.blocked', 'RU',
        'Устройство уже заблокировано - %s');
insert into public.localizations (key, lang, message)
values ('device.already.unblocked', 'EN',
        'The device is already unblocked by - %s'),
       ('device.already.unblocked', 'UZ',
        'Qurilma allaqachon - %s - tomonidan blokdan chiqarilgan'),
       ('device.already.unblocked', 'RU',
        'Устройство уже разблокировано - %s');
insert into public.localizations (key, lang, message)
values ('device.blocked', 'EN',
        'The evice is blocked'),
       ('device.blocked', 'UZ',
        'Qurilma bloklangan'),
       ('device.blocked', 'RU',
        'Устройство заблокировано');
insert into public.localizations (key, lang, message)
values ('device.not.blocked', 'EN',
        'The device is not locked yet'),
       ('device.not.blocked', 'UZ',
        'Qurilma hali qulflanmagan'),
       ('device.not.blocked', 'RU',
        'Устройство еще не заблокированоо');
insert into public.localizations (key, lang, message)
values ('user.id.not.null', 'EN',
        'The user id cannot be null'),
       ('user.id.not.null', 'UZ',
        'Foydalanuvchi identifikatori null bo''lishi mumkin emas'),
       ('user.id.not.null', 'RU',
        'Идентификатор пользователя не может быть нулевым');
