insert into mpa_rate (mpa_rate_name, mpa_rate_description)
select 'G',
       'у фильма нет возрастных ограничений'
    where not exists (select 1 from mpa_rate
        where mpa_rate_description = 'G'
            and mpa_rate_description = 'у фильма нет возрастных ограничений');
insert into mpa_rate (mpa_rate_name, mpa_rate_description)
select 'PG',
       'детям рекомендуется смотреть фильм с родителями'
    where not exists (select 1 from mpa_rate
        where mpa_rate_description = 'PG'
            and mpa_rate_description = 'детям рекомендуется смотреть фильм с родителями');
insert into mpa_rate (mpa_rate_name, mpa_rate_description)
select 'PG-13',
       'детям до 13 лет просмотр не желателен'
where not exists (select 1 from mpa_rate
                  where mpa_rate_description = 'PG-13'
                    and mpa_rate_description = 'детям до 13 лет просмотр не желателен');
insert into mpa_rate (mpa_rate_name, mpa_rate_description)
select 'R',
       'лицам до 17 лет просматривать фильм можно только в присутствии взрослого'
where not exists (select 1 from mpa_rate
                  where mpa_rate_description = 'R'
                    and mpa_rate_description = 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
insert into mpa_rate (mpa_rate_name, mpa_rate_description)
select 'NC-17',
       'лицам до 18 лет просмотр запрещён'
where not exists (select 1 from mpa_rate
                  where mpa_rate_description = 'NC-17'
                    and mpa_rate_description = 'лицам до 18 лет просмотр запрещён');

insert into genre(genre_name)
select 'Комедия'
    where not exists (select 1 from genre
                           where genre_name = 'Комедия');
insert into genre(genre_name)
select 'Драма'
    where not exists (select 1 from genre
                           where genre_name = 'Драма');
insert into genre(genre_name)
select 'Мультфильм'
    where not exists (select 1 from genre
                           where genre_name = 'Мультфильм');
insert into genre(genre_name)
select 'Триллер'
    where not exists (select 1 from genre
                           where genre_name = 'Триллер');
insert into genre(genre_name)
select 'Документальный'
    where not exists (select 1 from genre
                           where genre_name = 'Документальный');
insert into genre(genre_name)
select 'Боевик'
    where not exists (select 1 from genre
                           where genre_name = 'Боевик');